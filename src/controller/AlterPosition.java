package controller;

public class AlterPosition extends Communicate {
    public final String ID;
    public final char TYPE;
    protected final Coordinate coords;

    public AlterPosition(String id, char type, int x, int y) {
        this.ID = id;
        this.TYPE = type;
        this.coords = new Coordinate(x, y);
    }
    public AlterPosition(String id, char type, Coordinate coords) {
        this.ID = id;
        this.TYPE = type;
        this.coords = coords;
    }

    public int getX() { return coords.getX(); }
    public int getY() { return coords.getY(); }

    public boolean isApple() {
        return this.TYPE == 'a';
    }
    public boolean isPlayer() {
        return this.TYPE == 'p';
    }

    @Override
    public String toString() { return String.format("%s<%s>[%s]", TYPE, ID, coords.toString()); }
}