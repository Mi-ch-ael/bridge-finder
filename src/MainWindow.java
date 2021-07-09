import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import algorithm.Node;
import algorithm.Edge;
import algorithm.Graph;

public class MainWindow extends JFrame {
	private final Graph graph;
	private VisualStatus context;

    private PaintArea area;

    private JTextArea textArea;

    private JScrollPane scroll;

    private ButtonGroup buttonGroup;

    private JRadioButton buttonDrawNode;
    private JRadioButton buttonDrawEdge;
    private JRadioButton buttonErase;

    private JButton buttonOpenFile;
    private JButton buttonSaveInFile;
    private JButton buttonStop;
    private JButton buttonNext;
    private JButton buttonStart;

    private DrawNodeActionListener buttonDrawNodeActionListener;
    private DrawEdgeActionListener buttonDrawEdgeActionListener;
    private EraseActionListener buttonEraseActionListener;
    private StartActionListener buttonStartActionListener;
    private NextActionListener buttonNextActionListener;
    private OpenFileActionListener buttonOpenFileActionListener;
    private StopActionListener buttonStopActionListener;
    
    private PaintAreaMode stashedMode;

    public MainWindow(){
    	graph = new Graph();
    	
        setTitle("Tarjan's bridge-finding algorithm");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(900, 800));

        area = new PaintArea(this.graph);

        textArea = new JTextArea(5, 1);
        textArea.setOpaque(true);
        textArea.setBackground(Color.WHITE);
        textArea.setEditable(false);
        scroll = new JScrollPane(textArea);
        scroll.setBorder(BorderFactory.createLineBorder(Color.black));

        buttonGroup = new ButtonGroup();

        buttonDrawNode = new JRadioButton("Draw Node", false);
        buttonDrawEdge = new JRadioButton("Draw Edge", false);
        buttonErase = new JRadioButton("Erase", false);

        buttonGroup.add(buttonDrawNode);
        buttonGroup.add(buttonDrawEdge);
        buttonGroup.add(buttonErase);


        buttonOpenFile = new JButton("Open File");
        buttonSaveInFile = new JButton("Save In File");
        buttonStop = new JButton("Stop");
        buttonStop.setEnabled(false);
        buttonNext = new JButton("Next");
        buttonNext.setEnabled(false);
        buttonStart = new JButton("Start");

        buttonDrawNodeActionListener = new DrawNodeActionListener();
        buttonDrawNode.addActionListener(buttonDrawNodeActionListener);

        buttonDrawEdgeActionListener = new DrawEdgeActionListener();
        buttonDrawEdge.addActionListener(buttonDrawEdgeActionListener);

        buttonEraseActionListener = new EraseActionListener();
        buttonErase.addActionListener(buttonEraseActionListener);
        
        buttonStartActionListener = new StartActionListener();
        buttonStart.addActionListener(buttonStartActionListener);
        
        buttonNextActionListener = new NextActionListener();
        buttonNext.addActionListener(buttonNextActionListener);
        
        buttonOpenFileActionListener = new OpenFileActionListener();
        buttonOpenFile.addActionListener(buttonOpenFileActionListener);

        buttonStopActionListener = new StopActionListener();
        buttonStop.addActionListener(buttonStopActionListener);

        Container container = getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.weighty = 0.9;
        constraints.weightx = 0.7;
        constraints.gridheight = 6;
        constraints.gridwidth = 4;
        constraints.gridy = 0;
        constraints.gridx = 0;
        container.add(area, constraints);

        constraints.weighty = 0;
        constraints.weightx = 0;

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = 1;
        constraints.gridy = 6;
        constraints.gridx = 0;
        container.add(scroll, constraints);


        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        constraints.gridx = 5;
        constraints.gridy = 0;
        container.add(buttonDrawNode, constraints);

        constraints.gridx = 5;
        constraints.gridy = 1;
        container.add(buttonDrawEdge, constraints);

        constraints.gridx = 5;
        constraints.gridy = 2;
        container.add(buttonErase, constraints);

        constraints.gridx = 5;
        constraints.gridy = 4;
        container.add(buttonOpenFile, constraints);

        constraints.gridx = 5;
        constraints.gridy = 5;
        container.add(buttonSaveInFile, constraints);

        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.gridwidth = 1;
        constraints.gridx = 5;
        constraints.gridy = 5;
        container.add(buttonStop, constraints);

        constraints.gridx = 6;
        constraints.gridy = 5;
        container.add(buttonNext, constraints);

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = 2;
        constraints.gridx = 5;
        constraints.gridy = 6;
        container.add(buttonStart, constraints);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //UIManager.setLookAndFeel(
                //UIManager.getSystemLookAndFeelClassName());
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        new MainWindow();
    }

    public class DrawNodeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            area.setMode(PaintAreaMode.Node);
        }
    }

    public class DrawEdgeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            area.setMode(PaintAreaMode.Edge1);
        }
    }

    public class EraseActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            area.setMode(PaintAreaMode.Erase);
        }
    }
    
    public class OpenFileActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		textArea.setText("");
    		FileReaderWrapper wrapper = new FileReaderWrapper();
    		if(wrapper.open(MainWindow.this) == null) {
    			textArea.append("INFO: File has not been chosen or has not been found.\n");
    			return;
    		}
    		
    		ArrayList<ArrayList<String>> content = wrapper.read(FileReaderWrapper.Mode.SAVEFILE);
    		if(content == null) {
    			textArea.setText("ERROR: Failed to parse file.\n");
    			return;
    		}
    		graph.reset();
    		
    		for(ArrayList<String> line: content) {
    			if(line.size() != 2) {
    				textArea.append("WARNING: Input line doesn't describe an edge: "+line.toString()+". Ignored.\n");
    				continue;
    			}
    			if(line.get(0).length() == 1) graph.addNode(line.get(0));
    			if(line.get(1).length() == 1) graph.addNode(line.get(1));
    			if(!(graph.addEdge(line.get(0), line.get(1)))) {
    				textArea.append("ERROR: Edge " + line.get(0) + " - " + line.get(1) + " is not allowed.\n");
    				textArea.append("\t" + "Either invalid node name has been encountered, or this edge already " + 
    				"exists, or this edge is a loop.\n");
    			}
    		}
    		
    		int radius = Math.min(area.getBounds().height, area.getBounds().width) / 2 - 35;
    		int xCenter = area.getBounds().width/2;
    		int yCenter = area.getBounds().height/2;
    		
    		ArrayList<Node> lst = graph.getNodes();
    		for(int i = 0; i < lst.size(); ++i) {
    			double angle = 2*i*Math.PI/lst.size();
    			lst.get(i).setPoint(new algorithm.Point((int)(xCenter + radius*Math.cos(angle)), (int)(yCenter + radius*Math.sin(angle))));
    		}
    		
    		area.clear();
    		area.drawGraph();
    	}
    }
    
    public class StartActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		stashedMode = area.currentMode;
    		area.currentMode = PaintAreaMode.None;
    		for(Enumeration<AbstractButton> enumeration = buttonGroup.getElements();
    				enumeration.hasMoreElements();) {
    			AbstractButton processedButton = enumeration.nextElement();
    			processedButton.setEnabled(false);
    		}
    		buttonOpenFile.setEnabled(false);
    		buttonSaveInFile.setEnabled(false);
    		buttonStart.setEnabled(false);
    		buttonStop.setEnabled(true);
    		buttonNext.setEnabled(true);
    		
    		ArrayList<Edge> visEdges = graph.getVisualizationEdges();
    		textArea.append("\nStarting algorithm... Click 'Next' to explore steps.\n====\n");
    		graph.runAlgorithm();
    		
    		context = new VisualStatus(visEdges, graph.getNodes(), graph.getBridges());
    	}
    }
    
    public class NextActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		context.step();
    	}
    }
    
    public class StopActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		for(Enumeration<AbstractButton> enumeration = buttonGroup.getElements();
    				enumeration.hasMoreElements();) {
    			enumeration.nextElement().setEnabled(true);
    		}
    		buttonOpenFile.setEnabled(true);
    		buttonSaveInFile.setEnabled(true);
    		buttonStart.setEnabled(true);
    		buttonStop.setEnabled(false);
    		buttonNext.setEnabled(false);
    		area.currentMode = stashedMode;
    		area.clear();
    		area.drawGraph();
    	}
    }
    
    public static enum Stage {
		SEARCH,
		CALCULATION,
		DECISION
	}
    
    public class VisualStatus {
    	private Stage stage;
    	private int searchIndex;
    	private ArrayList<Edge> edgesInSearch;
    	private ArrayList<Node> nodesInSearch;
    	private ArrayList<Node> nodes;
    	//private ArrayList<Edge> edges;
    	private ArrayList<Edge> bridges;
    	private Deque<Edge> stack;
    	
    	public VisualStatus(ArrayList<Edge> edgesInSearch, ArrayList<Node> nodes, ArrayList<Edge> bridges) {
    		this.edgesInSearch = new ArrayList<Edge>();
    		this.edgesInSearch.addAll(edgesInSearch);
    		this.nodesInSearch = new ArrayList<Node>();
    		
    		this.nodes = new ArrayList<Node>();
    		this.nodes.addAll(nodes);
    		this.nodes.sort( (nd1, nd2) -> nd1.getAlgorithmValues()[0] - nd2.getAlgorithmValues()[0] );
    		this.bridges = bridges;
    		
    		if(edgesInSearch.size() > 0) {
    			this.nodesInSearch.add(edgesInSearch.get(0).getFirstNode());
    			for(Node node: this.nodes) {
    				if(node.getAlgorithmValues()[0] < this.nodesInSearch.get(0).getAlgorithmValues()[0]) {
    					area.drawNode(node, Color.GRAY);
    				}
    			}
    			area.drawNode(edgesInSearch.get(0).getFirstNode(), Color.YELLOW);
    		}
    		
    		stack = new ArrayDeque<Edge>();
    		
    		stage = Stage.SEARCH;
    		searchIndex = 0;
    	}
    	
    	public void step() {
    		switch(stage) {
    		case SEARCH:
    			if(searchIndex >= edgesInSearch.size()) {
    				textArea.append("No more edges left to perform depth-first search.\n");
    				stage = Stage.CALCULATION;
    				if(edgesInSearch.size() == 0) break;
    				for(Node node: nodes) {
    					if(edgesInSearch.get(edgesInSearch.size() - 1).getFirstNode().getAlgorithmValues()[0] < 
    						node.getAlgorithmValues()[0])
    						area.drawNode(node, Color.GRAY);
    				}
    				//area.drawNode(stack.peekFirst().getFirstNode(), Color.GRAY);
    				break;
    			}
    			if(edgesInSearch.get(searchIndex).getFirstNode().getAlgorithmValues()[0] -
    				nodesInSearch.get(nodesInSearch.size() - 1).getAlgorithmValues()[0] > 1) {
    				for(int i = nodesInSearch.get(nodesInSearch.size() - 1).getAlgorithmValues()[0] + 2;
    					i < edgesInSearch.get(searchIndex).getFirstNode().getAlgorithmValues()[0]; ++i) {
    					//textArea.append("No edges from Node " + nodes.get(i-1).getName() + " -- no search.\n" );
    					area.drawNode(nodes.get(i-1), Color.GRAY);
    					nodesInSearch.add(nodes.get(i-1));
    				}
    			}
    			if(edgesInSearch.get(searchIndex).getFirstNode().getAlgorithmValues()[0] >
    			(nodesInSearch.get(nodesInSearch.size() - 1).getAlgorithmValues()[0])) {
    				nodesInSearch.add(edgesInSearch.get(searchIndex).getFirstNode());
    			}
    			Edge current = edgesInSearch.get(searchIndex);
    			if(stack.size() > 0 && stack.peekFirst().equals(current)) {
    				if(current.isForward) {
    					textArea.append("No more edges from " + current.getSecondNode().getName() + ". Returning.\n");
    					area.drawNode(current.getSecondNode(), Color.GRAY);
    				}
    				else {
    					textArea.append("Edge " + current + " is now oriented backwards.\n");
    				}
    				stack.removeFirst();
    			}
    			else {
    				stack.addFirst(current);
    				if(current.isForward) {
    					textArea.append("Using edge " + current + "\n");
    					area.drawNode(current.getSecondNode(), Color.YELLOW);
    				}
    				else {
    					textArea.append("Edge " + current + " is not used in depth-first search: node " +
    									current.getFirstNode().getName() + " was visited earlier.\n");
    				}
    			}
    			
    			++searchIndex;
    			break;
    		case CALCULATION:
    			area.clear();
    			area.drawGraph();
    			break;
    		case DECISION:
    		}
    	}
    }

    
}
