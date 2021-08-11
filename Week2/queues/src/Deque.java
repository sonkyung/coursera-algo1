/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

//package src;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
The Deque class represents a double-ended queue (or deque, pronounced "deck") of generic items.

It is a generalization of a stack and a queue that supports adding and removing items from either
the front or the back of the data structure.

This implementation uses a generic LinkedList<> to implement to Deque data structure.
*/

public class Deque<Item> implements Iterable<Item>{
    //private LinkedList<Item> myDeck;
    private MyLinkedList<Item> myDeck;

    // construct an empty deque
    public Deque() {
        //myDeck = new LinkedList<Item>();
        myDeck = new MyLinkedList<Item>();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return myDeck.isEmpty();
    }

    // return the number of items on the deque
    public int size() {
        return myDeck.size();
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        myDeck.addFirst(item);
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        myDeck.addLast(item);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (myDeck.isEmpty()) throw new NoSuchElementException();
        return myDeck.removeFirst();
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (myDeck.isEmpty()) throw new NoSuchElementException();
        return myDeck.removeLast();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        //return myDeck.iterator();
        return new DequeIterator<Item>(myDeck);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();

        dq.addFirst(1);
        dq.addFirst(2);


        //System.out.println("empty==false? = " + dq.isEmpty());
        //System.out.println("size==2? = " + dq.size());
        //dq.addLast("A");
        //dq.addFirst("E");


        //System.out.println(dq.toString());

        //dq.removeFirst();
        //dq.removeLast();

        //System.out.println(dq.toString());

        Iterator<Integer> itr = dq.iterator();
        while (itr.hasNext()) {
            Integer val = itr.next();
            System.out.print(val + " -> ");
        }
        System.out.println("null");


    }

}


class DequeIterator<Item> implements Iterator<Item> {
    private Iterator<Item> itr;

    public DequeIterator(Iterable<Item> obj) {
        itr = obj.iterator();
    }

    public boolean hasNext() {
        return itr.hasNext();
    }

    public Item next() {
        if (itr == null) throw new NoSuchElementException();
        return itr.next();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}



//class MyLinkedList<Item> extends LinkedList<Item> {
class MyLinkedList<Item> implements Iterable<Item> {
    int size;
    Node<Item> head;
    Node<Item> tail;

    public MyLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public boolean hasNext() {
        return ((head != null) && (head.next != null));
    }

    public void addFirst(Item obj) {
        Node<Item> nd = new Node<Item>(obj);
        if (size > 0) {
            nd.next = head;
            head = nd;
        } else {
            //size==0
            nd.next = null;
            head = nd;
            tail = nd;
        }
        size += 1;
    }

    public void addLast(Item obj) {
        Node<Item> nd = new Node<Item>(obj);
        if (size > 0) {
            nd.next = null;
            tail.next = nd;
            tail = nd;
        } else {
            //size==0
            nd.next = null;
            tail = nd;
            head = nd;
        }
        size += 1;
    }

    public Item removeFirst() {
        Item retVal = null;
        Node<Item> nd = null;
        if (size > 0) {
            nd = head;
            if (size > 1) {
                head = head.next;
            } else if (size == 1) {
                head = null;
                tail = null;
            }
            retVal = nd.getData();
            size -= 1;
        }
        return retVal;
    }

    public Item removeLast() {
        Item retVal = null;
        Node<Item> nd = null;

        if (size > 0) {
            nd = tail;
            if (size > 1) {
                //get second to last node, denoted "tailPrev"
                Node<Item> tailPrev = null;
                Node<Item> ndItr = null;
                ndItr = head;
                while (ndItr.next != tail) {
                    ndItr = ndItr.next;
                }
                tailPrev = ndItr;

                tail = tailPrev;
                tail.next = null;

            } else if (size == 1) {
                head = null;
                tail = null;
            }
            retVal = nd.getData();
            size -= 1;
        }
        return retVal;
    }

    public Iterator<Item> iterator() {
        return new MyLinkedListIterator<Item>(this);
    }
}

class MyLinkedListIterator<Item> implements Iterator<Item> {
    Node<Item> ndItr;
    // constructor
    MyLinkedListIterator(MyLinkedList<Item> mll) {
        ndItr = mll.head;
    }

    // Checks if the next element exists
    public boolean hasNext() {
        return (ndItr != null);
    }

    // moves the cursor/iterator to next element
    public Item next() {
        Item tmp = ndItr.getData();
        ndItr = ndItr.next;
        return tmp;
    }

    // Used to remove an element. Implement only if needed
    public void remove() {
        throw new UnsupportedOperationException();
    }
}


class Node<Item> {
    Item data;
    Node<Item> next;

    public Node() {
        data = null;
        next = null;
    }
    public Node(Item obj) {
        data = obj;
        next = null;
    }

    public Item getData() {
        return data;
    }

}


