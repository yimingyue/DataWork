package me.yiming.avgusertime;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * This class is for generating very large test data
 */

public class TestDataGenerator {
    public static void main(String[] args) {
//        generateLargeRandomTestFile();
        generateMediumTestFile();
    }

    private static void generateLargeRandomTestFile() {
        long records = 1000000000L; // 1 billion records
        int users = 100000000; // 100 million users, each user has 10 record on average
        String outputFileName = "largeTestFile.csv";
        boolean[] b = new boolean[users];

        long timestamp = 1474441200;
        Random random = new Random();
        try (FileWriter fw = new FileWriter(outputFileName, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw);) {
            for (long i = 0; i < records; i++) {
                timestamp += random.nextInt(3);
                int userId = random.nextInt(users);
                out.println(userId + "," + timestamp + "," + (b[userId] ? "close" : "open"));
                b[userId] = !b[userId];
            }
        } catch (IOException ioe) {
            System.err.println("IOException for file " + outputFileName);
            throw new RuntimeException(ioe);
        }
    }

    private static void generateMediumTestFile() {
        long records = 10000000L; // 100M billion records
        int users = 1000000; // 1 million users, each user has 10 record on average
        String outputFileName = "mediumTestFile.csv";
        boolean[] b = new boolean[users];
        long timestamp = 1474441200;
        final long[] timestamps = new long[users];
        Random random = new Random();

        PriorityQueue<Integer> pq = new PriorityQueue<>(users, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (timestamps[o1] == timestamps[o2])
                    return 0;
                return timestamps[o1] < timestamps[o2]? -1 : 1;
            }
        });
        for (int i = 0; i < users; i++) {
            timestamps[i] = timestamp + random.nextInt(1000);
            pq.offer(i);
        }

        try (FileWriter fw = new FileWriter(outputFileName, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw);) {
            for (long i = 0; i < records; i++) {
                int id = pq.poll();
                String userId = String.valueOf(id);
                out.println(userId + "," + timestamps[id] + "," + (b[id] ? "close" : "open"));
                b[id] = !b[id];
                timestamps[id] += random.nextInt(id/200*2+2) + 3;
                pq.offer(id);
            }
        } catch (IOException ioe) {
            System.err.println("IOException for file " + outputFileName);
            throw new RuntimeException(ioe);
        }
    }
}
