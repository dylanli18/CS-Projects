import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Dylan Li
 * @version 1.0
 * @userid 903698897
 * @GTID dli471
 *
 *       Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 *       Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and comparator cannot be null");
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int swapIndex = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[swapIndex], arr[j]) < 0) {
                    swapIndex = j;
                }
            }
            if (swapIndex != i) {
                swap(arr, swapIndex, i);
            }
        }
    }

    /*
     * function to swap the values of an array at given indexes
     */
    public static <T> void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and comparator cannot be null");
        }
        boolean swaps = true;
        int start = 0;
        int end = arr.length - 1;
        int lastSwapped = 0;
        while (swaps) {
            swaps = false;
            for (int i = lastSwapped; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swaps = true;
                    swap(arr, i, i + 1);
                    lastSwapped = i;
                }
            }
            end = lastSwapped;
            if (swaps) {
                swaps = false;
                for (int i = lastSwapped; i > start; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        swaps = true;
                        swap(arr, i, i - 1);
                        lastSwapped = i;
                    }
                }
            }
            start = lastSwapped;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and comparator cannot be null");
        }
        merge(arr, comparator);
    }

    /*
     * helper method for mergeSort by recursively sorting the left and right halves
     * of the given array
     */
    public static <T> T[] merge(T[] arr, Comparator<T> comparator) {
        if (arr.length == 1) {
            return arr;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] left = (T[]) new Object[midIndex];
        for (int i = 0; i < midIndex; i++) {
            left[i] = arr[i];
        }
        T[] right = (T[]) new Object[length - midIndex];
        for (int i = midIndex; i < length; i++) {
            right[i - midIndex] = arr[i];
        }
        merge(left, comparator);
        merge(right, comparator);
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) < 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }

        return arr;
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * 
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
            Random rand) {
        if (arr == null || comparator == null || rand == null || k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Parameters are invalid");
        }
        return quickSelect(arr, comparator, 0, arr.length - 1, k, rand);

    }

    /*
     * runs one iteration of quickSort algorithm and recursively calls itself if
     * correct value is not found
     */
    public static <T> T quickSelect(T[] arr, Comparator<T> comparator, int start, int end, int k,
            Random rand) {
        int pivot = rand.nextInt(end - start + 1) + start;
        T pivotValue = arr[pivot];
        int i = start + 1;
        int j = end;
        swap(arr, pivot, start);
        while (j >= i) {
            while (j >= i && comparator.compare(arr[i], pivotValue) < 0) {
                i++;
            }
            while (j >= i && comparator.compare(arr[j], pivotValue) > 0) {
                j--;
            }
            if (j >= i) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, start, j);
        if (j == k - 1) {
            return arr[j];
        }
        if (j > k - 1) {
            return quickSelect(arr, comparator, start, j - 1, k, rand);
        } else {
            return quickSelect(arr, comparator, j, end, k, rand);
        }

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        LinkedList[] list = new LinkedList[20];
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        int numberOfIterations = 1;
        while (max != 0) {
            max = max / 10;
            numberOfIterations++;
        }
        lsdSort(arr, list, 0, numberOfIterations);
    }

    public static void lsdSort(int[] arr, LinkedList[] list, int count, int numberOfIterations) {
        for (int j = 0; j < arr.length; j++) {
            int sort = arr[j];
            for (int k = 0; k < count; k++) {
                sort /= 10;
            }
            if (list[sort % 10 + 10] == null) {
                list[sort % 10 + 10] = new LinkedList<Integer>();
            }
            list[sort % 10 + 10].add(arr[j]);
        }
        int index = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                for (int j = 0; j < list[i].size(); j++) {
                    arr[index] = (int) list[i].get(j);
                    index++;
                }
            }
        }
        list = new LinkedList[20];
        if (count < numberOfIterations) {
            lsdSort(arr, list, count + 1, numberOfIterations);
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     *         holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        Comparator<Integer> comparator = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a.compareTo(b);
            }
        };
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(comparator);
        for (int i = 0; i < data.size(); i++) {
            queue.add(data.get(i));
        }
        int[] arr = new int[data.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) queue.poll();
        }
        return arr;
    }
}
