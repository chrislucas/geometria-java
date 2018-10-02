package app.entity;


import java.util.ArrayList;

public class Point2f {

    private double x, y;
    // indica que tal ponto e uma referencia no ponto cartesiano
    private boolean reference;

    private static final double EPS = 1E-7;

    private ArrayList<Point2f> refAttempts;

    public Point2f(double x, double y, boolean reference) {
        this.x = x;
        this.y = y;
        this.reference = reference;
        this.refAttempts = new ArrayList<>();
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

    public ArrayList<Point2f> getAttempts() {
        return this.refAttempts;
    }

    public Point2f addAttempts(Point2f ref) {
        this.refAttempts.add(ref);
        return this;
    }

    public double distance(Point2f that) {
        return Math.sqrt( ((x - that.getX()) * (x - that.getX())) + ((y - that.getY()) * (y - that.getY())));
    }

    @Override
    public boolean equals(Object obj) {
        Point2f that = (Point2f) obj;
        return Math.abs(that.x - x) <= EPS && Math.abs(that.y - y) <= EPS;
    }

    @Override
    public String toString() {
        return String.format("P(%f, %f) - %s.\nTentativas de conexao: %d"
            , x
            , y
            , reference ? "Ponto de referência" : "Ponto Residencial"
            , this.refAttempts.size()
        );
    }
}
