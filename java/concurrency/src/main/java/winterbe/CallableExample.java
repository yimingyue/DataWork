package winterbe;

import java.util.concurrent.*;

/**
 * Created by ymyue on 9/28/16.
 */
public class CallableExample {
    public static void main(String[] args) {
        Callable<Integer> task = () -> {
            TimeUnit.SECONDS.sleep(2);
            return 123;
        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);

        System.out.println("Is future done? " + future.isDone());
        try {
            Integer result = future.get(1, TimeUnit.SECONDS);
            System.out.println("Is future done? "+ future.isDone());
            System.out.println("Result: "+ result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (executor.isShutdown())
                System.out.println("All non-finish tasks are cancelled.");
            executor.shutdownNow();
        }

    }
}
