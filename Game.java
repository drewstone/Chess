import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class Game extends JFrame implements Runnable{

	private static int WIDTH = 650;
	private static int HEIGHT = 650;

	public void run() {
		
		final JFrame frame = new JFrame("Chess");
		frame.setSize(WIDTH,HEIGHT);
		
		final JToolBar toolBar = new JToolBar();
		frame.add(toolBar, BorderLayout.NORTH);
		
		final ChessBoard chessboard = new ChessBoard();
		frame.getContentPane().add(chessboard, BorderLayout.CENTER);

		final JButton reset = new JButton("New");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessboard.reset();
				ChessBoard newCB = (ChessBoard) frame.getContentPane().getComponent(1);
				newCB.reset();
				frame.getContentPane().remove(newCB);
				frame.getContentPane().add(new ChessBoard());
			}
		});
		toolBar.add(reset);
		
		final JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		toolBar.add(quit);
		
		final JButton resign = new JButton("Resign");
		resign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessboard.resign();
			}
		});
		toolBar.add(resign);
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);		
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
