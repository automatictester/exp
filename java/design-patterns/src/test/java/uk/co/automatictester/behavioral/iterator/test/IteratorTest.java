package uk.co.automatictester.behavioral.iterator.test;

import org.testng.annotations.Test;
import uk.co.automatictester.behavioral.iterator.LinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertEquals;

public class IteratorTest {

    @Test
    public void testIterator() {
        LinkedList<Integer> sourceList = new LinkedList<>();
        sourceList.add(1);
        sourceList.add(2);
        sourceList.add(3);

        LinkedList<Integer> targetList = new LinkedList<>();

        Iterator<Integer> iterator = sourceList.iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            targetList.add(element);
        }

        assertEquals(sourceList, targetList);
    }

    @Test
    public void testIteratorOnEmpty() {
        LinkedList<Integer> sourceList = new LinkedList<>();
        LinkedList<Integer> targetList = new LinkedList<>();

        Iterator<Integer> iterator = sourceList.iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            targetList.add(element);
        }

        assertEquals(sourceList, targetList);
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testNextOnEmptyEx() {
        LinkedList<Integer> sourceList = new LinkedList<>();
        Iterator<Integer> iterator = sourceList.iterator();
        iterator.next();
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testNextOnNoMoreElementsLeftEx() {
        LinkedList<Integer> sourceList = new LinkedList<>();
        sourceList.add(1);
        Iterator<Integer> iterator = sourceList.iterator();
        int i = iterator.next();
        assertEquals(i, 1);
        iterator.next();
    }

    @Test
    public void testForEachLoop() {
        LinkedList<Integer> sourceList = new LinkedList<>();
        sourceList.add(1);
        sourceList.add(2);
        sourceList.add(3);

        LinkedList<Integer> targetList = new LinkedList<>();
        for (Integer i : sourceList) {
            targetList.add(i);
        }

        assertEquals(sourceList, targetList);
    }

    @Test
    public void testForEachLoopOnEmpty() {
        LinkedList<Integer> sourceList = new LinkedList<>();
        LinkedList<Integer> targetList = new LinkedList<>();

        for (Integer i : sourceList) {
            targetList.add(i);
        }

        assertEquals(sourceList, targetList);
    }

    @Test
    public void testStream() {
        LinkedList<Integer> sourceList = new LinkedList<>();
        sourceList.add(1);
        sourceList.add(2);
        sourceList.add(3);

        LinkedList<Integer> targetList = new LinkedList<>();
        sourceList.forEach(targetList::add);
        assertEquals(sourceList, targetList);
    }
}
