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
            case 2: return "server full";
            case 3: return "initialization";
            case 4: return "server stopped";
            case 5: return "Você Ganhou";
            case 6: return "Você Perdeu";
            default: return "random";
        }
    }

    @Override
    public String toString() { return String.format("<%s>(%s)", ID, REASON); }
}