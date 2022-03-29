import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private int n;
    private int[][] boardTiles;


    // create a board from an n-by-n array of tiles, where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        if(tiles != null) {
            n = tiles[0].length;
            boardTiles = new int[n][n];

            for(int i = 0; i < n; ++i) {
                for(int j = 0; j < n; ++j) {
                    boardTiles[i][j] = tiles[i][j];
                }
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

    // string representation of this board
    public String toString() {
        String sout = String.format("%d%n", n);

        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                sout = sout + String.format(" %3d", boardTiles[i][j]);
            }
            sout = sout + String.format("%n");
        }
        return sout;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDist = 0;
        int targettVal;
        int n_sq = n*n;

        for(int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                targettVal = i*n + j + 1;

                if(targettVal < n_sq) {     // tile #(n*n) is the blank tile, don't count it
                    if(boardTiles[i][j] != targettVal) ++hammingDist;
                }
            }
        }

        return hammingDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhDist = 0;
        int targettVal;
        int n_sq = n*n;

        for(int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                targettVal = i*n + j + 1;

                if(targettVal < n_sq) {     // tile #(n*n) is the blank tile, don't count it
                    if(boardTiles[i][j] != targettVal) {
                        int tileVal = boardTiles[i][j];
                        if (tileVal == 0) tileVal = n_sq;   // tile value of "0" corresponds to empty cell, which has equivalent value of n*n

                        int goal_i = (int)((tileVal - 1) / n);
                        int goal_j = (tileVal - 1) % n;

                        manhDist += Math.abs(goal_i - i);
                        manhDist += Math.abs(goal_j - j);
                    }
                }

            }
        }

        return manhDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (this.hamming() == 0);   //does this condition suffice to check if isGoal()?
    }

    // does this board equal y?
    public boolean equals(Object y) {
        String strObj;

        if(y == null) strObj = null;
        else strObj = y.toString();

        //is this a strong enough comparision? assuming same .toString() functions..
        return (this.toString().equals(strObj));
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> arrBoards = new ArrayList<Board>();

        //Find the array index of the blank tile (ie. tile #0)
        int blank_i = 0, blank_j = 0;
        findIndex: {
            for (blank_i = 0; blank_i < n; ++blank_i) {
                for (blank_j = 0; blank_j < n; ++blank_j) {
                    if (boardTiles[blank_i][blank_j] == 0) {
                        break findIndex;
                    }
                }
            }
        }


        //up?
        if(blank_i > 0) {
            int[][] boardUp = copyTiles();
            int temptVal = boardUp[blank_i][blank_j];
            boardUp[blank_i][blank_j] = boardUp[blank_i - 1][blank_j];
            boardUp[blank_i - 1][blank_j] = temptVal;
            Board nBoard = new Board(boardUp);
            arrBoards.add(nBoard);
        }

        //down?
        if(blank_i < (n-1)) {
            int[][] boardDown = copyTiles();
            int temptVal = boardDown[blank_i][blank_j];
            boardDown[blank_i][blank_j] = boardDown[blank_i + 1][blank_j];
            boardDown[blank_i + 1][blank_j] = temptVal;
            Board nBoard = new Board(boardDown);
            arrBoards.add(nBoard);
        }


        //left?
        if(blank_j > 0) {
            int[][] boardLeft = copyTiles();
            int temptVal = boardLeft[blank_i][blank_j];
            boardLeft[blank_i][blank_j] = boardLeft[blank_i][blank_j - 1];
            boardLeft[blank_i][blank_j - 1] = temptVal;
            Board nBoard = new Board(boardLeft);
            arrBoards.add(nBoard);
        }


        //right?
        if(blank_j < (n-1)) {
            int[][] boardRight = copyTiles();
            int temptVal = boardRight[blank_i][blank_j];
            boardRight[blank_i][blank_j] = boardRight[blank_i][blank_j + 1];
            boardRight[blank_i][blank_j + 1] = temptVal;
            Board nBoard = new Board(boardRight);
            arrBoards.add(nBoard);
        }

        return arrBoards;
    }

    private int[][] copyTiles() {
        int[][] cT = new int[n][n];

        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                cT[i][j] = boardTiles[i][j];
            }
        }
        return cT;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board newB = null;
        int[][] index = new int[2][2];    // [point#][i, j]

        //assuming only 1 exchange of a single pair of tiles
        //if HammingDistance==2, chose these two tiles to swap.
        if(hamming() == 2) {
            int numInd = 0;

            for(int i = 0; i < n; ++i) {
                for(int j = 0; j < n; ++j) {
                    if((boardTiles[i][j] != 0) && (boardTiles[i][j] != (i*n + j + 1))) {
                        index[numInd][0] = i;
                        index[numInd][1] = j;

                        ++numInd;
                    }
                }
            }

        } else if((n >= 3) && (boardTiles[n-1][n-3] != 0) && (boardTiles[n-1][n-2] != 0)) {
            //else chose "last" 2 tiles to switch

            index[0][0] = (n - 1);
            index[0][1] = (n - 3);

            index[1][0] = (n - 1);
            index[1][1] = (n - 2);
        } else {
            //choose the first two tiles that are not the blank tile
            int numInd = 0;

            findTwoTiles: for(int i = 0; i < n; ++i) {
                for(int j = 0; j < n; ++j) {
                    if((boardTiles[i][j] != 0)) {
                        index[numInd][0] = i;
                        index[numInd][1] = j;

                        ++numInd;

                        if(numInd > 2) break findTwoTiles;
                    }
                }
            }
        }


        int[][] newBoardTiles = copyTiles();
        int temp = newBoardTiles[index[0][0]][index[0][1]];
        newBoardTiles[index[0][0]][index[0][1]] = newBoardTiles[index[1][0]][index[1][1]];
        newBoardTiles[index[1][0]][index[1][1]] = temp;
        newB = new Board(newBoardTiles);

        return newB;
    }


    // unit testing (not graded)
    public static void main(String[] args) {
        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);

            //DEBUG only
            System.out.println(initial);
            System.out.println("dim=" + initial.dimension());
            System.out.println("hammingDist=" + initial.hamming());
            System.out.println("manhDist=" + initial.manhattan());

            Iterable<Board> bNeighItable = initial.neighbors();
            Iterator<Board> bNeighItor = bNeighItable.iterator();
            int numBoards = 0;
            while(bNeighItor.hasNext()) {
                bNeighItor.next();
                ++numBoards;
            }
            System.out.println("#neighbors=" + numBoards);
        }
    }
}
