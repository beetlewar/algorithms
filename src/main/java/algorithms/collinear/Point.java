import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int _x;
    private final int _y;

    public Point(int x, int y) {
        _x = x;
        _y = y;
    }

    public void draw() {
        StdDraw.point(_x, _y);
    }

    public void drawTo(Point that) {
        StdDraw.line(_x, _y, that._x, that._y);
    }

    public String toString() {
        return "(" + _x + ", " + _y + ")";
    }

    public int compareTo(Point that) {
        if (_y < that._y) {
            return -1;
        } else if (_y > that._y) {
            return 1;
        } else if (_y == that._y) {
            if (_x < that._x) {
                return -1;
            } else if (_x > that._x) {
                return 1;
            }
        }

        return 0;
    }

    public double slopeTo(Point that) {
        if (compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        if (_x == that._x) {
            return Double.POSITIVE_INFINITY;
        }

        if(_y == that._y){
            return +0;
        }

        return (that._y - _y) / (double) (that._x - _x);
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeComparator(this);
    }

    private class SlopeComparator implements Comparator<Point> {
        Point _thisPoint;

        public SlopeComparator(Point thisPoint) {
            _thisPoint = thisPoint;
        }

        public int compare(Point p1, Point p2) {
            double slope1 = _thisPoint.slopeTo(p1);
            double slope2 = _thisPoint.slopeTo(p2);

            return Double.compare(slope1, slope2);
        }
    }
}
