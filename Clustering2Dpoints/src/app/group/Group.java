package app.group;


import app.entity.Point2f;

import java.util.List;

public interface Group {
    Group apply();
    Point2f chooseOnePoint(List<Point2f> points);
}
