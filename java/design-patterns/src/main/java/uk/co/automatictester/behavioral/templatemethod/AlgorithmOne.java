package uk.co.automatictester.behavioral.templatemethod;

public class AlgorithmOne extends Algorithm {

    public AlgorithmOne(int i) {
        this.i = i;
    }

    @Override
    protected void stepTwo() {
        i = i * 10 / 2 + 5;
    }
}
