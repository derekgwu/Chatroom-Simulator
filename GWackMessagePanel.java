import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GWackMessagePanel extends JLayeredPane{
    public GWackMessagePanel(){
        //the messages
        JPanel msg_panel = new JPanel(new BorderLayout());
        JLabel msg_label = new JLabel("Messages");
        JTextArea msg_area = new JTextArea(10,50);
        msg_area.setEditable(false);
        msg_panel.add(msg_label, BorderLayout.NORTH);
        msg_panel.add(msg_area, BorderLayout.SOUTH);

        //the composer
        JPanel compose_panel = new JPanel(new BorderLayout());
        JLabel compose_label = new JLabel("Compose");
        JTextArea compose_area = new JTextArea(3, 50);
        compose_area.setEditable(true);
        compose_panel.add(compose_label, BorderLayout.NORTH);
        compose_panel.add(compose_area, BorderLayout.SOUTH);

        //putting it all together
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(msg_panel);
        this.add(compose_panel);
    }
}
