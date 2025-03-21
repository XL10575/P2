import org.junit.Test;
import static org.junit.Assert.*;
import java.util.NoSuchElementException;

public class MinHeapTest {

    /**
     * T1 (CC1, b1, GoodData)
     * Goal: Construct a new MinHeap and confirm it is empty.
     * - Checks that getSize() = 0 and toString() = "[]" right after construction.
     */
    @Test
    public void testNewHeapIsEmpty() {
        MinHeap h = new MinHeap();
        // Check size immediately after construction
        assertEquals("A new heap should have size 0.", 0, h.getSize());
        // Check toString() is an empty array-like representation
        assertEquals("A new heap's toString() should be [].", "[]", h.toString());
    }

    /**
     * T2 (CC2, B1, GoodData)
     * Goal: Insert a typical integer into an empty heap, then confirm insertion worked.
     */
    @Test
    public void testInsertIntoEmptyHeap() {
        MinHeap heap = new MinHeap();
        heap.insert(10);  // Insert a single typical integer
        assertEquals("Heap size should be 1 after inserting one element.", 1, heap.getSize());
        // Extract to confirm it's the same
        assertEquals("Extracted min should be the inserted value 10.", 10, heap.extractMin());
        assertEquals("Heap size should return to 0 after extraction.", 0, heap.getSize());
    }

    /**
     * T3 (CC3, BadData1)
     * Goal: Insert null is ignored; size remains the same and no exception is thrown.
     */
    @Test
    public void testIgnoreNullInsert() {
        MinHeap heap = new MinHeap();
        heap.insert(null);  // Should do nothing
        assertEquals("Inserting null should not increase heap size.", 0, heap.getSize());
    }

    /**
     * T4 (CC4, b2, GoodData)
     * Goal: Extract min from a single-element heap.
     */
    @Test
    public void testExtractMinFromSingleElementHeap() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        int min = heap.extractMin();
        assertEquals("Extracted min should be the only element (10).", 10, min);
        assertEquals("Heap should be empty after extracting the only element.", 0, heap.getSize());
    }

    /**
     * T5 (CC5, BadData2)
     * Goal: Extract from an empty heap -> NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void testExtractMinFromEmptyHeap() {
        MinHeap heap = new MinHeap();
        // This should throw NoSuchElementException
        heap.extractMin();
    }

    /**
     * T6 (B1, GoodData, b3, b4)
     * Goal: Insert both negative and large integers; check that re-heapify occurs properly.
     */
    @Test
    public void testNegativeAndLargeIntegers() {
        MinHeap heap = new MinHeap();
        heap.insert(1_000_000);
        heap.insert(-5);
        heap.insert(1);

        // The smallest inserted element is -5, so it should come out first
        assertEquals("Min should be the smallest inserted element (-5).", -5, heap.extractMin());
        // Next smallest is 1
        assertEquals("Next extracted should be 1.", 1, heap.extractMin());
        // Lastly, 1_000_000
        assertEquals("Final extracted should be the largest value (1,000,000).", 1_000_000, heap.extractMin());
        // Heap should be empty now
        assertEquals("Heap should be empty after extracting all elements.", 0, heap.getSize());
    }
}
