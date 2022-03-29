
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private MinPQ<SearchNode> pq;
    private Board initialBoard;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        initialBoard = initial;
    }


    // is the initial board solvable?
    public boolean isSolvable() {
        Iterable<Board> bIt = solution();
        return (bIt != null);
    }


    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        Iterable<Board> bItable = solution();
        int numMoves;

        if (bItable != null) {
            Iterator<Board> bItor = bItable.iterator();

            numMoves = 0;
            while (bItor.hasNext()) {
                bItor.next();
                ++numMoves;
            }
            numMoves = numMoves - 1;    //#moves between boards, so subtract 1
        }
        else {
            numMoves = -1;
        }
        return numMoves;
    }


    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        pq = new MinPQ<SearchNode>((int)Math.pow(initialBoard.dimension(), 2.0), new SortByManhPriority());
        SearchNode sn = new SearchNode(initialBoard, 0, null);
        pq.insert(sn);

        Iterable<Board> itB = null;
        SearchNode sn_sol = null;

        if (initialBoard.isGoal()) {
            sn_sol = sn;
        }

        while(sn_sol == null) {
            //System.out.println("pq.size()=" + pq.size());

            sn = pq.delMin();

            // make sure board is solvable
            // check by making sure that twin is not solvable
            if(TwinIsSolvable(sn.b)) {
                break;
            }

            Iterable<Board> bItable = sn.b.neighbors();
            Iterator<Board> bItor = bItable.iterator();
            while (bItor.hasNext()) {
                Board bNext = bItor.next();

                //optimization check: don't move back to parent
                Board prevBoard = null;
                if((sn != null) && (sn.prev != null)) prevBoard = sn.prev.b;
                if(!bNext.equals(prevBoard)) {
                    SearchNode sn_neighbor = new SearchNode(bNext, sn.numMoves + 1, sn);

                    if (bNext.isGoal()) {
                        sn_sol = sn_neighbor;
                        break;
                    }
                    else {
                        //System.out.println("inserting board:" + sn_neighbor.b);
                        pq.insert(sn_neighbor);
                    }
                }
            }
        }

        if(sn_sol != null) {
            ArrayList<Board> arrB = new ArrayList<Board>();
            while(sn_sol != null) {
                arrB.add(sn_sol.b);
                sn_sol = sn_sol.prev;
            }
            itB = arrB;
        }

        return itB;
    }


    private boolean TwinIsSolvable(Board b) {
        Board twin = b.twin();
        return twin.isGoal();
    }


    private class SearchNode {
        public Board b;
        public int numMoves;
        SearchNode prev;

        public SearchNode(Board init, int nMoves, SearchNode pr) {
            b = init;
            numMoves = nMoves;
            prev = pr;
        }
    }

    private class SortByManhPriority implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            int int_a, int_b;
            int_a = a.numMoves + a.b.manhattan();
            int_b = b.numMoves + b.b.manhattan();
            return new Integer(int_a).compareTo(int_b);
        }
    }

    // test client (see below)
    public static void main(String[] args) {

    }

}