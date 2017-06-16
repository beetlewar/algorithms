import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF _quickUnionFind;
    private final int _n;
    private final boolean[] _openSites;
    private int _numOpen;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("'n' must be >= 0.");
        }

        _n = n;

        int arraySize = n * n;

        _openSites = new boolean[arraySize];

        int quickUnionFindSize = arraySize + 2; // 2 - additional fake items (top and bottom)

        _quickUnionFind = new WeightedQuickUnionUF(quickUnionFindSize);
    }

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
            _quickUnionFind.union(unionFindIndex, topFakeIndex);
        } else {
            // connect with upper neighbour if it's open
            connectWithNeighbourIfItsOpen(row - 1, col, index);
        }

        if (isBottom(row)) {
            // connect with bottom fake
            int bottomFakeIndex = getBottomFakeIndex();
            _quickUnionFind.union(unionFindIndex, bottomFakeIndex);
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

    public boolean isOpen(int row, int col) {
        checkRowAndCol(row, col);

        int index = getArrayIndex(row, col);
        boolean isOpen = _openSites[index];
        return isOpen;
    }

    public boolean isFull(int row, int col) {
        checkRowAndCol(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        int arrayIndex = getArrayIndex(row, col);
        int topFakeIndex = getTopFakeIndex();

        return _quickUnionFind.connected(arrayIndex, topFakeIndex);
    }

    public int numberOfOpenSites() {
        return _numOpen;
    }

    public boolean percolates() {
        int topFakeIndex = getTopFakeIndex();
        int bottomFakeIndex = getBottomFakeIndex();

        boolean percolates = _quickUnionFind.connected(bottomFakeIndex, topFakeIndex);

        return percolates;
    }

    private void checkRowAndCol(int row, int col) {
        if (row <= 0 || col <= 0 || row > _n || col > _n) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void connectWithNeighbourIfItsOpen(
            int neighbourRow,
            int neighbourCol,
            int ownIndex) {
        boolean neighbourIsOpen = isOpen(neighbourRow, neighbourCol);

        if (neighbourIsOpen) {
            int neighbourArrayIndex = getArrayIndex(neighbourRow, neighbourCol);
            int neighbourUnionFindIndex = getUnionFindIndex(neighbourArrayIndex);
            int ownUnionFindIndex = getUnionFindIndex(ownIndex);

            _quickUnionFind.union(ownUnionFindIndex, neighbourUnionFindIndex);
        }
    }

    private boolean isTop(int row) {
        return row == 1;
    }

    private boolean isBottom(int row) {
        return row == _n;
    }

    private boolean isLeft(int col) {
        return col == 1;
    }

    private boolean isRight(int col) {
        return col == _n;
    }

    private int getArrayIndex(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;

        int index = rowIndex * _n + colIndex;
        return index;
    }

    private static int getUnionFindIndex(int arrayIndex) {
        return arrayIndex + 1;
    }

    private static int getTopFakeIndex() {
        return 0;
    }

    private int getBottomFakeIndex() {
        return _openSites.length + 1;
    }
}