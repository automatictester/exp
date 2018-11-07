package uk.co.automatictester.structural.decorator.decorators;

import uk.co.automatictester.structural.decorator.components.File;

public class WritableFile extends UsableFile {

    public WritableFile(File file) {
        decoratedFile = file;
    }

    @Override
    public String getAttributes() {
        if (decoratedFile.getAttributes().contains("w")) {
            return decoratedFile.getAttributes();
        } else {
            return decoratedFile.getAttributes() + "w";
        }
    }
}
