package app;


public class Point2f {

    private double x, y;
    // indica que tal ponto e uma referencia no ponto cartesiano
    private boolean reference;

    public Point2f(double x, double y, boolean reference) {
        this.x = x;
        this.y = y;
        this.reference = reference;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public boolean isReference() {
        return reference;
    }

    public double distance(Point2f that) {
        return Math.sqrt( ((x - that.getX()) * (x - that.getX())) + ((y - that.getY()) * (y - that.getY())));
    }

    @Override
    public String toString() {
        return String.format("P(%f, %f) - %s", x, y, reference ? "Ponto de referência" : "Ponto Residencial");
    }
}
