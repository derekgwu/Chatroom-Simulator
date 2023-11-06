import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;

public class GWackChannel {
    ServerSocket server;
    PriorityQueue<Socket> socket_queue = new PriorityQueue<Socket>();
    PriorityQueue<GWackConnectedClient> member_queue = new PriorityQueue<GWackConnectedClient>();
    PriorityQueue<String> message_queue = new PriorityQueue<String>();
    public GWackChannel(int port){
        try{
            server = new ServerSocket(port);
        } catch(Exception e){
            System.out.println("Error connecting");
        }
    }
    public void serve(){
        while(true){
            try{
                socket_queue.add(server.accept());
            } catch (Exception e){
                ;
            }
        }
    }

    public void addClient(GWackConnectedClient client){
        member_queue.add(client);
    }

    public void enqueue_msg(String msg){
        message_queue.add(msg);
    }

    public void dequeue_all(){
        
    }

    public PriorityQueue<GWackConnectedClient> get_client_list(){
        return member_queue;
    }
    

    private class GWackConnectedClient extends Thread{
        public void sendMessage(String msg){

        }

        public boolean isValid(){
            return false;
        }

        public String get_client_name(){
            return "";
        }

        public void run(){

        }
    }

    public static void main(String[] args) {
        GWackChannel server = new GWackChannel(2023);
        server.serve();
    }
}
