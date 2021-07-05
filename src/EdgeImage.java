import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class EdgeImage extends JComponent{
    private static final long serialVersionUID = 1L;
    private Point start;
    private Point end;
    private final int ARR_SIZE = 4;
    //private final int width;
    EdgeImage(Point start, Point end){
        this.start = start;
        this.end = end;
        setBounds(0, 0, 1000, 1000);
    };

    public void paintComponent(Graphics g1){
        /*double dx = end.x - start.x, dy = end.y - start.y;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(start.x, start.y);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        Graphics2D g = (Graphics2D) g1.create();
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);*/
        g1.drawLine(start.x, start.y, end.x, end.y);
    }
}

