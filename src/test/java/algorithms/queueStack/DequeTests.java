import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DequeTests {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void addFirst_null_throwsException() {
        exception.expect(IllegalArgumentException.class);

        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(null);
    }

    @Test
    public void addLast_null_throwsException() {
        exception.expect(IllegalArgumentException.class);

        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(null);
    }

    @Test
    public void removeFirst_empty_throwsException() {
        exception.expect(NoSuchElementException.class);

        Deque<Integer> deque = new Deque<Integer>();
        deque.removeFirst();
    }

    @Test
    public void removeLast_empty_throwsException() {
        exception.expect(NoSuchElementException.class);

        Deque<Integer> deque = new Deque<Integer>();
        deque.removeLast();
    }

    @Test
    public void remove_iterator_throwsException() {
        exception.expect(UnsupportedOperationException.class);

        Deque<Integer> deque = new Deque<Integer>();
        deque.iterator().remove();
    }

    @Test
    public void next_hasNoNext_throwsException() {
        exception.expect(NoSuchElementException.class);

        Deque<Integer> deque = new Deque<Integer>();
        deque.iterator().next();
    }

    @Test
    public void size_nonZero_returnsExpectedSize() {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("a");

        Assert.assertEquals(1, deque.size());
    }

    @Test
    public void isEmpty_notEmpty_returnsFalse() {
        Deque<String> deque = new Deque<String>();

        deque.addLast("a");

        Assert.assertFalse(deque.isEmpty());
    }

    @Test
    public void isEmpty_addFirstAndRemoveFirst_returnsEmpty() {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("a");
        deque.removeFirst();

        Assert.assertTrue(deque.isEmpty());
        Assert.assertEquals(0, deque.size());
    }

    @Test
    public void isEmpty_addFirstAndRemoveLast_returnsEmpty() {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("a");
        deque.removeLast();

        Assert.assertTrue(deque.isEmpty());
        Assert.assertEquals(0, deque.size());
    }

    @Test
    public void isEmpty_addLastAndRemoveFirst_returnsEmpty() {
        Deque<String> deque = new Deque<String>();

        deque.addLast("a");
        deque.removeFirst();

        Assert.assertTrue(deque.isEmpty());
        Assert.assertEquals(0, deque.size());
    }

    @Test
    public void isEmpty_addLastAndRemoveLast_returnsEmpty() {
        Deque<String> deque = new Deque<String>();

        deque.addLast("a");
        deque.removeLast();

        Assert.assertTrue(deque.isEmpty());
        Assert.assertEquals(0, deque.size());
    }

    @Test
    public void removeFirst_returnsAddedItem() {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("a");
        String result = deque.removeFirst();

        Assert.assertEquals("a", result);
    }

    @Test
    public void removeLast_returnsAddedItem() {
        Deque<String> deque = new Deque<String>();

        deque.addLast("a");
        String result = deque.removeLast();

        Assert.assertEquals("a", result);
    }

    @Test
    public void removeLast_combinedAdd_returnsExpectedItem() {
        Deque<String> deque = new Deque<String>();

        // b - a - c
        deque.addFirst("a");
        deque.addFirst("b");
        deque.addLast("c");

        String item1 = deque.removeLast();
        String item2 = deque.removeFirst();
        String item3 = deque.removeLast();

        Assert.assertEquals("c", item1);
        Assert.assertEquals("b", item2);
        Assert.assertEquals("a", item3);
    }

    @Test
    public void iterate_emptyDeque_HasNextReturnsFalse() {
        Deque<String> deque = new Deque<String>();

        Iterator<String> iterator = deque.iterator();

        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void iterate_ExpectedIteration() {
        Deque<String> deque = new Deque<String>();

        deque.addLast("Ilya");
        deque.addFirst("from");
        deque.addFirst("Hello");
        deque.addFirst("1");
        deque.removeFirst();
        deque.addLast("2");
        deque.removeLast();
        deque.addLast("!");

        Iterator<String> iterator = deque.iterator();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("Hello", iterator.next());
        Assert.assertEquals("from", iterator.next());
        Assert.assertEquals("Ilya", iterator.next());
        Assert.assertEquals("!", iterator.next());
    }
}
