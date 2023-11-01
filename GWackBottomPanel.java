import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GWackBottomPanel extends JPanel{
    public GWackBottomPanel(BorderLayout b){
        //grid constraints
        GridLayout g = new GridLayout(3, 1);
        this.setLayout(g);
        JButton send = new JButton("Send");
        this.add(send, BorderLayout.WEST);
    }
}
