import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of a BST.
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
     * @throws java.lang.IllegalArgumentException if data or any element in data is
     *                                            null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        size = 0;
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Data is null");
            }
            add(element);
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
            throw new IllegalArgumentException("Data cannot be null");
        }
        root = add(root, data);
    }

    public BSTNode<T> add(BSTNode<T> node, T data) {
        if (node == null) {
            size++;
            return new BSTNode<T>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(node.getRight(), data));
        }
        return node;
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
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data
     * that
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
            throw new IllegalArgumentException("Data cannot be null");
        }
        BSTNode<T> removed = new BSTNode<T>(null);
        root = remove(data, root, removed);
        size--;
        return removed.getData();
    }

    /*
     * finds successor in cases where a removed node has 2 children
     */
    public T findSucecessor(BSTNode<T> node) {
        node = node.getRight();
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node.getData();
    }

    /*
     * helper method for remove with 2 BSTNode<T> parameters, one to help traverse
     * through
     * the tree and another to save the removed value to be returned in remove(T
     * data)
     */
    public BSTNode<T> remove(T data, BSTNode<T> node, BSTNode<T> removed) {
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
                BSTNode<T> removeSuccessor = new BSTNode<T>(null);
                node.setRight(remove(findSucecessor(node), node.getRight(), removeSuccessor));
                node.setData(removeSuccessor.getData());
            }
        }
        return node;
    }

    /*
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data
     * that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * 
     * @return the data in the tree equal to the parameter
     * 
     * @throws java.lang.IllegalArgumentException if data is null
     * 
     * @throws java.util.NoSuchElementException if the data is not in the
     * tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        if (size == 0) {
            throw new NoSuchElementException("Data is not in tree");
        }
        return get(root, data);

    }

    public T get(BSTNode<T> node, T data) {
        if (node.getData().equals(data)) {
            return node.getData();
        }
        if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                throw new NoSuchElementException("data is not in tree");
            }
            return get(node.getLeft(), data);
        }
        if (node.getRight() == null) {
            throw new NoSuchElementException("Data is not in tree");
        }
        return get(node.getRight(), data);

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
     *         otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        if (size == 0) {
            return false;
        }
        BSTNode<T> head = root;
        if (root.getData().equals(data)) {
            return true;
        } else if (root.getLeft() == null && root.getRight() == null) {
            return false;
        } else if (data.compareTo(root.getData()) < 0) {
            if (root.getLeft() == null) {
                return false;
            }
            root = root.getLeft();
            boolean found = contains(data);
            root = head;
            return found;
        } else {
            if (root.getRight() == null) {
                return false;
            }
            root = root.getRight();
            boolean found = contains(data);
            root = head;
            return found;
        }
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
        List<T> list = new LinkedList<T>();
        if (size == 0) {
            return list;
        }
        return preorder(root, list);
    }

    public List<T> preorder(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return null;
        }
        list.add(node.getData());
        preorder(node.getLeft(), list);
        preorder(node.getRight(), list);
        return list;

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
        List<T> list = new ArrayList<T>();
        if (size == 0) {
            return list;
        }
        return inorder(root);
    }

    public List<T> inorder(BSTNode<T> node) {
        List<T> list = new LinkedList<T>();
        if (node.getLeft() != null) {
            list = inorder(node.getLeft());
        }
        list.add(node.getData());
        if (node.getRight() != null) {
            for (T data : inorder(node.getRight())) {
                list.add(data);
            }
        }
        return list;
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
        if (size == 0) {
            return new ArrayList<T>();
        }
        return postorder(root);

    }

    public List<T> postorder(BSTNode<T> node) {
        List<T> list = new LinkedList<T>();
        if (node.getLeft() != null) {
            list = postorder(node.getLeft());
        }
        if (node.getRight() != null) {
            for (T data : postorder(node.getRight())) {
                list.add(data);
            }
        }
        list.add(node.getData());
        return list;
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
        List<T> list = new ArrayList<T>();
        if (size == 0) {
            return list;
        }
        Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
        queue.add(root);
        while (queue.size() != 0) {
            BSTNode<T> node = queue.remove();
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
            list.add(node.getData());
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(getLeft().height, getRight().height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    public int height(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        }
        return Math.max(height(node.getLeft()), height(node.getRight())) + 1;
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
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     * 50
     * / \
     * 25 75
     * / \
     * 12 37
     * / \ \
     * 10 15 40
     * /
     * 13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("K must be greater than 0 and less than size");
        }
        if (k == size) {
            return inorder();
        }
        List<T> list = new LinkedList<T>();
        if (k == 0) {
            return list;
        }
        list = kLargest(k, list, root);
        if (list.size() < k) {
            List<T> list2 = new LinkedList<T>();
            list2 = kLargest(k - list.size(), list2, root.getLeft());
            for (T data : list) {
                list2.add(data);
            }
            return list2;

        }
        return list;
    }

    public List<T> kLargest(int k, List<T> list, BSTNode<T> node) {
        if (node == null) {
            return list;
        }
        if (list.size() == k) {
            return list;
        }
        if (node.getRight() == null) {
            if (node.getLeft() != null) {
                kLargest(k - 1, list, node.getLeft());
            }
            list.add(node.getData());
            return list;
        }
        List<T> list2 = new LinkedList<T>();
        list2 = kLargest(k, list2, node.getRight());
        if (list2.size() == k) {
            return list2;
        }
        List<T> list3 = new LinkedList<T>();
        list3.add(node.getData());
        for (T data : list2) {
            list3.add(data);
        }
        return list3;
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
