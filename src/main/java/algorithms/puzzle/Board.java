package algorithms.puzzle;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int[][] _blocks;
    private final int _hamming;
    private final int _manhattan;
    private int _zeroPos;

    public Board(int[][] blocks) {
        _blocks = blocks;
        _hamming = calculateHamming(blocks);
        _manhattan = calculateManhattan(blocks);
        _zeroPos = getZeroPos(blocks);
    }

    private int calculateHamming(int[][] blocks) {
        int hamming = 0;

        for (int iRow = 0; iRow < blocks.length; iRow++) {
            int[] row = blocks[iRow];
            for (int iCol = 0; iCol < row.length; iCol++) {
                int actualNumber = row[iCol];

                if (actualNumber == 0) {
                    continue;
                }

                int expectedNumber = iRow * blocks.length + iCol + 1;

                if (expectedNumber != actualNumber) {
                    hamming++;
                }
            }
        }

        return hamming;
    }

    private int calculateManhattan(int[][] blocks) {
        int manhattan = 0;

        for (int iRow = 0; iRow < blocks.length; iRow++) {
            int[] row = blocks[iRow];
            for (int iCol = 0; iCol < row.length; iCol++) {
                int actualNumber = row[iCol];

                if (actualNumber == 0) {
                    continue;
                }

                int expectedRow = (actualNumber - 1) / blocks.length;
                int expectedCol = (actualNumber - 1) % blocks.length;

                int deltaRow = Math.abs(expectedRow - iRow);
                int deltaCol = Math.abs(expectedCol - iCol);

                int distance = deltaRow + deltaCol;

                manhattan += distance;
            }
        }

        return manhattan;
    }

    private int getZeroPos(int[][] blocks) {
        int index = 0;

        for (int iRow = 0; iRow < blocks.length; iRow++) {
            for (int iCol = 0; iCol < blocks.length; iCol++) {
                if (blocks[iRow][iCol] == 0) {
                    return index;
                }
                index++;
            }
        }

        return -1;
    }

    public int dimension() {
        return _blocks.length;
    }

    public int hamming() {
        return _hamming;
    }

    public int manhattan() {
        return _manhattan;
    }

    public boolean isGoal() {
        return _hamming == 0;
    }

    public Board twin() {
        int iRow1, iCol1, iRow2, iCol2;

        if(_blocks[0][0] != 0)
        {
            iRow1 = 0;
            iCol1 = 0;
        }
        else {
            iRow1 = 0;
            iCol1 = 1;
        }

        if(_blocks[1][0] != 0)
        {
            iRow2 = 1;
            iCol2 = 0;
        }
        else {
            iRow2 = 1;
            iCol2 = 1;
        }

        int[][] copy = copyAndExchange(_blocks, iRow1, iCol1, iRow2, iCol2);

        return new Board(copy);
    }

    public boolean equals(Object y) {
        Board board = (y instanceof Board ? (Board) y : null);

        if (board == null) {
            return false;
        }

        return _zeroPos == board._zeroPos && _hamming == board._hamming && dimension() == board.dimension();
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>(4);

        int iRow = _zeroPos / dimension();
        int iCol = _zeroPos % dimension();

        if (iRow > 0) {
            // create top neighbor
            int[][] topBlocks = copyAndExchange(_blocks, iRow, iCol, iRow - 1, iCol);
            neighbors.add(new Board(topBlocks));
        }
        if (iRow < dimension() - 1) {
            // create bottom neighbor
            int[][] bottomBlocks = copyAndExchange(_blocks, iRow, iCol, iRow + 1, iCol);
            neighbors.add(new Board(bottomBlocks));
        }
        if (iCol > 0) {
            // create left neighbor
            int[][] leftBlocks = copyAndExchange(_blocks, iRow, iCol, iRow, iCol - 1);
            neighbors.add(new Board(leftBlocks));
        }
        if (iCol < dimension() - 1) {
            // create right neighbor
            int[][] rightBlocks = copyAndExchange(_blocks, iRow, iCol, iRow, iCol + 1);
            neighbors.add(new Board(rightBlocks));
        }

        return neighbors;
    }

    private int[][] copyAndExchange(int[][] blocks, int iRow1, int iCol1, int iRow2, int iCol2) {
        int[][] blockCopy = copyBlocks(blocks);

        int tmp = blockCopy[iRow1][iCol1];
        blockCopy[iRow1][iCol1] = blockCopy[iRow2][iCol2];
        blockCopy[iRow2][iCol2] = tmp;

        return blockCopy;
    }

    private int[][] copyBlocks(int[][] blocks) {
        int[][] copy = new int[blocks.length][];

        for (int i = 0; i < blocks.length; i++) {
            copy[i] = Arrays.copyOf(blocks[i], blocks[i].length);
        }

        return copy;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(dimension());

        for (int[] row : _blocks) {
            stringBuilder.append("\n");
            for (int col : row) {
                stringBuilder.append(String.format("%2d ", col));
            }
        }

        return stringBuilder.toString();
    }
}
