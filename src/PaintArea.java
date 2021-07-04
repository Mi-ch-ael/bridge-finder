import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PaintArea extends JLayeredPane {

    private int x1, x2, y1, y2;
    Graphics g;

    public enum Mode {
        Node,
        Edge1,
        Edge2,
        None
    }

    Mode currentMode;

    public PaintArea() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setMinimumSize(new Dimension(400, 400));

        currentMode = Mode.None;

        MyMouseHandler handler = new MyMouseHandler();
        this.addMouseListener(handler);
        this.addMouseMotionListener(handler);
    }

    public void setMode(Mode md){
        currentMode = md;
    }

    public void drawNode(String str, Point point){
        NodeImage nd = new NodeImage(str, point);
        add(nd);
    }

    public void drawEdge(Point start, Point end){
        EdgeImage nd = new EdgeImage(start, end);
        add(nd);
    }

    private class MyMouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();

            switch(currentMode) {
                case Node:
                    drawNode("A", new Point(x1, y1));
                    currentMode = Mode.None;
                    //............... Adding a node
                    break;
                case Edge1:
                    x2 = x1;
                    y2 = y1;
                    currentMode = Mode.Edge2;
                    break;
                case Edge2:
                    drawEdge(new Point(x2, y2), new Point(x1, y1));
                    currentMode = Mode.None;
                    //............. Adding a edge
                    break;
                case None:
                    break;
            }
        }

        public void mouseDragged(MouseEvent e) {
        }
    }
}