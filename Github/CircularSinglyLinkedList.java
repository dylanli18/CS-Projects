import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
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
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (size == 0) {
            CircularSinglyLinkedListNode<T> insertedNode = new CircularSinglyLinkedListNode<T>(data);
            head = insertedNode;
            head.setNext(head);
            size++;
        } else if (index == 0) {
            addToFront(data);
        } else {
            for (int i = 1; i < index; i++) {
                head = head.getNext();
            }
            CircularSinglyLinkedListNode<T> insertedNode = new CircularSinglyLinkedListNode<T>(data, head.getNext());
            head.setNext(insertedNode);
            size++;
            for (int i = index; i <= size; i++) {
                head = head.getNext();
            }
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (size == 0) {
            addAtIndex(0, data);
        } else {
            CircularSinglyLinkedListNode<T> insertedNode = new CircularSinglyLinkedListNode<T>(data, head);
            for (int i = 1; i < size; i++) {
                head = head.getNext();
            }
            head.setNext(insertedNode);
            head = head.getNext();
            size++;
        }
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (size == 0) {
            addToFront(data);
        } else {
            for (int i = 1; i < size; i++) {
                head = head.getNext();
            }
            CircularSinglyLinkedListNode<T> insertedNode = new CircularSinglyLinkedListNode<T>(data, head.getNext());
            head.setNext(insertedNode);
            size++;
            head = head.getNext();
            head = head.getNext();
        }
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
        if (index == 0) {
            return removeFromFront();
        } else {
            for (int i = 1; i < index; i++) {
                head = head.getNext();
            }
            CircularSinglyLinkedListNode<T> removedNode = head.getNext();
            head.setNext((head.getNext()).getNext());
            size--;
            for (int i = index; i <= size; i++) {
                head = head.getNext();
            }
            return removedNode.getData();
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        if (size == 1) {
            return removeFromBack();
        }
        CircularSinglyLinkedListNode<T> removedNode = head;
        for (int i = 1; i < size; i++) {
            head = head.getNext();
        }
        head.setNext(head.getNext().getNext());
        head = head.getNext();
        size--;
        return removedNode.getData();
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        if (size == 1) {
            CircularSinglyLinkedListNode<T> removedEntry = head;
            head = null;
            size = 0;
            return removedEntry.getData();
        } else {
            for (int i = 1; i < size - 1; i++) {
                head = head.getNext();
            }
            CircularSinglyLinkedListNode<T> removedEntry = head.getNext();
            head.setNext(head.getNext().getNext());
            size--;
            head = head.getNext();
            return removedEntry.getData();
        }
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
        for (int i = 0; i < index; i++) {
            head = head.getNext();
        }
        T data = head.getData();
        for (int i = index; i < size; i++) {
            head = head.getNext();
        }
        return data;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null || size == 0);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        int index = 0;
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (head.getData().equals(data)) {
                found = true;
                index = i;
            }
            head = head.getNext();
        }
        if (found) {
            return removeAtIndex(index);
        }
        throw new NoSuchElementException("Data not found");
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     *         nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = head.getData();
            head = head.getNext();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
