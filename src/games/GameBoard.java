package games;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Action;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

public class GameBoard extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private int[] CarYPosition = { -128, -128 };
	private int[] CarXPosition = { -30, -30 };
	private boolean play = false;
	private boolean Car2play = false;

	Random randomPlayXPos = new Random();

	private int lives = 3;

	final private int Y_PLAYER = 382;
	private int xPlayer;

	private ImageIcon enemy;
	private ImageIcon player;

	// speed of snake
	private Timer timer;
	final private int DELAY = 6;
	
	int score = 0;

	public GameBoard() {
		timer = new Timer(DELAY, this);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		int playerPos = randomPlayXPos.nextInt(2);
		// Randomize player position
		if (playerPos == 0) {
			// position to left
			xPlayer = -30;
		} else {
			// position to right
			xPlayer = 55;
		}

	}

	public void paint(Graphics g) {

		// draw background for game play
		g.setColor(Color.black);
		g.fillRect(0, 0, 405, 510);
		
		// draws score
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 20));
		g.drawString("Score: " + score, 280, 30);

		// no of lives
		g.setColor(Color.GREEN);
		g.setFont(new Font("arial", Font.PLAIN, 20));
		g.drawString("Lives: " + lives, 160, 30);

		// game status
		if (play) {
			g.setColor(Color.GREEN);
			g.setFont(new Font("arial", Font.PLAIN, 20));
			g.drawString("Status: Playing", 210, 100);

		} else {
			g.setColor(Color.RED);
			g.setFont(new Font("arial", Font.PLAIN, 20));
			g.drawString("Status: Stoped", 210, 100);

		}

		// instructions
		g.setColor(Color.RED);
		g.setFont(new Font("arial", Font.PLAIN, 20));
		g.drawString("INSTRUCTIONS", 210, 200);

		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 15));
		g.drawString("1. Press space to start or pause", 180, 219);

		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 15));
		g.drawString("2. Press Esc to restart", 180, 239);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 15));
		g.drawString("3. Collision press R to restart", 180, 259);

		// line to separate game play and info
		g.setColor(Color.WHITE);
		g.fillRect(156, 0, 2, 562);

		// draw road separation lines
		g.setColor(Color.WHITE);
		for (int i = 0; i <= 540; i += 10) {
			g.fillRect(77, i, 2, 5);
		}
		// enemy 1
		enemy = new ImageIcon("enemy.png");
		enemy.paintIcon(this, g, CarXPosition[0], CarYPosition[0]);

		// enemy 2
		enemy = new ImageIcon("enemy.png");
		enemy.paintIcon(this, g, CarXPosition[1], CarYPosition[1]);

		// player
		player = new ImageIcon("player.png");
		player.paintIcon(this, g, xPlayer, Y_PLAYER);

		// Head-on and side collision with car 1
		// car 1 is in index 0
		if (play) {

			if ((xPlayer == CarXPosition[0])
					&& ((CarYPosition[0] + 128 >= Y_PLAYER))) {

				lives--;
				play = false;
			}

			// Head-on and side collision with car 2
			// car 1 is in index 1
			if ((xPlayer == CarXPosition[1])
					&& (CarYPosition[1] + 128 >= Y_PLAYER)) {
				lives--;
				play = false;

			}
		}

		if (!play && lives == 0) {
			if (lives == 0) {

				g.setColor(Color.WHITE);
				g.setFont(new Font("arial", Font.PLAIN, 35));
				g.drawString("GAME OVER", 100, 275);

				g.setFont(new Font("arial", Font.PLAIN, 35));
				g.drawString("Press Esc to restart", 70, 310);
			}
		}

		timer.start();
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		// games starts when and pause when space bar is pressed
		if (play) {

			CarYPosition[0] += 2;
			if (CarYPosition[0] == 170) {
				// randomize x position of car 2
				randomizeCars(1);
				// Initialize enemy 2
				Car2play = true;
			}

			if (Car2play) {
				CarYPosition[1] += 2;
			}

			// reset car
			if (CarYPosition[0] == 510) {
				// randomize x position of car 2
				randomizeCars(0);
				score += 5;
				CarYPosition[0] = -128;
			}

			// reset car
			if (CarYPosition[1] == 510) {
				// randomize x position of car 2
				randomizeCars(1);
				score += 5;
				CarYPosition[1] = -128;
			}

		}

		repaint();
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (xPlayer >= 55) {
				xPlayer = 55;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (xPlayer < -30) {
				xPlayer = -30;
			} else {
				moveLeft();
			}
		}

		// pause and resume game
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (play == true) {
				play = false;
			} else {
				play = true;
			}
		}

		// restart game
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			reset();
		}

		if (e.getKeyCode() == KeyEvent.VK_R) {
			if (lives > 0) {
				restart();
			}

		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	

	private void restart() {
		CarYPosition[0] = -128;
		Car2play = false;
		CarYPosition[1] = -128;
		play = true;

	}

	private void reset() {
		CarYPosition[0] = -128;
		Car2play = false;
		CarYPosition[1] = -128;
		play = true;
		score = 0;
		lives = 3;
	}

	// moves playe's car to the right
	private void moveRight() {
		if (play) {
			xPlayer = 55;
		}
	}

	// moves playe's car to the left
	private void moveLeft() {
		if (play) {
			xPlayer = -30;
		}

	}
	
	private void randomizeCars(int car) {
		int rand = randomPlayXPos.nextInt(2);
		if (rand == 0) {
			// position to left
			CarXPosition[car] = -30;
		} else {
			// position to right
			CarXPosition[car] = 55;
		}
	}


}
