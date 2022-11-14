package controller;

public class GameController extends Grid {

    public GameController(int limitGrid) throws Exception 
    { super(limitGrid); }
    public GameController(Grid grid) throws Exception 
    { super(grid); }

    public void handleCollision(int indexPlayer) { super.isAteAnApple(indexPlayer); }

    public boolean movingVertical(int y, int indexPlayer) {
        Player p = super.players.get(indexPlayer);
        if(y > 0 && p.getY()+1 == super.limit) return false;
        if(y < 0 && p.getY()-1 == -1) return false;

        if(
            (y > 0 && !super.isPlayerInPosition(p.getX(), p.getY()+1)) ||
            y < 0 && !super.isPlayerInPosition(p.getX(), p.getY()-1)
        ) {
            super.players.get(indexPlayer).movingVertical(y);
            handleCollision(indexPlayer);
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
            handleCollision(indexPlayer);
            return true;
        }

        return false;
    }
}
