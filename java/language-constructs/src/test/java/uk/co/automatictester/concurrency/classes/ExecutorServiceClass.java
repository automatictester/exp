package uk.co.automatictester.concurrency.classes;

import org.testng.annotations.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ExecutorServiceClass {

    private Runnable r = () -> {
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }
    };

    @Test
    public void execute() throws InterruptedException {

        ExecutorService service = null;
        try {
            service = Executors.newCachedThreadPool();
            service.execute(r);
            service.execute(r);
            service.execute(r);
            service.execute(r);
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("last statement");
    }

    @Test
    public void submit() {

        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            service.submit(r);
            service.submit(r);
            System.out.println("last statement");
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }

    @Test
    public void submitAndWaitToTerminate() throws InterruptedException {

        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            service.submit(r);
            System.out.println("last statement");
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
        service.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void submitAndWaitToFinish() throws ExecutionException, InterruptedException {

        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            Future<?> f = service.submit(r);
            f.get(); // will return null as r is Runnable not Callable
            System.out.println("last statement");
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }

    private Callable<Integer> c = () -> IntStream.rangeClosed(1, 100).sum();

    @Test
    public void submitAndWaitForResult() throws ExecutionException, InterruptedException {

        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            Future<Integer> f = service.submit(c);
            int i = f.get();
            System.out.println("Total: " + i);
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }
}
