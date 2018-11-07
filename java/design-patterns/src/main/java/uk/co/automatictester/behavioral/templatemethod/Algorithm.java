package uk.co.automatictester.behavioral.templatemethod;

public abstract class Algorithm {

    protected int i;

    public void execute() {
        stepOne();
        stepTwo();
        stepThree();
        show();
    }

    private void stepOne() {
        i++;
    }

    protected abstract void stepTwo();

    private void stepThree() {
        i = i * 4;
    }

    private void show() {
        System.out.println("Result: " + i);
    }
}
