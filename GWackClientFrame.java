import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GWackClientFrame extends JFrame{

    public GWackClientFrame(){
        //create the panels
        GWackTopPanel top_panel = new GWackTopPanel(new FlowLayout());
        GWackMidPanel mid_panel = new GWackMidPanel(new FlowLayout());
        GWackBottomPanel bottom_panel = new GWackBottomPanel(new BorderLayout());
        this.setSize(770,200);
        this.add(top_panel, BorderLayout.NORTH);
        this.add(mid_panel, BorderLayout.CENTER);
        this.add(bottom_panel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();


    }
    
}


