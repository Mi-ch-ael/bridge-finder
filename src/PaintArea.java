import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PaintArea extends JLayeredPane {

    private int x1, x2, y1, y2;
    private ArrayList<NodeImage> nodes;


    public enum Mode {
        Node,
        Edge1,
        Edge2,
        Erase,
        None
    }

    Mode currentMode;

    public PaintArea() {
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setMinimumSize(new Dimension(400, 400));

        currentMode = Mode.None;

        nodes = new ArrayList<NodeImage>();

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
        nodes.add(nd);
    }

    public void drawEdge(Point start, Point end){
        EdgeImage nd = new EdgeImage(start, end);
        add(nd);
    }

    private NodeImage findNodeByCoordinate(Point point){
        for(NodeImage nd: nodes){
            if(Math.pow((point.x - nd.getx()), 2) + Math.pow((point.y - nd.gety()), 2) < 700 )
                return nd;
        }
        return null;
    }

    private class MyMouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();

            switch(currentMode) {
                case Node:
                    String s = (String) JOptionPane.showInputDialog(null, "Enter a Node name (single character):\n");
                    if(s != null & s.length() == 1) {
                        drawNode(s, new Point(x1, y1));
                        //............... Adding a node
                    }
                    currentMode = Mode.None;
                    break;
                case Edge1:
                    NodeImage nd = findNodeByCoordinate(new Point(x1, y1));
                    if(nd != null) {
                        x2 = nd.getx();
                        y2 = nd.gety();
                        currentMode = Mode.Edge2;
                    }
                    else
                        currentMode = Mode.None;
                    break;
                case Edge2:
                    NodeImage nd1 = findNodeByCoordinate(new Point(x1, y1));
                    if(nd1 != null) {
                        drawEdge(new Point(x2, y2), new Point(nd1.getx(), nd1.gety()));
                        //............. Adding a edge
                    }
                    currentMode = Mode.None;
                    break;
                case Erase:
                    NodeImage nd2 = findNodeByCoordinate(new Point(x1, y1));
                    if(nd2 != null) {
                        remove(nd2); //............. Erase a node
                        nodes.remove(nd2);
                        revalidate();
                        repaint();
                    }
                    currentMode = Mode.None;
                    break;
                case None:
                    break;
            }
        }

        public void mouseDragged(MouseEvent e) {
        }
    }
}