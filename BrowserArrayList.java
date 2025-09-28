import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.Serializable;

public class BrowserArrayList<AnyType> implements Iterable<AnyType>, Serializable {

    private AnyType[] items;
    private int size;
    private int front;
    private int capacity;

    // Initializes the circular array with a default capacity.
    @SuppressWarnings("unchecked")
    public BrowserArrayList() {
        capacity = 10;
        items = (AnyType[]) new Object[capacity];
        size = 0;
        front = 0;
    }

    // Returns number of elements in the Array List
    public int size() {
        return size;
    }

    // Checks whether the list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    /****
     Adds an element to the back (rear) of the list
     Time Complexity: O(1)
     Why O(1)? We add new element at the end of the list, which is a constant-time
     operation on average.
     ****/
    public void addLast(AnyType x) {
        // Checks if resizing is necessary before adding
        if (size == capacity) {
            resize(capacity * 2);
        }
        // Find the rear index in the circular array
        int rear = (front + size) % capacity;
        items[rear] = x;
        size++;
    }

    /****
     Doubles the size of the internal array and copies all elements to the new array
     Time Complexity: O(N)
     Why O(N)? Each element is copied one-by-one into new array,
     therefore, it is N times elements.
     ****/
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        AnyType[] newArray = (AnyType[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = items[(front + i) % capacity];
        }
        items = newArray;
        capacity = newCapacity;
        front = 0;
    }

    // Clears the list and resets internal array to default capacity
    @SuppressWarnings("unchecked")
    public void clear() {
        size = 0;
        front = 0;
        items = (AnyType[]) new Object[10];
        capacity = 10;
    }

    // Returns an Iterator over the elements in the correct order
    @Override
    public Iterator<AnyType> iterator() {
        return new ArrayListIterator();
    }

    // Internal implementation of the Iterator for the circular array
    private class ArrayListIterator implements Iterator<AnyType> {
        private int current = 0;

        // Checks if there are more elements left to iterate
        @Override
        public boolean hasNext() {
            return current < size;
        }

        // Returns the next element in the list's order and advances the iterator
        @Override
        public AnyType next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            AnyType data = items[(front + current) % capacity];
            current++;
            return data;
        }
    }
}
