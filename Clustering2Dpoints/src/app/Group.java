package app;


import java.util.List;

public interface Group {
    Group apply();
    Point2f chooseOnePoint(List<Point2f> points);
}
