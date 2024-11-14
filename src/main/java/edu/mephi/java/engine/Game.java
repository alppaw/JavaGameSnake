package edu.mephi.java.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

// TODO допишите все необходимые сущности для игры
public class Game extends JPanel implements ActionListener {
	private final int TILE_SIZE = 20;
	private final int WIDTH = 20;
	private final int HEIGHT = 20;
	private boolean gameOver = false;
	private Timer timer;
	private int xFood,yFood;
	private int [] x = new int[WIDTH*TILE_SIZE];
	private int [] y = new int[HEIGHT*TILE_SIZE];
	private int snakeLength;
	private int score ;
	private JButton restartButton;
	private int delay;
	private int record;
	private int newRecord;

	Motion motion;

	public Game() {
		setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
		setBackground(Color.BLUE);
		setFocusable(true);
		motion = new Motion(this);
		addKeyListener(motion);
		snakeLength = 3;
		record = 0;
		newRecord = 0;
		score = 0;
		delay = 200;
		for (int i = 0; i < snakeLength;i++){
			x[i] = WIDTH*TILE_SIZE/2;
			y[i] = HEIGHT*TILE_SIZE/2 + i*TILE_SIZE;
		}
		placeFood();
		timer = new Timer(delay, this);
		timer.start();
		setLayout(null);
		restartButton = new JButton("Restart");
		restartButton.setBounds((WIDTH-4)*TILE_SIZE/2-10,(HEIGHT+2)*TILE_SIZE/2,90,2*TILE_SIZE);
		restartButton.addActionListener(e -> restart());
		restartButton.setVisible(false);
		add(restartButton);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!gameOver){
			checkEat();
			motion.move(snakeLength,x,y);
			checkErrors();
		}
		repaint();
	}

	private void checkErrors(){
		if((x[0] < 0) || (y[0] < 0 ) || (x[0] >= WIDTH*TILE_SIZE) || (y[0] >= HEIGHT*TILE_SIZE)) gameOver = true;
		for(int i = snakeLength;i>0;i--){
			if((i > 3) && (x[0] == x[i]) && (y[0] == y[i])) gameOver = true;
		}
		if(gameOver) timer.stop();
	}
	private void checkEat(){
		if((x[0] == xFood) && (y[0] == yFood)){
			placeFood();
			snakeLength++;
			score++;
			newRecord++;
			delay -= 10;
			timer.setDelay(delay);
		}
	}
	private void paintScore(Graphics g){
		if(!gameOver){
			g.setColor(Color.WHITE);
			g.drawString("Score:"+score,0,TILE_SIZE);
		}
	}
	private void paintRecord(Graphics g){
		if(!gameOver){
			g.setColor(Color.WHITE);
			g.drawString("Record:" + record,0,2*TILE_SIZE);
		}
		if(gameOver){
			g.setColor(Color.WHITE);
			g.drawString("Record:" + record,(WIDTH-3)*TILE_SIZE/2,(HEIGHT+1)*TILE_SIZE/2);
		}
	}
	private void paintGameIsOver(Graphics g){
		if(gameOver){
			g.setColor(Color.RED);
			setBackground(Color.BLACK);
			g.drawString("GAME OVER",(WIDTH-4)*TILE_SIZE/2,(HEIGHT-1)*TILE_SIZE/2);
			g.drawString("Your score:"+score,(WIDTH-4)*TILE_SIZE/2,(HEIGHT)*TILE_SIZE/2);
			restartButton.setVisible(true);
		}
	}
	private void paintFood(Graphics g){
		if(!gameOver){
				g.setColor(Color.CYAN);
				g.fillOval(xFood,yFood,TILE_SIZE,TILE_SIZE);
		}
	}
	private void paintSnake(Graphics g){
		if(!gameOver){
			for(int i = 0; i < snakeLength;i++){
				if(i == 0){
					g.setColor(Color.YELLOW);
				}
				else{
					g.setColor(Color.BLACK);
				}
				g.fillRect(x[i],y[i],TILE_SIZE,TILE_SIZE);
			}

		}
	}
	private void placeFood(){
		var random = new Random();
		yFood = random.nextInt(HEIGHT)*TILE_SIZE;
		xFood = random.nextInt(WIDTH)*TILE_SIZE;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintFood(g);
		paintSnake(g);
		paintScore(g);
		paintGameIsOver(g);
		paintRecord(g);
	}

	public void restart() {
		if((newRecord > record )){
			record = score;
		}
		gameOver = false;
		snakeLength = 3;
		score = 0;
		delay = 200;
		motion.direction = 'U';
		setBackground(Color.BLUE);
		for (int i = 0; i < snakeLength;i++){
			x[i] = WIDTH*TILE_SIZE/2;
			y[i] = HEIGHT*TILE_SIZE/2 + i*TILE_SIZE;
		}
		timer.setDelay(delay);
		placeFood();
		timer.start();
		restartButton.setVisible(false);
		repaint();
	}
}
