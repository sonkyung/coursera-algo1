/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Deque<Item> dq = null;

    // construct an empty randomized queue
    public RandomizedQueue() {
        dq = new Deque<Item>();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return dq.isEmpty();
    }

    // return the number of items on the randomized queue
    public int size() {
        return dq.size();
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        dq.addLast(item);
    }

    // remove and return a random item
    public Item dequeue() {
        if(dq.isEmpty()) throw new NoSuchElementException();

        int ind = 0;
        Deque<Item> new_dq = new Deque<Item>();
        Item itm = null;

        int rand_index;
        rand_index = 1 + StdRandom.uniform(dq.size());
        //System.out.println("rand_index=" + rand_index);

        Iterator<Item> itr = dq.iterator();
        while (itr.hasNext()) {
            Item val = itr.next();
            ++ind;

            if (ind != rand_index)
                new_dq.addLast(val);
            else {
                itm = val;
            }
        }

        dq = new_dq;
        return itm;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if(dq.isEmpty()) throw new NoSuchElementException();

        int ind = 0;
        Item itm = null;

        int rand_index;
        rand_index = 1 + StdRandom.uniform(dq.size());
        //System.out.println("rand_index=" + rand_index);

        Iterator<Item> itr = dq.iterator();
        while (itr.hasNext()) {
            Item val = itr.next();
            ++ind;

            if (ind == rand_index) {
                itm = val;
                break;
            }
        }

        return itm;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        RandomizedQueue<Item> copy_dq = new RandomizedQueue<>();
        Deque<Item> rand_dq = new Deque<Item>();

        //create a copy of the original deque
        Iterator<Item> itr = dq.iterator();
        while (itr.hasNext()) {
            Item val = itr.next();
            copy_dq.enqueue(val);
        }

        //randomly remove items from the original deque
        while (copy_dq.size() > 0) {
            Item itm = copy_dq.dequeue();
            rand_dq.addLast(itm);
        }

        return rand_dq.iterator();
    }



    private Iterator<Item> orig_iterator() {
        return dq.iterator();
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);

        Iterator<Integer> itr;

        itr = rq.orig_iterator();
        System.out.println(itr.toString());

        rq.sample();
        itr = rq.orig_iterator();
        System.out.println(itr.toString());

        rq.dequeue();
        itr = rq.orig_iterator();
        System.out.println(itr.toString());

        itr = rq.iterator();
        System.out.println("rand-iterator: " + itr.toString());



    }
}

