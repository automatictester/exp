package uk.co.automatictester.decorator;

import org.testng.annotations.Test;
import uk.co.automatictester.decorator.components.File;
import uk.co.automatictester.decorator.components.SimpleFile;
import uk.co.automatictester.decorator.decorators.ReadableFile;
import uk.co.automatictester.decorator.decorators.WritableFile;

import static org.testng.Assert.assertEquals;

public class FileTest {

    @Test
    public void testReadReadWrite() {
        File file = new SimpleFile();
        String attribs = file.getAttributes();
        assertEquals(attribs, "");

        ReadableFile readOnlyFile = new ReadableFile(file);
        attribs = readOnlyFile.getAttributes();
        assertEquals(attribs, "r");

        WritableFile readWriteFile = new WritableFile(readOnlyFile);
        attribs = readWriteFile.getAttributes();
        assertEquals(attribs, "rw");
    }

    @Test
    public void testWriteReadWrite() {
        File file = new SimpleFile();
        String attribs = file.getAttributes();
        assertEquals(attribs, "");

        WritableFile writeOnlyFile = new WritableFile(file);
        attribs = writeOnlyFile.getAttributes();
        assertEquals(attribs, "w");

        ReadableFile readWriteFile = new ReadableFile(writeOnlyFile);
        attribs = readWriteFile.getAttributes();
        assertEquals(attribs, "rw");
    }

    @Test
    public void testReadWrite() {
        File file = new SimpleFile();
        String attribs = file.getAttributes();
        assertEquals(attribs, "");

        ReadableFile readOnlyFile = new ReadableFile(file);
        attribs = readOnlyFile.getAttributes();
        assertEquals(attribs, "r");

        WritableFile writeOnlyFile = new WritableFile(file);
        attribs = writeOnlyFile.getAttributes();
        assertEquals(attribs, "w");
    }
}
