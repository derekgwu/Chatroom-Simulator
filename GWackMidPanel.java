import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GWackMidPanel extends JPanel{
    public GWackMidPanel(FlowLayout f){
        //adds the member panel
        GWackMemberPanel mmb_panel = new GWackMemberPanel(new BorderLayout());
        this.add(mmb_panel);

        //adds the message panel
        GWackMessagePanel msg_panel =new GWackMessagePanel();
        this.add(msg_panel);
    }
}
