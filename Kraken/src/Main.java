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
        
    }
}