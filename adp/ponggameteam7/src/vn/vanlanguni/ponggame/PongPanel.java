/*
 * PONG GAME REQUIREMENTS
 * This simple "tennis like" game features two paddles and a ball, 
 * the goal is to defeat your opponent by being the first one to gain 3 point,
 *  a player gets a point once the opponent misses a ball. 
 *  The game can be played with two human players, one on the left and one on 
 *  the right. They use keyboard to start/restart game and control the paddles. 
 *  The ball and two paddles should be red and separating lines should be green. 
 *  Players score should be blue and background should be black.
 *  Keyboard requirements:
 *  + P key: start
 *  + Space key: restart
 *  + W/S key: move paddle up/down
 *  + Up/Down key: move paddle up/down
 *  
 *  Version: 0.5
 */
package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = -1097341635155021546L;
	//Sound 
	Sound StartGame = new Sound();
	Soundgameover Wingame = new Soundgameover();
	SoundHit hit = new SoundHit();
	SoundIntrogame Intro = new SoundIntrogame();
	SoundGoad Goad = new SoundGoad();


	private boolean showTitleScreen = true;
	private boolean playing;
	private boolean gameOver;

	/** Background. */
	private Color backgroundColor = Color.BLACK;

	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;

	/** The ball: position, diameter */
	private int ballX = 240;
	private int ballY = 240;
	private int diameter = 30;
	private int ballDeltaX = -2;
	private int ballDeltaY = 3;

	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 250;
	private int playerOneWidth = 10;
	private int playerOneHeight = 60;
	
	/** Player 2's paddle: position and size */
	private int playerTwoX = 472;
	private int playerTwoY = 250;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 60;

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;

	/** Player score, show on upper left and right. */
	private int playerOneScore;
	private int playerTwoScore;
	//khai bao bien Numbertype
	static int NumTypeBall=0;
	ImageIcon imColorBall = new ImageIcon("./BallImage/ColorBall.png");
	ImageIcon imTennis = new ImageIcon("./BallImage/Tenisball.png");
	ImageIcon imBasketball = new ImageIcon("./BallImage/Basketball.png");
	ImageIcon imPokemonball = new ImageIcon("./BallImage/Pokemonball.png");

	/** Construct a PongPanel. */
	int aNewNumber[] = new int[5];
	int aNewNumber2[] = new int[5];
	int aNewNumber3[] = new int[5];
	int touchsign = 0;
	
	public PongPanel() {
		setBackground(backgroundColor);
		// listen to key presses
		setFocusable(true);
		addKeyListener(this);
		// Sound introgame
		Intro.play(null);

		// call step() 60 fps
		Timer timer = new Timer(1000 / 60, this);
		timer.start();
	}


	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();
	}

	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */

			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (wPressed && playerOneY - paddleSpeed > 0) {
				playerOneY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed && playerOneY + playerOneHeight + paddleSpeed < getHeight()) {
				playerOneY += paddleSpeed;
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen
			if (upPressed && playerTwoY - paddleSpeed > 0) {
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed && playerTwoY + playerTwoHeight + paddleSpeed < getHeight()) {
				playerTwoY += paddleSpeed;
			}

			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
		
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY + diameter + ballDeltaY;
			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			// Player 2's paddle position
			float playerTwoLeft = playerTwoX;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				ballDeltaY *= -1;
				hit.play(null);
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

					playerTwoScore++;
					Goad.play(null);

					// Player 2 Win, restart the game
					if (playerTwoScore == 3) {
						playing = false;
						gameOver = true;
						Wingame.play(null);
					}
					ballX = 240;
					ballY = 240;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					hit.play(null);
					if(touchsign==1){
						if(aNewNumber3[0]==0){
							ballDeltaY=ballDeltaY+2;
							ballDeltaX=ballDeltaX-2;
						}else if(aNewNumber3[0]==1){
							ballDeltaY = ballDeltaY-1;
						}else if(aNewNumber3[0]==2){
							playerOneHeight=playerOneHeight-playerOneHeight*25/100;
						}else if(aNewNumber3[0]==3){
							playerOneHeight=playerOneHeight+playerOneHeight*25/100;
						}
					}
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

					playerOneScore++;
					Goad.play(null);

					// Player 1 Win, restart the game
					if (playerOneScore == 3) {
						playing = false;
						gameOver = true;
						Wingame.play(null);
					}
					ballX = 250;
					ballY = 250;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					hit.play(null);
					if(touchsign==1){
						if(aNewNumber3[0]==0){
							ballDeltaY=ballDeltaY+2;
							ballDeltaX=ballDeltaX-2;
						}else if(aNewNumber3[0]==1){
							ballDeltaY = ballDeltaY-1;
						}else if(aNewNumber3[0]==2){
							playerTwoHeight=playerOneHeight-playerTwoHeight*25/100;
						}else if(aNewNumber3[0]==3){
							playerTwoHeight=playerOneHeight+playerTwoHeight*25/100;
						}
					}
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
		}

		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {

			/* Show welcome screen */

			// Draw game title and start message
			
			ImageIcon imgbackground = new ImageIcon("background/hinh-anh-bong-da-3.jpg");
			g.drawImage(imgbackground.getImage(), 0, 0, 500, 500, null);
			g.setColor(Color.white);
			g.setFont(new Font(Font.SERIF , Font.BOLD, 42));
			g.drawString("Pong Game", 130, 100);

			// FIXME Wellcome message below show smaller than game title
			g.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 28));
			g.drawString("Press", 130, 230);
			g.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 28));
			g.drawString("to play", 250, 230);
			g.setColor(Color.RED);
			g.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 28));
			g.drawString("'P'", 210, 230);
			g.drawString("press 'B' to choose the ball", 100, 280);
		} else if (playing) {

			/* Game is playing */

			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center1
			g.setColor(Color.GREEN);
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.drawLine(250, lineY, 250, lineY + 25);
			}

			// draw "goal lines" on each side
			
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());

			// draw the scores
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.setColor(Color.BLUE);
			g.drawString(String.valueOf(playerOneScore), 100, 100); // Player 1
																	// score
			g.drawString(String.valueOf(playerTwoScore), 350, 100); // Player 2
																	// score

			// draw the ball 123
			//ImageIcon imgBall = new ImageIcon("background/apple.png");
 			//g.drawImage(imgBall.getImage(), ballX, ballY, diameter, diameter, null);
			//g.setColor(Color.RED);
			//g.fillOval(ballX, ballY, diameter, diameter);
 			g.setColor(Color.RED);
			if (NumTypeBall == 0) {
				g.drawImage(imColorBall.getImage(), ballX, ballY, diameter,
						diameter, null);
			} else if (NumTypeBall == 1) {
				g.drawImage(imTennis.getImage(), ballX, ballY, diameter,
						diameter, null);
			} else if (NumTypeBall == 2) {
				g.drawImage(imBasketball.getImage(), ballX, ballY, diameter,
						diameter, null);
			} else if (NumTypeBall == 3) {
				g.drawImage(imPokemonball.getImage(), ballX, ballY, diameter,
						diameter, null);
			}

			// draw the paddles
 			ImageIcon imgPaddle = new ImageIcon("background/000.png");
 			g.drawImage(imgPaddle.getImage(), playerOneX, playerOneY, playerOneWidth, playerOneHeight, null);
 			ImageIcon imgPaddle02 = new ImageIcon("background/000.png");
 			g.drawImage(imgPaddle.getImage(), 473, playerTwoY, playerTwoWidth, playerTwoHeight, null);
			//g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
			//g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
		} else if (gameOver) {

			/* Show End game screen with winner name and score */
			ImageIcon imgbground = new ImageIcon("background/hinh-anh-ban-phao-hoa-phao-bong-dep-nhat-4-khoahocthuvinet-2302.jpg");
			g.drawImage(imgbground.getImage(), 0,0, 500, 500, null);
			// Draw scores
			// TODO Set Blue color
			g.setColor(Color.blue);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 100);
			g.drawString(String.valueOf(playerTwoScore), 350, 100);

			// Draw the winner name 
			g.setColor(Color.RED);
			g.setFont(new Font(Font.SERIF, Font.BOLD, 50));
			if (playerOneScore > playerTwoScore) {
				ImageIcon imgwin1 = new ImageIcon("background/win.png");
				g.drawImage(imgwin1.getImage(), 100, 180, 300, 200, null);
				g.drawString("Player 1", 165, 200);
			} else {
				ImageIcon imgwin2 = new ImageIcon("background/win.png");
				g.drawImage(imgwin2.getImage(), 100, 180, 300, 200, null);
				g.drawString("Player 2", 165, 200);
			}

			// Draw Restart message
			g.setColor(Color.BLUE);
			g.setFont(new Font(Font.SERIF, Font.BOLD, 21));
			// TODO Draw a restart message
			g.drawString("Press", 200, 400);
			g.drawString("to restart", 325, 400);
			g.setColor(Color.RED);
			g.drawString("'Space'", 255, 400);
			
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyChar() == 'p') {
				showTitleScreen = false;
				playing = true;
				StartGame.play(null);
			}else if (e.getKeyChar() == 'b'||e.getKeyChar() == 'B') {
				BallColorWindow mainWidow = new BallColorWindow();
				mainWidow.setVisible(true);
			}
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = true;
			playerOneY = 250;
			playerTwoY = 250;
			ballX = 250;
			ballY = 250;
			playerOneScore = 0;
			playerTwoScore = 0;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false;
		}
	}

}
