import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] _items;
    private int _last;

    public RandomizedQueue() {
        _last = -1;
        _items = (Item[]) new Object[0];
    }

    public boolean isEmpty() {
        return _last == -1;
    }

    public int size() {
        return _last + 1;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (_last == _items.length - 1) {
            int resizeSize = _items.length * 2;

            if (resizeSize == 0) {
                resizeSize = 1;
            }

            resize(resizeSize);
        }

        _last++;
        _items[_last] = item;
    }

    private void resize(int size) {
        Item[] newArray = (Item[]) new Object[size];

        for (int i = 0; i <= _last; i++) {
            newArray[i] = _items[i];
        }

        _items = newArray;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniform(_last + 1);

        Item result = _items[random];

        if (random != _last) {
            _items[random] = _items[_last];
        }

        _items[_last] = null;

        _last--;

        int requiredArraySize = _items.length / 4;
        if (_last < requiredArraySize) {
            resize(requiredArraySize);
        }

        return result;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniform(_last + 1);
        return _items[random];
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator<Item>(_items, _last);
    }

    private class RandomizedIterator<Item> implements Iterator<Item> {
        private Item[] _items;
        private int _last;

        public RandomizedIterator(Item[] items, int last) {
            _items = (Item[]) new Object[last + 1];

            for (int i = 0; i <= last; i++) {
                _items[i] = items[i];
            }

            _last = last;
        }

        public boolean hasNext() {
            return _last >= 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int random = StdRandom.uniform(_last + 1);

            Item result = _items[random];

            _items[random] = _items[_last];
            _items[_last] = null;

            _last--;

            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}