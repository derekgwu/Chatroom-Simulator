import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
    //message panel variables
    private JLabel msg_label;
    private JTextArea msg_area;

    //compose variables
    private JPanel compose_panel;
    private JLabel compose_label;
    private JTextArea compose_area;

    //bottom panel variables
    private JButton send;

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
        disconnect_btn = new JButton("Disconnect");
        disconnect_btn.addActionListener(new Connect());

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

        //the composer
        compose_panel = new JPanel(new BorderLayout());
        compose_label = new JLabel("Compose");
        compose_area = new JTextArea(3, 50);
        compose_area.setEditable(true);
        compose_panel.add(compose_label, BorderLayout.NORTH);
        compose_panel.add(compose_area, BorderLayout.SOUTH);
        

        //add to the message panel
        msg_panel.add(msg_label, BorderLayout.NORTH);
        msg_panel.add(msg_area, BorderLayout.SOUTH);
        total_msg_panel.add(msg_panel, BorderLayout.NORTH);
        total_msg_panel.add(compose_panel, BorderLayout.SOUTH);

    
        //creates the variables for the member panel
        members_online_label = new JLabel("Members Online");
        members_list = new JTextArea(15,10);
        members_list.setEditable(false);

        //add to the member panel
        mmb_panel.add(members_online_label, BorderLayout.NORTH);
        mmb_panel.add(members_list, BorderLayout.SOUTH);

        //add member and messages panel to mid panel
        mid_panel.add(mmb_panel);
        mid_panel.add(total_msg_panel);

        //grid constraints
        send = new JButton("Send");
        send.setPreferredSize(new Dimension(5,25));
        send.setAlignmentX(RIGHT_ALIGNMENT);
        bottom_panel.setLayout(new BoxLayout(bottom_panel, BoxLayout.LINE_AXIS));
        bottom_panel.setLayout(new BoxLayout(bottom_panel, BoxLayout.X_AXIS));

        bottom_panel.add(send);
        

        //create the frame
        this.setSize(770,200);
        this.add(top_panel, BorderLayout.NORTH);
        this.add(mid_panel, BorderLayout.CENTER);
        this.add(bottom_panel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private class Connect implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(disconnect_btn.getText().equals("Disconnect")){
                disconnect_btn.setText("Connect");
            }
            else{
                disconnect_btn.setText("Disconnect");
            }
        }
    }

    
    
    
}


