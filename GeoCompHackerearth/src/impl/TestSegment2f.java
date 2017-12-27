package impl;

public class TestSegment2f {


    public static void test() {
        Segment2f.Point2f [][] matrix = {
             {new Segment2f.Point2f(2,1),  new Segment2f.Point2f(5,7), new Segment2f.Point2f(10,7)}
            ,{new Segment2f.Point2f(1,1),  new Segment2f.Point2f(10,4), new Segment2f.Point2f(3,7)}
        };
        int idx = 0;
        Segment2f s = new Segment2f(matrix[idx][0], matrix[idx][1]);
        System.out.println(s.distancePointToLine(matrix[idx][2]));
        System.out.println(s.distancePointToLine2(matrix[idx][2]));
    }

    public static void test2() {
        Segment2f.Point2f p = new Segment2f.Point2f(0, -6);
        Segment2f.Point2f q = new Segment2f.Point2f(8, 0);
        Segment2f.Point2f r = new Segment2f.Point2f(0, 5);
        Segment2f.Point2f s = new Segment2f.Point2f(12, 0);
        Segment2f a = new Segment2f(p, q);
        Segment2f b = new Segment2f(r, s);
        System.out.println(a.dotProduct(b, 59.5));
        System.out.println(a.dotProduct(b));
    }


    public static void main(String[] args) {
        test2();
    }
}
