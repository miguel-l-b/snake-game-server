package controller;

public class EatApple extends Communicate {
    public final String ID_APPLE;
    public final String ID_PLAYER;
    public final int POINTS;

    public EatApple(String idApple, String idPlayer, int points) throws Exception {
        if(idApple.isEmpty())
            throw new Exception("invalid idApple");
        if(idPlayer.isEmpty())
            throw new Exception("invalid idPlayer");
        
        this.ID_APPLE = idApple;
        this.ID_PLAYER = idPlayer;
        this.POINTS = points;
    }
}