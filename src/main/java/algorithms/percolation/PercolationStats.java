/*-------------------------------------------------------
Prokofev Ilya
6/17/2017
Implements Monte Carlo algorithms via Percolation class
-------------------------------------------------------*/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // sample means for each trial
    private final double[] _sampleMeans;

    /*
    Constructs new object with n by n sites and runs Monte Carlo algorithm
    n - number of sites in a row/col
    trials - number of trials/samples
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        _sampleMeans = new double[trials];

        for (int i = 0; i < trials; i++) {
            double sampleMean = sampleMean(n);
            _sampleMeans[i] = sampleMean;
        }
    }

    /*
    Runs percolation and estimates mean
     */
    private double sampleMean(int n) {
        Percolation percolation = new Percolation(n);

        int numOfSites = n * n;

        int[] randomSites = StdRandom.permutation(numOfSites);

        for (int i = 0; i < randomSites.length; i++) {
            int randomSiteIndex = randomSites[i];

            int row = (randomSiteIndex / n) + 1;
            int col = (randomSiteIndex % n) + 1;

            percolation.open(row, col);

            if (percolation.percolates()) {
                return (i + 1) / (double) numOfSites;
            }
        }

        return 1;
    }

    /*
    Returns mean
     */
    public double mean() {
        return StdStats.mean(_sampleMeans);
    }

    /*
    Returns Stddev
     */
    public double stddev() {
        return StdStats.stddev(_sampleMeans);
    }

    /*
    Returns Confidence Low
     */
    public double confidenceLo() {
        return confidence(-1);
    }

    /*
    Returns Confidence High
     */
    public double confidenceHi() {
        return confidence(1);
    }

    /*
    Returns Confidence (low or high based on sign)
     */
    private double confidence(int sign) {
        double stddev = stddev();
        if (Double.isNaN(stddev)) {
            return Double.NaN;
        }

        double mean = mean();
        double sqrtTrials = Math.sqrt(_sampleMeans.length);

        return mean + (1.96 * stddev / sqrtTrials) * sign;
    }

    /*
    Runs PercolationStats and outputs mean, stddev and confidence to StdOut
    Reads n from arg[0] and trials from arg[1] as integers
     */
    public static void main(String[] args) {
        String nString = args[0];
        int n = Integer.parseInt(nString);

        String trialsString = args[1];
        int trials = Integer.parseInt(trialsString);

        PercolationStats percolationStats = new PercolationStats(n, trials);

        double mean = percolationStats.mean();
        double stddev = percolationStats.stddev();
        double lo = percolationStats.confidenceLo();
        double hi = percolationStats.confidenceHi();

        StdOut.println(mean);
        StdOut.println(stddev);

        String format = String.format("[%f, %f]", lo, hi);
        StdOut.println(format);
    }
}
