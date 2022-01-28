/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;


public class Permutation {

    public static void main(String[] args) {
        Deque<String> dq = new Deque<String>();
        dq.addFirst("A");

        int szz = 9;
        System.out.println("random_n=" + StdRandom.uniform(szz) + " of [0," + szz + ")");

    }
}
