package algorithms.colinear;

import org.junit.Assert;
import org.junit.Test;

public class BruteCollinearPointsTests {
    @Test
    public void segments_returnsSingleExpectedSegment() {
        Point[] points = new Point[]{
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4)
        };

        BruteCollinearPoints brute = new BruteCollinearPoints(points);

        LineSegment[] segments = brute.segments();

        Assert.assertEquals(1, segments.length);
        Assert.assertEquals(1, brute.numberOfSegments());

        String segmentsString = segments[0].toString();

        Assert.assertTrue(segmentsString.equals("(1, 1) -> (4, 4)") || segmentsString.equals("(4, 4) -> (1, 1)"));
    }

    @Test
    public void segments_returnsTwoExpectedSegments() {
        Point[] points = new Point[]{
                new Point(1, 1),

                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4),

                new Point(2, 3),
                new Point(3, 5),
                new Point(4, 7),
        };

        BruteCollinearPoints brute = new BruteCollinearPoints(points);

        LineSegment[] segments = brute.segments();

        Assert.assertEquals(2, segments.length);
        Assert.assertEquals(2, brute.numberOfSegments());
    }
}
