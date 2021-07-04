import javax.swing.*;
import java.awt.*;

public class EdgeImage extends JComponent{
    private static final long serialVersionUID = 1L;
    private Point start;
    private Point end;
    //private final int width;
    EdgeImage(Point start, Point end){
        this.start = start;
        this.end = end;
        setBounds(0, 0, 1000, 1000);
    };
    public void paintComponent(Graphics g){
        g.drawLine(start.x, start.y, end.x, end.y);
    }
}

