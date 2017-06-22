import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node _first;
    private Node _last;
    private int _count;

    public Deque() {
    }

    public boolean isEmpty() {
        return _count == 0;
    }

    public int size() {
        return _count;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node first = new Node();
        first.Item = item;
        first.Next = _first;

        if (_first != null) {
            _first.Prev = first;
        }

        _first = first;
        _count++;

        if (_last == null) {
            _last = first;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node last = new Node();
        last.Item = item;
        last.Prev = _last;

        if (_last != null) {
            _last.Next = last;
        }

        _last = last;
        _count++;

        if (_first == null) {
            _first = last;
        }
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = (Item) _first.Item;
        _first = _first.Next;

        if (_first == null) {
            _last = null;
        } else {
            _first.Prev = null;
        }

        _count--;

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = (Item) _last.Item;

        _last = _last.Prev;

        if (_last == null) {
            _first = null;
        } else {
            _last.Next = null;
        }

        _count--;

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(_first);
    }

    private class Node<Item> {
        public Node Next;
        public Node Prev;
        public Item Item;
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node _nextNode;

        public DequeIterator(Node firstNode) {
            _nextNode = firstNode;
        }

        public boolean hasNext() {
            return _nextNode != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = (Item) _nextNode.Item;
            _nextNode = _nextNode.Next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
