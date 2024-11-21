import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main{
    private static final int NUM_THREADS = 1000;
    private static final int MAX_VALUE = 1_000_000_000;

    public static void main(String[] args)
    {
        long start = System.nanoTime();

        ExecutorService service = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Long>> results = new ArrayList<>();

        int range = MAX_VALUE / NUM_THREADS;

        for (int i = 0; i < NUM_THREADS; i++)
        {
            int lowerLimit = i * range + 1;
            int upperLimit = (i + 1) * range;

            Callable<Long> task = () ->
            {
                long partialCount = 0;
                for (int j = lowerLimit; j <= upperLimit; j++)
                {
                    partialCount++;
                }
                return partialCount;
            };

            results.add(service.submit(task));
        }

        long combinedCount = 0;
        for (Future<Long> result : results)
        {
            try
            {
                combinedCount += result.get();
            } catch (InterruptedException | ExecutionException ex)
            {
                System.err.println("Error obtaining thread result: " + ex.getMessage());
            }
        }

        service.shutdown();
        long finish = System.nanoTime();
        long timeTaken = (finish - start) / 1_000_000;

        System.out.println("Count" + combinedCount);
        System.out.println("Time: " + timeTaken + " ms");


        long count = 0;
        start = System.nanoTime();

        for (int i = 0; i < 1000000000; i++) {
            count++;
        }

        finish = System.nanoTime();
        timeTaken = (finish - start) / 1_000_000;
        System.out.println("Time: " + timeTaken + " ms");


        int numThreads = Runtime.getRuntime().availableProcessors(); // Dynamic thread count
        ExecutorService servicer = Executors.newFixedThreadPool(numThreads);

        start = System.nanoTime();
        List<Future<Long>> res = new ArrayList<>();

        range = MAX_VALUE / numThreads; // Divide work among threads

        for (int i = 0; i < numThreads; i++) {
            int lowerLimit = i * range + 1;
            int upperLimit = (i == numThreads - 1) ? MAX_VALUE : (i + 1) * range;

            Callable<Long> task = () -> {
                long partialCount = 0;
                for (int j = lowerLimit; j <= upperLimit; j++) {
                    partialCount++;
                }
                return partialCount;
            };

            res.add(servicer.submit(task));
        }

        combinedCount = 0;
        for (Future<Long> result : res) {
            try {
                combinedCount += result.get(); // Retrieve result
            } catch (InterruptedException | ExecutionException ex) {
                System.err.println("Error obtaining thread result: " + ex.getMessage());
            }
        }

        servicer.shutdown(); // Shut down the executor
        finish = System.nanoTime();
        timeTaken = (finish - start) / 1_000_000;
    }
}