import javax.swing.*;
import java.awt.*;
import java.sql.Array;

import algorithm.Point;

public class NodeImage extends JComponent{
    private static final long serialVersionUID = 1L;
    public static final int SIZE = 60;
    private Point center;
    private String name;
    private String[] text;
    private Color color = Color.CYAN;
    NodeImage(String text, Point center){
            this.name = text;
            this.center = center;
            setBounds(0, 0, 10000, 10000);
    };

    public void setText(String[] text){
        this.text = text;
    }


    public void setColor(Color color){this.color = color;}
    public Color getColor() { return this.color; }

    public void paintComponent(Graphics g){
            g.setColor(color);
            g.fillOval(center.x-SIZE/2,center.y-SIZE/2, SIZE, SIZE);
            g.setColor(Color.black);
            Font f = new Font("Times New Roman", Font.PLAIN, 20);
            g.setFont(f);
            g.drawString(name,  center.x-7, center.y+5);
            if(text != null) {
                g.drawString("N = " + text[0], center.x + SIZE / 2 + 5, center.y - SIZE / 2);
                g.drawString("D = " + text[1], center.x + SIZE / 2 + 5, center.y - SIZE / 2 + 20);
                g.drawString("L = " + text[2], center.x + SIZE / 2 + 5, center.y - SIZE / 2 + 40);
                g.drawString("H = " + text[3], center.x + SIZE / 2 + 5, center.y - SIZE / 2 + 60);
            }
    }
}

