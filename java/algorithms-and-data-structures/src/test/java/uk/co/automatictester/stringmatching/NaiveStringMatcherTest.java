package uk.co.automatictester.stringmatching;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.testng.Assert.*;

public class NaiveStringMatcherTest {

    @Test
    public void testMatch() {
        assertEquals(NaiveStringMatcher.match("abc", "abc"), Collections.singletonList(0));
        assertEquals(NaiveStringMatcher.match("abc", "abcde"), Collections.singletonList(0));
        assertEquals(NaiveStringMatcher.match("abc", "abd"), new ArrayList<>());
        assertEquals(NaiveStringMatcher.match("abcde", "abc"), new ArrayList<>());
        assertEquals(NaiveStringMatcher.match("abc", "abcdeabc"), Arrays.asList(0, 5));
        assertEquals(NaiveStringMatcher.match("abc", "abcabc"), Arrays.asList(0, 3));
    }
}
