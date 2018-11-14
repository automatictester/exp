package uk.co.automatictester.arraylist.test;

import org.testng.annotations.Test;
import uk.co.automatictester.arraylist.ArrayList;

import static org.testng.Assert.*;

public class ArrayListTest {

    @Test
    public void testAdd() {
        ArrayList<Integer> list = new ArrayList<>();
        assertEquals(list.size(), 0);
        boolean added = list.add(1);
        assertTrue(added);
        assertEquals(list.size(), 1);
        added = list.add(1);
        assertTrue(added);
        assertEquals(list.size(), 2);
        list.add(2);
        assertEquals(list.size(), 3);
    }

    @Test
    public void testGet() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(3);
        list.add(4);
        list.add(5);
        assertEquals((int) list.get(0), 1);
        assertEquals((int) list.get(1), 1);
        assertEquals((int) list.get(2), 3);
        assertEquals((int) list.get(3), 4);
        assertEquals((int) list.get(4), 5);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetNegative() {
        ArrayList<Integer> list = new ArrayList<>();
        list.get(-1);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetAbove() {
        ArrayList<Integer> list = new ArrayList<>();
        list.get(0);
    }

    @Test
    public void testRemove() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        assertEquals((int) list.remove(3), 4);
        assertEquals(list.size(), 6);
        assertEquals((int) list.remove(0), 1);
        assertEquals(list.size(), 5);
        assertEquals((int) list.remove(4), 7);
        assertEquals(list.size(), 4);
    }

    @Test
    public void testRemoveLastElementAtLastIndex() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals((int) list.remove(2), 3);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveNegative() {
        ArrayList<Integer> list = new ArrayList<>();
        list.remove(-1);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testRemoveAbove() {
        ArrayList<Integer> list = new ArrayList<>();
        list.remove(0);
    }

    @Test
    public void testClear() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.clear();
        assertEquals(list.size(), 0);
    }
}