package uk.co.automatictester.java11;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FilesTest {

    private static final Path PATH = Paths.get("sample-file.txt");
    private static final String TEXT = "abc";

    @Test
    public void readString() throws IOException {
        String content = Files.readString(PATH);
        assertThat(content, equalTo(TEXT));
    }

    @Test
    public void writeString() throws IOException {
        Path writePath = Files.writeString(PATH, TEXT);
        assertThat(writePath, equalTo(PATH));
    }
}
