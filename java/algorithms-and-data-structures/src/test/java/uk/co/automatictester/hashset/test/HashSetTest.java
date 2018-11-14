package uk.co.automatictester.hashset.test;

import org.testng.annotations.Test;
import uk.co.automatictester.hashset.HashSet;

import static org.testng.Assert.*;

public class HashSetTest {

    @Test
    public void testAdd() {
        HashSet<String> set = new HashSet<>();
        assertEquals(set.size(), 0);

        boolean added = set.add("a");
        assertEquals(set.size(), 1);
        assertTrue(added);

        added = set.add("a");
        assertEquals(set.size(), 1);
        assertFalse(added);

        added = set.add("b");
        assertEquals(set.size(), 2);
        assertTrue(added);

        added = set.add("c");
        assertEquals(set.size(), 3);
        assertTrue(added);
    }

    @Test
    public void testRehash() {
        HashSet<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        assertEquals(set.size(), 3);

        set.add("d");
        assertEquals(set.size(), 4);
        set.add("e");
        set.add("f");

        set.add("g");
        assertEquals(set.size(), 7);
    }

    @Test
    public void testHashCollision() {
        HashSet<String> set = new HashSet<>();
        set.add("AaAaAa");
        set.add("BBBBBB");
        set.add("BBAaAa");
        set.add("AaBBAa");
        set.add("AaAaBB");
        set.add("BBBBAa");
        set.add("AaBBBB");
        set.add("BBAaBB");
        assertEquals(set.size(), 8);

        set.add("z");
        assertEquals(set.size(), 9);
    }

    @Test
    public void testContains() {
        HashSet<String> set = new HashSet<>();
        set.add("AaAaAa");
        assertTrue(set.contains("AaAaAa"));
        assertFalse(set.contains("BBBBBB"));

        set.add("BBBBBB");
        assertTrue(set.contains("BBBBBB"));
        assertFalse(set.contains("BBAaAa"));

        set.add("z");
        assertTrue(set.contains("z"));
        assertFalse(set.contains("x"));
    }

    @Test
    public void testRemove() {
        HashSet<String> set = new HashSet<>();
        set.add("AaAaAa");
        set.add("BBBBBB");
        set.add("BBAaAa");
        set.add("AaBBAa");
        set.add("z");
        assertEquals(set.size(), 5);

        boolean removed = set.remove("AaAaAa");
        assertTrue(removed);
        assertEquals(set.size(), 4);

        removed = set.remove("BBBBBB");
        assertTrue(removed);
        assertEquals(set.size(), 3);

        removed = set.remove("BBAaAa");
        assertTrue(removed);
        assertEquals(set.size(), 2);

        removed = set.remove("z");
        assertTrue(removed);
        assertEquals(set.size(), 1);

        removed = set.remove("BBAaAa");
        assertFalse(removed);
        assertEquals(set.size(), 1);
    }

    @Test
    public void testClear() {
        HashSet<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        set.clear();
        assertEquals(set.size(), 0);

        set.add("x");
        assertEquals(set.size(), 1);
    }
}
