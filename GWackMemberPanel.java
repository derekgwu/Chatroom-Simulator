import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GWackMemberPanel extends JPanel{
    public GWackMemberPanel(BorderLayout b){
        //member panel creation
        JLabel members_online_label = new JLabel("Members Online");
        JTextArea members_list = new JTextArea(15,10);
        members_list.setEditable(false);
        this.add(members_online_label, BorderLayout.NORTH);
        this.add(members_list, BorderLayout.SOUTH);
    }
}
