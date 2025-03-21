import org.junit.Test;
import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import java.util.Arrays;

/**
 * Combined test class for both MinHeap and PermutationScheduler.
 * JUnit 4.13.2 version.
 */
public class JUnitTest {

    /* ==========================================================
       ============== SECTION: MINHEAP TESTS ===================
       ========================================================== */
    
    /**
     * MinHeap T1 (CC1, b1, GoodData)
     * Goal: Construct a new MinHeap and confirm it is empty.
     */
    @Test
    public void testMinHeap_NewHeapIsEmpty() {
        MinHeap h = new MinHeap();
        // Check size immediately after construction
        assertEquals("A new heap should have size 0.", 0, h.getSize());
        // Check toString() is empty array-like
        assertEquals("A new heap's toString() should be [].", "[]", h.toString());
    }

    /**
     * MinHeap T2 (CC2, B1, GoodData)
     * Goal: Insert a typical integer into an empty heap.
     */
    @Test
    public void testMinHeap_InsertIntoEmptyHeap() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        assertEquals("Heap size should be 1 after inserting one element.", 1, heap.getSize());
        assertEquals("Extracted min should be the inserted value 10.", 10, heap.extractMin());
        assertEquals("Heap size should return to 0 after extraction.", 0, heap.getSize());
    }

    /**
     * MinHeap T3 (CC3, BadData1)
     * Goal: Insert null is ignored.
     */
    @Test
    public void testMinHeap_IgnoreNullInsert() {
        MinHeap heap = new MinHeap();
        heap.insert(null);
        assertEquals("Inserting null should not increase heap size.", 0, heap.getSize());
    }

    /**
     * MinHeap T4 (CC4, b2, GoodData)
     * Goal: Extract min from a single-element heap.
     */
    @Test
    public void testMinHeap_ExtractMinFromSingleElementHeap() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        int min = heap.extractMin();
        assertEquals("Extracted min should be the only element (10).", 10, min);
        assertEquals("Heap should be empty after extracting the only element.", 0, heap.getSize());
    }

    /**
     * MinHeap T5 (CC5, BadData2)
     * Goal: Extract from empty heap -> throw NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void testMinHeap_ExtractMinFromEmptyHeap() {
        MinHeap heap = new MinHeap();
        heap.extractMin(); // Should throw NoSuchElementException
    }

    /**
     * MinHeap T6 (B1, GoodData, b3, b4)
     * Goal: Insert both negative and large integers; check re-heapify.
     */
    @Test
    public void testMinHeap_NegativeAndLargeIntegers() {
        MinHeap heap = new MinHeap();
        heap.insert(1_000_000);
        heap.insert(-5);
        heap.insert(1);

        assertEquals("Min should be the smallest inserted element (-5).", -5, heap.extractMin());
        assertEquals("Next extracted should be 1.", 1, heap.extractMin());
        assertEquals("Final extracted should be the largest value (1,000,000).",
                     1_000_000, heap.extractMin());
        assertEquals("Heap should be empty after extracting all elements.", 0, heap.getSize());
    }

    /* ==========================================================
       ======== SECTION: PERMUTATIONSCHEDULER TESTS ============
       ========================================================== */

    /**
     * PermutationScheduler T1 (CC1, GoodData)
     * Goal: Construct with valid arrays. No exceptions thrown.
     */
    @Test
    public void testPermutationScheduler_ConstructorValidArrays() {
        int[] perm = {10, 20, 5};
        int[] idx  = {0, 1, 2};
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);
        assertNotNull("Scheduler instance should not be null.", scheduler);
    }

    /**
     * PermutationScheduler T2 (CC2, BadData1)
     * Goal: Constructor throws if permutation or indexList is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPermutationScheduler_ConstructorNullPermutation() {
        int[] idx = {0};
        new PermutationScheduler(null, idx);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPermutationScheduler_ConstructorNullIndexList() {
        int[] perm = {1, 2, 3};
        new PermutationScheduler(perm, null);
    }

    /**
     * PermutationScheduler T3 (CC3, GoodData)
     * Goal: associateIndices() normal scenario.
     * perm = {3,1,2}
     * idx  = {0,1,2}
     * Expect ascending order in the result, if that is your logic.
     */
    @Test
    public void testPermutationScheduler_AssociateIndicesNormal() {
        int[] perm = {3, 1, 2};
        int[] idx  = {0, 1, 2};
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);
        int[] result = scheduler.associateIndices();

        assertEquals("Result should have length 3.", 3, result.length);

        // Check ascending order if your code is designed that way
        int[] sorted = Arrays.copyOf(result, result.length);
        Arrays.sort(sorted);
        assertArrayEquals("Result should be sorted if it extracts in ascending order.",
                          sorted, result);
    }

    /**
     * PermutationScheduler T4 (CC4)
     * Goal: associateIndices() with an empty indexList.
     * idx = {}
     * Expect an empty array as result.
     */
    @Test
    public void testPermutationScheduler_AssociateIndicesEmptyIndex() {
        int[] perm = {10, 20, 30};
        int[] idx  = {};
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);
        int[] result = scheduler.associateIndices();
        assertNotNull("Result should not be null.", result);
        assertEquals("Result length should be 0 with empty indexList.", 0, result.length);
    }

    /**
     * PermutationScheduler T5 (CC5, BadData2)
     * Goal: Repeated indices => second extract on empty heap => IllegalStateException.
     */
    @Test(expected = IllegalStateException.class)
    public void testPermutationScheduler_AssociateIndicesRepeatedIndices() {
        int[] perm = {5, 6};
        int[] idx  = {0, 0};
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);
        scheduler.associateIndices(); // Expect IllegalStateException on second extraction
    }

    /**
     * PermutationScheduler T6 (CC6)
     * Goal: Exhaust permutation => request more => throw exception.
     */
    @Test(expected = IllegalStateException.class)
    public void testPermutationScheduler_AssociateIndicesPermutationExhausted() {
        int[] perm = {1, 2};
        int[] idx  = {0, 1, 2};
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);
        scheduler.associateIndices(); // Should throw once it tries index=2 with no new elements
    }

    /**
     * PermutationScheduler T7 (GoodData)
     * Goal: Larger scenario with multiple indices => ascending output if your code so dictates.
     */
    @Test
    public void testPermutationScheduler_AssociateIndicesLargerScenario() {
        int[] perm = {2, 9, 1, 7, 6};
        int[] idx  = {0, 1, 2, 3, 4};
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);
        int[] result = scheduler.associateIndices();
        assertEquals("Result should have length 5.", 5, result.length);

        // If your logic sorts them, verify it's ascending
        int[] sorted = Arrays.copyOf(result, result.length);
        Arrays.sort(sorted);
        assertArrayEquals("Result array might be in ascending order after extraction.",
                          sorted, result);
    }
}
