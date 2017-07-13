import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private Node _root;
    private int _size;

    public boolean isEmpty() {
        return _root == null;
    }

    public int size() {
        return _size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.Point = p;

        if (_root == null) {
            _size = 1;
            node.Level = 0;
            node.Rect = new RectHV(0, 0, 1, 1);
            _root = node;
            return;
        }

        TargeNode tn = findTargetNode(_root, p);

        if (tn.CompareResult == 0) {
            return;
        } else if (tn.CompareResult > 0) {
            tn.Node.Left = node;
            node.Level = tn.Node.Level + 1;

            if (tn.Node.Level % 2 == 0) {
                node.Rect = new RectHV(tn.Node.Rect.xmin(), tn.Node.Rect.ymin(), tn.Node.Point.x(), tn.Node.Rect.ymax());
            } else {
                node.Rect = new RectHV(tn.Node.Rect.xmin(), tn.Node.Rect.ymin(), tn.Node.Rect.xmax(), tn.Node.Point.y());
            }
        } else {
            tn.Node.Right = node;
            node.Level = tn.Node.Level + 1;

            if (tn.Node.Level % 2 == 0) {
                node.Rect = new RectHV(tn.Node.Point.x(), tn.Node.Rect.ymin(), tn.Node.Rect.xmax(), tn.Node.Rect.ymax());
            } else {
                node.Rect = new RectHV(tn.Node.Rect.xmin(), tn.Node.Point.y(), tn.Node.Rect.xmax(), tn.Node.Rect.ymax());
            }
        }

        _size++;
    }

    private TargeNode findTargetNode(Node currentNode, Point2D newPoint) {
        int result = currentNode.compareTo(newPoint);

        if (result == 0) {
            TargeNode tn = new TargeNode();
            tn.Node = currentNode;
            tn.CompareResult = 0;
            return tn;
        }

        if (result > 0) {
            if (currentNode.Left == null) {
                TargeNode tn = new TargeNode();
                tn.Node = currentNode;
                tn.CompareResult = result;
                return tn;
            } else {
                return findTargetNode(currentNode.Left, newPoint);
            }
        } else {
            if (currentNode.Right == null) {
                TargeNode tn = new TargeNode();
                tn.Node = currentNode;
                tn.CompareResult = result;
                return tn;
            } else {
                return findTargetNode(currentNode.Right, newPoint);
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (_root == null) {
            return false;
        }

        TargeNode tn = findTargetNode(_root, p);
        return tn.CompareResult == 0;
    }

    public void draw() {
        StdDraw.setPenRadius(0.005);

        draw(_root);
    }

    private void draw(Node node) {
        if (node == null) {
            return;
        }

        if (node.Level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.Point.x(), node.Rect.ymin(), node.Point.x(), node.Rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.Rect.xmin(), node.Point.y(), node.Rect.xmax(), node.Point.y());
        }

        draw(node.Left);
        draw(node.Right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<Point2D> points = new ArrayList<Point2D>();

        findPoints(rect, _root, points);

        return points;
    }

    private void findPoints(RectHV rect, Node currentNode, ArrayList<Point2D> points) {
        if (currentNode == null) {
            return;
        }

        if (!rect.intersects(currentNode.Rect)) {
            return;
        }

        if (rect.contains(currentNode.Point)) {
            points.add(currentNode.Point);
        }

        findPoints(rect, currentNode.Left, points);
        findPoints(rect, currentNode.Right, points);
    }

    private static boolean isInRect(Point2D point, RectHV rect) {
        return rect.contains(point);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (_root == null) {
            return null;
        }

        MinDist winner = new MinDist();
        winner.Dist = Double.POSITIVE_INFINITY;
        winner.Point = _root.Point;

        winner = findNearest(p, _root, winner);

        return winner.Point;
    }

    private MinDist findNearest(
            Point2D targetPoint,
            Node currentNode,
            MinDist winner) {
        if (currentNode == null) {
            return winner;
        }

        double distToRect = currentNode.Rect.distanceSquaredTo(targetPoint);

        if (distToRect > winner.Dist) {
            return winner;
        }

        double dist = targetPoint.distanceSquaredTo(currentNode.Point);

        if (dist < winner.Dist) {
            winner.Dist = dist;
            winner.Point = currentNode.Point;
        }

        MinDist minLeft = findNearest(targetPoint, currentNode.Left, winner);
        MinDist minRight = findNearest(targetPoint, currentNode.Right, winner);

        if (minLeft.Dist < minRight.Dist) {
            return minLeft;
        } else {
            return minRight;
        }
    }

    private class Node {
        public Node Left;
        public Node Right;
        public int Level;
        public Point2D Point;
        public RectHV Rect;

        public int compareTo(Point2D otherPoint) {
            if (Level % 2 == 0) {
                int result = Double.compare(Point.x(), otherPoint.x());
                if (result == 0) {
                    result = Double.compare(Point.y(), otherPoint.y());
                }
                return result;
            } else {
                int result = Double.compare(Point.y(), otherPoint.y());
                if (result == 0) {
                    result = Double.compare(Point.x(), otherPoint.x());
                }
                return result;
            }
        }
    }

    private class MinDist {
        public Point2D Point;
        public double Dist;
    }

    private class TargeNode {
        public Node Node;
        public int CompareResult;
    }
}
