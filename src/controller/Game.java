package controller;
import game.Apple;
import game.Player;
public class Game extends Communicate {
    public final Apple[] APPLES;
    public final Player[] PLAYERS;
    
    public Game(Apple[] apples, Player[] players) {
        this.APPLES = apples;
        this.PLAYERS = players;
    }
}
