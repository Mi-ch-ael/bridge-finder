import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.awt.Component;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

class FileReaderWrapper {
	private Path path = null;
	private String filename = null;
	
	public static enum Mode {
		SAVEFILE,
		INPUTFILE
	}
	
	public FileReaderWrapper() {}
	
	public String open(Component dialogParent) {
		int dialogResult;
		String filename;
		
		JFileChooser chooser = new JFileChooser(".");
		chooser.setFileFilter(new FileNameExtensionFilter("Input files and save files", "bfin", "bfsv"));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		try {
			dialogResult = chooser.showOpenDialog(dialogParent);
		}
		catch(Exception e) {
			return null;
		}
		
		if(dialogResult != JFileChooser.APPROVE_OPTION) return null;
		
		//filename = chooser.getSelectedFile().getName();
		filename = chooser.getSelectedFile().getAbsolutePath();
		if(filename.length() == 0) return null;
		
		try {
			path = Paths.get(filename);
		}
		catch(Exception e) {
			return null;
		}
		
		this.filename = filename;
		return filename;
	}
	
	public ArrayList<ArrayList<String>> read(Mode mode) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			lines = (ArrayList<String>)Files.readAllLines(path, StandardCharsets.UTF_8);
		}
		catch(IOException e) {
			return null;
		}
		
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		for(String line: lines) {
			ArrayList<String> tmp = new ArrayList<String>();
			for(String word: line.split("\\s+")) {
				tmp.add(word);
			}
			//result.add((ArrayList<String>)Arrays.asList(line.split("\\s+")));
			result.add(tmp);
		}
		
		return result;
	}
}