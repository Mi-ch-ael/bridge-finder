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
    private SaveInFileActionListener buttonSaveInFileActionListener;
    
    private PaintAreaMode stashedMode;

    public MainWindow(){
    	graph = new Graph();
    	
        setTitle("Tarjan's bridge-finding algorithm");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(900, 800));

        area = new PaintArea(this.graph);

        textArea = new JTextArea(10, 1);
        textArea.setOpaque(true);
        textArea.setBackground(Color.WHITE);
        textArea.setEditable(true);
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
        
        buttonSaveInFileActionListener = new SaveInFileActionListener();
        buttonSaveInFile.addActionListener(buttonSaveInFileActionListener);

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
    	private final String SAVEFILE_SIGNATURE = "BFSV\u0002";
    	private final String ERROR_PREFIX = "ERROR: ";
    	private final String INFO_PREFIX = "INFO: ";
    	
    	public void actionPerformed(ActionEvent e) {
    		textArea.setText("");
    		FileReaderWrapper wrapper = new FileReaderWrapper();
    		if(wrapper.open(MainWindow.this) == null) {
    			textArea.append(INFO_PREFIX + "File has not been chosen.\n");
    			return;
    		}
    		
    		ArrayList<ArrayList<String>> content = wrapper.read();
    		if(content == null) {
    			textArea.setText(ERROR_PREFIX + "Failed to parse file.\n");
    			JOptionPane.showMessageDialog(null, "Failed to parse file.",
        				"Input error", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		graph.reset();
    		
    		boolean nonFatalErrorFlag = false;
    		if(content.size() > 0 && content.get(0).size() > 0 && content.get(0).get(0).equals(SAVEFILE_SIGNATURE)) {
    			content.remove(0);
    			try {
    				nonFatalErrorFlag = createGraphFromSaveData(content);
    			}
    			catch(Exception ex) {
    				area.clear();
    				JOptionPane.showMessageDialog(null,"File structure does not correspond with a save file structure.",
            				"Fatal file interpretation error", JOptionPane.ERROR_MESSAGE);
    			}
    		}
    		else {
    			nonFatalErrorFlag = createGraphFromInput(content);
    		}
    		
    		area.clear();
    		area.drawGraph();
    		
    		if(nonFatalErrorFlag) {
				JOptionPane.showMessageDialog(null,"Errors occurred while creating a graph. Check log for information.",
						"File interpretation error", JOptionPane.WARNING_MESSAGE);
			}
    	}
    	
    	private boolean createGraphFromInput(ArrayList<ArrayList<String>> content) {
    		boolean errorFlag = false;
    		for(ArrayList<String> line: content) {
    			if(line.size() != 2) {
    				errorFlag = true;
    				textArea.append(ERROR_PREFIX+"Input line doesn't describe an edge: "+line.toString()+". Ignored.\n");
    				continue;
    			}
    			if(line.get(0).length() == 1) graph.addNode(line.get(0));
    			if(line.get(1).length() == 1) graph.addNode(line.get(1));
    			if(!(graph.addEdge(line.get(0), line.get(1)))) {
    				errorFlag = true;
    				textArea.append(ERROR_PREFIX + "Edge " + line.get(0) + " - " + line.get(1) + " is not allowed.\n");
    				textArea.append("\t" + "Either invalid node name has been encountered, or this edge already " + 
    				"exists, or this edge is a loop.\n");
    			}
    		}
    		
    		int radius = Math.min(area.getBounds().height, area.getBounds().width) / 2 - NodeImage.SIZE/2;
    		int xCenter = area.getBounds().width/2;
    		int yCenter = area.getBounds().height/2;
    		
    		ArrayList<Node> lst = graph.getNodes();
    		for(int i = 0; i < lst.size(); ++i) {
    			double angle = 2*i*Math.PI/lst.size();
    			lst.get(i).setPoint(new algorithm.Point((int)(xCenter + radius*Math.cos(angle)), (int)(yCenter + radius*Math.sin(angle))));
    		}
    		return errorFlag;
    	}
    	
    	private boolean createGraphFromSaveData(ArrayList<ArrayList<String>> content) {
    		boolean errorFlag = false;
    		int expectedNodesCount;
    		textArea.append(INFO_PREFIX + "Save file signature recognized.\n");
    		try {
    			expectedNodesCount = Integer.parseInt(content.get(0).get(0));
    			content.remove(0);
    		}
    		catch(Exception ex) {
    			throw ex;
    		}
    		
    		int nodeCounter = 0;
    		ArrayList<String> names = new ArrayList<String>();
    		ArrayList<Integer> xValues = new ArrayList<Integer>();
    		ArrayList<Integer> yValues = new ArrayList<Integer>();
    		
    		while(content.size() > 0 && content.get(0).size() >= 3) {
    			ArrayList<String> line = content.get(0);
    			try {
    				names.add(line.get(0));
    				xValues.add(Integer.valueOf(Integer.parseInt(line.get(1))));
    				yValues.add(Integer.valueOf(Integer.parseInt(line.get(2))));
    			}
    			catch(Exception ex) {
    				textArea.append(ERROR_PREFIX + "Node " + line.get(0) + " coordinates don't seem to be valid.\n");
    				throw ex;
    			}
    			if(line.size() > 3) {
    				errorFlag = true;
    				textArea.append(ERROR_PREFIX + "Extra characters in a line describing node.\n");
    			}
    			if(xValues.get(xValues.size() - 1) > area.getWidth() || 
    					yValues.get(yValues.size() - 1) > area.getHeight()) {
    				textArea.append(INFO_PREFIX + "Coordinates larger than the canvas size may cause problems.\n");
    			}
    			++nodeCounter;
    			content.remove(0);
    		}
    		if(nodeCounter != expectedNodesCount) {
    			errorFlag = true;
    			textArea.append(ERROR_PREFIX + 
    					"Declared number of nodes does not match number of nodes present in a file.\n");
    		}
    		
    		for(int i = 0; i < names.size(); ++i) {
    			if(names.get(i).length() == 1) {
    				if(graph.addNode(names.get(i))) {
    					if(xValues.get(i) < NodeImage.SIZE/2) {
    						xValues.set(i, NodeImage.SIZE/2);
    						errorFlag = true;
    						textArea.append(ERROR_PREFIX+"Node is too close to the upper-left border or " +
    						"has negative coordinates. Changed node placement to the very edge of the working area.\n");
    					}
    					if(yValues.get(i) < NodeImage.SIZE/2) {
    						yValues.set(i, NodeImage.SIZE/2);
    						errorFlag = true;
    						textArea.append(ERROR_PREFIX+"Node is too close to the upper-left border or " +
    						"has negative coordinates. Changed node placement to the very edge of the working area.\n");
    					}
    					graph.getNodeByName(names.get(i)).setPoint(xValues.get(i), yValues.get(i));
    				}
    				else {
    					errorFlag = true;
    					textArea.append(ERROR_PREFIX + "Node " + names.get(i) + " already exists.\n");
    				}
    			}
    			else {
    				errorFlag = true;
    				textArea.append(ERROR_PREFIX + "Illegal node name: '" + names.get(i) + "'\n");
    			}
    		}
    		
    		for(ArrayList<String> line: content) {
    			if(line.size() == 2) {
    				if(!graph.addEdge(line.get(0), line.get(1))) {
    					errorFlag = true;
    					textArea.append(ERROR_PREFIX + "Edge " + line.get(0) + " - " + line.get(1) + 
    					" could not be added. Either nodes don't exist or edge already exists or edge is a loop.\n");
    				}
    			}
    			else {
    				errorFlag = true;
    				textArea.append(ERROR_PREFIX + "Line does not describe an edge: " + line + " Ignored.\n");
    			}
    		}
    		
    		return errorFlag;
    	}
    }
    
    public class SaveInFileActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		FileWriterWrapper wrapper = new FileWriterWrapper();
    		if(!wrapper.open(MainWindow.this)) {
    			textArea.append("INFO: File was not chosen.\n");
    			return;
    		}
    		if(!wrapper.write(graph)) {
    			textArea.append("ERROR: Failed to save graph in file.\n");
    		}
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
    		textArea.setEditable(false);
    		
    		ArrayList<Object> visObjects = graph.getVisualizationObjects();
    		textArea.append("\nStarting algorithm... Click 'Next' to explore steps.\n====\n");
    		graph.runAlgorithm();
    		
    		context = new VisualStatus(visObjects, graph.getNodes(), graph.getBridges());
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
    		textArea.setEditable(true);
    		area.currentMode = stashedMode;
    		area.clear();
    		area.drawGraph();
    	}
    }
    
    public static enum Stage {
		SEARCH,
		DECISION,
		FINISH
	}
    
    public class VisualStatus {
    	private Stage stage;
    	private ArrayList<Object> objectsInSearch;
    	private ArrayList<Edge> bridges;
    	private Deque<Edge> edgeStack;
    	private Deque<Node> nodeStack;
    	
    	private final Color usedNodeColor = Color.LIGHT_GRAY;
    	private final Color activeNodeColor = Color.YELLOW;
    	private final Color backwardEdgeColor = Color.BLUE;
    	private final Color bridgeColor = Color.RED;
    	
    	
    	public VisualStatus(ArrayList<Object> objectsInSearch, ArrayList<Node> nodes, ArrayList<Edge> bridges) {
    		this.objectsInSearch = objectsInSearch;
    		this.stage = Stage.SEARCH;
    		this.bridges = bridges;
    		this.nodeStack = new ArrayDeque<Node>();
    		this.edgeStack = new ArrayDeque<Edge>();
    	}
    	
    	public void step() {
    		switch(stage) {
    		case SEARCH:
    			if(objectsInSearch.isEmpty()) {
    				textArea.append("Depth-first search has finished.\n");
    				this.stage = Stage.DECISION;
    				break;
    			}
    			
    			Object current = objectsInSearch.get(0);
    			
    			if(current instanceof Node) {
    				Node currentNode = (Node)current;
    				if(nodeStack.size() > 0 && nodeStack.peekFirst().equals(currentNode)) {
    					nodeStack.removeFirst();
    					textArea.append("No more ways from Node " + currentNode + "\n");
    					textArea.append("Now coefficients cat be calculated:\n" + 
    									"\tNode number (entry time) N: " + currentNode.getAlgorithmValues()[0] + "\n" +
    									"\tNumber of nodes in subtree, including this node D: " +
    									currentNode.getAlgorithmValues()[1] + "\n" + 
    									"\tL = min(N - D + 1; {L(\u03bc) | " + currentNode + " -> \u03bc}; " + 
    									"{N(\u03bc) | "+currentNode+" <- \u03bc}): "+
    									currentNode.getAlgorithmValues()[2] +
    									"\n\tH = max(N; {H(\u03bc) | " + currentNode + " -> \u03bc}; {N(\u03bc) | " + 
    									currentNode +
    									" <- \u03bc}): " + currentNode.getAlgorithmValues()[3] + "\n");
    					area.drawNode(currentNode, usedNodeColor, new String[] {String.valueOf(currentNode.getAlgorithmValues()[0]),
								String.valueOf(currentNode.getAlgorithmValues()[1]), String.valueOf(currentNode.getAlgorithmValues()[2]),
								String.valueOf(currentNode.getAlgorithmValues()[3])});
    				}
    				else {
    					nodeStack.addFirst(currentNode);
    					textArea.append("Reached Node " + currentNode + "\n");
    					area.drawNode(currentNode, activeNodeColor);
    				}
    			}
    			else {
    				Edge currentEdge = (Edge)current;
    				if(edgeStack.size() > 0 && edgeStack.peekFirst().equals(currentEdge)) {
    					edgeStack.removeFirst();
    					if(currentEdge.isForward) {
    						textArea.append("Returning to " + currentEdge.getFirstNode() + "\n");
    					}
    					objectsInSearch.remove(0);
    					this.step();
    					return;
    				}
    				else {
    					edgeStack.addFirst(currentEdge);
    					if(currentEdge.isBackward) {
    						textArea.append("Edge " + currentEdge + " is not used because " + 
    						currentEdge.getFirstNode() + " was visited earlier. This edge becomes backward-oriented.\n");
							area.drawArrow(currentEdge, backwardEdgeColor);
    					}
    					else {
    						textArea.append("Choosing edge " + currentEdge + " to move on.\n");
    						area.drawArrow(currentEdge);
    					}
    				}
    			}
    			
    			objectsInSearch.remove(0);
    			break;
    		case DECISION:
    			area.clear();
    			textArea.append("Edge \u03bc -> \u03bd is a bridge if and only if H(\u03bd) \u2264 N(\u03bd) and " +
    							"L(\u03bd) > N(\u03bd) - D(\u03bd)\nBridges are highlighted:\n");
    			for(Edge bridge: this.bridges) {
    				area.drawEdge(bridge, bridgeColor);
    				textArea.append(bridge + "\n");
    			}
    			area.drawGraph();
    			stage = Stage.FINISH;
    			break;
    		case FINISH:
    			area.clear();
    			area.drawGraph();
    			textArea.append("Algorithm has finished.\n====\n");
    		}
    		
    	}
    }

    
}
