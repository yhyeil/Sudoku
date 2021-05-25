import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Board extends JPanel{
	//creates a 3*3 SubBoards
	
	private SubBoard panel1;
	private SubBoard panel2;
	private SubBoard panel3;
	private SubBoard panel4;
	private SubBoard panel5;
	private SubBoard panel6;
	private SubBoard panel7;
	private SubBoard panel8;
	private SubBoard panel9;
	
	private int[][] table=new int[9][9];
	
	public Board()
    {
        super();
        setLayout(new GridLayout(3, 3));
        setBackground(Color.black);

        
        panel1 = new SubBoard(0,3,0,3);
  
        add(panel1);
        panel2 = new SubBoard(0,3,3,6);
        add(panel2);
        panel3 = new SubBoard(0,3,6,9);
        add(panel3);
        panel4 = new SubBoard(3,6,0,3);
        add(panel4);
        panel5 = new SubBoard(3,6,3,6);
        add(panel5);
        panel6 = new SubBoard(3,6,6,9);
        add(panel6);
        panel7 = new SubBoard(6,9,0,3);
        add(panel7);
        panel8 = new SubBoard(6,9,3,6);
        add(panel8);
        panel9 = new SubBoard(6,9,6,9);
        add(panel9);
    }
	
	public static void main(String[] args) {
		new Board();
	}
	
	public void reset() {
		panel1.clearGrid();
		panel2.clearGrid();
		panel3.clearGrid();
		panel4.clearGrid();
		panel5.clearGrid();
		panel6.clearGrid();
		panel7.clearGrid();
		panel8.clearGrid();
		panel9.clearGrid();
	}
	
	
	public void saveToFile(String filename) throws IOException{
		writeToFile(toString(),filename);
	}
	
	private static void writeToFile(String text, String filename)
		    throws IOException
		    {
		        PrintStream out = new PrintStream(new File(filename));
		        out.print(text);
		        out.flush();
		        out.close();
		    }
	
	public void setValue(int x, int y, int val, boolean editable) {
		if(x>=0 && x<3) {
			if(y>=0 && y<3) {
				panel1.setValue(x% 3, y% 3, val,editable);
			}
			else if(y >= 3 && y <6) {
                panel2.setValue(x% 3, y% 3, val,editable);
            }
            else {
                panel3.setValue(x% 3, y% 3, val,editable);
            }
		}else if(x>=3 && x<6) {
			if(y >= 0 && y <3) {
				panel4.setValue(x% 3, y% 3, val,editable);
			}else if(y>=3 && y<6) {
				panel5.setValue(x% 3, y% 3, val,editable);
			}else {
				panel6.setValue(x% 3, y% 3, val,editable);
			}
		}else {
			if(y>=0 && y<3) {
				panel7.setValue(x% 3, y% 3, val,editable);
			} else if(y>=3 && y<6) {
				panel8.setValue(x% 3, y% 3, val,editable);
			}else {
				panel9.setValue(x% 3, y% 3, val,editable);
			}
		}
	}
	
	public Container loadFrom(File file) {
		// super();
			Container c=new JPanel();
	        c.setLayout(new GridLayout(3, 3));
	        c.setBackground(Color.black);
	        reset();
	        
	        panel1 = new SubBoard(0,3,0,3);
	        panel2 = new SubBoard(0,3,3,6);
	        panel3 = new SubBoard(0,3,6,9);
	        panel4 = new SubBoard(3,6,0,3);
	        panel5 = new SubBoard(3,6,3,6);
	        panel6 = new SubBoard(3,6,6,9);
	        panel7 = new SubBoard(6,9,0,3);

	        panel8 = new SubBoard(6,9,3,6);
	       
	        panel9 = new SubBoard(6,9,6,9);
	        
	        
	        Scanner scanner;
	        
			try {
				scanner = new Scanner(new File(file.getAbsolutePath()));
				while (scanner.hasNextLine()) {
					//String str=scanner.next();
					if(!scanner.hasNextLine())
						break;
					
					String[] arr=scanner.nextLine().split(" ");
					setValue(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),false);
					
				}
				scanner.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 c.add(panel1);
			 c.add(panel2);
			 c.add(panel3);
			 c.add(panel4);
			 c.add(panel5);
			 c.add(panel6);
			 c.add(panel7);
			 c.add(panel8);
			 c.add(panel9);
			
			return c;
	}
	
	//return the number in given location
	public int getDigit(int x, int y) {
		if(x>=0 && x<3) {
			if(y>=0 && y<3) {
				return panel1.getDigit(x % 3, y% 3);
			}
			else if(y >= 3 && y <6) {
               return panel2.getDigit(x % 3, y% 3);
            }
            else {
               return panel3.getDigit(x % 3, y% 3);
            }
		}else if(x>=3 && x<6) {
			if(y >= 0 && y <3) {
				return panel4.getDigit(x % 3, y% 3);
			}else if(y>=3 && y<6) {
				return panel5.getDigit(x % 3, y% 3);
			}else {
				return panel6.getDigit(x % 3, y% 3);
			}
		}else {
			if(y>=0 && y<3) {
				return panel7.getDigit(x % 3, y% 3);
			} else if(y>=2 && y<6) {
				return panel8.getDigit(x % 3, y% 3);
			}else {
				return panel9.getDigit(x % 3, y% 3);
			}
		}
	}
	
	public int[][] BoardToTable() {
		for(int x=0;x<9;x++) {
			for(int y=0;y<9;y++) {
				if(getDigit(x, y)!=0){
					table[x][y]=getDigit(x,y);
				}else{
					table[x][y]=0;
				}
			}
		}
		
		for(int x=0;x<9;x++) {
			for(int y=0;y<9;y++) {
				System.out.print(table[x][y]);
			}
		}
		return table;
	}
	
	public void TableToBoard() {
		for(int x=0;x<9;x++) {
			for(int y=0;y<9;y++) {
				if(table[x][y]!=0)
				setValue(x, y, table[x][y], false);
				repaint();
			}
		}
	}
	
	public boolean checkRowCol(int x, int y, int val) {
		for(int i=0;i<table.length;i++) {
			if(table[x][i]==val) {
				return true;
			}
			if(table[i][y]==val) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkBox(int xstart, int ystart, int val) {
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++) {
				if(table[i+xstart][j+ystart]==val) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean able( int row, int col, int val) {
		return(!checkRowCol(row,col,val)&&checkBox(row-row%3,col-col%3,val));
	}
	/*
	public boolean solve() {
		for(int r=0;r<9;r++) {
			for(int c=0;c<9;c++) {
				if(table[r][c]==0) {
					for(int n=1;n<10;n++) {
						if(able(r,c,n)) {
							table[r][c]=n;
							if(solve()) {
								return true;
							}else {
								table[r][c]=0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}
	*/
	public static int counter=0;
	public void solve(int x, int y) {
		if(table[x][y]!=0) {
			System.out.println("got here");
			next(x,y);
			counter++;
			System.out.println(counter);
		}else {
			for(int n=1;n<10;n++) {
				if(!checkRowCol(x,y,n)&& !checkBox(x-x%3,y-y%3,n)) {
					table[x][y]=n;
					repaint();
					next(x,y);
				}
			}
			counter=0;
			//table[x][y]=0;
			
			repaint();
		}
	}
	
	public void next(int r, int c) {
		if(c<8)
			solve(r,c+1);
		else 
			solve(r+1,0);
		
	}
}

