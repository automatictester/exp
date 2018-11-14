package uk.co.automatictester.arraysintersection.test;

import org.testng.annotations.Test;
import uk.co.automatictester.arraysintersection.ArrayIntersection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;

public class ArraysIntersectionTest {

    @Test
    public void testSame() {
        int[] a = new int[]{1,2,3,4,5};
        int[] b = new int[]{5,1,2,3,4};
        List<Integer> intersection = ArrayIntersection.find(a, b);
        assertEquals(intersection, Arrays.asList(1,2,3,4,5));
    }

    @Test
    public void testBothEmpty() {
        int[] a = new int[]{};
        int[] b = new int[]{};
        List<Integer> intersection = ArrayIntersection.find(a, b);
        assertEquals(intersection, Collections.emptyList());
    }

    @Test
    public void testOneEmpty() {
        int[] a = new int[]{1,2,3,4,5};
        int[] b = new int[]{};
        List<Integer> intersection = ArrayIntersection.find(a, b);
        assertEquals(intersection, Collections.emptyList());
    }

    @Test
    public void testDiff() {
        int[] a = new int[]{1,2,5,4,3};
        int[] b = new int[]{6,7,10,9,8};
        List<Integer> intersection = ArrayIntersection.find(a, b);
        assertEquals(intersection, Collections.emptyList());
    }

    @Test
    public void testOverlap() {
        int[] a = new int[]{1,5,3,4,2};
        int[] b = new int[]{5,10,1};
        List<Integer> intersection = ArrayIntersection.find(a, b);
        assertEquals(intersection, Arrays.asList(1,5));
    }
}
