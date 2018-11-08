package uk.co.automatictester.structural.composite;

import java.util.HashSet;
import java.util.Set;

public class Dir extends File {

    private Set<File> content = new HashSet<>();

    public Dir(String name) {
        super(name);
    }

    public void add(File file) {
        content.add(file);
    }

    @Override
    public int size() {
        int size = 0;
        for (File item : content) {
            size += item.size();
        }
        return size;
    }
}
