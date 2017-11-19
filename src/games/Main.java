package games;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Main {


	public static void main(String[] args) {
		JFrame obj = new JFrame();
		GameBoard gameplay = new GameBoard();
		
	//	obj.setSize(400, 552);
		obj.setSize(410, 538);
		obj.setVisible(true);
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);
		gameplay.requestFocus();
		gameplay.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		System.gc();

	}


}
