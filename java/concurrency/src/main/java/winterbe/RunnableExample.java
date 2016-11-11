package winterbe;

import java.util.concurrent.TimeUnit;

/**
 * Created by ymyue on 9/28/16.
 */
public class RunnableExample {
    public static void main(String[] args) {
        Runnable task = () -> {
            try {
                String threadName = Thread.currentThread().getName();
                System.out.println("Foo " + threadName);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + threadName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        task.run();

        Thread thread = new Thread(task);
        thread.start();
        System.out.println("Done!");
    }
}
