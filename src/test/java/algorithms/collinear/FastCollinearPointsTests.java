import org.junit.Assert;
import org.junit.Test;

public class FastCollinearPointsTests {
    @Test
    public void segments_returnsSingleExpectedSegment() {
        Point[] points = new Point[]{
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4)
        };

        FastCollinearPoints fast = new FastCollinearPoints(points);

        LineSegment[] segments = fast.segments();

        Assert.assertEquals(1, segments.length);
        Assert.assertEquals(1, fast.numberOfSegments());

        String segmentsString = segments[0].toString();

        Assert.assertTrue(segmentsString.equals("(1, 1) -> (4, 4)") || segmentsString.equals("(4, 4) -> (1, 1)"));
    }

    @Test
    public void segments_returnsTwoExpectedSegments() {
        Point[] points = new Point[]{
                new Point(1, 1),
                new Point(2, 3),
                new Point(3, 3),
                new Point(4, 4),
                new Point(3, 5),
                new Point(4, 7),
                new Point(2, 2),
        };

        FastCollinearPoints fast = new FastCollinearPoints(points);

        LineSegment[] segments = fast.segments();

        Assert.assertEquals(2, segments.length);
        Assert.assertEquals(2, fast.numberOfSegments());
    }
}
