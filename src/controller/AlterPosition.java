package controller;

import game.Coordinate;

public class AlterPosition extends Communicate {
    public final String ID;
    public final char TYPE;
    public final int X, Y;

    public AlterPosition(String id, char type, int x, int y) {
        this.ID = id;
        this.TYPE = type;
        this.X = x;
        this.Y = y;
    }

    public Coordinate getCoords() {
        return new Coordinate(this.X, this.Y);
    }

    public boolean isApple() {
        return this.TYPE == 'a';
    }
    public boolean isPlayer() {
        return this.TYPE == 'p';
    }
}