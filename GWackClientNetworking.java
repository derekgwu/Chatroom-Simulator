import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class GWackClientNetworking {
    private Socket sock;
    private PrintWriter pw;
    private BufferedReader br;
    public GWackClientNetworking(InetAddress ip, int port){
        try{
            this.sock = new Socket(ip, port);
            this.pw = new PrintWriter(sock.getOutputStream());
            this.br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch(Exception e){
            ;
        }
    }
    public void writeMessage(String msg){
        pw.println(msg);
        pw.flush();
    }

    public boolean isConnected(){
        return false;
    }

    public void disconnect(){
        ;
    }

    private class ReadingThread extends Thread{
        

        
    }

}
