package uk.co.automatictester.decorator.decorators;

import uk.co.automatictester.decorator.components.File;

@SuppressWarnings("WeakerAccess")
public abstract class UsableFile implements File {

    protected File decoratedFile;

    @Override
    public abstract String getAttributes();
}
