package algorithms.kdTrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> _set = new TreeSet<Point2D>();

    public boolean isEmpty() {
        return _set.isEmpty();
    }

    public int size() {
        return _set.size();
    }

    public void insert(Point2D p) {
        _set.add(p);
    }

    public boolean contains(Point2D p) {
        return _set.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> points = new ArrayList<Point2D>();

        for (Point2D point : _set) {
            if (isInRect(point, rect)) {
                points.add(point);
            }
        }

        return points;
    }

    private static boolean isInRect(Point2D point, RectHV rect) {
        return rect.contains(point);
    }

    public Point2D nearest(Point2D p) {
        Point2D minPoint = null;
        double minDist = Double.POSITIVE_INFINITY;

        for (Point2D point : _set) {
            double dist = point.distanceTo(p);

            if (dist < minDist) {
                minPoint = point;
                minDist = dist;
            }
        }

        return minPoint;
    }
}
