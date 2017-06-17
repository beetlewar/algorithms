/*-------------------------------------------------------
Prokofev Ilya
6/17/2017
Implements percolation algorithms, that allows to determine
connectivity bottom points to top
-------------------------------------------------------*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // object to determine percolation
    private final WeightedQuickUnionUF _percolationQuickUnionFind;

    // object to determine site fullness
    private final WeightedQuickUnionUF _fullnessQuickUnionFind;

    // number of sites in a row/col
    private final int _n;

    // array of open sites - site at index is open if it's set to true
    private final boolean[] _openSites;

    // current number of open sites
    private int _numOpen;

    /*
    Constructs new object with n by n sites
    Top left is row = 1 and col = 1 and bottom right is row = n and col = n
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("'n' must be >= 0.");
        }

        _n = n;

        int arraySize = n * n;
        _openSites = new boolean[arraySize];

        int quickUnionFindSize = arraySize + 2; // 2 - additional fake items (top and bottom)
        _percolationQuickUnionFind = new WeightedQuickUnionUF(quickUnionFindSize);

        int fullnessQuickUnionFindSize = arraySize + 1; // 1 - additional fake item (top)
        _fullnessQuickUnionFind = new WeightedQuickUnionUF(fullnessQuickUnionFindSize);
    }

    /*
    Opens new item (site) in a row and col position.
     */
    public void open(int row, int col) {
        checkRowAndCol(row, col);

        if (isOpen(row, col)) {
            return;
        }

        int index = getArrayIndex(row, col);

        _openSites[index] = true;
        _numOpen++;

        int unionFindIndex = getUnionFindIndex(index);

        if (isTop(row)) {
            // connect with top fake
            int topFakeIndex = getTopFakeIndex();
            _percolationQuickUnionFind.union(unionFindIndex, topFakeIndex);
            _fullnessQuickUnionFind.union(unionFindIndex, topFakeIndex);
        } else {
            // connect with upper neighbour if it's open
            connectWithNeighbourIfItsOpen(row - 1, col, index);
        }

        if (isBottom(row)) {
            // connect with bottom fake
            int bottomFakeIndex = getBottomFakeIndex();
            _percolationQuickUnionFind.union(unionFindIndex, bottomFakeIndex);
        } else {
            // connect with bottom neighbour if it's open
            connectWithNeighbourIfItsOpen(row + 1, col, index);
        }

        if (!isLeft(col)) {
            // connect with left neighbour if it's open
            connectWithNeighbourIfItsOpen(row, col - 1, index);
        }

        if (!isRight(col)) {
            // connect with right neighbour if it's open
            connectWithNeighbourIfItsOpen(row, col + 1, index);
        }
    }

    /*
    Returns true if site is open at row and col position
     */
    public boolean isOpen(int row, int col) {
        checkRowAndCol(row, col);

        int index = getArrayIndex(row, col);
        return _openSites[index];
    }

    /*
    Returns true if site is full (connected with any top site) at row and col position
     */
    public boolean isFull(int row, int col) {
        checkRowAndCol(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        int arrayIndex = getArrayIndex(row, col);
        int unionFindIndex = getUnionFindIndex(arrayIndex);
        int topFakeIndex = getTopFakeIndex();

        return _fullnessQuickUnionFind.connected(unionFindIndex, topFakeIndex);
    }

    /*
    Returns number of open sites
     */
    public int numberOfOpenSites() {
        return _numOpen;
    }

    /*
    Returns true if system percolates
     */
    public boolean percolates() {
        int topFakeIndex = getTopFakeIndex();
        int bottomFakeIndex = getBottomFakeIndex();

        return _percolationQuickUnionFind.connected(bottomFakeIndex, topFakeIndex);
    }

    /*
    Checks row and col parameters and throws exception if they are invalid
     */
    private void checkRowAndCol(int row, int col) {
        if (row <= 0 || col <= 0 || row > _n || col > _n) {
            throw new IndexOutOfBoundsException();
        }
    }

    /*
    Connects specified site with neighbour if it's open
     */
    private void connectWithNeighbourIfItsOpen(
            int neighbourRow,
            int neighbourCol,
            int ownIndex) {
        boolean neighbourIsOpen = isOpen(neighbourRow, neighbourCol);

        if (neighbourIsOpen) {
            int neighbourArrayIndex = getArrayIndex(neighbourRow, neighbourCol);
            int neighbourUnionFindIndex = getUnionFindIndex(neighbourArrayIndex);
            int ownUnionFindIndex = getUnionFindIndex(ownIndex);

            _percolationQuickUnionFind.union(ownUnionFindIndex, neighbourUnionFindIndex);
            _fullnessQuickUnionFind.union(ownUnionFindIndex, neighbourUnionFindIndex);
        }
    }

    /*
    Returns true if row is top
     */
    private boolean isTop(int row) {
        return row == 1;
    }

    /*
    Returns true if row is bottom
     */
    private boolean isBottom(int row) {
        return row == _n;
    }

    /*
    Returns true if col is left
     */
    private boolean isLeft(int col) {
        return col == 1;
    }

    /*
    Returns true if col is right
     */
    private boolean isRight(int col) {
        return col == _n;
    }

    /*
    Returns open site array index
     */
    private int getArrayIndex(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;

        return rowIndex * _n + colIndex;
    }

    /*
    Returns index of union find object array
     */
    private static int getUnionFindIndex(int arrayIndex) {
        return arrayIndex + 1;
    }

    /*
    Returns index of top fake site of union find array
     */
    private static int getTopFakeIndex() {
        return 0;
    }

    /*
    Returns index of bottom fake site of union find array
     */
    private int getBottomFakeIndex() {
        return _openSites.length + 1;
    }
}