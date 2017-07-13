import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Assert;
import org.junit.Test;

public class PointSETTests {
    @Test
    public void size_containsFev_returnsExpectedSize() {
        PointSET pointSET = new PointSET();

        pointSET.insert(new Point2D(0.1, 0.1));
        pointSET.insert(new Point2D(0.2, 0.2));

        Assert.assertEquals(2, pointSET.size());
    }

    @Test
    public void empty_containsPoints_returnsFalse() {
        PointSET pointSET = new PointSET();

        pointSET.insert(new Point2D(0.1, 0.1));

        Assert.assertFalse(pointSET.isEmpty());
    }

    @Test
    public void empty_empty_returnsTrue() {
        PointSET pointSET = new PointSET();

        Assert.assertTrue(pointSET.isEmpty());
    }

    @Test
    public void contains_doesNotContain_returnsFalse() {
        PointSET pointSet = new PointSET();

        Assert.assertFalse(pointSet.contains(new Point2D(1, 1)));
    }

    @Test
    public void contains_contains_returnsTrue() {
        PointSET pointSet = new PointSET();

        pointSet.insert(new Point2D(0.5, 0.5));

        Assert.assertTrue(pointSet.contains(new Point2D(0.5, 0.5)));
    }

    @Test
    public void nearest_returnsNearestPoint() {
        PointSET pointSET = new PointSET();

        pointSET.insert(new Point2D(0.2, 0.2));
        pointSET.insert(new Point2D(0.5, 0.5));
        pointSET.insert(new Point2D(0.8, 0.8));
        pointSET.insert(new Point2D(0.9, 0.9));

        Point2D nearestPoint = pointSET.nearest(new Point2D(0.55, 0.55));

        Assert.assertEquals(new Point2D(0.5, 0.5), nearestPoint);
    }

    @Test
    public void range_returnsPointInsideTheRectangle() {
        PointSET pointSet = new PointSET();

        pointSet.insert(new Point2D(0, 0));
        pointSet.insert(new Point2D(0.2, 0.2));
        pointSet.insert(new Point2D(0.25, 0.25));
        pointSet.insert(new Point2D(0.31, 0.28));
        pointSet.insert(new Point2D(0.28, 0.31));

        Iterable<Point2D> points = pointSet.range(new RectHV(0.2, 0.2, 0.3, 0.3));

        Assert.assertNotNull(points);

        int count = 0;

        for (Point2D point : points) {
            if (point.equals(new Point2D(0.2, 0.2))) {
            } else if (point.equals(new Point2D(0.25, 0.25))) {
            } else {
                Assert.fail("Point " + point + " not expected");
            }

            count++;
        }

        Assert.assertEquals(2, count);
    }
}
