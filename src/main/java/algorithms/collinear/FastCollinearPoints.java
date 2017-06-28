import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] _lineSegments;

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

    public FastCollinearPoints(Point[] points) {
        checkPoints(points);

        Point[] sortedPoints = Arrays.copyOf(points, points.length);

        Arrays.sort(sortedPoints);

        List<Line> lines = new ArrayList<Line>();

        // - 3 - means we observe except last 3 points
        for (int i = 0; i < sortedPoints.length - 3; i++){
            List<Line> newLines = getLines(sortedPoints, i, lines);
            lines.addAll(newLines);
        }

        _lineSegments = CreateLineSegments(lines);
    }

    private List<Line> getLines(Point[] points, int pointIndex, List<Line> lines){
        if(points.length < 4){
            return new ArrayList<Line>();
        }

        Point startPoint = points[pointIndex];

        Point[] slopePoints = Arrays.copyOfRange(points, pointIndex + 1, points.length);

        Arrays.sort(slopePoints, startPoint.slopeOrder());

        List<Line> newLines = new ArrayList<Line>();

        int numPoints = 2;
        Point maxPoint = slopePoints[0];
        double prevSlope = startPoint.slopeTo(maxPoint);

        for(int i = 1; i < slopePoints.length; i++){
            Point point = slopePoints[i];

            double slope = startPoint.slopeTo(point);

            if(Double.compare(slope, prevSlope) == 0) {
                if(point.compareTo(maxPoint) > 0){
                    maxPoint = point;
                }

                numPoints++;
                continue;
            }

            if(numPoints >= 4) {
                if (!hasLine(lines, point, prevSlope)) {
                    Line newLine = new Line(startPoint, maxPoint, prevSlope);
                    newLines.add(newLine);
                }
            }

            prevSlope = slope;
            numPoints = 2;
            maxPoint = point;
        }

        if(numPoints >= 4) {
            if (!hasLine(lines, maxPoint, prevSlope)) {
                Line newLine = new Line(startPoint, maxPoint, prevSlope);
                newLines.add(newLine);
            }
        }

        return newLines;
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

    private LineSegment[] CreateLineSegments(List<Line> lines) {
        LineSegment[] segments = new LineSegment[lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);

            segments[i] = new LineSegment(line.FirstPoint, line.LastPoint);
        }

        return segments;
    }

    private void checkPoints(Point[] points) {
        if(points == null)
            throw new IllegalArgumentException();

        for (Point p : points){
            if( p == null)
                throw new IllegalArgumentException();
        }

        Arrays.sort(points);
        for(int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException();
        }
    }

    private LineSegment createLineSegment(Point startPoint, Index[] sortedPoints, int startIndex, int endIndex){
        int count = endIndex - startIndex + 1;
        if(count >= 3) {
            Point min = startPoint;
            Point max = startPoint;

            for(int i = startIndex; i <= endIndex; i++){
                Point point = sortedPoints[i].Point;
                if(point.compareTo(min) < 0){
                    min = point;
                }
                if(point.compareTo(max) > 0){
                    max = point;
                }
            }

            return new LineSegment(min, max);
        }

        return null;
    }

    private Index[] sortPoints(Point startPoint, Point[] points) {
        Index[] indexes = new Index[points.length - 1];
        int index = 0;

        for (Point p : points) {
            if (p == startPoint) {
                continue;
            }

            double slop = startPoint.slopeTo(p);
            indexes[index] = new Index(p, slop);

            index++;
        }

        Arrays.sort(indexes);

        return indexes;
    }

    public int numberOfSegments() {
        return _lineSegments.length;
    }

    public LineSegment[] segments() {
        return _lineSegments;
    }

    private class Index implements Comparable<Index> {
        public Point Point;
        public double Slope;

        public Index(Point point, double slope) {
            Point = point;
            Slope = slope;
        }

        public int compareTo(Index o) {
            return Double.compare(Slope, o.Slope);
        }
    }
}
