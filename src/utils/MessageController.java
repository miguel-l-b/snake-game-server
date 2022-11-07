package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageController {
    public final Socket socket;
    public final BufferedReader in;
    public final PrintWriter out;

    public MessageController(Socket socket) throws Exception {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public String getMsg() { return getMessage(); }
    public String getMessage() {
        try {
            return in.readLine();
        } catch(IOException err) { return null; }
    }

    public boolean send(String message) { return sendMessage(message); }
    public boolean sendMessage(String value) {
        out.println(value);

        return !out.checkError();
    }

        public void close() throws Exception {
        try {
            in.close();
            out.close();
            socket.close();
        } catch(IOException err) { 
            throw new Exception("Error when closing: \n ->"+err.getMessage());
        }
    }
}