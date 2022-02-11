/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;


public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int n = 0;

        //read n strings from standard input
        int k = Integer.parseInt(args[0]);


        while(!StdIn.isEmpty()) {
            String str = StdIn.readString();
            rq.enqueue(str);
            ++n;
        }

        if(k > n) throw new RuntimeException();

        //print a k subset of strings (0 <= k <= n)
        Iterator<String> itr = rq.iterator();
        for(int i = 0; i < k; ++i) {
            String str = itr.next();
            System.out.println(str);
        }

    }
}
