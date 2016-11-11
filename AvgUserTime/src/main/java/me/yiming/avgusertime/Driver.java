package me.yiming.avgusertime;

import java.io.*;

/**
 * The Driver class for the project.
 */
public class Driver {
    public static void main(String[] args) throws IOException {
        checkParameters(args);
        String inputFileName = args[0];
        String outputFileName = args[1];
        String workingDirectory = args[2];
        String strategy = args.length > 3 ? args[3] : "default";
        StrategyUtils.setStrategy(strategy);
        prepareWorkingDirectory(workingDirectory);
        deleteOutputFile(outputFileName);
        new LogProcessor(inputFileName, workingDirectory).processInput();
        new Aggregator(workingDirectory, outputFileName).aggregate();
        System.out.println("Output is generated at " + outputFileName);
    }

    private static void checkParameters(String[] args) {
        if (args.length == 0) {
            printUsage();
            System.exit(0);
        } else if (args.length < 3 || args.length > 4) {
            System.err.println("Invalid number of input parameters!");
            printUsage();
            System.exit(-1);
        }
    }

    private static void prepareWorkingDirectory(String workingDirectory) {
        File workingDir = new File(workingDirectory);
        if (!workingDir.exists()) {
            workingDir.mkdir();
        } else {
            String files[] = workingDir.list();
            for (String temp : files) {
                File fileDelete = new File(workingDir, temp);
                fileDelete.delete();
            }
        }
    }

    private static void deleteOutputFile(String outputFileName) {
        File file = new File(outputFileName);
        if (file.exists() && !file.delete()) {
            throw new RuntimeException("Can not delete output file.");
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar target/avgusertime-1.0.jar <inputFilePath> <outputFilePath> <workingDirectory> <strategy>\n" +
        "Caution! The working directory will be cleaned!\n" +
        "strategy param is optional and can be left out. If specifies, there are four options for it:\n" +
                "\t1. drop - drop strategy will drop the unmatched open or close event\n" +
                "\t2. average - average will use the average time stamp between two consecutive open or close event as missing the close or open time\n" +
                "\t3. max - max will use the new open time stamp for the missing close or the last close time as missing open" + 
                "\t4. default - the same as leave it blank. Use the same logic as drop option in the current version\n" +
        "example: # java -jar target/avgusertime-1.0.jar input.csv output.csv /tmp/avgusertime drop\n");
    }
}
