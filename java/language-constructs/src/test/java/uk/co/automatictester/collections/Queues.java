package uk.co.automatictester.collections;

import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.Queue;

public class Queues {

    @Test
    public void remove() {

        // Queue is a single-ended queue - can't add at the front (push)
        Queue<String> q = new ArrayDeque<>();
        q.add("ha");
        q.add("haha");

        // removes element which was added first - ha
        String s = q.remove();
        boolean b = q.remove("haha");

        System.out.println(q.size());
    }

    @Test
    public void push() {

        // ArrayDeque is a double-ended queue - can add element at the front (push method) and at the back (add method)
        ArrayDeque<String> q = new ArrayDeque<>();
        q.add("ha");

        // push() is a method on ArrayDeque (and LinkedList) class, NOT on Queue interface
        q.push("haha");

        // removes element most recently added to the front of the queue - haha
        String s = q.remove();

        // haha is removed already, so this one doesn't remove anything
        boolean b = q.remove("haha");

        System.out.println(q.size());
    }
}
