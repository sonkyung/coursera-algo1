
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.lang.Math;


public class PercolationStats {
    private double Xt[] = null;
    private int numTrials;
    private int NN; //NN=n*n


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if ((n <= 0) || (trials <= 0)) throw new IllegalArgumentException();

        numTrials = trials;
        Xt = new double[trials];
        NN = n*n;

        for (int trialNum = 1; trialNum <= trials; ++trialNum) {
            //System.out.print("Trial #" + trialNum + "/" + trials + "...");
            Percolation perc = new Percolation(n);

            while(!perc.percolates()) {
                int max_n = NN - perc.numberOfOpenSites();
                int r_ind = StdRandom.uniform(max_n) + 1;
                int r=0, c=0;

                //get r_ind'th blocked cell
                int cnt=0;
                outer: for (r=1; r<=n; ++r) {
                    for (c=1; c<=n; ++c) {
                        if (!perc.isOpen(r,c)) ++cnt;
                        if (cnt == r_ind) {
                            break outer;
                        }
                    }
                }
                perc.open(r, c);
                //System.out.print("open(" + r + "," + c + ").");
            }

            Xt[trialNum - 1] = perc.numberOfOpenSites()/((double)NN);
            //System.out.println("done.");
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
        try {
            PercolationStats ps = new PercolationStats(100, 5);
            //System.out.println(ps.mean());
            //System.out.println(ps.stddev());
        } catch(IllegalArgumentException e) {
            //e.printStackTrace();
        }

    }


}