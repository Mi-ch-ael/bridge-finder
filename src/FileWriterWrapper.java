import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import algorithm.Edge;
import algorithm.Node;
import algorithm.Graph;

public class FileWriterWrapper {
	private Path path = null;
	private static final String SAVEFILE_SIGNATURE = "BFSV\u0002";
	
	public boolean open(Component dialogParent) {
		int dialogResult;
		String filename;
		
		JFileChooser chooser = new JFileChooser(".");
		chooser.setFileFilter(new FileNameExtensionFilter("Bridge-finder format save files", "bfsv"));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		try {
			dialogResult = chooser.showSaveDialog(dialogParent);
		}
		catch(Exception e) {
			return false;
		}
		
		if(dialogResult != JFileChooser.APPROVE_OPTION) return false;
		
		filename = chooser.getSelectedFile().getAbsolutePath();
		if(filename.length() == 0) return false;
		if(!filename.endsWith(".bfsv")) {
			filename = filename.split("\\.")[0] + ".bfsv";
		}
		
		try {
			path = Paths.get(filename);
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	public boolean write(Graph graph) {
		try(BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
			writer.write(SAVEFILE_SIGNATURE);
			writer.newLine();
			writer.write(String.valueOf(graph.getNodes().size()));
			writer.newLine();
			for(Node node: graph.getNodes()) {
				writer.write(node.toString() + " " + String.valueOf(node.getPoint().x) + " " + 
						String.valueOf(node.getPoint().y));
				writer.newLine();
			}
			for(Edge edge: graph.getEdges()) {
				writer.write(edge.getFirstNode().toString() + " " + edge.getSecondNode().toString());
				writer.newLine();
			}
		}
		catch(IOException e) {
			return false;
		}
		
		return true;
	}
}
