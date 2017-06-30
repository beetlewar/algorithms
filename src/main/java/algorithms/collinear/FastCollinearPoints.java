import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] _lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException();
        }

        ArrayList<Line> lines = new ArrayList<Line>();

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);

        checkPointDuplicates(sortedPoints);

        for (int i = 0; i < sortedPoints.length; i++) {
            FindLines(sortedPoints, i, lines);
        }

        _lineSegments = createLineSegments(lines);
    }

    private void FindLines(Point[] points, int currentPointIndex, List<Line> lines) {
        Point startPoint = points[currentPointIndex];

        Point[] pointsCopy = Arrays.copyOfRange(points, currentPointIndex + 1, points.length);
        Arrays.sort(pointsCopy, startPoint.slopeOrder());

        int index = 0;
        while (index < pointsCopy.length) {
            index = ProcessNextLine(startPoint, pointsCopy, index, lines);
        }
    }

    private int ProcessNextLine(Point startPoint, Point[] sortedPoints, int index, List<Line> lines) {
        int currentIndex = index;

        Line line = null;

        while (currentIndex < sortedPoints.length) {
            Point nextPoint = sortedPoints[currentIndex];

            double slope = startPoint.slopeTo(nextPoint);

            if (line == null) {
                line = new Line(startPoint, slope);
                line.addPoint(nextPoint);
            } else if (line.sameSlope(slope)) {
                line.addPoint(nextPoint);
            } else {
                break;
            }

            currentIndex++;
        }

        if (line != null) {
            addToLinesIfRequired(line, lines);
        }

        return currentIndex;
    }

    private void addToLinesIfRequired(Line line, List<Line> lines) {
        if (line.count() < 4) {
            return;
        }

        for (Line comparingLine : lines) {
            if (comparingLine.theSame(line)) {
                return;
            }
        }

        lines.add(line);
    }

    private LineSegment[] createLineSegments(List<Line> lines) {
        LineSegment[] segments = new LineSegment[lines.size()];

        int index = 0;
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            segments[index] = line.createSegment();
            index++;
        }

        return segments;
    }

    private void checkPointDuplicates(Point[] sortedPoints) {
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0)
                throw new IllegalArgumentException();
        }
    }

    public int numberOfSegments() {
        return _lineSegments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(_lineSegments, _lineSegments.length);
    }

    private class Line {
        private Point _startPoint;
        private Point _tailPoint;
        private int _count;
        private double _slope;

        public Line(Point startPoint, double slope) {
            _startPoint = startPoint;
            _tailPoint = startPoint;
            _slope = slope;
            _count = 1;
        }

        public void addPoint(Point point) {
            _tailPoint = point;
            _count++;
        }

        public boolean sameSlope(double slope) {
            return Double.compare(_slope, slope) == 0;
        }

        public boolean theSame(Line other) {
            if (Double.compare(_slope, other._slope) != 0) {
                return false;
            }

            return _tailPoint.compareTo(other._tailPoint) == 0;
        }

        public int count() {
            return _count;
        }

        public LineSegment createSegment() {
            return new LineSegment(_startPoint, _tailPoint);
        }
    }
}