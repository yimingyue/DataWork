package me.yiming.avgusertime;

/**
 * This class reads the files from the working directory and does aggregation for each file, append the result to the outputFile.
 */
public class Aggregator {
    private final String workingDirectory;
    private final String outputFileName;

    public Aggregator(String workingDirectory, String outputFileName) {
        this.workingDirectory = workingDirectory;
        this.outputFileName = outputFileName;
    }

    public void aggregate() {
        for (int i = 0; i < Constants.numOfTempFiles; i++) {
            String fileName =  workingDirectory + "/" + i + ".csv";
            new FileAggregator(fileName, outputFileName).aggregate();
        }
    }
}
