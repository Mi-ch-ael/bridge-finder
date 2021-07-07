import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import algorithm.Node;
import algorithm.Edge;
import algorithm.Graph;

public class MainWindow extends JFrame {
	private final Graph graph;

    private PaintArea area;

    private JTextArea textArea;

    private JScrollPane scroll;

    private JButton buttonDrawNode;
    private JButton buttonDrawEdge;
    private JButton buttonErase;
    private JButton buttonOpenFile;
    private JButton buttonSaveInFile;
    private JButton buttonStop;
    private JButton buttonNext;
    private JButton buttonStart;

    private DrawNodeActionListener buttonDrawNodeActionListener;
    private DrawEdgeActionListener buttonDrawEdgeActionListener;
    private EraseActionListener buttonEraseActionListener;
    private StartActionListener buttonStartActionListener;
    private OpenFileActionListener buttonOpenFileActionListener;

    public MainWindow(){
    	graph = new Graph();
    	
        setTitle("Tarjan's bridge-finding algorithm");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(900, 800));

        area = new PaintArea(this.graph);

        textArea = new JTextArea(5, 1);
        textArea.setOpaque(true);
        textArea.setBackground(Color.WHITE);
        //textArea.setText("Hello!\n");
        textArea.setEditable(false);
        scroll = new JScrollPane(textArea);
        scroll.setBorder(BorderFactory.createLineBorder(Color.black));

        buttonDrawNode = new JButton("Draw Node");
        buttonDrawEdge = new JButton("Draw Edge");
        buttonErase = new JButton("Erase");
        buttonOpenFile = new JButton("Open File");
        buttonSaveInFile = new JButton("Save In File");
        buttonStop = new JButton("Stop");
        buttonNext = new JButton("Next");
        buttonStart = new JButton("Start");

        buttonDrawNodeActionListener = new DrawNodeActionListener();
        buttonDrawNode.addActionListener(buttonDrawNodeActionListener);

        buttonDrawEdgeActionListener = new DrawEdgeActionListener();
        buttonDrawEdge.addActionListener(buttonDrawEdgeActionListener);

        buttonEraseActionListener = new EraseActionListener();
        buttonErase.addActionListener(buttonEraseActionListener);
        
        buttonStartActionListener = new StartActionListener();
        buttonStart.addActionListener(buttonStartActionListener);
        
        buttonOpenFileActionListener = new OpenFileActionListener();
        buttonOpenFile.addActionListener(buttonOpenFileActionListener);


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
    			/*if(!(graph.addNode(line.get(0)))) {
    				textArea.append("ERROR: Node " + line.get(0) + " is not allowed.\n");
    				continue;
    			}
    			if(!(graph.addNode(line.get(1)))) {
    				graph.removeNode(line.get(0));
    				textArea.append("ERROR: Node " + line.get(1) + " is not allowed.\n");
    				continue;
    			}*/
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
    		textArea.setText("");
    		graph.runAlgorithm();
    		ArrayList<Edge> bridges = graph.getBridges();
    		if(bridges.size() == 0) {
    			textArea.append("This graph has no bridges.\n");
    		}
    		StringBuilder answer;
    		for(Edge bridge: bridges) {
    			answer = new StringBuilder("Edge ");
    			answer.append(bridge.getFirstNode().getName()).append(" - ").append(bridge.getSecondNode().getName());
    			textArea.append(answer.append(" is a bridge\n").toString());
    		}
    	}
    }
}
