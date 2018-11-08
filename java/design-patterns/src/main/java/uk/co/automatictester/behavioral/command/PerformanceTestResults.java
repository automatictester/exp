package uk.co.automatictester.behavioral.command;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PerformanceTestResults {

    private PerformanceTestResults() {}

    private static List<TestResult> results = new LinkedList<>();

    static {
        results.add(new TestResult("Login", 10));
        results.add(new TestResult("Login", 15));
        results.add(new TestResult("Login", 20));
        results.add(new TestResult("Logout", 40));
        results.add(new TestResult("Logout", 50));
        results.add(new TestResult("Logout", 60));
    }

    public static int min(String transactionName) {
        return results.stream()
                .filter(x -> x.transactionName.equals(transactionName))
                .map(x -> x.responseTime)
                .min(Comparator.naturalOrder()).get();
    }

    public static int max(String transactionName) {
        return results.stream()
                .filter(x -> x.transactionName.equals(transactionName))
                .map(x -> x.responseTime)
                .max(Comparator.naturalOrder()).get();
    }

    private static class TestResult {

        private String transactionName;
        private int responseTime;

        private TestResult(String transactionName, int responseTime) {
            this.transactionName = transactionName;
            this.responseTime = responseTime;
        }
    }
}
