package algorithms.puzzle;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class BoardTests {
    @Test
    public void dimension_returnsExpectedDimension() {
        Board board = new Board(new int[][]{new int[]{0, 1}, new int[]{2, 3}});

        Assert.assertEquals(2, board.dimension());
    }

    @Test
    public void hamming_case1_returnsExpectedHamming() {
        Board board = new Board(new int[][]{
                new int[]{2, 1},
                new int[]{3, 1}});

        Assert.assertEquals(3, board.hamming());
    }

    @Test
    public void hamming_case2_returnsExpectedHamming() {
        Board board = new Board(new int[][]{
                new int[]{1, 2},
                new int[]{0, 3}});

        Assert.assertEquals(1, board.hamming());
    }

    @Test
    public void manhattan_returnsExpectedManhattan() {
        Board board = new Board(new int[][]{
                new int[]{8, 1, 3},
                new int[]{4, 0, 2},
                new int[]{7, 6, 5}});

        Assert.assertEquals(10, board.manhattan());
    }

    @Test
    public void equals_differentBoards_returnsFalse() {
        Board x = new Board(new int[][]{
                new int[]{1, 0},
                new int[]{2, 3}});

        Board y = new Board(new int[][]{
                new int[]{0, 1},
                new int[]{2, 3}});

        Assert.assertFalse(x.equals(y));
    }

    @Test
    public void equals_equalBoards_returnsTrue() {
        Board x = new Board(new int[][]{
                new int[]{0, 1},
                new int[]{2, 3}});

        Board y = new Board(new int[][]{
                new int[]{0, 1},
                new int[]{2, 3}});

        Assert.assertTrue(x.equals(y));
    }

    @Test
    public void neighbors_returnsExpectedBoards() {
        Board board = new Board(new int[][]{
                new int[]{8, 1, 3},
                new int[]{4, 2, 0},
                new int[]{7, 6, 5}});

        Iterator<Board> boards = board.neighbors().iterator();

        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;

        while (boards.hasNext()) {
            Board nextBoard = boards.next();

            if (nextBoard.equals(new Board(new int[][]{
                    new int[]{8, 1, 0},
                    new int[]{4, 2, 3},
                    new int[]{7, 6, 5}}))) {
                b1 = true;
            } else if (nextBoard.equals(new Board(new int[][]{
                    new int[]{8, 1, 3},
                    new int[]{4, 0, 2},
                    new int[]{7, 6, 5}}))) {
                b2 = true;
            } else if (nextBoard.equals(new Board(new int[][]{
                    new int[]{8, 1, 3},
                    new int[]{4, 2, 5},
                    new int[]{7, 6, 0}}))) {
                b3 = true;
            } else {
                Assert.fail("Boards not expected");
            }
        }

        Assert.assertTrue(b1);
        Assert.assertTrue(b2);
        Assert.assertTrue(b3);
    }

    @Test
    public void isGoal_notGoal_returnsFalse() {
        Board board = new Board(new int[][]{
                new int[]{0, 1},
                new int[]{2, 3}});

        Assert.assertFalse(board.isGoal());
    }

    @Test
    public void isGoal_goal_returnsTrue() {
        Board board = new Board(new int[][]{
                new int[]{1, 2},
                new int[]{3, 0}});

        Assert.assertTrue(board.isGoal());
    }

    @Test
    public void toString_returnsExpectedString() {
        Board board = new Board(new int[][]{
                new int[]{0, 1},
                new int[]{3, 2}});

        String boardString = board.toString();

        String expectedString = "2\n 0  1 \n 3  2 ";

        Assert.assertEquals(expectedString, boardString);
    }

    @Test
    public void twin_case1_returnsExpectedBoard() {
        Board board = new Board(new int[][]{
                new int[]{0, 1},
                new int[]{3, 2}});

        Board twinBoard = board.twin();

        Assert.assertEquals(
                new Board(new int[][]{
                        new int[]{0, 3},
                        new int[]{1, 2}}),
                twinBoard);
    }

    @Test
    public void twin_case2_returnsExpectedBoard() {
        Board board = new Board(new int[][]{
                new int[]{1, 2},
                new int[]{3, 0}});

        Board twinBoard = board.twin();

        Assert.assertEquals(
                new Board(new int[][]{
                        new int[]{3, 2},
                        new int[]{1, 0}}),
                twinBoard);
    }

    @Test
    public void twin_case3_returnsExpectedBoard() {
        Board board = new Board(new int[][]{
                new int[]{1, 0},
                new int[]{2, 3}});

        Board twinBoard = board.twin();

        Assert.assertEquals(
                new Board(new int[][]{
                        new int[]{2, 0},
                        new int[]{1, 3}}),
                twinBoard);
    }
}
