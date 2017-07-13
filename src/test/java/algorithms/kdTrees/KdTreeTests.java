import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Assert;
import org.junit.Test;

public class KdTreeTests {
    @Test
    public void size_containsFev_returnsExpectedSize() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(0.2, 0.2));

        Assert.assertEquals(2, kdTree.size());
    }

    @Test
    public void empty_containsPoints_returnsFalse() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.1, 0.1));

        Assert.assertFalse(kdTree.isEmpty());
    }

    @Test
    public void empty_empty_returnsTrue() {
        KdTree kdTree = new KdTree();

        Assert.assertTrue(kdTree.isEmpty());
    }

    @Test
    public void contains_doesNotContain_returnsFalse() {
        KdTree kdTree = new KdTree();

        Assert.assertFalse(kdTree.contains(new Point2D(1, 1)));
    }

    @Test
    public void contains_contains_returnsTrue() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.5, 0.5));

        Assert.assertTrue(kdTree.contains(new Point2D(0.5, 0.5)));
    }

    @Test
    public void nearest_returnsNearestPoint() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.2, 0.2));
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.8, 0.8));
        kdTree.insert(new Point2D(0.9, 0.9));

        Point2D nearestPoint = kdTree.nearest(new Point2D(0.55, 0.55));

        Assert.assertEquals(new Point2D(0.5, 0.5), nearestPoint);
    }

    @Test
    public void range_returnsPointInsideTheRectangle() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(0.2, 0.2));
        kdTree.insert(new Point2D(0.25, 0.25));
        kdTree.insert(new Point2D(0.31, 0.28));
        kdTree.insert(new Point2D(0.28, 0.31));

        Iterable<Point2D> points = kdTree.range(new RectHV(0.2, 0.2, 0.3, 0.3));

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

    @Test
    public void insert_useCase1_returnsExpected() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.7, 0.3));
        kdTree.insert(new Point2D(0.75, 0.2));

        Assert.assertTrue(kdTree.contains(new Point2D(0.5, 0.5)));
        Assert.assertTrue(kdTree.contains(new Point2D(0.7, 0.3)));
        Assert.assertTrue(kdTree.contains(new Point2D(0.75, 0.2)));
    }

    @Test
    public void insert_useCase2_returnsExpected() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.6, 0.8));
        kdTree.insert(new Point2D(0.7, 0.8));

        Assert.assertTrue(kdTree.contains(new Point2D(0.5, 0.5)));
        Assert.assertTrue(kdTree.contains(new Point2D(0.6, 0.8)));
        Assert.assertTrue(kdTree.contains(new Point2D(0.7, 0.8)));
    }
}
