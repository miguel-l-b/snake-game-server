package controller;

public class Kick extends Communicate {
    public final String ID;
    public final int REASON;

    public Kick(String id, int reason) {
        this.ID = id;
        this.REASON = reason;
    }

    public String getReason() {
        switch(this.REASON) {
            case 0: return "kicked";
            case 1: return "timeout";
            case 2: return "ban";
            case 3: return "initialization";
            default: return "random";
        }
    }
}