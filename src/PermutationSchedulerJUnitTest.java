import static org.junit.Assert.*;
import org.junit.Test;
import java.util.NoSuchElementException;

public class PermutationSchedulerJUnitTest {

    // ----------------------- PermutationScheduler Tests ------------------------

    /**
     * Tests a normal execution of associateIndices with a typical permutation and indices.
     */
    @Test
    public void testAssociateIndices_Normal() {
        int[] permutation = {4, 8, 3, 9, 2, 6, 1, 7, 5};
        int[] indices = {2, 3, 6, 6, 6, 8};

        PermutationScheduler scheduler = new PermutationScheduler(permutation, indices);
        int[] result = scheduler.associateIndices();

        // Expected result based on the scheduling algorithm.
        int[] expected = {3, 8, 1, 2, 6, 5};
        assertArrayEquals("associateIndices should return the expected output.", expected, result);
    }

    /**
     * Tests associateIndices when some indices exceed the available permutation elements.
     */
    @Test
    public void testAssociateIndices_IndexBeyondPermutationLength() {
        int[] permutation = {1, 2, 3};
        int[] indices = {1, 3, 4}; // Note: index 4 exceeds permutation.length.
        PermutationScheduler scheduler = new PermutationScheduler(permutation, indices);
        int[] result = scheduler.associateIndices();

        assertEquals("Result length should match indices length.", indices.length, result.length);
    }

    /**
     * Tests that a repeated index causes an IllegalStateException rather than a NoSuchElementException.
     * For permutation [1, 3] and indices [1, 1], after extracting for the first index,
     * the heap is empty, so the second extraction should throw an exception.
     */
    @Test(expected = IllegalStateException.class)
    public void testAssociateIndices_RepeatedIndexException() {
        int[] permutation = {1, 3};
        int[] indices = {1, 1};
        PermutationScheduler scheduler = new PermutationScheduler(permutation, indices);
        scheduler.associateIndices();
    }

    /**
     * Tests that the PermutationScheduler constructor throws IllegalArgumentException
     * when the permutation array is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_NullPermutation() {
        int[] indices = {1, 2, 3};
        new PermutationScheduler(null, indices);
    }

    /**
     * Tests that the PermutationScheduler constructor throws IllegalArgumentException
     * when the indexList array is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_NullIndexList() {
        int[] permutation = {1, 2, 3};
        new PermutationScheduler(permutation, null);
    }

    // ----------------------- MinHeap Tests ------------------------

    /**
     * Tests that inserting a null value does not alter the heap,
     * and that extracting from an empty heap throws NoSuchElementException.
     */
    @Test
    public void testMinHeap_InsertNull() {
        MinHeap heap = new MinHeap();
        heap.insert(null);  // Should do nothing.
        try {
            heap.extractMin();
            fail("Expected NoSuchElementException when extracting from an empty heap.");
        } catch (NoSuchElementException e) {
            // Expected exception.
        }
    }

    /**
     * Tests the toString() method of MinHeap.
     */
    @Test
    public void testMinHeap_ToString() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(5);
        String heapString = heap.toString();
        assertNotNull("MinHeap toString() should not return null", heapString);
        assertTrue("MinHeap toString() should contain inserted elements",
                   heapString.contains("10") && heapString.contains("5"));
    }

    /**
     * Tests heapifyDown by inserting elements that force multiple reheapification steps,
     * including a case where the right child is smaller than the left child.
     */
    @Test
    public void testMinHeap_HeapifyDownScenarios() {
        MinHeap heap = new MinHeap();
        // Insert elements in an order that requires reordering:
        // The order and values are chosen so that after several extracts the right-child branch is triggered.
        heap.insert(3);
        heap.insert(9);
        heap.insert(7);
        heap.insert(8);
        heap.insert(2);

        // First extraction should return the smallest (2).
        assertEquals("First extractMin() should return 2.", 2, heap.extractMin());
        // Second extraction should return 3.
        assertEquals("Second extractMin() should return 3.", 3, heap.extractMin());
        // Next extractions:
        assertEquals("Third extractMin() should return 7.", 7, heap.extractMin());
        assertEquals("Fourth extractMin() should return 8.", 8, heap.extractMin());
        assertEquals("Fifth extractMin() should return 9.", 9, heap.extractMin());

        // Confirm that extracting from an empty heap throws an exception.
        try {
            heap.extractMin();
            fail("Expected NoSuchElementException when extracting from an empty heap.");
        } catch (NoSuchElementException e) {
            // Expected exception.
        }
    }

    /**
     * Tests a sequence of insertions and extractions to cover both single-child and two-child scenarios.
     */
    @Test
    public void testMinHeap_InsertAndExtract() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(5);
        heap.insert(7);
        heap.insert(2);
        heap.insert(11);
        heap.insert(1);

        assertEquals("Expected extractMin() to return 1.", 1, heap.extractMin());
        assertEquals("Expected extractMin() to return 2.", 2, heap.extractMin());
        assertEquals("Expected extractMin() to return 5.", 5, heap.extractMin());
        assertEquals("Expected extractMin() to return 7.", 7, heap.extractMin());
        assertEquals("Expected extractMin() to return 10.", 10, heap.extractMin());
        assertEquals("Expected extractMin() to return 11.", 11, heap.extractMin());

        try {
            heap.extractMin();
            fail("Expected NoSuchElementException from empty heap.");
        } catch (NoSuchElementException e) {
            // Expected exception.
        }
    }
}
