import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;


public class GWackChannel {
    ServerSocket server;
    LinkedList<Socket> socket_queue = new LinkedList<Socket>();
    LinkedList<GWackConnectedClient> member_queue = new LinkedList<GWackConnectedClient>();


    public GWackChannel(int port){
        try{
            server = new ServerSocket(port);
        } catch(Exception e){
            System.out.println(e.toString());
        }
    }
    public void serve(){
        while(true){
            try{
                //add the accepted to the socket queue
                socket_queue.add(server.accept());
                if(socket_queue.isEmpty()){
                    System.out.print("error queuing");

                }
                
                //create a new client from the socket queue
                GWackConnectedClient new_client = new GWackConnectedClient(socket_queue.getLast());
                addClient(new_client);

                //start the thread
                new_client.start();
            } catch (Exception e){
                break;
            }
        }
        dequeue_all();
    }

    public synchronized void addClient(GWackConnectedClient client){
        member_queue.add(client);
    }

    public void enqueue_msg(String msg){
        
    }

    public void dequeue_all(){
        //dequeue members
        member_queue.clear();
        
        //dequeue sockets
        socket_queue.clear();
    }

    //send the
    public synchronized void getClientList(){
        for(Socket s : socket_queue){
            try{
                PrintWriter pw = new PrintWriter(s.getOutputStream());
                pw.println("START_CLIENT_LIST");
                for(GWackConnectedClient client : member_queue){
                    pw.println(client.getClientName());
                }
                pw.println("END_CLIENT_LIST");
                pw.flush();
            } catch (Exception e){
                ;
            }

        }
    }
    

    private class GWackConnectedClient extends Thread{
        private Socket sock;
        private String username;
        private String msg_to_send;

        public GWackConnectedClient(Socket sock){
            this.sock = sock;
        }
        
        public synchronized void sendMessage(String msg){
            for(Socket s: socket_queue){
                try{
                    PrintWriter pw = new PrintWriter(s.getOutputStream());
                    pw.println("START MSG");
                    pw.println(msg);
                    pw.println("END MSG");
                    pw.flush();
                } catch (Exception e){
                    ;
                }
            }
        }

        public synchronized void isValid(){
            for(Socket s: socket_queue){
                if(s.isClosed()){
                    socket_queue.remove(s);
                    getClientList();
                }
            }
            return;
        }

        public String getClientName(){
            return this.username;
        }

        public void run(){
            username = "";
            PrintWriter pw = null;
            BufferedReader br = null;

            try{
                pw = new PrintWriter(sock.getOutputStream());
                br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                boolean is_connected = false;
                boolean secret_pass = false;
                boolean name_pass = false;
                boolean message_pass = false;
                while(true){
                    //read line
                    String message = br.readLine();

                    //if there's no message, we break out
                    if(message == null){
                        break;
                    }

                    //if the message equals SECRET, next pass will check the key
                    if(message.equals("SECRET")){
                        System.out.print("SECRET READ\n");
                        secret_pass = true;
                        continue;
                    }

                    /*if the first line isn't the word SECRET, we can keep
                     * looping until we get a proper start
                     */

                    /*
                     * Secret pass: if the line is the correct key, connect the user
                     */
                    else if(message.equals("3c3c4ac618656ae32b7f3431e75f7b26b1a14a87")){
                        System.out.print("SECRET PASSWORD ACCEPTED\n");
                        is_connected = true;
                        secret_pass = false;
                        continue;
                    }

                    

                    //name check
                    else if(message.equals("NAME")){
                        System.out.print("NAME READ\n");
                        name_pass = true;
                        continue;
                    }

                    //if the name check passed, the next line should be the username
                    else if(name_pass == true){
                        username = message;
                        System.out.print("NAME RECEIVED:" + username +"\n");
                        getClientList();
                        name_pass = false;
                        continue;
                    }

                    //start message check
                    else if(message.equals("START MSG")){
                        System.out.print("START MSG RECEIVED\n");
                        message_pass = true;
                        continue;
                    }

                    //end message check
                    else if(message.equals("END MSG")){
                        System.out.print("END MESSAGE RECEIVED\n");
                        sendMessage(msg_to_send);
                        msg_to_send = "";
                        message_pass = false;
                        continue;
                    }

                    //concantenate the string
                    else if(message_pass == true){
                        msg_to_send = message;
                        continue;
                    }

                    isValid();

                    
                }

                //close the fields
                pw.close();
                br.close();
                if(socket_queue.contains(sock)){
                    socket_queue.remove(sock);
                    member_queue.remove(this);
                    getClientList();
                }

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
        GWackChannel server = new GWackChannel(Integer.parseInt(args[0]));
        server.serve();
    }
}
