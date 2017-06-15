package algorithms;

import edu.princeton.cs.algs4.QuickUnionUF;

public class Percolation {
    private int _n;
    private boolean[] _openSites;
    private int _numOpen;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("'n' must be >= 0.");
        }

        _n = n;

        int arraySize = n * n;
        _openSites = new boolean[arraySize];
    }

    public void open(int row, int col) {
        checkRowAndCol(row, col);

        if (isOpen(row, col)) {
            return;
        }

        int index = getArrayIndex(row, col);

        _openSites[index] = true;
        _numOpen++;
    }

    private int getArrayIndex(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;

        int index = rowIndex * _n + colIndex;
        return index;
    }

    private void checkRowAndCol(int row, int col) {
        if (row <= 0 || col <= 0 || row > _n || col > _n) {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean isOpen(int row, int col) {
        checkRowAndCol(row, col);

        int index = getArrayIndex(row, col);
        boolean isOpen = _openSites[index];
        return isOpen;
    }

    public boolean isFull(int row, int col) {
        checkRowAndCol(row, col);

        return false;
    }

    public int numberOfOpenSites() {
        return _numOpen;
    }

    public boolean percolates() {
        return false;
    }
}
