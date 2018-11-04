package uk.co.automatictester.binarytree;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertTrue;

@SuppressWarnings("SimplifiedTestNGAssertion")
public class BinaryTreeTest {

    @Test
    public void testDefaultConstructor() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        assertTrue(tree.left == null);
        assertTrue(tree.value == null);
        assertTrue(tree.right == null);
    }

    @Test
    public void testConstructor() {
        BinaryTree<Integer> tree = new BinaryTree<>(1);
        assertTrue(tree.left == null);
        assertTrue(tree.value == 1);
        assertTrue(tree.right == null);
    }

    @Test
    public void testInsertEmpty() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(1);
        assertTrue(tree.value == 1);
    }

    @Test
    public void testInsertEmptyLeft() {
        BinaryTree<Integer> tree = new BinaryTree<>(10);
        tree.insert(5);
        assertTrue(tree.left.value == 5);
        assertTrue(tree.value == 10);
    }

    @Test
    public void testInsertEmptyRight() {
        BinaryTree<Integer> tree = new BinaryTree<>(10);
        tree.insert(20);
        assertTrue(tree.value == 10);
        assertTrue(tree.right.value == 20);
    }

    @Test
    public void testInsertLeft() {
        BinaryTree<Integer> tree = new BinaryTree<>(10);
        tree.insert(5);
        tree.insert(2);
        assertTrue(tree.left.left.value == 2);
        assertTrue(tree.left.value == 5);
        assertTrue(tree.value == 10);
    }

    @Test
    public void testInsertRight() {
        BinaryTree<Integer> tree = new BinaryTree<>(10);
        tree.insert(20);
        tree.insert(25);
        assertTrue(tree.value == 10);
        assertTrue(tree.right.value == 20);
        assertTrue(tree.right.right.value == 25);
    }

    @Test
    public void testContains() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        assertTrue(!tree.contains(10));
        tree.insert(10);
        assertTrue(tree.contains(10));
        assertTrue(!tree.contains(5));
        assertTrue(!tree.contains(20));
        tree.insert(5);
        assertTrue(tree.contains(5));
        tree.insert(2);
        assertTrue(tree.contains(2));
        tree.insert(20);
        assertTrue(tree.contains(20));
        tree.insert(25);
        assertTrue(tree.contains(25));
    }

    @Test
    public void testToListBalanced() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);
        tree.insert(20);
        tree.insert(15);
        tree.insert(25);
        List<Integer> list = Arrays.asList(2, 5, 7, 10, 15, 20, 25);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testToList() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(2);
        tree.insert(5);
        tree.insert(7);
        tree.insert(10);
        tree.insert(15);
        tree.insert(20);
        tree.insert(25);
        List<Integer> list = Arrays.asList(2, 5, 7, 10, 15, 20, 25);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteFromEmpty() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.delete(10);
    }

    @Test
    public void testDeleteOnlyElement() {
        BinaryTree<Integer> tree = new BinaryTree<>(10);
        tree.delete(10);
        List<Integer> list = Collections.emptyList();
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteLowerThanOnlyElement() {
        BinaryTree<Integer> tree = new BinaryTree<>(10);
        tree.delete(7);
        List<Integer> list = Collections.singletonList(10);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteGreaterThanOnlyElement() {
        BinaryTree<Integer> tree = new BinaryTree<>(10);
        tree.delete(20);
        List<Integer> list = Collections.singletonList(10);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteLeftMostLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);
        tree.insert(20);
        tree.insert(15);
        tree.insert(25);
        tree.delete(2);
        List<Integer> list = Arrays.asList(5, 7, 10, 15, 20, 25);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteRightMostLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);
        tree.insert(20);
        tree.insert(15);
        tree.insert(25);
        tree.delete(25);
        List<Integer> list = Arrays.asList(2, 5, 7, 10, 15, 20);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteLeftNodeWithLeftLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);
        tree.insert(1);
        tree.insert(6);
        tree.insert(8);
        tree.delete(2);
        List<Integer> list = Arrays.asList(1, 5, 6, 7, 8, 10);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteLeftNodeWithRightLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);
        tree.insert(3);
        tree.insert(6);
        tree.insert(8);
        tree.delete(2);
        List<Integer> list = Arrays.asList(3, 5, 6, 7, 8, 10);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteTopNodeWithLeftLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.delete(10);
        List<Integer> list = Collections.singletonList(5);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteTopNodeWithRightLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.delete(10);
        List<Integer> list = Collections.singletonList(20);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteRightNodeWithLeftLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);
        tree.insert(1);
        tree.insert(3);
        tree.insert(6);
        tree.delete(7);
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 6, 10);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteRightNodeWithRightLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);
        tree.insert(1);
        tree.insert(3);
        tree.insert(8);
        tree.delete(7);
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 8, 10);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteLeftNodeWithTwoLeafs() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(2);
        tree.insert(15);
        tree.insert(1);
        tree.insert(3);
        tree.delete(2);
        List<Integer> list = Arrays.asList(1, 3, 5, 10, 15, 20);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteRightNodeWithTwoLeafs() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(8);
        tree.insert(15);
        tree.insert(6);
        tree.insert(9);
        tree.delete(8);
        List<Integer> list = Arrays.asList(5, 6, 9, 10, 15, 20);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteTopNodeWithTwoLeafs() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.delete(10);
        List<Integer> list = Arrays.asList(5, 20);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteRightNodeWithTwoLeafsAndRightLeftLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.insert(15);
        tree.insert(25);
        tree.insert(22);
        tree.insert(30);
        tree.delete(20);
        List<Integer> list = Arrays.asList(10, 15, 22, 25, 30);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteLeftNodeWithTwoLeafsAndRightLeftLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(60);
        tree.insert(20);
        tree.insert(15);
        tree.insert(25);
        tree.insert(22);
        tree.insert(30);
        tree.delete(20);
        List<Integer> list = Arrays.asList(15, 22, 25, 30, 60);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteTopNodeWithTwoLeafsAndRightLeftLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.insert(15);
        tree.insert(25);
        tree.insert(22);
        tree.insert(30);
        tree.insert(5);
        tree.delete(10);
        List<Integer> list = Arrays.asList(5, 15, 20, 22, 25, 30);
        assertTrue(tree.toList().equals(list));
    }

    @Test
    public void testDeleteTopNodeWithTwoLeafsAndRightLeftRightLeaf() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(15);
        tree.insert(21);
        tree.insert(18);
        tree.delete(10);
        List<Integer> list = Arrays.asList(5, 15, 18, 20, 21);
        assertTrue(tree.toList().equals(list));
    }
}
