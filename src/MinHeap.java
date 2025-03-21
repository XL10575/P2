import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MinHeap {

    private final ArrayList<Integer> heapArray;
    private int size;

    /**
     * Constructor for MinHeap.
     */
    public MinHeap() {
        this.heapArray = new ArrayList<>();
        this.size = 0;
    }

    @Override
    public String toString() {
        // Just rely on ArrayList’s built-in toString()
        return heapArray.toString();
    }

    /**
     * Inserts a value into the min-heap.
     * Ignores null values.
     *
     * @param value the integer value to insert.
     */
    public void insert(Integer value) {
        if (value == null) {
            return; // Do nothing for null values.
        }
        heapArray.add(value);
        size++;
        heapifyUp(size - 1);
    }

    /**
     * Extracts and returns the smallest integer in the min-heap.
     *
     * @return the minimum integer.
     * @throws NoSuchElementException if the heap is empty.
     */
    public int extractMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty.");
        }
        int minValue = heapArray.get(0);
        // Move the last element to the root.
        int lastValue = heapArray.get(size - 1);
        heapArray.set(0, lastValue);
        heapArray.remove(size - 1);
        size--;
        // Restore the heap property.
        if (size > 0) {
            heapifyDown(0);
        }
        return minValue;
    }

    /**
     * Restores the heap property by moving the element at the given index upward.
     *
     * @param index the starting index.
     */
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heapArray.get(index) < heapArray.get(parentIndex)) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    /**
     * Restores the heap property by moving the element at the given index downward.
     *
     * @param index the starting index.
     */
    private void heapifyDown(int index) {
        while (true) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;
            int smallest = index;

            if (leftChildIndex < size && heapArray.get(leftChildIndex) < heapArray.get(smallest)) {
                smallest = leftChildIndex;
            }
            if (rightChildIndex < size && heapArray.get(rightChildIndex) < heapArray.get(smallest)) {
                smallest = rightChildIndex;
            }
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    /**
     * Swaps the elements at the given indices in the heapArray.
     *
     * @param i the first index.
     * @param j the second index.
     */
    private void swap(int i, int j) {
        int temp = heapArray.get(i);
        heapArray.set(i, heapArray.get(j));
        heapArray.set(j, temp);
    }

    /**
     * Returns the current number of elements in the heap.
     *
     * @return the size of the heap.
     */
    public int getSize() {
        return size;
    }
}
