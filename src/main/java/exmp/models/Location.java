package exmp.models;

public class Location {
    private float x;
    private double y;
    private double z;
    private String name; //Поле может быть null

    public Location(float x, double y, double z, String name) {
        setX(x);
        setY(y);
        setZ(z);
        setName(name);
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
