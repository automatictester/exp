package uk.co.automatictester.behavioral.command;

public class MinTimeCommand implements Command {

    private String transactionName;

    public MinTimeCommand(String transactionName) {
        this.transactionName = transactionName;
    }

    @Override
    public void execute() {
        int result = PerformanceTestResults.min(transactionName);
        String text = String.format("Min %s: %d", transactionName, result);
        System.out.println(text);
    }
}
