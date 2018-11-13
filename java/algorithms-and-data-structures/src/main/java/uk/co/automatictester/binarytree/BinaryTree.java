package uk.co.automatictester.binarytree;

import java.util.*;

import static uk.co.automatictester.binarytree.BinaryTree.TraversalStrategy.DFS;

public class BinaryTree<T extends Comparable<T>> {

    private BinaryTree<T> left;
    private T value;
    private BinaryTree<T> right;
    private TraversalStrategy traversalStrategy = DFS;

    public BinaryTree() {
    }

    public BinaryTree<T> left() {
        return left;
    }

    public T value() {
        return value;
    }

    public BinaryTree<T> right() {
        return right;
    }

    public BinaryTree(T element) {
        this.value = element;
    }

    public void setTraversalStrategy(TraversalStrategy traversalStrategy) {
        this.traversalStrategy = traversalStrategy;
    }

    public void insert(T element) {
        if (value == null) {
            value = element;
        } else {
            int cmp = element.compareTo(value);
            if (cmp < 0 && left == null) {
                left = new BinaryTree<>(element);
            } else if (cmp < 0 && left != null) {
                left.insert(element);
            } else if (cmp > 0 && right == null) {
                right = new BinaryTree<>(element);
            } else {
                right.insert(element);
            }
        }
    }

    public boolean contains(T element) {
        if (value == null) {
            return false;
        } else if (value.equals(element)) {
            return true;
        } else {
            int cmp = element.compareTo(value);
            if (cmp < 0 && left == null) {
                return false;
            } else if (cmp < 0 && left != null) {
                return left.contains(element);
            } else if (cmp > 0 && right == null) {
                return false;
            } else {
                return right.contains(element);
            }
        }
    }

    public List<T> toList() {
        switch (traversalStrategy) {
            case DFS:
                return toListDfs();
            case BFS:
                return toListBfs();
            default:
                throw new IllegalStateException();
        }
    }

    private List<T> toListDfs() {
        if (value == null) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>();
        if (left != null) {
            list.addAll(left.toList());
        }
        list.add(value);
        if (right != null) {
            list.addAll(right.toList());
        }
        return list;
    }

    private List<T> toListBfs() {
        Queue<BinaryTree<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<>();
        queue.add(this);
        while (!queue.isEmpty()) {
            BinaryTree<T> item = queue.poll();
            list.add(item.value);
            if (item.left != null) queue.add(item.left);
            if (item.right != null) queue.add(item.right);
        }
        return list;
    }

    public void delete(T element) {
        delete(null, element);
    }

    public void delete(BinaryTree<T> parent, T element) {
        if (value == null) {
            return;
        } else if (value == element && left == null && right == null) {
            value = null;
        } else if (value == element && left == null) {
            if (parent != null && parent.left.equals(this)) {
                parent.left = right;
                value = null;
                right = null;
            } else if (parent != null && parent.right.equals(this)) {
                parent.right = right;
                value = null;
                right = null;
            } else if (parent == null) {
                value = right.value;
                right = right.left;
            }
        } else if (value == element && right == null) {
            if (parent != null && parent.left.equals(this)) {
                parent.left = left;
                value = null;
                left = null;
            } else if (parent != null && parent.right.equals(this)) {
                parent.right = left;
                value = null;
                left = null;
            } else if (parent == null) {
                value = left.value;
                left = left.left;
            }
        } else if (value == element && left != null && right != null && right.left == null) {
            right.left = left;
            if (parent != null && parent.left != null && parent.left.equals(this)) {
                parent.left = right;
                value = null;
                left = null;
                right = null;
            } else if (parent != null && parent.right != null && parent.right.equals(this)) {
                parent.right = right;
                value = null;
                left = null;
                right = null;
            } else if (parent == null) {
                value = right.value;
                right = null;
            }
        } else if (value == element && left != null && right != null && right.left != null) {
            BinaryTree<T> rightLeftMostChild = right.leftMostChild();
            BinaryTree<T> rightLeftMostChildParent = right.leftMostChildParent(this);
            BinaryTree<T> rightLeftMostChildRight = rightLeftMostChild.right;
            rightLeftMostChild.left = left;
            rightLeftMostChild.right = right;
            rightLeftMostChildParent.left = null;
            if (parent != null && parent.left != null && parent.left.equals(this)) {
                parent.left = rightLeftMostChild;
                value = null;
                left = null;
                right = null;
            } else if (parent != null && parent.right != null && parent.right.equals(this)) {
                parent.right = rightLeftMostChild;
                value = null;
                left = null;
                right = null;
            } else if (parent == null) {
                value = rightLeftMostChild.value;
            }
            rightLeftMostChildParent.left = rightLeftMostChildRight;
        } else if (element.compareTo(value) < 0 && left == null) {
            return;
        } else if (element.compareTo(value) < 0 && left != null) {
            left.delete(this, element);
        } else if (element.compareTo(value) > 0 && right == null) {
            return;
        } else if (element.compareTo(value) > 0 && right != null) {
            right.delete(this, element);
        }
    }

    private BinaryTree<T> leftMostChild() {
        if (left == null) {
            return this;
        } else {
            return left.leftMostChild();
        }
    }

    private BinaryTree<T> leftMostChildParent(BinaryTree<T> parent) {
        if (left == null) {
            return parent;
        } else {
            return left.leftMostChildParent(this);
        }
    }

    public enum TraversalStrategy {
        DFS, BFS
    }
}
