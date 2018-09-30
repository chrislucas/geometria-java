package impl;


import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class AnotherGrahanScan {


    private static ArrayList<Point2f> points = new ArrayList<>();

    private static Point2f p0;

    public static class Point2f implements Comparable<Point2f> {
        double x, y;

        public Point2f(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(@NotNull Point2f that) {
            return compare(this, that);
        }

        @Override
        public String toString() {
            return String.format("Point2f (%f %f)", x, y);
        }
    }

    private static int distance(Point2f p, Point2f q) {
        int diffX = (int) (p.x - q.x);
        int diffY = (int) (p.y - q.y);
        return (diffX * diffX) + (diffY * diffY);
    }

    private static int orientation(Point2f p, Point2f q, Point2f r) {
        long val = Math.round((q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y));
        if (val == 0)
            return 0;
        // 1 - clockwise, 2 - counterclockwise
        return val > 0 ? 1 : 2;
    }

    private static int compare(Point2f p, Point2f q) {
        int o = orientation(p0, p, q);
        if (o == 0) {
            return distance(p0, q) >= distance(p0, p) ? -1 : 1;
        }
        return o == 2 ? -1 : 1;
    }


    private static Point2f nextTop(Stack<Point2f> stack) {
        Point2f p = null;
        if (!stack.isEmpty() && stack.size() > 2) {
            Point2f pp = stack.pop();
            p = stack.peek();
            stack.push(pp);
        }
        return p;
    }

    private static Stack<Point2f> test(ArrayList<Point2f> points) {
        int size = points.size();
        int minIdx = 0;
        for (int i = 1; i < points.size() ; i++) {
            Point2f aux = points.get(i);
            if (aux.y < points.get(minIdx).y
                    || (aux.y == points.get(minIdx).y && aux.x < points.get(minIdx).x)) {
                minIdx = i;
            }
        }

        Point2f aux = points.get(0);
        Point2f min = points.get(minIdx);
        points.remove(0);
        points.add(0, min);
        points.remove(minIdx);
        points.add(minIdx, aux);
        p0 = points.get(0);
        Collections.sort(points.subList(1, size));
        int m = 1;
        for (int i = 1; i < size ; i++) {
            /**
             * Nao levar em consideracao
             * */
            while ( i < (size-1) && orientation(p0, points.get(i), points.get(i+1)) == 0) {
                i++;
            }
            aux = points.get(i);
            points.remove(m);
            points.add(m, aux);
            m++;
        }

        Stack<Point2f> hull = new Stack<>();
        if (m > 2) {
            hull.push(points.get(0));
            hull.push(points.get(1));
            hull.push(points.get(2));
            for (int i = 3; i < m ; i++) {
                /**
                 * Se a orientacao dos 3 pontos nao for anti horaria, remova-o
                 * do feixo
                 * */
                while (hull.size() > 2 && orientation(nextTop(hull), hull.peek(), points.get(i)) != 2) {
                    hull.pop();
                }
                hull.push(points.get(i));
            }
        }
        return hull;
    }

    private static void test() {
        // {0, 0}, {1, 1}, {2, 2}, {3, -1}}

        points.add(new Point2f(0, 3));
        points.add(new Point2f(1, 1));
        points.add(new Point2f(2, 2));
        points.add(new Point2f(4, 4));
        points.add(new Point2f(0, 0));
        points.add(new Point2f(1, 2));
        points.add(new Point2f(3, 1));
        points.add(new Point2f(3, 3));

        /*
        for (int i = 0; i < 100; i++) {
            points.add(new Point2f(i/10, i%10));
        }
        */
        Stack<Point2f> stack = test(points);
        while (stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    public static void main(String[] args) {
        test();
    }
}
