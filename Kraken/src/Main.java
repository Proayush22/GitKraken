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

    }
}