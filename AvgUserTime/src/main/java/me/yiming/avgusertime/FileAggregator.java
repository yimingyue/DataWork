package me.yiming.avgusertime;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the core class to do the aggregation of the user - timestamp pairs. 
 * It load the intermediate files one by one. 
 * For each file, allocate a HashMap to track userId - timestamp and another HashMap to track the userId - userSpent. 
 * It gets the timeSpent of two consecutive timestamps and aggregate it together. 
 * After processing all the records in one intermediate file, it appends the result to the output file
 */
public class FileAggregator {
    private Map<String, Long> map;
    private String fileName;
    private Map<String, UserSpent> visits;
    private String outputFileName;

    public FileAggregator(String inputFileName, String outputFileName) {
        this.fileName = inputFileName;
        map = new HashMap<>();
        this.outputFileName = outputFileName;
        visits = new HashMap<>();
    }

    public void aggregate() {
        aggregateInput();
        appendToOutput();
    }

    private void aggregateInput() {
        FileInputStream in = null;
        try {
            in = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = br.readLine()) != null) {
                aggregateLine(line);
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void appendToOutput() {
        try (FileWriter fw = new FileWriter(outputFileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw);) {
            for (UserSpent visit : visits.values())
                out.println(visit);
        } catch (IOException ioe) {
            System.err.println("IOException for file " + outputFileName);
            throw new RuntimeException(ioe);
        }
    }

    private void aggregateLine(String line) {
        String[] strs = line.split(",");
        String userId = strs[0];
        if (strs.length != 2) {
            System.err.println("Invalid line: " + line);
            return;
        }
        long timestamp = Long.parseLong(strs[1]);

        // assumption: each user has at least two events
        if (!map.containsKey(userId)) {
            map.put(userId, timestamp);
            visits.put(userId, new UserSpent(userId));
        } else {
            visits.get(userId).aggregate(StrategyUtils.getAvgTime(map.get(userId), timestamp));
            map.put(userId, timestamp);
        }
    }
}
