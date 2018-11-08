package uk.co.automatictester.behavioral.command;

public class MaxTimeCommand implements Command {

    private String transactionName;

    public MaxTimeCommand(String transactionName) {
        this.transactionName = transactionName;
    }

    @Override
    public void execute() {
        int result = PerformanceTestResults.max(transactionName);
        String text = String.format("Max %s: %d", transactionName, result);
        System.out.println(text);
    }
}
