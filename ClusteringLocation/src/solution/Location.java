package solution;

public class Location {

    private double distance;
    private long time;
    private boolean isReference;

    public Location(double distance, long time, boolean isReference) {
        this.distance = distance;
        this.time = time;
        this.isReference = isReference;
    }
}
