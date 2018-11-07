package uk.co.automatictester.behavioral.templatemethod;

public class AlgorithmTwo extends Algorithm {

    public AlgorithmTwo(int i) {
        this.i = i;
    }

    @Override
    protected void stepTwo() {
        i = i * 12 / 4 + 5;
    }
}
