import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GWackTopPanel extends JPanel{
     //top panel variables
    private JLabel name_label;
    private JTextField name_entry;
    private JLabel ip_label;
    private JTextField ip_entry;
    private JLabel port_label;
    private JTextField port_entry;
    private JButton disconnect_btn;

    
    public GWackTopPanel(FlowLayout f){
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
        this.add(name_label);
        this.add(name_entry);
        this.add(ip_label);
        this.add(ip_entry);
        this.add(port_label);
        this.add(port_entry);
        this.add(disconnect_btn);
    }
    //action listener for connection button
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
