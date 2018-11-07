package uk.co.automatictester.structural.decorator.decorators;

import uk.co.automatictester.structural.decorator.components.File;

public abstract class UsableFile implements File {

    protected File decoratedFile;

    @Override
    public abstract String getAttributes();
}
