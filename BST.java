import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import static java.lang.Math.max;

/**
 * Your implementation of a BST.
 *
 * @author Aron Silberwasser
 * @version 1.0
 * @userid asilberwasser3
 * @GTID 903683147
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data.contains(null)) {
            throw new IllegalArgumentException("data in collection cannot be null");
        }

        Iterator<T> it = data.iterator();

        while (it.hasNext()) {
            add(it.next());
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        
        root = addHelper(data, root);
    }

    /**
     * helper method for recursion to work
     * @param data the data to add
     * @param curr current node
     * @return reinforces node
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(data, curr.getRight()));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        BSTNode<T> dummy = new BSTNode<>(null);
        root = removeHelper(data, root, dummy);
        return dummy.getData();
    }

    /**
     * helper method for recursion to work
     * @param data the data to remove
     * @param curr current node
     * @param dummy dummy node to hold removed data
     * @return reinforces node
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("data is not in the tree");
        }

        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(data, curr.getLeft(), dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(data, curr.getRight(), dummy));
        } else if (data.equals(curr.getData())) {

            dummy.setData(curr.getData());

            if (curr.getRight() == null && curr.getLeft() == null) {
                curr = curr.getRight();
            } else if (curr.getLeft() == null) {
                curr = curr.getRight();
            } else if (curr.getRight() == null) {
                curr = curr.getLeft();
            } else {
                BSTNode<T> pred = curr.getLeft();
                while (pred.getRight() != null) {
                    pred = pred.getRight();
                }

                pred.setRight(curr.getRight());
                curr = pred;
            }

            size--;
        }

        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        return getHelper(data, root).getData();
    }

    /**
     * helper method for recursion to work
     * @param data the data to search for
     * @param curr current node
     * @return reinforces node
     */
    private BSTNode<T> getHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("data is not in tree");
        } else if (data.compareTo(curr.getData()) < 0) {
            return getHelper(data, curr.getLeft());
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(data, curr.getRight());
        }
        return curr;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        return containsHelper(data, root) != null;
    }

    /**
     * helper method for recursion to work
     * @param data the data to search for
     * @param curr current node
     * @return reinforces node
     */
    private BSTNode<T> containsHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            return null;
        } else if (data.compareTo(curr.getData()) < 0) {
            return getHelper(data, curr.getLeft());
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(data, curr.getRight());
        }
        return curr;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<>();
        preorderHelper(root, list);
        return list;
    }

    /**
     * helper method for recursion to work
     * @param curr current node
     * @param list the preorder traversal of the tree
     */
    private void preorderHelper(BSTNode<T> curr, ArrayList<T> list) {
        if (curr != null) {
            list.add(curr.getData());
            preorderHelper(curr.getLeft(), list);
            preorderHelper(curr.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }
    /**
     * helper method for recursion to work
     * @param curr current node
     * @param list the inorder traversal of the tree
     */
    private void inorderHelper(BSTNode<T> curr, ArrayList<T> list) {
        if (curr != null) {
            inorderHelper(curr.getLeft(), list);
            list.add(curr.getData());
            inorderHelper(curr.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<>();
        postorderHelper(root, list);
        return list;
    }
    /**
     * helper method for recursion to work
     * @param curr current node
     * @param list the postorder traversal of the tree
     */
    private void postorderHelper(BSTNode<T> curr, ArrayList<T> list) {
        if (curr != null) {
            postorderHelper(curr.getLeft(), list);
            postorderHelper(curr.getRight(), list);
            list.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        ArrayList<T> list = new ArrayList<>();
        Queue<BSTNode<T>> q = new LinkedList<>();

        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.remove();

            if (curr != null) {
                list.add(curr.getData());
                q.add(curr.getLeft());
                q.add(curr.getRight());
            }

        }

        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }

        return heightHelper(root);
    }

    /**
     * helper method for recursion to work
     * @param curr current node
     * @return reinforces node
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        return max(heightHelper(curr.getRight()), heightHelper(curr.getLeft())) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        ArrayList<T> list = new ArrayList<>();
        gmdplHelper(root, list, 0);
        return list;
    }

    /**
     * helper method for recursion to work
     * @param curr current node
     * @param list the list containing the max data of each level
     * @param level the current level given the node
     */
    private void gmdplHelper(BSTNode<T> curr, ArrayList<T> list, int level) {
        if (list.size() == level) {
            list.add(curr.getData());
        }

        if (curr.getRight() != null) {
            gmdplHelper(curr.getRight(), list, level + 1);
        }
        if (curr.getLeft() != null) {
            gmdplHelper(curr.getLeft(), list, level + 1);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
