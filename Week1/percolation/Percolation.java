/* *****************************************************************************

 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    //private UF cellComponentID;
    private WeightedQuickUnionUF cellComponentID;

    private boolean cellOpenStatus[];
    private int N=0;
    private int NN=0;


    private int rowCol2rc(int row, int  col) {
        //row: 1, 2, ..., n
        //col: 1, 2, ..., n
        //rc: n*(row-1) + (col-1) = 0, 1, 2, 3, ..., (n^2-1)

        int val = N*(row-1) + (col-1);

        if (val < 0 || val >= NN) throw new IllegalArgumentException();

        return val;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        N = n;
        NN = n*n;
        cellComponentID = new WeightedQuickUnionUF(NN);
        cellOpenStatus = new boolean[NN];

        for(int i=0; i < N*N; ++i) cellOpenStatus[i] = false;

    }

    // opens the site (row, col) if it is not already open
    public void open(int row, int col) {
        // row: 1,2,3,...,n
        // col: 1,2,3,...,n
        try {
            int rc = rowCol2rc(row, col);

            if (cellOpenStatus[rc] == false) {
                cellOpenStatus[rc] = true;

                // join the cell to its {UP,DOWN,LEFT,RIGHT} open neighbors
                if((row - 1) >= 1) join(row, col, row - 1, col);   // UP
                if((row + 1) <= N) join(row, col, row + 1, col);   // DOWN
                if((col - 1) >= 1) join(row, col, row, col - 1);   // LEFT
                if((col + 1) <= N) join(row, col, row, col + 1);   // RIGHT

            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void join(int row1, int col1, int row2, int col2) {
        try {
            int rc1 = rowCol2rc(row1, col1);
            int rc2 = rowCol2rc(row2, col2);
            if (isOpen(row2, col2)) cellComponentID.union(rc1, rc2);
        } catch(IllegalArgumentException e) {
            //e.printStackTrace();
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // row: 1,2,3,...,n
        // col: 1,2,3,...,n

        boolean status = false;
        int rc;

        try {
            rc = rowCol2rc(row, col);
            status = cellOpenStatus[rc];
        } catch(IllegalArgumentException e) {
            //e.printStackTrace();
        }

        return status;
    }

    // a cell is FULL if it is open and connected to any open cell in the top row
    public boolean isFull(int row, int col) {
        // row: 1,2,3,...,n
        // col: 1,2,3,...,n

        boolean status = false;
        int rc1, rc2;

        try {
            rc1 = rowCol2rc(row, col);

            for (int j = 1; j <= N; ++j) {
                rc2 = rowCol2rc(1, j);
                if (cellOpenStatus[rc1] && cellOpenStatus[rc2]) {
                    if (cellComponentID.find(rc1) == cellComponentID.find(rc2)) {
                        status = true;
                        break;
                    }
                }
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        return status;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int sum = 0;

        for(int rc=0; rc<N*N; ++rc) {
            if(cellOpenStatus[rc]) sum += 1;
        }

        return sum;
    }

    // does the system percolate?
    // the system percolates if any cell in top row is connected to any cell in bottom row
    public boolean percolates() {
        int rc_top, rc_bottom;
        boolean status = false;

        try {
            for (int i = 1; i <= N; ++i) {
                for (int j = 1; j <= N; ++j) {
                    rc_top = rowCol2rc(1, i);
                    rc_bottom = rowCol2rc(N, j);

                    if (cellComponentID.find(rc_top) == cellComponentID.find(rc_bottom)) {
                        status = true;
                        break;
                    }
                }
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        return status;
    }

    // test client (optional)
    public static void main(String[] args) {

        int n = 3;
        WeightedQuickUnionUF grid = new WeightedQuickUnionUF(n*n);

        grid.union(0,3);
        grid.union(3, 8);


        if (grid.find(0) == grid.find(8)) {
            int i=0;
        }
    }

}
