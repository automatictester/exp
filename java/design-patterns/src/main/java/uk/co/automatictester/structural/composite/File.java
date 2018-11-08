package uk.co.automatictester.structural.composite;

public class File {

    private String name;
    private int size;

    protected File(String name) {
        this.name = name;
    }

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public int size() {
        return size;
    }
}
