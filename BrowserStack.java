import java.util.Iterator;
import java.util.EmptyStackException;
import java.io.Serializable;

// This class connects with BrowserLinkedList
public class BrowserStack<AnyType> implements Iterable<AnyType>, Serializable {

    // Connect stackList to BrowserLinkedList
    private final BrowserLinkedList<AnyType> stackList;

    // Constructor: initializes the stack
    public BrowserStack() {
        this.stackList = new BrowserLinkedList<>();
    }

    /****
     Insert an url into the first node in the list (PUSH operation)
     Time complexity: O(1)
     Why O(1)? A new node is created and linked at the head of the list,
     hence it is done in constant time.
     ****/
    public void push(AnyType url) {
        stackList.insert(url);
    }

    /****
     Remove an url placing in the first node out of the list (POP operation)
     Time complexity: O(1)
     Why O(1)? TThe head node is removed and the reference is updated.
     This is a constant time operation.
     ****/
    public AnyType pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackList.remove();
    }

    // Checks if the stack is empty
    public boolean isEmpty() {
        return stackList.isEmpty();
    }

    // Returns the size of the stack
    public int size() {
        return stackList.size();
    }

    // Clears the stack
    public void clear() {
        stackList.clear();
    }

    // Use the custom Iterator the traverse in the stack
    @Override
    public Iterator<AnyType> iterator() {
        return new StackIterator<>(stackList);
    }
}
