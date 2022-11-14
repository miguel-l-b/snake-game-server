package controller;

public class LogIn extends Communicate {
    public final String USERNAME;

    public LogIn(String username) {
        this.USERNAME = username;
    }

    @Override
    public String toString() { return String.format("'%s'", USERNAME); }
}