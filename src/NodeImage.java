import javax.swing.*;
import java.awt.*;

import algorithm.Point;

public class NodeImage extends JComponent{
    private static final long serialVersionUID = 1L;
    private final int size = 70;
    private Point center;
    private String text;
    private Color color = Color.CYAN;
    NodeImage(String text, Point center){
            this.text = text;
            this.center = center;
            setBounds(0, 0, 10000, 10000);
    };

    public int getx(){return center.x;}
    public int gety(){return center.y;}

    public void setColor(Color color){this.color = color;}

    public void paintComponent(Graphics g){
            g.setColor(color);
            g.fillOval(center.x-size/2,center.y-size/2, size, size);
            g.setColor(Color.black);
            Font f = new Font("Times New Roman", Font.PLAIN, 20);
            g.setFont(f);
            g.drawString(text,  center.x-7, center.y+5);
    }
}

