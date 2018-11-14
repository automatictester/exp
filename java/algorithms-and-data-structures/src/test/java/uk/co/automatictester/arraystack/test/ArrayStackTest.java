package uk.co.automatictester.arraystack.test;

import org.testng.annotations.Test;
import uk.co.automatictester.arraystack.ArrayStack;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class ArrayStackTest {

    @Test
    public void testPush() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        assertEquals(stack.size(), 0);
        stack.push(1);
        assertEquals(stack.size(), 1);
        stack.push(1);
        assertEquals(stack.size(), 2);
        stack.push(2);
        assertEquals(stack.size(), 3);
    }

    @Test
    public void testPeek() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(1);
        assertEquals((int) stack.peek(), 1);
        stack.push(1);
        assertEquals((int) stack.peek(), 1);
        stack.push(3);
        assertEquals((int) stack.peek(), 3);
        stack.push(4);
        assertEquals((int) stack.peek(), 4);
        stack.push(5);
        assertEquals((int) stack.peek(), 5);
        assertEquals(stack.size(), 5);
    }

    @Test
    public void testPop() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        stack.push(7);
        assertEquals((int) stack.pop(), 7);
        assertEquals(stack.size(), 6);
        assertEquals((int) stack.pop(), 6);
        assertEquals(stack.size(), 5);
        assertEquals((int) stack.pop(), 5);
        assertEquals(stack.size(), 4);
    }

    @Test
    public void testPeekPopOnEmpty() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        assertNull(stack.peek());
        assertNull(stack.pop());
    }

    @Test
    public void testClear() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        stack.push(7);
        assertEquals(stack.size(), 7);
        stack.clear();
        assertEquals(stack.size(), 0);
    }
}
