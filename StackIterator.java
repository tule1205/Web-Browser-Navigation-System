import java.util.Iterator;
import java.util.NoSuchElementException;

// This traversal is used by the closeBrowser() method to save the session state.
public class StackIterator<AnyType> implements Iterator<AnyType> {
    // Defines current node being iterated
    private BrowserLinkedList.Node<AnyType> current;

    // Defines tail node to stop traversal
    private final BrowserLinkedList.Node<AnyType> tail;

    // Constructor for the StackIterator.
    public StackIterator(BrowserLinkedList<AnyType> list) {
        this.current = list.getHead().next;
        this.tail = list.getTail();
    }

    // Checks if there is another element in the stack to return
    @Override
    public boolean hasNext() {
        // Stop when the current node reaches the tail sentinel
        return current != tail;
    }

    // Returns the next element in the stack's history order
    @Override
    public AnyType next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        AnyType data = current.data;
        current = current.next; // Move to the next element
        return data;
    }
}