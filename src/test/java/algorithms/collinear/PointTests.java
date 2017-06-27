import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;

public class PointTests {
    @Test
    public void compareTo_equal_returnsZero() {
        Point thisPoint = new Point(1, 1);
        Point thatPoint = new Point(1, 1);

        Assert.assertEquals(0, thisPoint.compareTo(thatPoint));
    }

    @Test
    public void compareTo_lessByY_returnsNegative() {
        Point thisPoint = new Point(1, 2);
        Point thatPoint = new Point(1, 3);

        Assert.assertEquals(-1, thisPoint.compareTo(thatPoint));
    }

    @Test
    public void compareTo_equalByYLessByX_returnsNegative() {
        Point thisPoint = new Point(1, 3);
        Point thatPoint = new Point(2, 3);

        Assert.assertEquals(-1, thisPoint.compareTo(thatPoint));
    }

    @Test
    public void compareTo_equalByYBiggerByX_returnsPositive() {
        Point thisPoint = new Point(2, 3);
        Point thatPoint = new Point(1, 3);

        Assert.assertEquals(1, thisPoint.compareTo(thatPoint));
    }

    @Test
    public void compareTo_biggerByY_returnsPositive() {
        Point thisPoint = new Point(1, 3);
        Point thatPoint = new Point(1, 2);

        Assert.assertEquals(1, thisPoint.compareTo(thatPoint));
    }

    @Test
    public void slopeTo_returnsSlopeToThatPoint() {
        Point thisPoint = new Point(2, 1);
        Point thatPoint = new Point(4, 2);

        Assert.assertEquals(0.5, thisPoint.slopeTo(thatPoint), 0.1);
    }

    @Test
    public void slopeTo_horizontal_positiveZero() {
        Point thisPoint = new Point(2, 1);
        Point thatPoint = new Point(4, 1);

        Assert.assertEquals(0, Double.compare(0.0, thisPoint.slopeTo(thatPoint)));
    }

    @Test
    public void slopeTo_vertical_positiveInfinity() {
        Point thisPoint = new Point(1, 2);
        Point thatPoint = new Point(1, 1);

        Assert.assertEquals(0, Double.compare(Double.POSITIVE_INFINITY, thisPoint.slopeTo(thatPoint)));
    }

    @Test
    public void slopeTo_equalPoints_negativeInfinity() {
        Point point = new Point(1, 2);

        Assert.assertEquals(0, Double.compare(Double.NEGATIVE_INFINITY, point.slopeTo(point)));
    }

    @Test
    public void slopeOrder_firstPointSlopeLessSeconds_returnsNegative() {
        Point thisPoint = new Point(1, 1);
        Point thatPoint1 = new Point(3, 2);
        Point thatPoint2 = new Point(2, 4);

        Comparator<Point> comparator = thisPoint.slopeOrder();
        int slopeOrder = comparator.compare(thatPoint1, thatPoint2);

        Assert.assertEquals(-1, slopeOrder);
    }

    @Test
    public void slopeOrder_firstPointSlopeBiggerSeconds_returnsPositive() {
        Point thisPoint = new Point(1, 1);
        Point thatPoint1 = new Point(2, 4);
        Point thatPoint2 = new Point(3, 2);

        Comparator<Point> comparator = thisPoint.slopeOrder();
        int slopeOrder = comparator.compare(thatPoint1, thatPoint2);

        Assert.assertEquals(1, slopeOrder);
    }

    @Test
    public void slopeOrder_slopesEqual_returnsZero() {
        Point thisPoint = new Point(1, 1);
        Point thatPoint1 = new Point(2, 2);
        Point thatPoint2 = new Point(3, 3);

        Comparator<Point> comparator = thisPoint.slopeOrder();
        int slopeOrder = comparator.compare(thatPoint1, thatPoint2);

        Assert.assertEquals(0, slopeOrder);
    }

    @Test
    public void slopeOrder_secondVertical_returnsNegative() {
        Point thisPoint = new Point(1, 1);
        Point thatPoint1 = new Point(3, 3);
        Point thatPoint2 = new Point(1, 2);

        Comparator<Point> comparator = thisPoint.slopeOrder();
        int slopeOrder = comparator.compare(thatPoint1, thatPoint2);

        Assert.assertEquals(-1, slopeOrder);
    }

    @Test
    public void slopeOrder_firstVertical_returnsPositive() {
        Point thisPoint = new Point(1, 1);
        Point thatPoint1 = new Point(1, 2);
        Point thatPoint2 = new Point(3, 3);

        Comparator<Point> comparator = thisPoint.slopeOrder();
        int slopeOrder = comparator.compare(thatPoint1, thatPoint2);

        Assert.assertEquals(1, slopeOrder);
    }

    @Test
    public void slopeOrder_bothVertical_returnsZero() {
        Point thisPoint = new Point(1, 1);
        Point thatPoint1 = new Point(1, 2);
        Point thatPoint2 = new Point(1, 3);

        Comparator<Point> comparator = thisPoint.slopeOrder();
        int slopeOrder = comparator.compare(thatPoint1, thatPoint2);

        Assert.assertEquals(0, slopeOrder);
    }
}
