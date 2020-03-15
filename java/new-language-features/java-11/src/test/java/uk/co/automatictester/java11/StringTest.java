package uk.co.automatictester.java11;

import org.testng.annotations.Test;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class StringTest {

    @Test
    public void isBlank() {
        // isBlank() -> isEmpty() == true (size = 0) OR contains only white space
        boolean isBlank = " ".isBlank();
        assertThat(isBlank, is(true));
    }

    @Test
    public void lines() {
        var lines = "a\nb\nc"
                .lines()
                .collect(Collectors.toList());

        assertThat(lines, hasSize(3));
    }

    @Test
    public void strip() {
        String stripped = " s ".strip(); // similar to trim(), but faster
        String strippedLeading = " s ".stripLeading();
        String strippedTrailing = " s ".stripTrailing();

        assertThat(stripped, equalTo("s"));
        assertThat(strippedLeading, equalTo("s "));
        assertThat(strippedTrailing, equalTo(" s"));
    }

    @Test
    public void repeat() {
        String repeat = "=".repeat(10);
        assertThat(repeat, equalTo("=========="));
    }
}
