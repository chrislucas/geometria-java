package impl;


import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

/**
 * Implementacao baseada nesses links
 * https://algs4.cs.princeton.edu/99hull/
 * https://algs4.cs.princeton.edu/99hull/GrahamScan.java.html
 * */

public class GrahanScan {

    public static final double EPS = 10E-9;

    public static class Point2f implements Comparable<Point2f> {
        double x, y;
        public Point2f(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Point2f() { }

        /**
         * @return {-1,0,1} {sentido horario, colinear, anti-horario}
         * */
        public static int orientation(Point2f a, Point2f b, Point2f c) {
            double area = (b.y-a.y)*(c.x-b.x)-(b.x-a.x)*(c.y-b.y);
            if(area < 0)
                return 1;       // antic
            else if(area > 0)
                return -1;      // clockwise
            return 0;           // colinear
        }

        public double distanceSquaredTo(Point2f q) {
            double diffX = x - q.x;
            double diffY = y - q.y;
            return diffX*diffX+diffY*diffY;
        }

        public double distanceTo(Point2f q) {
            return Math.sqrt(distanceSquaredTo(q));
        }

        public static boolean almostEquals(double a, double b) {
            return Math.abs(a - b)  < EPS;
        }

        @Override
        public int compareTo(Point2f that) {
            return almostEquals(y, that.y) ? (int) (x - that.x) : (int) (y - that.y);

        }

        public Comparator<Point2f> comparatorPolarOrder() {
            return new PolarOrderAsc();
        }

        private final class YOrderAsc implements Comparator<Point2f> {
            @Override
            public int compare(Point2f p, Point2f q) {
                return (int) (p.y - q.y);
            }
        }

        private final class XOrderAsc implements Comparator<Point2f> {
            @Override
            public int compare(Point2f p, Point2f q) {
                return (int) (p.x - q.x);
            }
        }

        private final class YXOrderAsc implements Comparator<Point2f> {
            @Override
            public int compare(Point2f p, Point2f q) {
                return almostEquals(p.y, q.y) ? (int) (p.x - q.x) : (int) (p.y - q.y);
            }
        }

        private final class PolarOrderAsc implements Comparator<Point2f> {
            @Override
            public int compare(Point2f b, Point2f c) {
                int o =  orientation(Point2f.this, b, c);
                return (o == 0) ? (int) (distanceSquaredTo(b) - distanceSquaredTo(c)) : o;
            }
        }

        @Override
        public String toString() {
            return String.format("Point (%f %f)", x, y);
        }
    }

    private static void swap(Point2f [] points, int i, int j) {
        Point2f aux = points[i];
        points[i] = points[j];
        points[j] = aux;
    }

    public static Point2f next(Stack<Point2f> stack) {
        Point2f top = stack.pop();
        Point2f nodeNext = stack.peek();
        stack.push(top);
        return nodeNext;
    }

    public static Stack<Point2f> scan(Point2f [] points) {
        int minIdxY = 0;
        int n = points.length;
        for(int i=1; i<n; i++) {
            if(points[i].y < points[minIdxY].y || (points[i].y == points[minIdxY].y && points[i].x < points[minIdxY].x)) {
                minIdxY = i;
            }
        }
        if(minIdxY != 0)
            swap(points, 0, minIdxY);
        Arrays.sort(points, 1, n, points[0].comparatorPolarOrder());
        int k = 1;
        for(int i=1; i<n; i++) {
            while ( i<n-1 && Point2f.orientation(points[0], points[i], points[i+1]) == 0)
                i++;
            points[k++] = points[i];
        }
        Stack<Point2f> stack = new Stack<>();
        if(k < 3)
            return stack;
        stack.push(points[0]);
        stack.push(points[1]);
        stack.push(points[2]);
        for(int i=3; i<k; i++) {
            Point2f nextPoint = next(stack);
            Point2f topPoint = stack.peek();
            while (stack.size() > 1 && Point2f.orientation(nextPoint, topPoint, points[i]) < 1) {
                stack.pop();
                if(stack.size() > 1) {
                    topPoint = stack.peek();
                    nextPoint = next(stack);
                }
            }
            stack.push(points[i]);
        }
        return stack;
    }

    public static void testScan() {
        Point2f [][] matrix = {
            {
                 new Point2f(0,3)
                ,new Point2f(1,1)
                ,new Point2f(2,2)
                ,new Point2f(4,4)
                ,new Point2f(0,0)
                ,new Point2f(1,2)
                ,new Point2f(3,1)
                ,new Point2f(3,3)
            }
            ,{
                 new Point2f(0,0)
                ,new Point2f(1,1)
                ,new Point2f(2,2)
                ,new Point2f(3,-1)
            }
        };
        Stack<Point2f> convex = scan(matrix[0]);
        while (!convex.empty()) {
            System.out.println(convex.pop());
        }
    }

    public static void main(String[] args) {
        testScan();
    }

}
