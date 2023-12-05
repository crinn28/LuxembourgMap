package Tema7;

import java.util.List;

public class Nod implements Comparable<Nod> {
    private int idNode;
    private double longitude;
    private double latitude;
    private double x;
    private double y;

    public List<Arc> adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public int previousNodeId = -1;

    public Nod(int idNode, int longitude, int latitude) {
        this.idNode = idNode;
        this.longitude = longitude ;
        this.latitude = latitude ;
    }

    public int getIdNode() {
        return idNode;
    }

    public void setIdNode(int idNode) {
        this.idNode = idNode;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int compareTo(Nod other) {

        return Double.compare(minDistance, other.minDistance);
    }

}
