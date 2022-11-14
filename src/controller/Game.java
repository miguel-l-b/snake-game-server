package controller;

public class Game extends Communicate {
    public final Apple[] APPLES;
    public final Player[] PLAYERS;
    
    public Game(Apple[] apples, Player[] players) {
        this.APPLES = apples;
        this.PLAYERS = players;
    }

    @Override
    public String toString() { return String.format("[ Apples: %s, Players: %s ]", APPLES.toString(), PLAYERS.toString()); }
}
