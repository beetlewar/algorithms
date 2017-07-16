package algorithms.kdTrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {
    private Node _root;

    public boolean isEmpty() {
        return _root == null;
    }

    public int size() {
        return count(_root);
    }

    private int count(Node currentNode) {
        if (currentNode == null) {
            return 0;
        }

        int count = 1;

        if (currentNode.Left != null) {
            count += count(currentNode.Left);
        }

        if (currentNode.Right != null) {
            count += count(currentNode.Right);
        }

        return count;
    }

    public void insert(Point2D p) {
        Node node = new Node();
        node.Point = p;

        if (_root == null) {
            node.Level = 0;
            _root = node;
            return;
        }

        Node targetNode = FindTargetNode(_root, p);

        int cmpResult = p.compareTo(targetNode.Point);

        if (cmpResult == 0) {
            return;
        } else if (cmpResult < 0) {
            targetNode.Left = node;
            node.Level = targetNode.Level + 1;
        } else {
            targetNode.Right = node;
            node.Level = targetNode.Level + 1;
        }
    }

    private Node FindTargetNode(Node currentNode, Point2D newPoint) {
        int result = currentNode.compareTo(newPoint);

        if (result == 0) {
            return currentNode;
        }

        if (result > 0) {
            if (currentNode.Left == null) {
                return currentNode;
            } else {
                return FindTargetNode(currentNode.Left, newPoint);
            }
        } else {
            if (currentNode.Right == null) {
                return currentNode;
            } else {
                return FindTargetNode(currentNode.Right, newPoint);
            }
        }
    }

    public boolean contains(Point2D p) {
        if (_root == null) {
            return false;
        }

        Node target = FindTargetNode(_root, p);
        return target.Point.compareTo(p) == 0;
    }

    public void draw() {
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> points = new ArrayList<Point2D>();

        findPoints(rect, _root, points);

        return points;
    }

    private void findPoints(RectHV rect, Node currentNode, ArrayList<Point2D> points) {
        if (currentNode == null) {
            return;
        }

        if (rect.contains(currentNode.Point)) {
            points.add(currentNode.Point);
        }

        if (currentNode.Level % 2 == 0) {
            if (currentNode.Point.x() >= rect.xmin()) {
                findPoints(rect, currentNode.Left, points);
            }
            if (currentNode.Point.x() <= rect.xmax()) {
                findPoints(rect, currentNode.Right, points);
            }
        } else {
            if (currentNode.Point.y() >= rect.ymin()) {
                findPoints(rect, currentNode.Left, points);
            }
            if (currentNode.Point.y() <= rect.ymax()) {
                findPoints(rect, currentNode.Right, points);
            }
        }
    }

    private static boolean isInRect(Point2D point, RectHV rect) {
        return rect.contains(point);
    }

    public Point2D nearest(Point2D p) {
        if (_root == null) {
            return null;
        }

        Point2D minPoint = findNearest(p, _root, _root.Point, Double.POSITIVE_INFINITY, 0);
        return minPoint;
    }

    private Point2D findNearest(
            Point2D targetPoint,
            Node currentNode,
            Point2D minPoint,
            double minDist,
            int level) {
        if (currentNode == null) {
            return minPoint;
        }

        double dist = targetPoint.distanceTo(currentNode.Point);

        if (dist < minDist) {
            minDist = dist;
            minPoint = currentNode.Point;
        }

        int compareResult;
        if (level % 2 == 0) {
            // compare X axis
            compareResult = Double.compare(targetPoint.x(), currentNode.Point.x());
        } else {
            // compare Y axis
            compareResult = Double.compare(targetPoint.y(), currentNode.Point.y());
        }

        if (compareResult < 0) {
            return findNearest(targetPoint, currentNode.Left, minPoint, minDist, level + 1);
        } else {
            return findNearest(targetPoint, currentNode.Right, minPoint, minDist, level + 1);
        }
    }

    private class Node {
        public Node Left;
        public Node Right;
        public int Level;
        public Point2D Point;

        public int compareTo(Point2D otherPoint) {
            if (Level % 2 == 0) {
                return Double.compare(Point.x(), otherPoint.x());
            } else {
                return Double.compare(Point.y(), otherPoint.y());
            }
        }
    }
}
