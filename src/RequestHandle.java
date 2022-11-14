// import Console.ConsoleManager;
// import controller.*;
// import utils.MessageController;

// public class RequestHandle implements Runnable {
//     public final Player player;
//     public final MessageController client;
//     private boolean running;
//     public RequestHandle(Player player) {
//         this.player = player;
//         this.client = player.client;
//     }

//     @Override
//     public void run() {
//         while(running) {
//             Object request = client.getObject();
//             ConsoleManager.println(Console.Colors.GREEN, "request of: "+request.getClass());
//             // if(request instanceof AlterPosition) {
//             //     // verificar se é possível o player andar nessa direção e fazer o player andar
//             //     int index = -1;
//             //     try {
//             //         index = gameGrid.indexOfPlayerByID(player.ID);
//             //     } catch(Exception e) { }
                
//             //     if(((AlterPosition)request).X > 0)
//             //         gameGrid.movingHorizontal(+1, index);
//             //     else if(((AlterPosition)request).X < 0)
//             //         gameGrid.movingHorizontal(-1, index);
//             //     else if(((AlterPosition)request).Y > 0)
//             //         gameGrid.movingVertical(+1, index);
//             //     else if(((AlterPosition)request).Y < 0)
//             //         gameGrid.movingVertical(-1, index);
//             //     // mandar para todos os clientes a nova alteração do player atual
                
//             // }

//             // if(request instanceof Kick) {
                
//             // }
//         }
//     }
    
//     public void start() {
//         running = true;
//         run();
//     }
   
//     public void kill(){
//         running = false;
//     }
// }
