import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainWindow extends JFrame {

    private PaintArea area;

    private JLabel lbl;

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


    public MainWindow(){
        setTitle("MainWindow");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(900, 800));

        area = new PaintArea();

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

        buttonDrawNodeActionListener = new DrawNodeActionListener();
        buttonDrawNode.addActionListener(buttonDrawNodeActionListener);

        buttonDrawEdgeActionListener = new DrawEdgeActionListener();
        buttonDrawEdge.addActionListener(buttonDrawEdgeActionListener);


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


        setVisible(true);
    }

    public static void main(String[] args){
        new MainWindow();
    }

    public class DrawNodeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            area.setMode(PaintArea.Mode.Node);
        }
    }

    public class DrawEdgeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            area.setMode(PaintArea.Mode.Edge1);
        }
    }
}
