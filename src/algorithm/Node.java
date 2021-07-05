//package algorithm;
//import Edge;
//import Point;

import java.util.ArrayList;

public class Node {
    private String name = "";
    private int[] algorithmValues = new int[4];
    private Edge parentTreeEdge = null;
    private Point point;
    private ArrayList<Edge> adjacentEdges = new ArrayList<>();

    public Node(String name){
        this.name = name;
        this.point = new Point(0, 0);
    }

    public Node(String name, int x, int y){
        this.name = name;
        this.point = new Point(x, y);
    }

    public Node(String name, point newPoint){
        this.name = name;
        this.point = newPoint;
    }

    public Point getPoint(){ return point; }

    public void setPoint(Point newPoint){ setPoint(newPoint.x, newPoint.y); }

    public void setPoint(int x, int y){
        point.x = x;
        point.y = y;
    }

    public String getName(){ return name; }

    public int[] getAlgorithmValues(){ return algorithmValues; }

    public void setAlgorithmValues(int[] newValues){
        for(int i = 0; i < algorithmValues.length; i++ )
            algorithmValues[i] = newValues[i];
    }

    public boolean addEdge(Edge newEdge){
        for(int i = 0; i < adjacentEdges.size(); i++)
            if(adjacentEdges.get(i).equals(newEdge))
                return false;
        return adjacentEdges.add(newEdge);
    }

    public boolean removeEdge(Edge removedEdge){
        int tempIndex = adjacentEdges.indexOf(removedEdge);
        return (tempIndex == -1) ? false : adjacentEdges.remove(tempIndex);
    }
    
    public ArrayList<Edge> getAdjacentEdges(){ return adjacentEdges; }

    public Edge getParentTreeEdge(){ return parentTreeEdge; }

    public void setParentTreeEdge(Edge parentTreeEdge){ this.parentTreeEdge = parentTreeEdge; }

    public boolean equals(Node anotherNode){ return this.getName().equals(anotherNode.getName()); }

    public void reset(){
        algorithmValues = new int[4];
        parentTreeEdge = null;
    }
}
