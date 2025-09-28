import java.util.Iterator;
import java.io.Serializable;

// This class connects with BrowserArrayList
public class BrowserQueue<AnyType> implements Iterable<AnyType>, Serializable {

    // Connect queueList to Browser ArrayList
    private final BrowserArrayList<AnyType> queueList;

    // Constructor: Initializes the queue
    public BrowserQueue() {
        this.queueList = new BrowserArrayList<>();
    }

    /****
     Enqueue an url into the last place of the list (ENQUEUE operation)
     Time complexity: O(1)
     Why O(1)? A single element is appended to the end of the list,
     hence, it is done in constant time.
     ****/
    public void enqueue(AnyType url) {
        queueList.addLast(url);
    }

    // Checks if the queue is empty
    public boolean isEmpty() {
        return queueList.isEmpty();
    }

    // Returns the size of the queue
    public int size() {
        return queueList.size();
    }

    // Clears the queue
    public void clear() {
        queueList.clear();
    }

    // Use the custom Iterator the traverse in the queue
    @Override
    public Iterator<AnyType> iterator() {
        return queueList.iterator();
    }
}