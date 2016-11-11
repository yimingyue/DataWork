package me.yiming.avgusertime;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * This class does the heavy lifting for average user time spent computation. For each user,
 * it use a HashMap to store the timestamp of the last open action of the user.
 *
 * Whenever the map size is larger than a predefined threshold. It will clean it up by removing
 * some open timestamp which happened a long time (controlled by threshold).
 *
 * The class also maintains the userVisit information as a HashMap. The userVisit information
 * includes the total user spent time and the count of the user visits. Whenever this HashMap is
 * too large to fit in memory, it will be flushed to the disk files. The flush function will use
 * the hashcode of the userId to determine which file each record should go. In the end, all records
 * of the same user goes into the same file.
 */
public class AggregatorBkp {
    private Strategy strategy;
    private Map<String, Long> timestamps;
    private Map<String, UserVisit> userVisits;
    private String workingDirectory;
    private long currentTime = 0;

    public AggregatorBkp(Strategy strategy, String workingDirectory) {
        this.strategy = strategy;
        this.timestamps = new HashMap<String, Long>();
        this.userVisits = new HashMap<String, UserVisit>();
        this.workingDirectory = workingDirectory;
    }

    public void aggregate(Event e) {
        currentTime = e.getTimestamp();
        if (e.getActionType() == ActionType.OPEN) {
            aggregateOpen(e.getUserId(), e.getTimestamp());
        } else if (e.getActionType() == ActionType.CLOSE) {
            aggregateClose(e.getUserId(), e.getTimestamp());
        }
        if (timestamps.size() > Constants.maxTimestampMapSize)
            cleanupTimeStampMap();
        if (userVisits.size() >= Constants.maxUserMapSize) {
            Map<String, UserVisit> tmpMap = userVisits;
            userVisits = new HashMap<String, UserVisit>();
            writeToDisk(tmpMap);
        }
    }

    /**
     * Function to cleanup the timestamp map when it grows too large. This can be improved by starting a
     * background process to do this task. If there are still too many open timestamp in the map, throw
     * an exception with this information. Use the factor 0.8 to avoid the cleanup function get called too
     * many times before it throws the exception.
     */
    private void cleanupTimeStampMap() {
        for (String userId : timestamps.keySet()) {
            if (currentTime - timestamps.get(userId) > Constants.maxOpenTime) {
                timestamps.remove(userId);
            }
        }

        if (timestamps.size() > 0.8 * Constants.maxTimestampMapSize) {
            throw new IllegalStateException("Too many concurrent open events, try to reduce the maxOpenTime.");
        }
    }

    /**
     * The function to flush the userSpent map into disk. Again, this can be improved by using a separated thread
     * to do this task.
     */
    private void writeToDisk(Map<String, UserVisit> map) {
        // Transform the map to lists before writing to files. Avoid open too many files at the same time.
        List<List<UserVisit>> lists = new ArrayList<>();
        for (int i = 0; i < Constants.numOfTempFiles; i++)
            lists.add(new ArrayList<UserVisit>());
        for (Map.Entry<String, UserVisit> entry : map.entrySet()) {
            int fileNum = entry.getKey().hashCode() % Constants.numOfTempFiles;
            UserVisit userVisit = entry.getValue();
            userVisit.setUser(entry.getKey());
            lists.get(fileNum).add(userVisit);
        }

        for (int i = 0; i < Constants.numOfTempFiles; i++) {
            writeToFile(lists.get(i), workingDirectory + "/" + i + ".csv");
        }
    }

    public void aggregateOpen(String userId, long timestamp) {
        // missing close
        if (timestamps.containsKey(userId)) {
            if (strategy == Strategy.DROP) {
                ;   // do nothing for Drop strategy
            } else if (strategy == Strategy.AVERAGE) {
                // use average time between two open events as the close time
                aggregateClose(userId, (timestamps.get(userId) + timestamp) / 2);
            } else if (strategy == Strategy.LONGEST) {
                // use the timestamp of the second open event as the close time of the first event;
                aggregateClose(userId, timestamp);
            }
        }
        timestamps.put(userId, timestamp);
    }

    public void aggregateClose(String userId, long timestamp) {
        if (!timestamps.containsKey(userId)) {
            // the first event of this user is close
            // drop this event
        } else {
            if (timestamps.get(userId) < 0) {
                long timeSpent = timestamp + timestamps.get(userId);
                if (!userVisits.containsKey(userId))
                    userVisits.put(userId, new UserVisit());
                userVisits.get(userId).aggregate(timeSpent, 1L);
            } else { // missing open event
                if (strategy == Strategy.DROP) {
                    ;  // do nothing for drop strategy
                } else if (strategy == Strategy.AVERAGE) {
                    // use the average time between two closed events as the open time
                    aggregateOpen(userId, (timestamps.get(userId) + timestamp) / 2);
                    aggregateClose(userId, timestamp);
                } else if (strategy == Strategy.LONGEST) {
                    // use the timestamp of last close event as the open time
                    aggregateOpen(userId, timestamps.get(userId));
                    aggregateClose(userId, timestamp);
                }
            }
        }
        timestamps.put(userId, timestamp);
    }

    public void writeToDisk() throws IOException {
        writeToDisk(userVisits);
    }

    private void writeToFile(List<UserVisit> list, String fileName) {
        if (list.size() == 0)
            return;
        try (FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);) {
            for (UserVisit userVisit : list)
                out.println(userVisit);
        } catch (IOException ioe) {
            System.err.println("IOException for file " + fileName);
            throw new RuntimeException(ioe);
        }
    }
}
