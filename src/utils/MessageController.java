package utils;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controller.Communicate;

public class MessageController {
    public final Socket socket;
    public final ObjectInput in;
    public final ObjectOutput out;

    public MessageController(Socket socket) throws Exception {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }

     public Object getObject() {
        try {
            return in.readObject();
        } catch(Exception err) { return null; }
    }

    public boolean sendObject(Communicate message) {
        try {
            out.writeObject(message);
            out.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void close() throws Exception {
        try {
            in.close();
            out.close();
            socket.close();
        } catch(IOException err) { 
            throw new Exception("Error when closing: \n ->"+
                err.getMessage());
        }
    }
}