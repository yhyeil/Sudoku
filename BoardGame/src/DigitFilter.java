import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class DigitFilter extends DocumentFilter{
	//puts limit to number of characters entered
	//only allows numbers from 1 to 9 to be entered
	
	private int limit; 
	DigitFilter(int limit){
		super();
		this.limit=limit;
	}
	@Override
	public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException{
		if(isNumeric(str)) {
			if(limit>0 && fb.getDocument().getLength()+str.length()>limit){
				return;
			}
			super.insertString(fb, offset, str, attr);
		}
	}
	
	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException{
		if(isNumeric(text)) {
			if(limit>0 && fb.getDocument().getLength()+text.length()>limit ) {
				return;
			}
			super.insertString(fb, offset, text, attrs);
		}
	}
	
	private boolean isNumeric(String text) {
		if(text==null||text.trim().equals("")) {
			return false;
		}
		if(text.equals("0"))
			return false;
		for(int i=0;i<text.length();i++) {
			if(!Character.isDigit(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
