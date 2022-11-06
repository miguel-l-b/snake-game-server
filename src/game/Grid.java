package game;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    protected List<Player> players;
    protected List<Apple> apples;

    public final int limit;

    public Grid(int limit) {
        this.players = new ArrayList<Player>();
        this.apples = new ArrayList<Apple>();
        this.limit = limit;
    }

    public Grid(Grid grid) throws Exception {
        if(grid == null)
            throw new Exception("The grid is null");
        
        this.limit = grid.limit;
        this.players = grid.players;
        this.apples = grid.apples;
    }

    public boolean isAteAnApple(int indexPlayer) {
        Player p = players.get(indexPlayer);
        Apple apple = getAppleInPosition(p.getX(), p.getY());
        if(apple != null) {
            players.get(indexPlayer).addPoint(apple.getValue());
            removeApple(apple);
            return true;
        }
        return false;
    }
    public boolean isAteAnApple(Player player) {
        return isAteAnApple(players.indexOf(player));
    }

    public Player[] getPlayers() {
        Player[] result = new Player[players.size()];

        for (int i = 0; i < players.size(); i++)
            result[i] = players.get(i);

        return result;
    }
    public Player getFistPlayer() { return players.get(0); }
    public Player getPlayerByID(String id) throws Exception 
    { return players.get(indexOfPlayerByID(id)); }
    public int indexOfPlayerByID(String id) throws Exception {
        for (int i = 0; i < this.players.size(); i++)
            if(this.players.get(i).ID == id) return i;

        throw new Exception("error in find player by id");
    }
    public Player getPlayerCloseTo(Apple apple) {
        double distance = 0;
        Player p = null;
        for (Player player : players) {
            double current = player.distanceTo(apple);
            if(distance == 0 || distance < current)
            {
                distance = current;
                p = player;
            }
        }

        return p;
    }
    protected boolean isExistPlayerInPosition(int x, int y) {
        for(Coordinate player : players)
            if(x == player.getX() && y == player.getY()) return true;
        
        return false;
    }
    protected boolean isPlayerInPosition(Coordinate coordinates) 
    { return isExistPlayerInPosition(coordinates.getX(), coordinates.getY()); }

    public void addPlayer(Player value) 
    { this.players.add(value); }
    protected void addPlayers(Player[] values) 
    { for (Player player : values) this.players.add(player); }
    protected void removePlayerByID(String id) throws Exception 
    { this.players.remove(getPlayerByID(id)); }

    public Apple[] getApples() {
        Apple[] result = new Apple[apples.size()];
        
        for (int i = 0; i < apples.size(); i++)
            result[i] = apples.get(i);

        return result;
    }
    protected boolean isExistAppleInPosition(int x, int y) 
    { return getAppleInPosition(x, y) == null ? false : true; }
    protected boolean isExistAppleInPosition(Coordinate coordinates) 
    { return isExistPlayerInPosition(coordinates.getX(), coordinates.getY()); }
    protected Apple getAppleInPosition(int x, int y) {
        for(Apple apple : apples)
            if(x == apple.getX() && y == apple.getY()) return apple;

        return null;
    }
    protected Apple getAppleInPosition(Coordinate coordinates)
    { return getAppleInPosition(coordinates.getX(), coordinates.getY()); }
    protected Apple getAppleByID(String id) throws Exception
    { return this.apples.get(indexOfAppleByID(id)); }
    protected int indexOfAppleByID(String id) throws Exception {
        for (int i = 0; i < this.apples.size(); i++)
            if(this.apples.get(i).ID == id) return i;

        throw new Exception("error in find apple by id");
    }

    public void addApple(Apple value) { this.apples.add(value); }
    protected void addApples(Apple[] values) { for (Apple apple : values) this.apples.add(apple); }
    public void removeApple(Apple apple) { this.apples.remove(apple); }
    public Apple getAppleCloseTo(Player player) {
        double distance = 0;
        Apple a = null;
        for (Apple apple : apples) {
            double current = apple.distanceTo(player);
            if(distance == 0 || distance < current)
            {
                distance = current;
                a = apple;
            }
        }

        return a;
    }

    @Override
    public boolean equals (Object obj) {

        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
    
        Grid g = (Grid)obj;
        if(this.players != g.players) return false;
        if(this.apples != g.apples) return false;
    
        return true;
    }

    @Override   
    public int hashCode() {
        int hash = 2;
        for (Apple apple : apples)
            hash = 7 * hash + apple.hashCode();

        for (Player player : players)
            hash = 17 * hash + player.hashCode();

        if(hash < 0) hash *= -1;
        return hash;
    }

    @Override
    public String toString() {
        return String.format("players: %s, apples: %s", this.players.toString(), this.apples.toString());
    }
}