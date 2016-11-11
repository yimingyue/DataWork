package me.yiming.avgusertime;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class OutputAggregator {
    private Map<String, UserSpent> map;

    public OutputAggregator() {
        map = new HashMap<>();
    }

    public void aggregateFile(String fileName) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = br.readLine()) != null) {
                aggregateLine(line);
            }
            br.close();
        } finally {
            in.close();
        }
    }

    private void aggregateLine(String line) {
        String[] strings = line.split(",");
        if (strings.length != 3)
            throw new RuntimeException("Invalid internal file format for line " + line);
        aggregate(strings[0], Long.parseLong(strings[1]), Long.parseLong(strings[2]));
    }

    private void aggregate(String userId, long timeSpent, long counter) {
        if (!map.containsKey(userId))
            map.put(userId, new UserSpent(userId));
        map.get(userId).aggregate(timeSpent, counter);
    }

    public void appendToResult(String fileName) throws IOException {
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw);) {
            for (Map.Entry<String, UserSpent> entry : map.entrySet()) {
                out.println(entry.getKey() + "," + entry.getValue().getAverageTimeSpent());
            }

        } catch (IOException ioe) {
            System.err.println("IOException for file " + fileName);
            throw ioe;
        }
    }
}
