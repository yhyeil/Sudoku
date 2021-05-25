import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.PlainDocument;

public class SubBoard extends JPanel {
	//creates 3*3 grid
	//that allows users to enter a number
	
	
	private JTextField field[][];
	//private Table table=new Table();
	
	public SubBoard(int xStart, int xStop, int yStart, int yStop) {
		super();
        int x = 0;
        int y = 0;
        int val = 0;
        field = new JTextField[3][3];
        
        for(int i = xStart; i < xStop; i++) {
            for(int j = yStart; j < yStop; j++) {
            	field[x][y]=new JTextField();
            	Font font= new Font("Serif",Font.BOLD,40);
            	field[x][y].setFont(font);
            	AbstractDocument doc=(AbstractDocument)field[x][y].getDocument();
            	doc.setDocumentFilter(new DigitFilter(1));
            	add(field[x][y]);
            	y++;
				val++;
            	if(y==3) {
            		y=0;
            	}
            }
            x++;
        }
        
        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));
        setLayout(new GridLayout(3,3));
	
	}
	
	//clears grid
	public void clearGrid() {
		for(int x=0;x<3;x++) {
			for(int y=0;y<3;y++) {
					field[x][y].setEditable(true);
					field[x][y].setText(" ");
					repaint();
				
			}
		}
	}
	
	//put the given value in a given position
	public void setValue(int x, int y, int val, boolean editable) {
		field[x][y].setEditable(editable);
		field[x][y].setText(""+val);
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				System.out.print(field[i][j].getText());
			}
		}
	}
	
	//return the value at given location
	public int getDigit(int x, int y) {
		if(x>=0 && x<3 && y>=0 && y<3) {
			if(!field[x][y].getText().isEmpty()) {
				return Integer.parseInt(field[x][y].getText());
			}else {
				return 0;
			}
		}
		throw new IndexOutOfBoundsException();
	}
}
	

