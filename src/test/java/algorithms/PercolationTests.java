package algorithms;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PercolationTests {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void Ctor_ZeroOrNegativeN_ThrowsException() {
        exception.expect(IllegalArgumentException.class);

        new Percolation(0);
    }

    @Test
    public void NumberOfOpenSites_NoOpen_ReturnsZero() {
        Percolation percolation = new Percolation(1);

        int numberOpen = percolation.numberOfOpenSites();

        Assert.assertEquals(0, numberOpen);
    }

    @Test
    public void Open_ZeroRow_ThrowsException() {
        exception.expect(IndexOutOfBoundsException.class);

        Percolation percolation = new Percolation(1);
        percolation.open(0, 1);
    }

    @Test
    public void Open_ZeroCol_ThrowsException() {
        exception.expect(IndexOutOfBoundsException.class);

        Percolation percolation = new Percolation(1);
        percolation.open(1, 0);
    }

    @Test
    public void Open_LargeRow_ThrowsException() {
        exception.expect(IndexOutOfBoundsException.class);

        Percolation percolation = new Percolation(1);
        percolation.open(2, 1);
    }

    @Test
    public void Open_LargeCol_ThrowsException() {
        exception.expect(IndexOutOfBoundsException.class);

        Percolation percolation = new Percolation(1);
        percolation.open(1, 2);
    }

    @Test
    public void Open_IsOpenReturnsOpened() {
        Percolation percolation = new Percolation(3);

        percolation.open(3, 2);

        boolean isOpen = percolation.isOpen(3, 2);

        Assert.assertTrue(isOpen);
    }

    @Test
    public void NumberOfOpenSites_OpenFewSites_ReturnsExpectedNumber() {
        Percolation percolation = new Percolation(2);

        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(2, 1);
        percolation.open(2, 1);

        int numberOfOpenSites = percolation.numberOfOpenSites();

        Assert.assertEquals(3, numberOfOpenSites);
    }
}
