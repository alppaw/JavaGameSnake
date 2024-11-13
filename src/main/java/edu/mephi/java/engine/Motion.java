package edu.mephi.java.engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Motion extends KeyAdapter {
    private char direction = 'U';
    public final int TILE_SIZE = 20;
    private Game game;

    public Motion(Game game) {
        this.game = game;
    }
    public void move(int snakeLength, int [] x, int [] y){
        for(int i = snakeLength; i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'R' -> x[0] += TILE_SIZE;
            case 'L' -> x[0] -= TILE_SIZE;
            case 'U' -> y[0] -= TILE_SIZE;
            case 'D' -> y[0] += TILE_SIZE;
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        int key = e.getKeyCode();
        if(((key == KeyEvent.VK_RIGHT)||(key == KeyEvent.VK_D))&&(direction != 'L')){
            direction = 'R';
        }
        if(((key == KeyEvent.VK_LEFT) || (key == KeyEvent.VK_A)) && (direction != 'R')){
            direction = 'L';
        }
        if(((key == KeyEvent.VK_UP) || (key == KeyEvent.VK_W)) && (direction != 'D')){
            direction = 'U';
        }
        if(((key == KeyEvent.VK_DOWN) || (key == KeyEvent.VK_S))&&(direction != 'U')){
            direction = 'D';
        }
    }
}