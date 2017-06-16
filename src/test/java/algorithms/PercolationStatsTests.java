import org.junit.Assert;
import org.junit.Test;

public class PercolationStatsTests {
    @Test
    public void Mean_OneByOne_ReturnsOne() {
        PercolationStats percolationStats = new PercolationStats(1, 1);

        double mean = percolationStats.mean();

        Assert.assertEquals(1.0, mean, 0.1);
    }

    @Test
    public void Mean_ReturnsValueInPredictedRange() {
        for(int i = 1; i < 3; i++) {
            int n = (int)Math.pow(10, i);
            PercolationStats percolationStats = new PercolationStats(n, 10);

            double mean = percolationStats.mean();

            Assert.assertTrue(mean >= 0.3);

            if( i > 0) {
                Assert.assertTrue(mean <= 0.7);
            }
        }
    }

    @Test
    public void StdDev_OneByOne_ReturnsZero() {
        PercolationStats percolationStats = new PercolationStats(1, 1);

        double stddev = percolationStats.stddev();

        Assert.assertEquals(0.0, stddev, 0.1);
    }

    @Test
    public void StdDev_ReturnsValueInPredictedRange() {
        PercolationStats percolationStats = new PercolationStats(10, 10);

        double stddev = percolationStats.stddev();

        Assert.assertTrue(stddev > 0);
        Assert.assertTrue(stddev <= 0.5);
    }

    @Test
    public void ConfidenceLoConfidenceHi_ReturnsValueInPredictedRange() {
        PercolationStats percolationStats = new PercolationStats(10, 10);

        double confidenceLo = percolationStats.confidenceLo();
        double confidenceHi = percolationStats.confidenceHi();

        Assert.assertTrue(confidenceLo >= 0.3);
        Assert.assertTrue(confidenceLo <= 0.7);

        Assert.assertTrue(confidenceHi >= 0.3);
        Assert.assertTrue(confidenceHi <= 0.7);

        Assert.assertTrue(confidenceHi >= confidenceLo);
    }

    @Test
    public void ConfidenceLoConfidenceHi_OneByOne_ReturnsOne() {
        PercolationStats percolationStats = new PercolationStats(1, 1);

        double confidenceLo = percolationStats.confidenceLo();
        double confidenceHi = percolationStats.confidenceHi();

        Assert.assertEquals(1.0, confidenceLo, 0.1);
        Assert.assertEquals(1.0, confidenceHi, 0.1);
    }
}
