package uk.co.automatictester.java12;

import org.testng.annotations.Test;

import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class StringTest {

    @Test
    public void indent() {
        String indented = "abc".indent(3);
        assertThat(indented, equalTo("   abc\n"));
    }

    @Test
    public void transform() {
        Function<String, String> f = (var s) -> s.replace("a", "x");
        String transformed = "abc".transform(f);
        assertThat(transformed, equalTo("xbc"));
    }
}
