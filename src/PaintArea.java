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
                    if (findNodeByCoordinate(new Point(x1, y1)) != null) {
                        JOptionPane.showMessageDialog(null, "Do not put nodes too close");
                        return;
                    }
                    String s = (String) JOptionPane.showInputDialog(null, "Enter a Node name (single character):\n");
                    if(s != null && s.length() == 1) {
                        if(graph.addNode(s)) {
                        	Node node = graph.getNodeByName(s);
                        	node.setPoint(x1, y1);
                        	drawNode(node);
                        }
                    }
                    break;
                case Edge1:
                    Node nd = findNodeByCoordinate(new Point(x1, y1));
                    if(nd != null) {
                        x2 = nd.getPoint().x;
                        y2 = nd.getPoint().y;
                        currentMode = PaintAreaMode.Edge2;
                    }
                    break;
                case Edge2:
                    Node nd1 = findNodeByCoordinate(new Point(x1, y1));
                    if(nd1 != null) {
                    	Edge edge = new Edge(findNodeByCoordinate(new Point(x2, y2)), nd1);
                    	if(graph.addEdge(edge)) {
                    		drawEdge(edge);
                    	}
                    }
                    currentMode = PaintAreaMode.Edge1;
                    break;
                case Erase:
                    Node deletedNode = findNodeByCoordinate(new Point(x1, y1));
                    if(deletedNode != null) {
                    	graph.removeNode(deletedNode);
                        clear();
                        drawGraph();
                    }
                    else{
                        Edge deletedEdge = findEdgeByCoordinate(new Point(x1, y1));
                        if(deletedEdge != null){
                            graph.removeEdge(deletedEdge);
                            clear();
                            drawGraph();
                        }
                    }
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


        currentMode = PaintAreaMode.None;

        MyMouseHandler handler = new MyMouseHandler();
        this.addMouseListener(handler);
        this.addMouseMotionListener(handler);
    }

    public void setMode(PaintAreaMode md){
        currentMode = md;
    }

    public void drawNode(Node node) {
    	this.add(new NodeImage(node.getName(), node.getPoint()));
    }
    
    public void drawEdge(Edge edge) {
    	this.add(new EdgeImage(edge.getFirstNode().getPoint(), edge.getSecondNode().getPoint()));
    }

    public void drawArrow(Edge edge) {
        EdgeImage arrow = new EdgeImage(edge.getFirstNode().getPoint(), edge.getSecondNode().getPoint());
        arrow.transformIntoArrow();
        add(arrow);
    }
    
    public void drawNode(Node node, Color color) {
    	NodeImage img = new NodeImage(node.getName(), node.getPoint());
    	img.setColor(color);
    	this.add(img);
    	this.moveToFront(img);
    }

    public void drawNode(Node node, Color color, String[] text) {
        NodeImage img = new NodeImage(node.getName(), node.getPoint());
        img.setText(text);
        img.setColor(color);
        this.add(img);
        this.moveToFront(img);
    }

    public void drawEdge(Edge edge, Color color) {
    	EdgeImage img = new EdgeImage(edge.getFirstNode().getPoint(), edge.getSecondNode().getPoint());
    	img.setColor(color);
    	this.add(img);
    	this.moveToFront(img);
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
            if(Math.pow((point.x - nd.getPoint().x), 2) + Math.pow((point.y - nd.getPoint().y), 2) < 3500 )
                return nd;
        }
        return null;
    }

    private Edge findEdgeByCoordinate(Point point){
        for(Edge edge: graph.getEdges()){
            int EPS = 10;
            int x1 = edge.getFirstNode().getPoint().x;
            int y1 = edge.getFirstNode().getPoint().y;
            int x2 = edge.getSecondNode().getPoint().x;
            int y2 = edge.getSecondNode().getPoint().y;
            int minx = Math.min(x1, x2);
            int miny = Math.min(y1, y2);
            int maxx = Math.max(x1, x2);
            int maxy = Math.max(y1, y2);
            if(point.x > minx && point.y > miny && point.x < maxx && point.y < maxy &&
            point.x * (y2 - y1) - point.y * (x2 - x1) - x1 * (y2 - y1) + y1 * (x2 - x1) <= EPS )
                return edge;
        }
        return null;
    }
}