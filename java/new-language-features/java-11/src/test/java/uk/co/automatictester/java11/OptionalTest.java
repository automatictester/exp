package uk.co.automatictester.java11;

import org.testng.annotations.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SuppressWarnings("ALL")
public class OptionalTest {

    @Test
    public void isEmpty() {
        var optional = Optional.empty();
        assertThat(optional.isEmpty(), is(true)); // opposite to isPresent()
    }
}
