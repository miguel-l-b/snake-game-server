package controller;

public class Coordinate extends Communicate {
    private int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void setPosition(int x, int y) 
    { this.x = x; this.y = y; }

    public int getX() {return this.x; }
    public int getY() {return this.y; }

    public double distanceTo(Coordinate b)
    { return Math.sqrt(Math.pow(this.x + b.x, 2) + Math.pow(this.y + b.y, 2)); }

    @Override
    public String toString() {
        return String.format("x: %d, y: %d", this.x, this.y);
    }

    @Override
    public boolean equals (Object obj) {

        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        
        Coordinate c = (Coordinate)obj;
        if(this.x != c.x) return false;
        if(this.y != c.y) return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 2;
        hash = 3 * hash + Integer.valueOf(this.x).hashCode();
        hash = 5 * hash + Integer.valueOf(this.y).hashCode();

        if(hash<0) hash =- hash;
        return hash;
    } 
}
