public class PermutationScheduler {

    private final int[] permutation;  // the permutation P
    private final int[] indexList;    // the sorted list of indices (assumed sorted in increasing order)
    private final MinHeap minHeap;    // min-priority queue for integers
    private int currentPos;           // tracks how far we have "scanned" in the permutation

    /**
     * Constructor for PermutationScheduler.
     * @param permutation An array representing the permutation (must not be null).
     * @param indexList An array of indices (must not be null).
     * @throws IllegalArgumentException if any input array is null.
     */
    public PermutationScheduler(int[] permutation, int[] indexList) {
        if (permutation == null || indexList == null) {
            throw new IllegalArgumentException("Input arrays cannot be null.");
        }
        this.permutation = permutation;
        this.indexList = indexList;
        this.minHeap = new MinHeap();
        this.currentPos = 0;
    }

    /**
     * Associates each index in indexList with the smallest available integer
     * from the permutation using a min-heap.
     *
     * @return An array of the k distinct associated integers.
     */
    public int[] associateIndices() {
        int k = indexList.length;
        int[] result = new int[k];
    
        for (int j = 0; j < k; j++) {
            int targetIndex = indexList[j];
    
            // Insert new elements from permutation into the heap until we reach targetIndex
            while (currentPos < targetIndex) {
                currentPos++;
                if (currentPos < permutation.length) {
                    minHeap.insert(permutation[currentPos]);
                }
            }
    
            // NEW CHECK: If the minHeap is empty here, it means we have no elements to extract
            // (e.g., repeated index without advancing currentPos).
            // Instead of letting MinHeap throw NoSuchElementException,
            // you can throw your own custom exception or handle it differently.
            if (isHeapEmpty()) {
                throw new IllegalStateException(
                    "Cannot extract from an empty heap. "
                  + "Possibly caused by repeated index: " + targetIndex
                );
            }
    
            // Extract the smallest integer from the minHeap.
            result[j] = minHeap.extractMin();
        }
        return result;
    }
    
    /**
     * A helper method to check if the heap is empty.
     * (Assuming you have a size variable or a way to get the size of the heap.)
     */
    private boolean isHeapEmpty() {
        // If MinHeap doesn't have a public isEmpty() or getSize() method,
        // add one, or modify as appropriate.
        // For example, if you have MinHeap.size as a public field or a getter:
        return minHeap.getSize() == 0;
    }
    

}
