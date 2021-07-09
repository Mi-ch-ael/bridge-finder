package algorithm;

public class Edge {
  private Node firstNode = null, secondNode = null;
  public boolean isBridge = false;
  public boolean isForward = false;
  public boolean isBackward = false;

  public Edge(Node firstNode, Node secondNode){
      this.firstNode = firstNode;
      this.secondNode = secondNode;
  }
  
  public Node getFirstNode(){ return firstNode; }

  public Node getSecondNode(){ return secondNode; }

  public void swapNodes(){
      Node tempNode = firstNode;
      firstNode = secondNode;
      secondNode = tempNode;
  }

  public Node getAdjacentNode(Node node){ return (firstNode == node) ? secondNode : firstNode; }

  public boolean equals(Edge anotherEdge){
      boolean isEqualForward = ((this.firstNode == anotherEdge.firstNode) && (this.secondNode == anotherEdge.secondNode));
      boolean isEqualBackward = ((this.firstNode == anotherEdge.secondNode) && (this.secondNode == anotherEdge.firstNode));
      return (isEqualForward || isEqualBackward);
  }

  public void reset(){
      isBridge = false;
      isForward = false;
      isBackward = false;
  }
  
  @Override
  public String toString() {
	  String delimiter;
	  delimiter = isForward ? " -> " : " <- ";
	  return this.getFirstNode().getName() + delimiter + this.getSecondNode().getName();
  }
}

