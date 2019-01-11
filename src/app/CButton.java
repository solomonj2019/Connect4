package app;

import javax.swing.JButton;

public class CButton extends JButton{
	
	public int column;
	
	public CButton(String body, int column) {
		super(body);
		
		this.column = column; 
	}

}
