import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import algorithm.Point;

public class EdgeImage extends JComponent{
    private static final long serialVersionUID = 1L;
    private Point start;
    private Point end;
    private final int ARR_SIZE = 4;

    private boolean isArrow = false;

    private Color color = Color.BLACK;


    EdgeImage(Point start, Point end){
        this.start = start;
        this.end = end;
        setBounds(0, 0, 1000, 1000);
    };

    public void transformIntoArrow(){   // Преобразование координат конца ребра в координаты конца ребра-стрелки
        int x1 = start.x - end.x;
        int y1 = start.y - end.y;
        int x2 = 0;
        int y2 = 0;

        double a = y2 - y1;
        double b = x1 - x2;
        double c = y1 * (x2 - x1) - x1 * (y2 - y1);
        int r = 35;

        double x0 = -a * c / (a * a + b * b), y0 = -b * c / (a * a + b * b);

        double d = r * r - c * c / (a * a + b * b);
        double mult = Math.sqrt(Math.abs(d) / (a * a + b * b));
        double ax, ay, bx, by;
        ax = x0 + b * mult;
        bx = x0 - b * mult;
        ay = y0 - a * mult;
        by = y0 + a * mult;
        if (Math.abs(ax - start.x) + Math.abs(ay - start.y) < Math.abs(bx - start.x) + Math.abs(by - start.y)) {
            end.x =  (int) ax + end.x;
            end.y =  (int) ay + end.y;
        } else {
            end.x = - (int) bx + end.x;
            end.y = - (int) by + end.y;
        }

        isArrow = true;

    }


    public void setColor(Color color){ this.color = color; }
    public Color getColor(Color color) { return this.color; }


    public void paintComponent(Graphics g1){
        g1.setColor(color);
        if(isArrow) {   //   Помимо обычной линии рисуется наконечник у стрелки
            double dx = end.x - start.x, dy = end.y - start.y;
            double angle = Math.atan2(dy, dx);
            int len = (int) Math.sqrt(dx*dx + dy*dy);
            AffineTransform at = AffineTransform.getTranslateInstance(start.x, start.y);
            at.concatenate(AffineTransform.getRotateInstance(angle));
            Graphics2D g = (Graphics2D) g1.create();
            g.transform(at);

            g.drawLine(0, 0, len, 0);
            g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                    new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
        }
        else
            g1.drawLine(start.x, start.y, end.x, end.y);
    }
}

