import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by ymyue on 8/7/16.
 */
public class DesktopSearch {
    public static void startIndexing(File[] roots) {
        BlockingDeque<File> queue = new LinkedBlockingDeque<>(10000);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };
        for (File root : roots)
            new Thread(new FileCrawler(queue, fileFilter, root)).start();
        for (int i = 0; i < 100; i++)
            new Thread(new Indexer(queue)).start();
    }
}
