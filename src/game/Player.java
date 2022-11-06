package game;
import java.awt.Color;

import com.google.gson.Gson;

import utils.GenerateJson;

public class Player extends Coordinate implements GenerateJson {
    public final String ID;
    public final String username;
    public final Color color;
    private int points;

    public Player(String ID, String username, Color color, int x, int y) throws Exception {
        super(x, y);
        if(username.length() > 10 || username.contains(" ")) throw new Exception("invalid username");
        // if(ID == null || ID.length() < 7) throw new Exception("invalid ID");
        if(color == null) throw new Exception("invalid color");
        this.ID = ID;
        this.username = username;
        this.color = color;
        this.points = 0;
    }

    protected boolean movingHorizontal(int x) {
        if(x == 0) return false;

        if(x > 0) super.setPosition(super.getX()+1, super.getY());
        if(x < 0) super.setPosition(super.getX()-1, super.getY());

        return true;
    }
    protected boolean movingVertical(int y) {
        if(y == 0) return false;

        if(y > 0) super.setPosition(super.getX(), super.getY()+1);
        if(y < 0) super.setPosition(super.getX(), super.getY()-1);

        return true;
    }
    
    public int getPoints() { return points; }
    protected void setPoints(int value) { this.points = value; }
    protected void addPoint(int point) { this.points += point; }

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return String.format("username: %s, color: %s, points: %d, coordinate: { %s }", this.username, this.color.toString(), this.points, super.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(obj.getClass() != this.getClass()) return false;
        if(this.username != ((Player)obj).username) return false;
        if(this.points != ((Player)obj).points) return false;
        if(this.color != ((Player)obj).color) return false;

        return super.equals((Coordinate)obj);
    }

    @Override
    public int hashCode() {
        int hash = 2;
        hash = 3  * hash + Integer.valueOf(this.points).hashCode();
        hash = 7  * hash + this.color.hashCode();
        hash = 13 * hash + this.username.hashCode();

        if(hash < 0) hash *= -1;

        return hash;
    }
}