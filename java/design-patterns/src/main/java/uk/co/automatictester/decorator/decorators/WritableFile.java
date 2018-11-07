package uk.co.automatictester.decorator.decorators;

import uk.co.automatictester.decorator.components.File;

@SuppressWarnings("WeakerAccess")
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
