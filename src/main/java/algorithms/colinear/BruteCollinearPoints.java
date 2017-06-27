package algorithms.colinear;

import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private final LineSegment[] _segments;

    public BruteCollinearPoints(Point[] points) {
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

        LinkedList<LineSegment> lineSegments = new LinkedList<LineSegment>();

        for (Point point : points) {
            for (Point slopingPoint : points) {
                if (slopingPoint == point) {
                    // don't observe current point
                    continue;
                }
                LineSegment lineSegment = linkPoints(points, point, slopingPoint);

                if(lineSegment != null){
                    lineSegments.addItem(lineSegment);
                }
            }
        }

        _segments = LineSegmentsToArray(lineSegments);
    }

    private LineSegment[] LineSegmentsToArray(LinkedList<LineSegment> lineSegments) {
        // copy to array (generic toArray doesn't work)

        Object[] arr = lineSegments.toArray();

        LineSegment[] segments = new LineSegment[arr.length];

        int numDistinct = 0;

        for(int i = 0; i < segments.length; i++){
            LineSegment newSegment = (LineSegment)arr[i];
            boolean hasSegment = false;
            // search distinct segments
            for(int j = 0; j < i; j++){
                LineSegment distinctSegment = (LineSegment)arr[j];
                if(newSegment.toString().equals(distinctSegment.toString())){
                    hasSegment = true;
                    break;
                }
            }

            if(!hasSegment) {
                numDistinct++;
                segments[i] = newSegment;
            }
        }

        LineSegment[] distinctSegments = new LineSegment[numDistinct];
        int index = 0;
        for(int i = 0; i < segments.length; i++){
            LineSegment segment = segments[i];
            if(segment != null){
                distinctSegments[index] = segment;
                index++;
            }
        }

        return distinctSegments;
    }

    private LineSegment linkPoints(Point[] points, Point startPoint, Point slopingPoint) {
        Comparator<Point> comparator = startPoint.slopeOrder();

        LinkedList linkedPoints = new LinkedList();
        linkedPoints.addItem(startPoint);
        linkedPoints.addItem(slopingPoint);

        for (Point slopingPoint2 : points) {
            if (slopingPoint2 == startPoint || slopingPoint2 == slopingPoint) {
                // don't observe current points
                continue;
            }

            if (comparator.compare(slopingPoint, slopingPoint2) == 0) {
                linkedPoints.addItem(slopingPoint2);
            }
        }

        if (linkedPoints.count() >= 4) {
            return createSegment(linkedPoints);
        }

        return null;
    }

    private LineSegment createSegment(LinkedList linkedList){
        Object[] arr = linkedList.toArray();
        Point[] points = new Point[arr.length];
        for(int i = 0; i < arr.length; i++){
            points[i] = (Point)arr[i];
        }

        Point min = points[0];
        Point max = points[points.length - 1];

        for(Point point: points){
            if(point.compareTo(min) < 0){
                min = point;
            }
            if(point.compareTo(max) > 0){
                max = point;
            }
        }

        return new LineSegment(min, max);
    }

    public int numberOfSegments() {
        return _segments.length;
    }

    public LineSegment[] segments() {
        return _segments;
    }

    private class LinkedList<Item> {
        private Node<Item> _first;
        private Node<Item> _last;

        public void addItem(Item item){
            Node last = new Node(item);

            if(_first == null){
                _first = _last = last;
                return;
            }

            _last.Next = last;
            _last = last;
        }

        public int count(){
            int count = 0;

            Node current = _first;
            while(current != null){
                count++;
                current = current.Next;
            }

            return count;
        }

        public Item first(){
            return _first.Item;
        }

        public Item last(){
            return _last.Item;
        }

        public Item[] toArray(){
            int count = count();
            Item[] items = (Item[])new Object[count];

            Node current = _first;
            int index = 0;
            while(current != null){
                items[index] = (Item)current.Item;

                current = current.Next;
                index++;
            }

            return items;
        }
    }

    private class Node<Item>{
        public Node Next;
        public Item Item;

        private Node(Item item){
            Item = item;
        }
    }
}
