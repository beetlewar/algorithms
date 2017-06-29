import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BruteCollinearPointsTests {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void ctor_equalPoints_throwsException() {
        exception.expect(IllegalArgumentException.class);

        Point[] points = new Point[]{
                new Point(1, 1),
                new Point(1, 1)
        };

        new BruteCollinearPoints(points);
    }

    @Test
    public void ctor_doesNotMutatePoints() {
        Point[] points = new Point[]{
                new Point(4, 5),
                new Point(9, 5),
                new Point(7, 2)
        };

        new BruteCollinearPoints(points);

        Assert.assertEquals(0, new Point(4, 5).compareTo(points[0]));
        Assert.assertEquals(0, new Point(9, 5).compareTo(points[1]));
        Assert.assertEquals(0, new Point(7, 2).compareTo(points[2]));
    }

    @Test
    public void segments_emptyPoints_returnsEmptyArray(){
        Point[] points = new Point[0];

        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        LineSegment[] segments = brute.segments();

        Assert.assertEquals(0, segments.length);
    }

    @Test
    public void segments_noSegments_returnsEmptyArray(){
        Point[] points = new Point[]{
                new Point(1, 1),
                new Point(2, 2)
        };

        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        LineSegment[] segments = brute.segments();

        Assert.assertEquals(0, segments.length);
    }

    @Test
    public void segments_case10_returnsExpectedSegments() {
        Point[] points = new Point[]{
                new Point(4, 5),
                new Point(9, 5),
                new Point(7, 2),
                new Point(0, 0),
                new Point(4, 0),
                new Point(5, 4),
                new Point(8, 5),
                new Point(6, 3),
                new Point(9, 0),
                new Point(8, 0)
        };

        BruteCollinearPoints brute = new BruteCollinearPoints(points);

        LineSegment[] segments = brute.segments();

        Assert.assertEquals(2, segments.length);

        assertContainsSegment("(0, 0) -> (9, 0)", segments);
        assertContainsSegment("(9, 0) -> (4, 5)", segments);
    }

    private void assertContainsSegment(String expectedSegmentString, LineSegment[] segments){
        boolean contains = false;

        for(LineSegment segment : segments){
            if(expectedSegmentString.equals(segment.toString())){
                contains = true;
                break;
            }
        }

        Assert.assertTrue(contains);
    }

    @Test
    public void segments_case20_returnsExpectedSegments() {
        Point[] points = new Point[]{
                new Point(4096, 20992),
                new Point(5120, 20992),
                new Point(6144, 20992),
                new Point(7168, 20992),
                new Point(8128, 20992),
                new Point(4096, 22016),
                new Point(4096, 23040),
                new Point(4096, 24064),
                new Point(4096, 25088),
                new Point(5120, 25088),
                new Point(7168, 25088),
                new Point(8192, 25088),
                new Point(8192, 26112),
                new Point(8192, 27136),
                new Point(8192, 28160),
                new Point(8192, 29184),
                new Point(4160, 29184),
                new Point(5120, 29184),
                new Point(6144, 29184),
                new Point(7168, 29184)
        };

        BruteCollinearPoints brute = new BruteCollinearPoints(points);

        LineSegment[] segments = brute.segments();

        Assert.assertEquals(5, segments.length);

        assertContainsSegment("(4096, 20992) -> (8128, 20992)", segments);
        assertContainsSegment("(4096, 20992) -> (4096, 25088)", segments);
        assertContainsSegment("(4096, 25088) -> (8192, 25088)", segments);
        assertContainsSegment("(8192, 25088) -> (8192, 29184)", segments);
        assertContainsSegment("(4160, 29184) -> (8192, 29184)", segments);
    }
}
