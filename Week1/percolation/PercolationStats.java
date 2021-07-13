
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.lang.Math;


public class PercolationStats {
    private double Xt[] = null;
    private int numTrials;
    private int NN; //NN=n*n


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if ((n < 1) || (trials < 1)) throw new IllegalArgumentException();

        numTrials = trials;
        Xt = new double[trials];
        NN = n*n;
        Percolation perc;
        boolean cellOpenStatus[];

        for (int trialNum = 1; trialNum <= trials; ++trialNum) {
            //System.out.print("Trial #" + trialNum + "/" + trials + "...");
            perc = new Percolation(n);
            cellOpenStatus = new boolean[NN];   // index: 0,1,2,...(n^2-1)
            for (int i = 0; i < NN; ++i) cellOpenStatus[i] = false;
            int numOpenSites = 0;

            while(!perc.percolates()) {
                //int max_n = NN - perc.numberOfOpenSites();
                int max_n = NN - numOpenSites;
                int r_ind = StdRandom.uniform(max_n) + 1;   //r_ind: 1,2,3,...,(n^2 - #openSites)
                int r=-1,c=-1;  //init to non-used values

                //get r_ind'th blocked cell
                int cnt=0;
                outer: for (r=1; r<=n; ++r) {
                    for (c=1; c<=n; ++c) {
                        //if (!perc.isOpen(r,c)) ++cnt;
                        if (!cellOpenStatus[(r-1)*n + (c-1)]) ++cnt;
                        if (cnt == r_ind) {
                            break outer;
                        }
                    }
                }
                perc.open(r, c);
                cellOpenStatus[(r-1)*n + (c-1)] = true;
                numOpenSites++;
            }

            Xt[trialNum - 1] = perc.numberOfOpenSites()/((double)NN);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(Xt);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(Xt);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double m = mean();
        double s = stddev();

        return m - 1.96*s/Math.sqrt(numTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double m = mean();
        double s = stddev();

        return m + 1.96*s/Math.sqrt(numTrials);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(20, 10);
        System.out.println("mean = " + ps.mean());
    }


}