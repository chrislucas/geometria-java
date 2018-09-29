package impl;

public class TestPoint2f {

    public static void testArea() {
        Point2f [] points = {
                new Point2f(4,10)
                ,new Point2f(14,12)
                ,new Point2f(11,2)
                ,new Point2f(2,2)
        };
        System.out.println(Point2f.areaOfPolygon(points));
    }

    public static void testCCW() {
        Point2f [][] matrix = {
            {
                new Point2f(0,0)
                ,new Point2f(1,1)
                ,new Point2f(4,4)
            }
            ,{
                new Point2f(0,0)
                ,new Point2f(1,1)
                ,new Point2f(0,3)
            }
                // anti-horario
                ,{
                new Point2f(2,2)
                ,new Point2f(6,3)
                ,new Point2f(5,7)
            }
                // horario
            ,{
                new Point2f(2,2)
                ,new Point2f(5,4)
                ,new Point2f(8,1)
            }
                // horario
            ,{
                new Point2f(2,2)
                ,new Point2f(1,4)
                ,new Point2f(4,6)
            }
        };
        int idx = 4;
        System.out.println(Point2f.areaOfPolygon(matrix[idx]));
        System.out.println(Point2f.ccw(matrix[idx][0], matrix[idx][1], matrix[idx][2]));
    }

    public static void testCrossProduct() {
        Point2f [][] matrix = {
            {
                 new Point2f(2,2)
                ,new Point2f(1,4)
                ,new Point2f(4,6)
            }
        };
        int idx = 0;
        System.out.println(Point2f.crossProduct(matrix[idx][0], matrix[idx][1], matrix[idx][2]));
    }

    /**
     * https://www.mathportal.org/calculators/analytic-geometry/line-point-distance.php
     * */
    public static void testDistancePointToSegment() {
        Point2f [][] matrix = {
                 {new Point2f(2,1),  new Point2f(5,7), new Point2f(10,7)}
                ,{new Point2f(1,1),  new Point2f(10,4), new Point2f(3,7)}
        };
        int idx = 1;
        Point2f.Segment2f segment2f = new Point2f.Segment2f(matrix[idx][0], matrix[idx][1]);
        System.out.println(Point2f.distancePointToLine(segment2f, matrix[idx][2]));
        System.out.println(Point2f.distancePointToLine2(segment2f, matrix[idx][2]));
    }

    public static void main(String[] args) {
        testDistancePointToSegment();
    }
}
