package algorithm;

import java.util.ArrayList;

public class Graph {
  private ArrayList<Node> nodes = new ArrayList<>();
  private ArrayList<Edge> edges = new ArrayList<>();
  private ArrayList<Edge> bridges = new ArrayList<>();
  private ArrayList<Object> visualizationObjects = new ArrayList<>();

  public Graph(){}

  public Node getNodeByName(String searchNodeName){
      for(int i = 0; i < nodes.size(); i++)
          if(nodes.get(i).getName().equals(searchNodeName))
              return nodes.get(i);
      return null;
  }

  public boolean addNode(String newNodeName){ return addNode(new Node(newNodeName)); }

  public boolean addNode(Node newNode){
      for(int i = 0; i < nodes.size(); i++)
          if(nodes.get(i).equals(newNode))
              return false;
      return nodes.add(newNode);
  }

  public boolean removeNode(String nodeName){ return removeNode(getNodeByName(nodeName)); }

  public boolean removeNode(Node removedNode){
      if(removedNode == null)
          return false;
      Edge tempEdge;
      Node tempNode;
      ArrayList<Edge> tempAdjacentEdges;
      int tempIndex = nodes.indexOf(removedNode);
      if(tempIndex == -1)
          return false;
      nodes.remove(tempIndex);
      for(int i = 0; i < edges.size(); i++){
          tempEdge = edges.get(i);
          if((tempEdge.getFirstNode() == removedNode)||(tempEdge.getSecondNode() == removedNode)){
              edges.remove(i);
              tempNode = (tempEdge.getFirstNode() == removedNode) ? tempEdge.getSecondNode() : tempEdge.getFirstNode();
              tempAdjacentEdges = tempNode.getAdjacentEdges();
              tempAdjacentEdges.remove(tempAdjacentEdges.indexOf(tempEdge));
              i--;
          }
      }
      return true;
  }

  public boolean addEdge(String newFirstNodeName, String newSecondNodeName){
      Node firstNode = getNodeByName(newFirstNodeName), secondNode = getNodeByName(newSecondNodeName);
      return ((firstNode == null)||(secondNode == null)) ? false : addEdge(new Edge(getNodeByName(newFirstNodeName), getNodeByName(newSecondNodeName)));
  }

  public boolean addEdge(Edge newEdge){
      if(newEdge.getFirstNode() == newEdge.getSecondNode())
          return false;
      for(int i = 0; i < edges.size(); i++)
          if(edges.get(i).equals(newEdge))
              return false;
      edges.add(newEdge);
      newEdge.getFirstNode().addEdge(newEdge);
      newEdge.getSecondNode().addEdge(newEdge);
      return true;
  }

  public boolean removeEdge(String firstNodeName, String secondNodeName){ return removeEdge(getNodeByName(firstNodeName), getNodeByName(secondNodeName)); }

  public boolean removeEdge(Node firstNode, Node secondNode){
      if((firstNode == null)||(secondNode == null))
          return false;
      Edge currentEdge = new Edge(firstNode, secondNode);
      for(int i = 0; i < edges.size(); i++)
          if(edges.get(i).equals(currentEdge))
              return removeEdge(edges.get(i));
      return false;
  } 

  public boolean removeEdge(Edge removedEdge){
      int tempIndex = edges.indexOf(removedEdge);
      if(tempIndex == -1)
          return false;
      edges.remove(tempIndex);
      ArrayList<Edge> tempAdjacentEdges;
      tempAdjacentEdges = removedEdge.getFirstNode().getAdjacentEdges();
      tempIndex = tempAdjacentEdges.indexOf(removedEdge);
      if(tempIndex != -1)
          tempAdjacentEdges.remove(tempIndex);
      tempAdjacentEdges = removedEdge.getSecondNode().getAdjacentEdges();
      tempIndex = tempAdjacentEdges.indexOf(removedEdge);
      if(tempIndex != -1)
          tempAdjacentEdges.remove(tempIndex);
      return true;
  }

  public ArrayList<Node> getNodes(){ return nodes; }

  public ArrayList<Edge> getEdges(){ return edges; }

  public ArrayList<Edge> getBridges(){ return bridges; }

  public ArrayList<Object> getVisualizationObjects(){ return visualizationObjects; }

  public void runAlgorithm(){
      bridges.clear();
      visualizationObjects.clear();
      for(int i = 0; i < edges.size(); i++){
          edges.get(i).reset();
      }
      for(int i = 0; i < nodes.size(); i++){
          nodes.get(i).reset();
      }
      int timeDFS = 0;
      for(int i = 0; i < nodes.size(); i++)
          if(nodes.get(i).getAlgorithmValues()[0] == 0)
              timeDFS = algorithmDFS(nodes.get(i), timeDFS);
      for(int i = 0; i < edges.size(); i++)
          if(edges.get(i).isBridge)
              bridges.add(edges.get(i));
  }

  private int algorithmDFS(Node nodeComponent, int timeDFS){
      timeDFS++;
      Node nextNode;
      Edge adjacentEdge;
      ArrayList<Edge> adjacentEdges = nodeComponent.getAdjacentEdges();
      int[] newAlgorithmValues = new int[4];
      int[] nextNodeAlgorithmValues;
      newAlgorithmValues[0] = timeDFS;
      newAlgorithmValues[1] = 1;
      newAlgorithmValues[2] = timeDFS;
      newAlgorithmValues[3] = timeDFS;
      nodeComponent.setAlgorithmValues(newAlgorithmValues);
	  visualizationObjects.add(nodeComponent);

      for(int i = 0; i < adjacentEdges.size(); i++){
          adjacentEdge = adjacentEdges.get(i);
          if(nodeComponent.getParentTreeEdge() == adjacentEdge)
              continue;
          nextNode = adjacentEdge.getAdjacentNode(nodeComponent);
          nextNodeAlgorithmValues = nextNode.getAlgorithmValues();
          if(nextNodeAlgorithmValues[1] == 0){
              if(adjacentEdge.getFirstNode() != nodeComponent)
                  adjacentEdge.swapNodes();
              adjacentEdge.isForward = true;
              nextNode.setParentTreeEdge(adjacentEdge);
              visualizationObjects.add(adjacentEdge);
              timeDFS = algorithmDFS(nextNode, timeDFS);
              newAlgorithmValues[1] += nextNodeAlgorithmValues[1];
              if(nextNodeAlgorithmValues[2] < newAlgorithmValues[2])
                  newAlgorithmValues[2] = nextNodeAlgorithmValues[2];
              if(nextNodeAlgorithmValues[3] > newAlgorithmValues[3])
                  newAlgorithmValues[3] = nextNodeAlgorithmValues[3];
          } else {
              if(adjacentEdge.getFirstNode() != nodeComponent)
                  adjacentEdge.swapNodes();
              if(!adjacentEdge.isBackward){
                visualizationObjects.add(adjacentEdge);
                visualizationObjects.add(adjacentEdge);
              }
              adjacentEdge.isBackward = true;
              if(nextNodeAlgorithmValues[0] < newAlgorithmValues[2])
                  newAlgorithmValues[2] = nextNodeAlgorithmValues[0];
              if(nextNodeAlgorithmValues[0] > newAlgorithmValues[3])
                  newAlgorithmValues[3] = nextNodeAlgorithmValues[0];
          }
      }
      nodeComponent.setAlgorithmValues(newAlgorithmValues);
	  visualizationObjects.add(nodeComponent);
      if(nodeComponent.getParentTreeEdge() != null){
          visualizationObjects.add(nodeComponent.getParentTreeEdge());
          if((newAlgorithmValues[0] == newAlgorithmValues[2])&&(newAlgorithmValues[3] < newAlgorithmValues[0] + newAlgorithmValues[1]))
              nodeComponent.getParentTreeEdge().isBridge = true;
      }
      return timeDFS;
  }

  public void print(){
      for(int i = 0; i < nodes.size(); i++){
          int[] nodeAlgorithmValues = nodes.get(i).getAlgorithmValues();
          Point nodePoint = nodes.get(i).getPoint();
          System.out.printf("Node: %s | AlgValues: %d,%d,%d,%d | Point: %d %d\n", nodes.get(i).getName(), nodeAlgorithmValues[0], nodeAlgorithmValues[1], nodeAlgorithmValues[2], nodeAlgorithmValues[3], nodePoint.x, nodePoint.y);
      }
      System.out.printf("\n");
      for(int i = 0; i < edges.size(); i++)
          System.out.printf("Edge: %s-%s | Forward/Backward: %b/%b | isBridge: %b\n", edges.get(i).getFirstNode().getName(), edges.get(i).getSecondNode().getName(), edges.get(i).isForward, edges.get(i).isBackward, edges.get(i).isBridge);
      System.out.printf("\n");
  }

  public void reset(){
      nodes.clear();
      edges.clear();
      bridges.clear();
      visualizationObjects.clear();
  }
}
