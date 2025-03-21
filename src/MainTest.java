

import java.util.Arrays;

public class MainTest {

    public static void main(String[] args) {

        // --------------------------------------------------
        // 1) Setup the example permutation and indices (1-based description)
        //    For clarity, we’ll store them in 0-based arrays as in typical Java usage.
        // --------------------------------------------------
        int[] permutation = {4, 8, 3, 9, 2, 6, 1, 7, 5}; // length 9
        int[] indices     = {2, 3, 6, 6, 6, 8};          // k=6, sorted ascending

        // --------------------------------------------------
        // 2) Prepare a MinHeap and variables for tracking the progress
        // --------------------------------------------------
        MinHeap minHeap = new MinHeap();
        int currentPos = 0;                 // Tracks how far we've "scanned" in permutation
        int[] result   = new int[indices.length];

        // --------------------------------------------------
        // 3) Replicate the logic step-by-step, printing the details 
        // --------------------------------------------------
        System.out.println("Permutation P: " + Arrays.toString(permutation));
        System.out.println("Indices I: " + Arrays.toString(indices));
        System.out.println("---------------------------------------------\n");

        for (int step = 0; step < indices.length; step++) {
            int i_k = indices[step];

            System.out.println("Step " + (step + 1) + ": i_k = " + i_k);

            // Insert elements P[currentPos], P[currentPos+1], ..., P[i_k-1] as long as currentPos < i_k
            while (currentPos < i_k) {
                System.out.println("  Inserting P[" + currentPos + "] = " + permutation[currentPos]
                        + " into min-heap.");
                minHeap.insert(permutation[currentPos]);
                currentPos++;
            }

            // Extract the smallest from the min-heap
            int extracted = minHeap.extractMin();
            result[step] = extracted;

            System.out.println("  extractMin() = " + extracted);
            System.out.println("  min-heap after extraction: " + minHeap);
            System.out.println();
        }

        // --------------------------------------------------
        // 4) Print the final result array
        // --------------------------------------------------
        System.out.println("Final result array: " + Arrays.toString(result));
    }
}
