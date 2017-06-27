import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] _lineSegments;

    public FastCollinearPoints(Point[] points) {
        checkPoints(points);

        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        LineSegment[] lineSegments = new LineSegment[pointsCopy.length];
        int lineIndex = 0;

        for (Point point : pointsCopy) {
            Index[] sortedPoints = sortPoints(point, pointsCopy);

            double currentSlope = sortedPoints[0].Slope;
            int startIndexOfIndex = 0;

            for (int i = 1; i <= sortedPoints.length; i++) {
                LineSegment newLineSegment = null;

                if (i == sortedPoints.length) {
                    newLineSegment = createLineSegment(point, sortedPoints, startIndexOfIndex, i - 1);
                } else {
                    Index index = sortedPoints[i];

                    if (Double.compare(currentSlope, index.Slope) != 0) {
                        newLineSegment = createLineSegment(point, sortedPoints, startIndexOfIndex, i - 1);
                        startIndexOfIndex = i;
                        currentSlope = index.Slope;
                    }
                }

                if(newLineSegment != null){
                    boolean hasSegment = false;
                    // search distinct segments
                    for(int j = 0; j < lineIndex; j++){
                        LineSegment distinctSegment = lineSegments[j];
                        if(newLineSegment.toString().equals(distinctSegment.toString())){
                            hasSegment = true;
                            break;
                        }
                    }

                    if(!hasSegment) {
                        lineSegments[lineIndex] = newLineSegment;
                        lineIndex++;
                    }
                }
            }
        }

        _lineSegments = new LineSegment[lineIndex];
        for(int i = 0; i < lineIndex; i++){
            _lineSegments[i] = lineSegments[i];
        }
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
