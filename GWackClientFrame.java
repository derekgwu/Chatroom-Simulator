import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class GWackClientFrame extends JFrame{
    //panels
    private JPanel top_panel;
    private JPanel mid_panel;
    private JPanel bottom_panel;
    private JPanel mmb_panel;
    private JPanel msg_panel;
    private JPanel total_msg_panel;

     //top panel variables
    private JLabel name_label;
    private JTextField name_entry;
    private JLabel ip_label;
    private JTextField ip_entry;
    private JLabel port_label;
    private JTextField port_entry;
    private JButton disconnect_btn;
    private JLabel members_online_label;
    private JTextArea members_list;
    private JScrollPane mmbers_scroll;
    private JScrollPane msg_scroll;
    private JScrollPane compose_scroll;
    //message panel variables
    private JLabel msg_label;
    private JTextArea msg_area;

    //compose variables
    private JPanel compose_panel;
    private JLabel compose_label;
    private JTextArea compose_area;

    //bottom panel variables
    private JButton send;

    //print writers
    private PrintWriter pw;

    //buffered readers

    //sockets
    private Socket sock;

    public GWackClientFrame(){
        //create the panels
        top_panel = new JPanel();
        mid_panel = new JPanel(new FlowLayout());
        bottom_panel = new JPanel(new BorderLayout());
        mmb_panel = new JPanel(new BorderLayout());
        msg_panel = new JPanel(new BorderLayout());
        total_msg_panel = new JPanel(new BorderLayout());
        
        //top panel variable creation
        name_label = new JLabel("Name: ");
        name_entry = new JTextField(10);
        ip_label = new JLabel("IP Address: ");
        ip_entry = new JTextField(10);
        port_label = new JLabel("Port: ");
        port_entry = new JTextField(5);
        disconnect_btn = new JButton("Connect");
        disconnect_btn.addActionListener((e) -> {
            if(disconnect_btn.getText().equals("Connect")){
                connect_to_server(this);
                disconnect_btn.setText("Disconnect");
            }
            else{
                disconnect_to_server(this);
                disconnect_btn.setText("Connect");
            }
        });

        //scrolls
        mmbers_scroll = new JScrollPane();
        msg_scroll = new JScrollPane();
        compose_scroll = new JScrollPane();
    
    

        //add variables to top panel
        top_panel.add(name_label);
        top_panel.add(name_entry);
        top_panel.add(ip_label);
        top_panel.add(ip_entry);
        top_panel.add(port_label);
        top_panel.add(port_entry);
        top_panel.add(disconnect_btn);


        //create the variables for the message panel
        msg_label = new JLabel("Messages");
        msg_area = new JTextArea(10,50);
        msg_area.setEditable(false);
        msg_scroll.setViewportView(msg_area);
        

        //the composer
        compose_panel = new JPanel(new BorderLayout());
        compose_label = new JLabel("Compose");
        compose_area = new JTextArea(3, 50);
        compose_area.setEditable(true);
        compose_area.addKeyListener(new KeyListener(){

            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '\n'){
                    send.doClick();
                }                
            }

            public void keyPressed(KeyEvent e) {}

            public void keyReleased(KeyEvent e) {}

        }); 
        compose_scroll.setViewportView(compose_area);
        compose_panel.add(compose_label, BorderLayout.NORTH);
        compose_panel.add(compose_scroll, BorderLayout.SOUTH);
        

        //add to the message panel
        msg_panel.add(msg_label, BorderLayout.NORTH);
        msg_panel.add(msg_scroll, BorderLayout.SOUTH);
        total_msg_panel.add(msg_panel, BorderLayout.NORTH);
        total_msg_panel.add(compose_panel, BorderLayout.SOUTH);

    
        //creates the variables for the member panel
        members_online_label = new JLabel("Members Online");
        members_list = new JTextArea(15,10);
        members_list.setEditable(false);
        mmbers_scroll.setViewportView(members_list);

        //add to the member panel
        mmb_panel.add(members_online_label, BorderLayout.NORTH);
        mmb_panel.add(mmbers_scroll, BorderLayout.SOUTH);

        //add member and messages panel to mid panel
        mid_panel.add(mmb_panel);
        mid_panel.add(total_msg_panel);

        //grid constraints
        send = new JButton("Send");
        send.addActionListener((e) -> {
            sendMessage(this);
        });
        bottom_panel.add(send, BorderLayout.EAST);

        

        //create the frame
        this.setSize(770,200);
        this.add(top_panel, BorderLayout.NORTH);
        this.add(mid_panel, BorderLayout.CENTER);
        this.add(bottom_panel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    public boolean connect_to_server(JFrame j){
        //if the name, host, and port are empty
        if(name_entry.getText().equals("")){
            disconnect_btn.setText("Connect");
            JOptionPane.showMessageDialog(j, "No username given");
            return false;
        }

        if(ip_label.getText().equals("")){
            disconnect_btn.setText("Connect");
            JOptionPane.showMessageDialog(j, "No IP address given");
            return false;
        }

        if(port_entry.getText().equals("")){
            disconnect_btn.setText("Connect");
            JOptionPane.showMessageDialog(j, "No port given");
            return false;
        }
        try{
            this.sock = new Socket(InetAddress.getByName(ip_entry.getText()), Integer.parseInt(port_entry.getText()));
        } catch (Exception e){
            //failed to created socket
            JOptionPane.showMessageDialog(j, "Failed to create socket");
            return false;
        }

        //try to pass secret and name
        try{
            pw = new PrintWriter(sock.getOutputStream());
            pw.println("SECRET");
            pw.println("3c3c4ac618656ae32b7f3431e75f7b26b1a14a87");
            pw.println("NAME");
            pw.println(name_entry.getText());
            pw.flush();

            //if successful, connect the ip and port and start a new thread
            msg_area.append("CONNECTED\n");
            GWackClientNetworking g = new GWackClientNetworking(this.sock);

        } catch (Exception e){
            JOptionPane.showMessageDialog(j, "Login failed");
            return false;
        }

        //login succesfull
        return true;
    }

    public boolean disconnect_to_server(JFrame j){
        try{
            pw.close();
            sock.close();
            msg_area.setText("");
            compose_area.setText("");
            members_list.setText("");
            name_entry.setText("");
            ip_entry.setText("");
            port_entry.setText("");

        } catch (Exception e){
            JOptionPane.showMessageDialog(j, "Could not disconnect from server");
        }
        return true;
    }

    public void sendMessage(JFrame j){
        if(sock == null || sock.isClosed()){
            JOptionPane.showMessageDialog(j, "Connect to the server");
            return;
        }
       
        
        compose_area.setText(compose_area.getText().replace("\n", ""));
        try{
            pw.println("START MSG");
            String handle = "[" + name_entry.getText() + "] ";
            pw.println(handle + " " +newMessage());
            pw.println("END MSG");
            pw.flush();
            compose_area.setText("");
        } catch (Exception e){
            JOptionPane.showMessageDialog(j, "Error sending message");
        }
    }

    public String newMessage(){
        return compose_area.getText();
    }

    private class GWackClientNetworking extends Thread{
        private Socket sock;
        private PrintWriter pw;
        private BufferedReader br;


        public GWackClientNetworking(Socket sock){
            try{
                this.sock = sock;
                ReadingThread thread = new ReadingThread(sock);
                thread.start();
            } catch(Exception e){
                ;
            }
        }

        //should be synchronized so no two people write at the same time
        public synchronized void writeMessage(String msg){
            msg_area.append(msg + "\n");
        }

        public boolean isConnected(){
            if(sock.isClosed()){
                return false;
            }
            return true;
        }

        public void disconnect(){
            try{
                sock.close();
                pw.close();
                br.close();
            } catch (Exception e){
                ;
            }
        }

        private class ReadingThread extends Thread{
            String line = "";
            String current_list = "";
            Socket sock;

            public ReadingThread(Socket sock){
                this.sock = sock;
            }

            public void run(){
                boolean getting_message = false;
                boolean getting_list = false;
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    if(isConnected() == false){
                        return;
                    }
                    while((line = br.readLine()) != null){
                        if(line.contains("START MSG")){
                            getting_message = true;
                            continue;
                        }

                        else if(line.contains("END MSG")){
                            getting_message = false;
                            continue;
                        }

                        else if(getting_message == true){
                            writeMessage(line);
                            continue;
                        }

                        else if(line.equals("START_CLIENT_LIST")){
                            members_list.setText(null);
                            getting_list = true;
                            continue;
                        }

                        else if(line.equals("END_CLIENT_LIST")){
                            String[] list_split = current_list.split("|");
                            for(String s: list_split){
                                s.replaceAll("|", "");
                                members_list.append(s);
                            }
                            getting_list = false;
                            current_list = "";
                            continue;
                        }
                        else if(getting_list == true){
                            current_list = current_list + line + "\n";
                            continue;
                        }
                    }
                    
                    disconnect();
                } catch (Exception e){
                    ;
                }
            }
        }

}   

    
    
    
}


