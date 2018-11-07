package uk.co.automatictester.decorator.decorators;

import uk.co.automatictester.decorator.components.File;

@SuppressWarnings("WeakerAccess")
public class ReadableFile extends UsableFile {

    public ReadableFile(File file) {
        decoratedFile = file;
    }

    @Override
    public String getAttributes() {
        if (decoratedFile.getAttributes().contains("r")) {
            return decoratedFile.getAttributes();
        } else {
            return "r" + decoratedFile.getAttributes();
        }
    }
}
