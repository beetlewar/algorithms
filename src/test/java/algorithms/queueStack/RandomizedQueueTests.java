import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueueTests {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void enqueue_null_throwsException() {
        exception.expect(IllegalArgumentException.class);

        RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();
        deque.enqueue(null);
    }

    @Test
    public void dequeue_empty_throwsException() {
        exception.expect(NoSuchElementException.class);

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.dequeue();
    }

    @Test
    public void sample_empty_throwsException() {
        exception.expect(NoSuchElementException.class);

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.sample();
    }

    @Test
    public void remove_iterator_throwsException() {
        exception.expect(UnsupportedOperationException.class);

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.iterator().remove();
    }

    @Test
    public void next_hasNoNext_throwsException() {
        exception.expect(NoSuchElementException.class);

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.iterator().next();
    }

    @Test
    public void isEmpty_Empty() {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        Assert.assertTrue(queue.isEmpty());
    }

    @Test
    public void size_zero() {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        Assert.assertEquals(0, queue.size());
    }

    @Test
    public void isEmpty_enqueue_notEmpty() {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        queue.enqueue("b");

        Assert.assertFalse(queue.isEmpty());
    }

    @Test
    public void size_enqueueNTimes_sizeIsN() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        int n = 5;

        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
        }

        Assert.assertEquals(n, queue.size());
    }

    @Test
    public void size_enqueueAndRandomDequeue_zero() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        queue.enqueue(1);
        int result = queue.dequeue();

        Assert.assertEquals(1, result);
        Assert.assertEquals(0, queue.size());
    }

    @Test
    public void dequeue_repeatNTimes_eachTimeReturnsDifferentItems() {
        boolean[] results = new boolean[10];

        int numOfTrials = 100;
        for (int i = 0; i < numOfTrials; i++) {
            RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

            for (int j = 0; j < results.length; j++) {
                queue.enqueue(j);
            }

            int random = queue.dequeue();
            results[random] = true;
        }

        for (boolean result : results) {
            Assert.assertTrue(result);
        }
    }

    @Test
    public void sample_repeatNTimes_eachTimeReturnsRandomItem() {
        boolean[] results = new boolean[10];

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        for (int i = 0; i < results.length; i++) {
            queue.enqueue(i);
        }

        int numOfTrials = 100;
        for (int i = 0; i < numOfTrials; i++) {
            int result = queue.sample();
            results[result] = true;
        }

        for (boolean result : results) {
            Assert.assertTrue(result);
        }
    }

    @Test
    public void iterate_empty_HasNoNext() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        Assert.assertFalse(queue.iterator().hasNext());
    }

    @Test
    public void iterate_iteratesThroughAllItems() {
        boolean[] results = new boolean[10];

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        for (int i = 0; i < results.length; i++) {
            queue.enqueue(i);
        }

        Iterator<Integer> iterator = queue.iterator();

        while (iterator.hasNext()) {
            int next = iterator.next();
            results[next] = true;
        }

        for (boolean result : results) {
            Assert.assertTrue(result);
        }

        Assert.assertEquals(10, queue.size());
    }
}
