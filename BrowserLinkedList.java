import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.Serializable;

public class BrowserLinkedList<AnyType> implements Iterable<AnyType>, Serializable {

    private final Node<AnyType> head; // beginning node
    private final Node<AnyType> tail; // ending node
    private int size; // size of the Linked List

    // Doubly Linked List Structure
    public static class Node<AnyType> implements Serializable {
        public AnyType data;
        public Node<AnyType> next;
        public Node<AnyType> prev;

        // Constructor for a node
        public Node(AnyType data, Node<AnyType> prev, Node<AnyType> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    // Initializes the list with head and tail sentinel nodes
    public BrowserLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(null, head, null);
        head.next = tail;
    }

    // Returns head node (beginning node)
    public Node<AnyType> getHead() {
        return head;
    }

    // Returns tail node (ending node)
    public Node<AnyType> getTail() {
        return tail;
    }

    /****
     Insertion method of the Linked List
     Time Complexity: 0(1)
     Why O(1)? We insert new node at the beginning. These operations involve only a few
     pointer manipulations, which take constant time regardless of the size of the list.
     ****/
    public void insert(AnyType x) {
        Node<AnyType> newNode = new Node<>(x, head, head.next); // insert new node after head
        head.next.prev = newNode; // update the current first node's 'prev' pointer to point to the new node
        head.next = newNode; // Update the head's 'next' pointer to point to the new node
        size++;
    }

    /****
     Removal method of the Linked List
     Time Complexity: 0(1)
     Why O(1)? We delete the node at the beginning. These operations involve only a few
     pointer manipulations, which take constant time regardless of the size of the list.
     ****/
    public AnyType remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        Node<AnyType> toRemove = head.next; // remove the node after head
        head.next = toRemove.next; // head.next now points to the node after toRemove
        toRemove.next.prev = head; // the new first node's 'prev' pointer back to head
        size--;
        return toRemove.data;
    }

    // Returns the size of the Linked List
    public int size() {
        return size;
    }

    // Checks whether the list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Empties the list by resetting the head and tail node's links and size
    public void clear() {
        // Resets the list by linking head to tail and setting size to 0
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    // Returns a standard Iterator for the list
    @Override
    public Iterator<AnyType> iterator() {
        return new LinkedListIterator();
    }

    // Internal implementation of the Iterator for this list
    private class LinkedListIterator implements Iterator<AnyType> {
        private Node<AnyType> current = head.next;

        // Checks if there are more elements to iterate
        @Override
        public boolean hasNext() {
            return current != tail;
        }

        // Returns the next element in the list and advances the iterator
        @Override
        public AnyType next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            AnyType data = current.data;
            current = current.next;
            return data;
        }
    }
}
