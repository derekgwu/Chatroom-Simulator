import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
                GWackConnectedClient new_client = new GWackConnectedClient(socket_queue.poll());
                new_client.start();
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
        //dequeue members
        
        //dequeue sockets
        for(Socket sock : socket_queue){
            socket_queue.poll();
        }
    }

    public PriorityQueue<GWackConnectedClient> get_client_list(){
        return member_queue;
    }
    

    private class GWackConnectedClient extends Thread{
        Socket sock;
        String username;
        String msg_to_send;

        public GWackConnectedClient(Socket sock){
            this.sock = sock;
        }
        public void sendMessage(String msg){
            
        }

        public boolean isValid(){
            return false;
        }

        public String get_client_name(){
            return this.username;
        }

        public void run(){
            username = "";
            boolean is_connected = false;
            boolean secret_pass = false;
            boolean name_pass = false;
            boolean message_pass = false;
            PrintWriter pw = null;
            BufferedReader br = null;

            try{
                pw = new PrintWriter(sock.getOutputStream());
                br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                while(true){
                    //read line
                    String message = br.readLine();

                    //if there's no message, we break out
                    if(message == null){
                        break;
                    }

                    //if the message equals SECRET, next pass will check the key
                    if(message.equals("SECRET")){
                        secret_pass = true;
                        continue;
                    }

                    /*if the first line isn't the word SECRET, we can keep
                     * looping until we get a proper start
                     */
                    if(secret_pass == false){
                        continue;
                    }

                    /*
                     * Secret pass: if the line is the correct key, connect the user
                     */
                    else if(message.equals("3c3c4ac618656ae32b7f3431e75f7b26b1a14a87")){
                        is_connected = true;
                        secret_pass = false;
                        continue;
                    }

                    //if connection false, break out
                    else if(is_connected == false){
                        //failed secret
                        break;
                    }

                    //name check
                    if(message.equals("NAME")){
                        name_pass = true;
                        continue;
                    }

                    //if the name check passed, the next line should be the username
                    if(name_pass == true){
                        username = br.readLine();
                        member_queue.add(this);
                        name_pass = false;
                        continue;
                    }

                    //start message check
                    if(message.equals("START MSG")){
                        message_pass = true;
                        continue;
                    }

                    //end message checl
                    else if(message.equals("END MSG")){
                        message_pass = false;
                        continue;
                    }

                    //concantenate the string
                    if(message_pass == true){
                        msg_to_send = msg_to_send + message;
                        continue;
                    }

                    
                }

                //close the fields
                pw.close();
                br.close();
                sock.close();
            } catch (Exception e){
                try{
                    br.close();
                    pw.close();
                    sock.close();
                }
                //can't close the neccesary fields
                catch (Exception f){}
            }
        }
    }

    public static void main(String[] args) {
        GWackChannel server = new GWackChannel(2023);
        server.serve();
    }
}
