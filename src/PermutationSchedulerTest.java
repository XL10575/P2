import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class PermutationSchedulerTest {

    /**
     * T1 (CC1, GoodData)
     * Goal: Construct with valid arrays.
     * Setup:
     *   int[] perm = {10, 20, 5};
     *   int[] idx  = {0, 1, 2};
     *   new PermutationScheduler(perm, idx);
     * Assertions:
     *   - No exceptions; internal MinHeap is constructed empty.
     */
    @Test
    public void testConstructorValidArrays() {
        int[] perm = {10, 20, 5};
        int[] idx  = {0, 1, 2};
        // Expect no exception
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);
        // If you want to confirm something about internal state (beyond "no exception"),
        // you can add further checks if your class has relevant getters or reflection-based checks.
        assertNotNull("Scheduler instance should not be null.", scheduler);
    }

    /**
     * T2 (CC2, BadData1)
     * Goal: Constructor throws if permutation or indexList is null.
     * Setup:
     *   new PermutationScheduler(null, idx);
     *   or new PermutationScheduler(perm, null);
     * Assertion:
     *   - IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullPermutation() {
        // perm is null
        int[] idx = {0};
        new PermutationScheduler(null, idx);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullIndexList() {
        // indexList is null
        int[] perm = {1, 2, 3};
        new PermutationScheduler(perm, null);
    }

    /**
     * T3 (CC3, GoodData)
     * Goal: associateIndices() normal scenario.
     * Setup:
     *   perm = {3, 1, 2}
     *   idx  = {0, 1, 2}
     *   scheduler.associateIndices() → returns array of size 3
     * Assertion:
     *   - Elements come out in ascending order of the inserted portion.
     *     (Depending on your actual code’s logic.)
     */
    @Test
    public void testAssociateIndicesNormal() {
        int[] perm = {3, 1, 2};
        int[] idx  = {0, 1, 2};
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);
        int[] result = scheduler.associateIndices();

        assertEquals("Result should have length 3.", 3, result.length);
        // The exact expected order depends on how the code processes idx=0,1,2.
        // If your logic yields ascending extracted values, check that here:
        // e.g., [1, 2, 3] or [3, 1, 2], etc.
        // For demonstration, let's assume the code extracts them in ascending order:
        // Adjust if your code differs.
        int[] sortedCopy = Arrays.copyOf(result, result.length);
        Arrays.sort(sortedCopy);
        assertArrayEquals("Result should be ascending if the code inserts/extracts properly.",
                          sortedCopy, result);
    }

    /**
     * T4 (CC4)
     * Goal: associateIndices() with an empty indexList.
     * Setup:
     *   idx = {}
     *   associateIndices() returns an empty array.
     * Assertion:
     *   - result length = 0
     */
    @Test
    public void testAssociateIndicesEmptyIndexList() {
        int[] perm = {10, 20, 30};
        int[] idx  = {}; // empty
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);
        int[] result = scheduler.associateIndices();

        assertNotNull("Result should not be null.", result);
        assertEquals("Result length should be 0 when indexList is empty.", 0, result.length);
    }

    /**
     * T5 (CC5, BadData2)
     * Goal: Repeated indices cause a second extract on an empty MinHeap.
     * Setup:
     *   perm = {5, 6}
     *   idx  = {0, 0}
     * Assertion:
     *   - IllegalStateException (or custom exception) is thrown
     */
    @Test(expected = IllegalStateException.class)
    public void testAssociateIndicesRepeatedIndices() {
        int[] perm = {5, 6};
        int[] idx  = {0, 0}; // repeated index
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);

        // Expect an IllegalStateException on second extraction
        scheduler.associateIndices();
    }

    /**
     * T6 (CC6)
     * Goal: Exhaust permutation and then request more.
     * Setup:
     *   perm = {1, 2}
     *   idx  = {0, 1, 2}
     * Assertion:
     *   - By the time we process index=2, no more elements => code throws exception
     */
    @Test(expected = IllegalStateException.class)
    public void testAssociateIndicesPermutationExhausted() {
        int[] perm = {1, 2};
        int[] idx  = {0, 1, 2};
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);

        // The code should eventually run out of elements to insert,
        // leading to an empty MinHeap extraction => IllegalStateException
        scheduler.associateIndices();
    }

    /**
     * T7 (GoodData)
     * Goal: Larger typical scenario.
     * Setup:
     *   perm = {2, 9, 1, 7, 6}; idx = {0, 1, 2, 3, 4};
     *   associateIndices() returns the smallest available integer for each index
     *   in ascending order of new elements inserted.
     * Assertion:
     *   - Output array is sorted ascending if code inserts/extracts in a typical order.
     */
    @Test
    public void testAssociateIndicesLargerScenario() {
        int[] perm = {2, 9, 1, 7, 6};
        int[] idx  = {0, 1, 2, 3, 4}; 
        PermutationScheduler scheduler = new PermutationScheduler(perm, idx);
        int[] result = scheduler.associateIndices();

        assertEquals("Result should have length 5.", 5, result.length);

        // If your code is designed so that each index request picks
        // the next smallest integer inserted, we might expect something like [1,2,6,7,9].
        // The exact logic depends on how PermutationScheduler uses 'currentPos' and the heap.
        // Demonstrate a simple ascending check:
        int[] sortedCopy = Arrays.copyOf(result, result.length);
        Arrays.sort(sortedCopy);

        assertArrayEquals("For a typical scenario, results might be in ascending order.",
                          sortedCopy, result);
    }
}
