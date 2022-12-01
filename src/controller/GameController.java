package controller;

public class GameController extends Grid {

    public GameController(int limitGrid) throws Exception 
    { super(limitGrid); }
    public GameController(Grid grid) throws Exception 
    { super(grid); }

    public boolean isFinish() {
        for (Player p : players) {
            if(p.getPoints() >= 100)
                return true;
        }
        return false;
    }

    public Apple handleCollision(int indexPlayer) { 
        Apple apple = super.isAteAnApple(indexPlayer);
        if(apple == null) return null;

        players.get(indexPlayer).addPoint(apple.getValue());
        removeApple(apple);
        return apple;
    }

    public boolean movingVertical(int y, int indexPlayer) {
        Player p = super.players.get(indexPlayer);
        if(y > 0 && p.getY()+1 == super.limit) return false;
        if(y < 0 && p.getY()-1 == -1) return false;

        if(
            (y > 0 && !super.isPlayerInPosition(p.getX(), p.getY()+1)) ||
            y < 0 && !super.isPlayerInPosition(p.getX(), p.getY()-1)
        ) {
            super.players.get(indexPlayer).movingVertical(y);
            return true;
        }

        return false;
    }
    public boolean movingHorizontal(int x, int indexPlayer) {
        Player p = super.players.get(indexPlayer);
        if(x > 0 && p.getX()+1 == super.limit) return false;
        if(x < 0 && p.getX()-1 == -1) return false;

        if(
            (x > 0 && !super.isPlayerInPosition(p.getX()+1, p.getY())) ||
            (x < 0 && !super.isPlayerInPosition(p.getX()-1, p.getY()))
        ) {
            super.players.get(indexPlayer).movingHorizontal(x);
            return true;
        }

        return false;
    }
}
