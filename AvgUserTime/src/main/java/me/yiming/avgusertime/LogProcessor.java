package me.yiming.avgusertime;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class read the large input large file line by line and write the user-timestamp pair to the intermediate output files.
 * For each line from the input file, it determin the id of the output file by the hashcode of the userId mod total file nunbers.
 * Also, it reduced the space usage of the intermidate files by store the timestamp as negative value for open actions and positive value for close actions.
 * This also simplifies the average time spent calculation.
 */

public class LogProcessor {
    private final String filePath;
    private String workingDirectory;
    private List<List<UserEvent>> lists;

    public LogProcessor(String filePath, String workingDirectory) {
        this.filePath = filePath;
        this.workingDirectory = workingDirectory;
        lists = new ArrayList<>(Constants.numOfTempFiles);
        for (int i = 0; i < Constants.numOfTempFiles; i++)
            lists.add(new ArrayList<UserEvent>());
    }

    public void processInput() throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = br.readLine()) != null) {
                UserEvent e = processLine(line);
                if (e == null)
                    continue;
                int id = getListNumber(e.getUserId());
                lists.get(id).add(e);
                if (lists.get(id).size() > Constants.maxListSize)
                    writeListToFile(id);
            }
            for (int i = 0; i < Constants.numOfTempFiles; i++)
                writeListToFile(i);
            br.close();
        } finally {
            in.close();
        }
    }

    private void writeListToFile(int id) {
        List<UserEvent> list = lists.get(id);
        lists.set(id, new ArrayList<UserEvent>());
        String fileName =  workingDirectory + "/" + id + ".csv";
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw);) {
            for (UserEvent userEvent : list)
                out.println(userEvent);
        } catch (IOException ioe) {
            System.err.println("IOException for file " + fileName);
            throw new RuntimeException(ioe);
        }
    }

    // function to determine which intermediate file the current userId - timestamp pair should be write into.
    private int getListNumber(String userId) {
        return (userId.hashCode() % Constants.numOfTempFiles + Constants.numOfTempFiles) % Constants.numOfTempFiles;
    }

    private UserEvent processLine(String line) {
        String[] strings = line.split(",");
        if (strings.length != 3)
            return null;

        long timestamp = 0;
        try {
            timestamp = Long.parseLong(strings[1]);
        } catch (NumberFormatException nfe) {
            return null;
        }

        if (strings[2].equalsIgnoreCase("open"))
            return new UserEvent(strings[0], -timestamp);
        else if (strings[2].equalsIgnoreCase("close"))
            return new UserEvent(strings[0], timestamp);
        else {
            System.out.println("Invalid action type: " + strings[2]);
            return null;
        }
    }
}
