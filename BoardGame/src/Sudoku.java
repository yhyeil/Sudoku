import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

public class Sudoku extends JFrame{
	
	
	JFrame frame;
	private Board board=new Board();
	private JMenuBar menuBar;
	private String name;
	
	
	
	private JMenuBar createMenuBar() {
		menuBar=new JMenuBar();
		JMenu file= new JMenu("File");
	
		
		addToMenu(file, "Load", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				board=new Board();
				JFileChooser jfc = new JFileChooser(".");
				int returnValue = jfc.showOpenDialog(null);
                
				if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    name=selectedFile.getName();
                    loadFrom(selectedFile);
                    System.out.println(selectedFile.getAbsolutePath());
                }
                else
                {
                    System.out.println("Open command cancelled by user.");
                }
				jfc.setFileFilter(new FileNameExtensionFilter("TEXT FILES","txt","text"));
				File workingDirectory=new File(System.getProperty("user.dir"));
				jfc.setCurrentDirectory(workingDirectory);

			}
		});
		
		addToMenu(file,"Save", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				File workingDirectory=new File(System.getProperty("user.dir"));
				JFileChooser jfc=new JFileChooser(workingDirectory);
				jfc.setFileFilter(new FileNameExtensionFilter("TEXT FILES","txt","text"));
				jfc.setDialogTitle("Save a file");
				int returnValue = jfc.showSaveDialog(null);

	                if (returnValue == JFileChooser.APPROVE_OPTION) {
	                    File f;
	                    if(jfc.getSelectedFile().getName().contains(".txt")) {
	                    	f=new File(jfc.getSelectedFile().getName());
	                    }else {
	                    	f= new File(jfc.getSelectedFile()+".txt");
	                    }
	                    int underArr[][]=board.BoardToTable();
	                    try {
	                       FileWriter fw= new FileWriter(f.getPath());
	                    	BufferedWriter bufferedWriter=new BufferedWriter(fw);
	                    	for(int i=0;i<9;i++) {
	                    		for(int j=0;j<9;j++) {
                                    String row = Integer.toString(i);
                                    String col = Integer.toString(j);
                                    String val = Integer.toString(underArr[i][j]);
                                    if(underArr[i][j]!=0) {
                                    	bufferedWriter.write(row);
                                    	bufferedWriter.write(" ");
                                    	bufferedWriter.write(col);
                                    	bufferedWriter.write(" ");
                                    	bufferedWriter.write(val);
                                    	bufferedWriter.newLine();
                                    }
	                    		}
	                    	}
	                    	bufferedWriter.close();
	                    } catch (IOException ex) {
	                        throw new RuntimeException(ex);
	                    }
	                }
			}
		});
		
		
		addToMenu(file, "New Board", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.reset();
				frame=new JFrame();
				frame.setMinimumSize(new Dimension(600,600));
				frame.setResizable(false);
				frame.setTitle("SUDOKU");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				frame.add(new Board());
				frame.setJMenuBar(createMenuBar());
				frame.pack();
				frame.setFocusable(true);
				frame.setVisible(true);
				frame.setLocation(100,100);
				repaint();
			}
		});
		menuBar.add(file);
		
		JMenu help= new JMenu("Help");
		addToMenu(help, "How To Use", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Sudoku.this, "Load sudoku puzzle by using Load button inside File button\n"+
						"There are five games under Games folder\n"+
						"Solve button will show the answers \n"+
						"Click on New Board to create new, fresh board\n"
						+ "Save the current work by clicking on Save button in the File button\n"+
						"How To Use button shows how to use the program\n"+
						"If confused with the rule of sudoku, click on How To Play Sudoku button under Help button",
						"How To Use", JOptionPane.PLAIN_MESSAGE);
			}
		});
		menuBar.add(help);
		addToMenu(help, "How To Play Sudoku", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Sudoku.this, "Fill the puzzle using number 1-9\n"+
			"Each number should be used only once in each row, column and grid.\n",
			"How To Play Sudoku",JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		JMenu solve= new JMenu("Solve");
		menuBar.add(solve);
		addToMenu(solve, "Solve", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File workingDirectory=new File(System.getProperty("user.dir"));
				File f=new File(workingDirectory+"\\Sudoku-main\\BoardGame\\Answer\\"+name+"_solve");
				Scanner scanner;
				try {
					scanner = new Scanner(new File(f.getAbsolutePath()));
					while (scanner.hasNextLine()) {
						if(!scanner.hasNextLine())
							break;
						
						String[] arr=scanner.nextLine().split(" ");
						board.setValue(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),true);
						
					}
					scanner.close();

				} catch (FileNotFoundException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		this.setJMenuBar(menuBar);
		return menuBar;
	}
	
	private void addToMenu(JMenu menu, String title, ActionListener listener) {
		JMenuItem menuItem= new JMenuItem(title);
		menu.add(menuItem);
		menuItem.addActionListener(listener);
	}
	
	
	public Sudoku() {
		frame=new JFrame();
		frame.setMinimumSize(new Dimension(600,600));
		frame.setResizable(false);
		frame.setTitle("SUDOKU");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(board);
		frame.setJMenuBar(createMenuBar());
		frame.pack();
		frame.setFocusable(true);
		frame.setVisible(true);
		frame.setLocation(100,100);
		repaint();
		
	}
	
	//loads the puzzle from a file
	//and puts it in a new board
	public void loadFrom(File file) {
		JFrame frame=new JFrame();
		board=new Board();
		
		frame.setMinimumSize(new Dimension(600,600));
		frame.setResizable(false);
		frame.setTitle("SUDOKU");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(board.loadFrom(file));
		frame.setJMenuBar(createMenuBar());
		frame.pack();
		frame.setFocusable(true);
		frame.setVisible(true);
		frame.setLocation(100,100);
		repaint();
		
	}
	
}
	

