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

        for (Point p : points) {
            FindLines(points, p, lines);
        }

        _lineSegments = createLineSegments(lines);
    }

    private void FindLines(Point[] points, Point currentPoint, List<Line> lines) {
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy, currentPoint.slopeOrder());

        int index = 1;
        while (index < pointsCopy.length) {
            index = ProcessNextLine(pointsCopy, index, lines);
        }
    }

    private int ProcessNextLine(Point[] sortedPoints, int index, List<Line> lines) {
        Point startPoint = sortedPoints[0];

        int currentIndex = index;

        Line line = null;

        while (currentIndex < sortedPoints.length) {
            Point nextPoint = sortedPoints[currentIndex];
            currentIndex++;

            double slope = startPoint.slopeTo(nextPoint);

            if (line == null) {
                line = new Line(startPoint, slope);
                line.addPoint(nextPoint);
            } else if (line.sameSlope(slope)) {
                line.addPoint(nextPoint);
            } else {
                break;
            }
        }

        if (line != null) {
            line.addToLinesIfRequired(lines);
        }

        return currentIndex;
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

//    private void checkPointDuplicates(Point[] sortedPoints) {
//        for (int i = 0; i < sortedPoints.length - 1; i++) {
//            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0)
//                throw new IllegalArgumentException();
//        }
//    }

    public int numberOfSegments() {
        return _lineSegments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(_lineSegments, _lineSegments.length);
    }

    private class Line {
        private ArrayList<Point> _points;
        private double _slope;

        public Line(Point startPoint, double slope) {
            _points = new ArrayList<Point>(4);
            _slope = slope;

            addPoint(startPoint);
        }

        public void addPoint(Point point) {
            _points.add(point);
        }

        public boolean sameSlope(double slope) {
            return Double.compare(_slope, slope) == 0;
        }

        public void addToLinesIfRequired(List<Line> lines) {
            if (_points.size() < 4) {
                return;
            }

            for (Line line : lines) {
                if (theSame(line)) {
                    return;
                }
            }

            lines.add(this);
        }

        private boolean theSame(Line other) {
            if (_points.size() != other._points.size()) {
                return false;
            }

            if (Double.compare(_slope, other._slope) != 0) {
                return false;
            }

            Point thisOrigin = getOrigin();
            Point otherOrigin = other.getOrigin();

            return thisOrigin.compareTo(otherOrigin) == 0;
        }

        private Point getOrigin() {
            Point origin = _points.get(0);

            for (int i = 1; i < _points.size(); i++) {
                Point point = _points.get(i);

                if (point.compareTo(origin) < 0) {
                    origin = point;
                }
            }

            return origin;
        }

        private Point getTail() {
            Point tail = _points.get(0);

            for (int i = 1; i < _points.size(); i++) {
                Point point = _points.get(i);

                if (point.compareTo(tail) > 0) {
                    tail = point;
                }
            }

            return tail;
        }

        public LineSegment createSegment() {
            return new LineSegment(getOrigin(), getTail());
        }
    }
}