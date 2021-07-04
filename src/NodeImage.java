import javax.swing.*;
import java.awt.*;

public class NodeImage extends JComponent{
    private static final long serialVersionUID = 1L;
    private final int size = 70;
    private Point center;
    private String text;
    NodeImage(String text, Point center){
            this.text = text;
            this.center = center;
            setBounds(0, 0, 10000, 10000);
        };
    public void paintComponent(Graphics g){
            g.drawOval(center.x,center.y, size, size);
            g.setColor(Color.black);
            g.drawString(text,  center.x + size/2, center.y + size/2);
    }
}

