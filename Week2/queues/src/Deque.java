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

This implementation uses a generic LinkedList<> to implement the Deque data structure.
*/

public class Deque<Item> implements Iterable<Item>{
    //private LinkedList<Item> myDeck;
    private MyLinkedList<Item> myList;

    // construct an empty deque
    public Deque() {
        //myDeck = new LinkedList<Item>();
        myList = new MyLinkedList<Item>();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return myList.isEmpty();
    }

    // return the number of items on the deque
    public int size() {
        return myList.size();
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        myList.addFirst(item);
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        myList.addLast(item);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (myList.isEmpty()) throw new NoSuchElementException();
        return myList.removeFirst();
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (myList.isEmpty()) throw new NoSuchElementException();
        return myList.removeLast();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        //return myDeck.iterator();
        return new DequeIterator<Item>(myList);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();

        dq.addFirst(1);
        dq.addFirst(2);
        dq.addFirst(3);



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

        Iterator<Integer> itr2 = dq.iterator();
        System.out.println(itr2.toString());


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

    public String toString() {
        String str = null;
        while (itr.hasNext()) {
            Item val = itr.next();

            if (str == null)
                str = val.toString();
            else
                str = str + " -> " + val.toString();
        }
        str = str + " -> " + null;
        return str;
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
        Item tmp = null;

        if (ndItr != null) {
            tmp = ndItr.getData();
            ndItr = ndItr.next;
        } else {
            throw new NoSuchElementException();
        }

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


