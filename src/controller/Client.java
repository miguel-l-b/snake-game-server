package controller;
import java.net.Socket;

import utils.MessageController;

public class Client extends MessageController {
   public final String ID;
   
   public Client(String id, Socket socket) throws Exception {
    super(socket);
    this.ID = id;
   }

   public Client(String id, MessageController client) throws Exception {
    super(client);
    this.ID = id;
   }
}
