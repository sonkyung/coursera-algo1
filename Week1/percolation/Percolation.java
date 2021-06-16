/* *****************************************************************************

 **************************************************************************** */

import edu.princeton.cs.algs4.UF;

public class Percolation {

    UF cellComponentID;
    boolean cellOpenStatus[];
    int N=0;


    private int rowCol2rc(int row, int  col) {
        //row: 1, 2, ..., n
        //col: 1, 2, ..., n
        //rc: n*(row-1) + (col-1) = 0, 1, 2, 3, ..., (n^2-1)

        return N*(row-1) + (col-1) + 1;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        N = n;
        cellComponentID = new UF(N*N);
        cellOpenStatus = new boolean[N*N];

        for(int i=0; i < N*N; ++i) cellOpenStatus[i] = false;

    }

    // opens the site (row, col) if it is not already open
    public void open(int row, int col) {
        int rc = rowCol2rc(row, col);

        if (cellOpenStatus[rc] == false) {
            cellOpenStatus[rc] = true;

            //connect the cell to its {UP,DOWN,LEFT,RIGHT} open neighbors
            int rc_neighbor;
            rc_neighbor = rowCol2rc(row-1, col);
            if (isOpen(row-1, col)) cellComponentID.union(rc, rc_neighbor);

            rc_neighbor = rowCol2rc(row+1, col);
            if (isOpen(row+1, col)) cellComponentID.union(rc, rc_neighbor);

            rc_neighbor = rowCol2rc(row, col+1);
            if (isOpen(row, col+1)) cellComponentID.union(rc, rc_neighbor);

            rc_neighbor = rowCol2rc(row, col-1);
            if (isOpen(row, col-1)) cellComponentID.union(rc, rc_neighbor);

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        boolean status = false;
        int rc;

        rc = rowCol2rc(row, col);
        status = cellOpenStatus[rc];

        return status;
    }

    // a cell is FULL if it is connected to any cell in the top row
    public boolean isFull(int row, int col) {
        boolean status = false;
        int rc1, rc2;

        rc1 = rowCol2rc(row, col);

        for(int i = 1; i <= N; ++i) {
            rc2 = rowCol2rc(i, 1);
            if (cellComponentID.find(rc1) == cellComponentID.find(rc2)) {
                status = true;
                break;
            }
        }

        return status;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int sum = 0;

        for(int rc=0; rc<=N*N; ++rc) {
            if(cellOpenStatus[rc]) sum += 1;
        }

        return sum;
    }

    // does the system percolate?
    // the system percolates if any cell in top row is connected to any cell in bottom row
    public boolean percolates() {
        int rc_top, rc_bottom;
        boolean status = false;

        for(int i=1; i<=N; ++i) {
            for(int j=1; j<=N; ++j) {
                rc_top = rowCol2rc(1, i);
                rc_bottom = rowCol2rc(N, j);

                if (cellComponentID.find(rc_top) == cellComponentID.find(rc_bottom)) {
                    status = true;
                    break;
                }
            }
        }

        return status;
    }

    // test client (optional)
    public static void main(String[] args) {

        int n = 3;
        UF grid = new UF(n*n);

        grid.union(0,3);
        grid.union(3, 8);


        if (grid.find(0) == grid.find(8)) {
            int i=0;
        }


    }

}
