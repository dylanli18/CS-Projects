import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import java.util.ArrayList;

/**
 * Your implementation of an AVL.
 *
 * @author Dylan Li
 * @version 1.0
 * @userid dli471
 * @GTID 903698897
 *
 *       Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 *       Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        size = 0;
        for (T addData : data) {
            add(addData);
        }

    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (size == 0) {
            root = math(new AVLNode<T>(data));
            size++;
        } else {
            root = add(data, root);
        }
    }

    /*
     * helper method for add with a AVLNode<T> parameter to help traverse through
     * AVL tree
     */
    public AVLNode<T> add(T data, AVLNode<T> node) {
        if (node == null) {
            AVLNode<T> newNode = new AVLNode<T>(data);
            size++;
            return math(newNode);
        }
        if (data.equals(node.getData())) {
            return node;
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
        } else {
            node.setRight(add(data, node.getRight()));
        }
        return rotate(node);
    }

    /*
     * checks whether the node in the parameter needs to be rotated by checking
     * balance factor,
     * then if roation is needed will check for double rotation cases
     */
    public AVLNode<T> rotate(AVLNode<T> node) {
        math(node);
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node);
        }
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        }
        return node;
    }

    /*
     * method to rotate right, then returns the new "head" node
     */
    public AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> pointer = node.getLeft();
        node.setLeft(pointer.getRight());
        pointer.setRight(math(node));
        return math(pointer);
    }
    /*
     * method to rotate left, then returns the new "head" node
     */

    public AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> pointer = node.getRight();
        node.setRight(pointer.getLeft());
        pointer.setLeft(math(node));
        return math(pointer);
    }

    /*
     * calculates the height and balance factor of a given node and returns the node
     */
    public AVLNode<T> math(AVLNode<T> node) {
        int left = -1;
        int right = -1;
        if (node.getLeft() != null) {
            left = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            right = node.getRight().getHeight();
        }
        node.setHeight(Math.max(left, right) + 1);
        node.setBalanceFactor(left - right);
        return node;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> removed = new AVLNode<T>(null);
        root = remove(data, root, removed);
        size--;
        return removed.getData();
    }

    /*
     * finds predecessor in cases where a removed node has 2 children
     */
    public T findPredecessor(AVLNode<T> node) {
        AVLNode<T> tracker = node;
        tracker = tracker.getLeft();
        while (tracker.getRight() != null) {
            tracker = tracker.getRight();
        }
        return tracker.getData();
    }

    /*
     * helper method for remove with 2 AVLNode<T> parameters, one to help traverse
     * through
     * the tree and another to save the removed value to be returned in remove(T
     * data)
     */
    public AVLNode<T> remove(T data, AVLNode<T> node, AVLNode<T> removed) {
        if (node == null) {
            throw new NoSuchElementException("Data is not in tree");
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(data, node.getLeft(), removed));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(data, node.getRight(), removed));
        } else {
            removed.setData(node.getData());
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                AVLNode<T> removePredecessor = new AVLNode<T>(findPredecessor(node));
                node.setLeft(remove(removePredecessor.getData(), node.getLeft(), removePredecessor));
                node.setData(removePredecessor.getData());
            }
        }
        return rotate(node);
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return get(data, root);
    }

    /*
     * helper method for get with an AVLNode<T> parameter to help traverse the tree
     */
    public T get(T data, AVLNode<T> node) {
        if (node.getData().equals(data)) {
            return node.getData();
        }
        if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                throw new NoSuchElementException("Data is not in tree");
            }
            return get(data, node.getLeft());
        }
        if (node.getRight() == null) {
            throw new NoSuchElementException("Data is not in tree");
        }
        return get(data, node.getRight());
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     *         otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return contains(data, root);
    }

    /*
     * helper method for contains to help traverse the tree
     */
    public boolean contains(T data, AVLNode<T> node) {
        if (node.getData().equals(data)) {
            return true;
        }
        if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                return false;
            }
            return contains(data, node.getLeft());
        }
        if (node.getRight() == null) {
            return false;
        }
        return contains(data, node.getRight());

    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * This must be done recursively.
     *
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     * 10
     * / \
     * 5 15
     * / \ / \
     * 2 7 13 20
     * / \ / \ \ / \
     * 1 4 6 8 14 17 25
     * / \ \
     * 0 9 30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     *         traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<T>();
        return deepestBranches(root, list);
    }

    /*
     * helper method to help traverse the tree
     */
    public List<T> deepestBranches(AVLNode<T> node, List<T> list) {
        if (node == null) {
            return list;
        }
        list.add(node.getData());
        if (node.getBalanceFactor() >= 0) {
            deepestBranches(node.getLeft(), list);
        }
        if (node.getBalanceFactor() <= 0) {
            deepestBranches(node.getRight(), list);
        }
        return list;
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * This must be done recursively.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     * 10
     * / \
     * 5 15
     * / \ / \
     * 2 7 13 20
     * / \ / \ \ / \
     * 1 4 6 8 14 17 25
     * / \ \
     * 0 9 30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     *                                  or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null || data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Data1 and Data2 are not valid parameters");
        }
        List<T> list = new ArrayList<T>();
        return sortedInBetween(data1, data2, root, list);
    }

    /*
     * helper method to help traverse the tree
     */
    public List<T> sortedInBetween(T data1, T data2, AVLNode<T> node, List<T> list) {
        if (node == null) {
            return list;
        }
        if (node.getData().compareTo(data1) <= 0) {
            return sortedInBetween(data1, data2, node.getRight(), list);
        }
        if (node.getData().compareTo(data2) >= 0) {
            return sortedInBetween(data1, data2, node.getLeft(), list);
        }
        sortedInBetween(data1, data2, node.getLeft(), list);
        list.add(node.getData());
        sortedInBetween(data1, data2, node.getRight(), list);
        return list;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
