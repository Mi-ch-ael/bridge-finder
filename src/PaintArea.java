import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import algorithm.Point;
import algorithm.Edge;
import algorithm.Node;
import algorithm.Graph;

public class PaintArea extends JLayeredPane {

	private final Graph graph;
    private int x1, y1, x2, y2;
    PaintAreaMode currentMode;

    private class MyMouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();

            switch(currentMode) {
                case Node:
                    String s = (String) JOptionPane.showInputDialog(null, "Enter a Node name (single character):\n");
                    if(s != null && s.length() == 1) {
                        if(graph.addNode(s)) {
                        	Node node = graph.getNodeByName(s);
                        	node.setPoint(x1, y1);
                        	drawNode(node);
                        }
                    }
                    currentMode = PaintAreaMode.None;
                    break;
                case Edge1:
                    Node nd = findNodeByCoordinate(new Point(x1, y1));
                    if(nd != null) {
                        x2 = nd.getPoint().x;
                        y2 = nd.getPoint().y;
                        currentMode = PaintAreaMode.Edge2;
                    }
                    else
                        currentMode = PaintAreaMode.None;
                    break;
                case Edge2:
                    Node nd1 = findNodeByCoordinate(new Point(x1, y1));
                    if(nd1 != null) {
                    	Edge edge = new Edge(findNodeByCoordinate(new Point(x2, y2)), nd1);
                    	if(graph.addEdge(edge)) {
                    		drawEdge(edge);
                    	}
                    }
                    currentMode = PaintAreaMode.None;
                    break;
                case Erase:
                    Node deletedNode = findNodeByCoordinate(new Point(x1, y1));
                    if(deletedNode != null) {
                    	graph.removeNode(deletedNode);
                        clear();
                        drawGraph();
                    }
                    currentMode = PaintAreaMode.None;
                    break;
                case None:
                    break;
            }
        }

        public void mouseDragged(MouseEvent e) {
        }
    }

    public PaintArea(Graph graph) {
    	this.graph = graph;
    	
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setMinimumSize(new Dimension(400, 400));

        currentMode = PaintAreaMode.None;

        MyMouseHandler handler = new MyMouseHandler();
        this.addMouseListener(handler);
        this.addMouseMotionListener(handler);
    }

    public void setMode(PaintAreaMode md){
        currentMode = md;
    }

    private void drawNode(Node node) {
    	this.add(new NodeImage(node.getName(), node.getPoint()));
    }

    private void drawEdge(Edge edge) {
    	this.add(new EdgeImage(edge.getFirstNode().getPoint(), edge.getSecondNode().getPoint()));
    }
    
    public void drawGraph() {
    	for(Node node: this.graph.getNodes()) {
    		this.drawNode(node);
    	}
    	for(Edge edge: this.graph.getEdges()) {
    		this.drawEdge(edge);
    	}
    	this.revalidate();
    	this.repaint();
    }
    
    public void clear() {
    	Component[] components = this.getComponents();
    	while(components.length > 0) {
    		this.remove(components[0]);
    		components = this.getComponents();
    	}
    }

    private Node findNodeByCoordinate(Point point){
        for(Node nd: graph.getNodes()){  
            if(Math.pow((point.x - nd.getPoint().x), 2) + Math.pow((point.y - nd.getPoint().y), 2) < 700 )
                return nd;
        }
        return null;
    }


}