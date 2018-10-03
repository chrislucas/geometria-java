package solution.algorithm;

import solution.entity.Location;
import java.util.List;

public interface Group {
    Group apply();
    Location chooseOneOption(List<Location> locations);
}
