package app;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class GUIBoard implements ActionListener  {

	CButton[][] board = new CButton[7][7];
	private boolean playerOne = true;
	private int oneWins = 0;
	private int twoWins = 0;
	JPanel panel;
	final JFrame f;
	
	
	
	public GUIBoard() {
        f = new JFrame("Connect4");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reset();

	}
	
	public void reset() {
        panel = new JPanel(new GridLayout(7, 7, 3, 3));
        for(CButton[] row : board) {
        	for(int i = 0; i < row.length; i++) {
        		row[i] = new CButton("-", i);
        		row[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        		row[i].setFont(row[i].getFont().deriveFont(20f));
        		row[i].addActionListener(this);
                panel.add(row[i]);
        	}
        }
        f.setContentPane(panel);
        f.setSize(400, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}
	


	



	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//determine player to drop then switch it 
		String player = "";
		if(playerOne) {
			player = "X";
		}
		else {
			player = "O";
		}
		
		playerOne = !playerOne;
		CButton example = (CButton)e.getSource();
		//JOptionPane.showMessageDialog(null, example.column);
		dropPiece(player, example.column);
		
		if(isWinner(player)) {
			if(player.equals("X")) {
				if(player.equals("X"))
					oneWins++;
			
				JOptionPane.showMessageDialog(null, "X Wins!");
				
				JOptionPane.showMessageDialog(null, "Player 1 Wins: " +  oneWins +  "\n" + "Player 2 Wins: "  +  twoWins);
				}
				else {
			
					if(player.equals("O")){
						twoWins++;
				
						JOptionPane.showMessageDialog(null, "O Wins!");
				
						JOptionPane.showMessageDialog(null, "Player 1 Wins: " +  oneWins +  "\n" + "Player 2 Wins: "  + twoWins);
			}
		}
			if(JOptionPane.showConfirmDialog(null, "Play again?") == 0) {
				reset();
				playerOne = true;
				return;
			}
	}

		if(tieGame()) {
			JOptionPane.showMessageDialog(null, "Tie Game :(");
		}

	}
	
	public boolean tieGame() {
		return !isWinner("X") && !isWinner("O")  && !anotherPlayPossible();
	}
	
	public boolean isWinner(String player) {
		// check for vertical wins
		
		if (verticalWin(player)) return true;
		
	
		
		// check for horizontal wins
		
		else if (horizontalWin(player)) return true;
		
		// check for diagonal wins
		
		else if (diagonalPosWin(player)) return true; 
		
		else if (diagonalNegWin(player)) return true;
		
		// no winner:
		return false;
	}
	
	
	public boolean verticalWin(String player) {
		
		
		for(int col=0; col <board.length; col++) {
			int count = 0;
			for(int row=0; row<board.length;row++) {
				if(board[row][col].getText().equals(player)) {
					count++;
				}
				else count = 0;
				
				if(count==4) return true;
			}
		}
		return false;
	}

	
	public boolean horizontalWin(String player) {
		
		
		for(int row=0; row <board.length; row++) {
			int count = 0;
			for(int col=0; col<board.length;col++) {
				if(board[row][col].getText().equals(player)) {
					count++;
				}
				else count = 0;
				
				if(count==4) return true;
			}
		}
		return false;
	}
	
	
	public boolean diagonalPosWin(String player) {
		for(int row = 3; row < board.length; row++) {
			int count = 0;
			for(int col = 0; col <= row; col++) {
				if(board[row-col][col].getText().equals(player)) count++;
				else count = 0;
				
				if (count == 4) return true;
			}
		}
		// Tim condition
		for(int col = 1; col <= 3; col++) {
			int count = 0;
			int col_copy = col;
			for(int row = 6; row >= col; row--) {
				if(board[row][col_copy].getText().equals(player)) count++;
				else count = 0;
				
				col_copy++;
				if (count == 4) return true;
			}
		}
		
		return false;
	}
	
	
	public boolean diagonalNegWin(String player) {
		for(int r = 0; r < board.length; r++) {
			int count = 0;
			int row_copy = r;
			for(int c = 0; row_copy < board.length; c++) {
				if(board[row_copy][c].getText().equals(player)) count++;
				
				else count = 0;
				
				row_copy++;
				if (count == 4) return true;
			}
		}
		// TIM CONDITION
		for(int c = 1; c <= 3; c++) {
			int count = 0;
			for(int r = 0; r+c < board.length; r++) {
				if(board[r][c+r].getText().equals(player)) count++;
				else count = 0;
				
				if (count == 4) return true;
			}
		}
		
		return false;
	}
	
	// is another play possible?
	public boolean anotherPlayPossible() {
		for(int x = 0; x < board.length; x++) {
			for(int y =0; y < board.length; y++) {
				if(board[x][y].getText().equals("-"))
					return true;
			}
		}
		
		return false;
	}

	
	
public boolean dropPiece(String player, int column) {
		
		//check that colum param makes sense
		if(column >= board.length || column < 0)
			return false;
	
		
		for(int x = board.length-1; x >= 0; x--) {
			if ((board[x][column].getText().equals("-"))) {
				board[x][column].setText(player);
				board[x][column].setBackground(new Color(10, 0, 0));
				
				if(player.equals("X")){				
					board[x][column].setForeground(new Color(0, 0, 255));
				}
				else
					board[x][column].setForeground(new Color(0, 255, 50));
				
				return true;
			//	board[x][column] = player;
		}
		
			
		}
		return false; 
	}		

}