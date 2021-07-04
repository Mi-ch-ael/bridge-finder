import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {


    private JLabel lbl;

    private JButton buttonDrawNode;
    private JButton buttonDrawEdge;
    private JButton buttonErase;
    private JButton buttonOpenFile;
    private JButton buttonSaveInFile;
    private JButton buttonStop;
    private JButton buttonNext;
    private JButton buttonStart;


    public MainWindow(){
        setTitle("MainWindow");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(900, 800));

        PaintArea area = new PaintArea();

        lbl = new JLabel("Error\n");
        lbl.setBorder(BorderFactory.createLineBorder(Color.black));


        buttonDrawNode = new JButton("Draw Node");
        buttonDrawEdge = new JButton("Draw Edge");
        buttonErase = new JButton("Erase");
        buttonOpenFile = new JButton("Open File");
        buttonSaveInFile = new JButton("Save In File");
        buttonStop = new JButton("Stop");
        buttonNext = new JButton("Next");
        buttonStart = new JButton("Start");


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
        container.add(lbl, constraints);


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
        container.add(buttonNext, constraints);

        constraints.gridx = 6;
        constraints.gridy = 5;
        container.add(buttonStop, constraints);

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = 2;
        constraints.gridx = 5;
        constraints.gridy = 6;
        container.add(buttonStart, constraints);


        NodeImage nd = new NodeImage("A", new Point(10, 20));
        NodeImage nd1 = new NodeImage("Bcd", new Point(500, 500));
        area.add(nd);
        area.add(nd1);
        EdgeImage line = new EdgeImage( new Point(100, 200), new Point(50, 300));
        EdgeImage line1 = new EdgeImage( new Point(0, 0), new Point(150, 200));
        area.add(line);
        area.add(line1);


        setVisible(true);
    }

    public static void main(String[] args){
        new MainWindow();
    }
}
