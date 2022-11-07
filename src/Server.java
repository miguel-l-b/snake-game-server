import java.net.ServerSocket;

public class Server {
    public final ServerSocket server;
    public Server(int port) throws Exception {
        this.server = new ServerSocket(port);
    }
}