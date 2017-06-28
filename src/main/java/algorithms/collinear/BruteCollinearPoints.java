import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] _segments;

    public BruteCollinearPoints(Point[] points) {
        checkPoints(points);

        Point[] sortedPoints = Arrays.copyOf(points, points.length);

        Arrays.sort(sortedPoints);

        List<Line> lines = new ArrayList<Line>();

        // - 3 - means we observe except last 3 points
        for (int i = 0; i < sortedPoints.length - 3; i++) {
            for (int j = i + 1; j < sortedPoints.length; j++) {
                Line line = getLine(sortedPoints, i, j, lines);
                if (line != null) {
                    lines.add(line);
                }
            }
        }

        _segments = CreateLineSegments(lines);
    }

    private Line getLine(Point[] points, int startIndex, int nextIndex, Iterable<Line> lines) {
        Point startPoint = points[startIndex];
        Point nextPoint = points[nextIndex];

        double slope = startPoint.slopeTo(nextPoint);

        Point lastPoint = null;
        int numSlopePoints = 2;

        for (int i = startIndex + 2; i < points.length; i++) {
            Point testPoint = points[i];
            double testSlope = startPoint.slopeTo(testPoint);

            if (Double.compare(slope, testSlope) == 0) {
                lastPoint = testPoint;
                numSlopePoints++;
            }
        }

        if (numSlopePoints < 4) {
            return null;
        }

        if (hasLine(lines, lastPoint, slope)) {
            return null;
        }

        return new Line(startPoint, lastPoint, slope);
    }

    private LineSegment[] CreateLineSegments(List<Line> lines) {
        LineSegment[] segments = new LineSegment[lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);

            segments[i] = new LineSegment(line.FirstPoint, line.LastPoint);
        }

        return segments;
    }

    private boolean hasLine(
            Iterable<Line> lines,
            Point lastPoint,
            double slope) {
        for (Line line : lines) {
            if (line.LastPoint == lastPoint && Double.compare(line.Slope, slope) == 0) {
                return true;
            }
        }

        return false;
    }

    private void checkPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException();
        }

        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException();
        }
    }

    public int numberOfSegments() {
        return _segments.length;
    }

    public LineSegment[] segments() {
        return _segments;
    }

    private class Line {
        public Point FirstPoint;
        public Point LastPoint;
        public double Slope;

        public Line(Point firstPoint, Point lastPoint, double slope) {
            FirstPoint = firstPoint;
            LastPoint = lastPoint;
            Slope = slope;
        }
    }
}

