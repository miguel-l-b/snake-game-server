package game;

import com.google.gson.Gson;
import utils.GenerateJson;

public class Apple extends Coordinate implements GenerateJson {
    public final String ID;
    private int value;
    public final int minValue;
    public final long timeout;
    private Thread handleValue;

    public Apple(String ID, int x, int y, int value, int minValue, long timeout) {
        super(x, y);
        this.ID = ID;
        this.value = value;
        this.minValue = minValue;
        this.timeout = timeout;

        this.handleValue = new Thread(() -> { handleValue(); });

        this.handleValue.start();
    }

    private void handleValue() {
        while(value > minValue) {
            try { this.handleValue.sleep(this.timeout); } catch(InterruptedException e) {}
            
            value--;
        }
    }

    public int getValue() 
    { return this.value; }

    public int getLevel() {
        if(value+(minValue/0.5) > minValue)
            return 2;
        if(value > minValue)
            return 1;

        return 0;
    }

    @Override
    public String toString() {
        return String.format("value: %d, minValue: %d, timeout: %d, coordinate: { %s }", this.value, this.minValue, this.timeout, super.toString());
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    } 

    @Override
    public boolean equals (Object obj) {

        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;

        Apple a = (Apple)obj;
        if(this.handleValue != a.handleValue) return false;
        if(this.minValue != a.minValue) return false;
        if(this.timeout != a.timeout) return false;
        if(this.value != a.value) return false;


        return super.equals((Coordinate)obj);
    }

    @Override
    public int hashCode() {
        int hash = 2;
        hash = 3  * hash + super.hashCode();
        hash = 7  * hash + Integer.valueOf(this.value).hashCode();
        hash = 13 * hash + Integer.valueOf(this.minValue).hashCode();
        hash = 17 * hash + Long.valueOf(this.timeout).hashCode();

        if(hash<0) hash =- hash;
        return hash;
    }

}