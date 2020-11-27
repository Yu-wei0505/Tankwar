package com.udemy.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {

    private  int x;
    private  int y;
    private  Direction direction;
    private boolean enemy;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public Tank(int x, int y, Direction direction) {
        this(x,y,direction,false);
    }

    public Tank(int x, int y, Direction direction, boolean enemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }

    void move(){
        if (this.stopped) return;
        switch (direction){
            case UP:
                y-=5; break;
            case DOWN:
                y+=5; break;
            case RIGHT:
                x+=5; break;
            case LEFT:
                x-=5; break;
            case UPLEFT:
                y-=5;
                x-=5;
                break;
            case UPRIGHT:
                y-=5;
                x+=5;
                break;
            case DOWNLEFT:
                y+=5;
                x-=5;
                break;
            case DOWNRIGHT:
                y+=5;
                x+=5;
                break;

        }
    }

    Image getImage(){
        String prefix= enemy?"e":"";
        switch (direction){
            case UP:
                return new ImageIcon("assets/images/"+prefix+"tankU.gif").getImage();
            case DOWN:
                return new ImageIcon("assets/images/"+prefix+"tankD.gif").getImage();
            case LEFT:
                return new ImageIcon("assets/images/"+prefix+"tankL.gif").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/"+prefix+"tankR.gif").getImage();
            case UPLEFT:
                return new ImageIcon("assets/images/"+prefix+"tankLU.gif").getImage();
            case UPRIGHT:
                return new ImageIcon("assets/images/"+prefix+"tankRU.gif").getImage();
            case DOWNLEFT:
                return new ImageIcon("assets/images/"+prefix+"tankLD.gif").getImage();
            case DOWNRIGHT:
                return new ImageIcon("assets/images/"+prefix+"tankRD.gif").getImage();

        }
        return null;
    }

    void draw(Graphics g){
        this.determineDirection();
        this.move();
        g.drawImage(this.getImage(),this.x,this.y,null);
    }

    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:up=true; break;
            case KeyEvent.VK_DOWN:down=true; break;
            case KeyEvent.VK_LEFT:left=true; break;
            case KeyEvent.VK_RIGHT:right=true; break;
        }
    }
    private boolean stopped;

    private void  determineDirection(){
        if (!up && !left && !down && !right) {
            this.stopped=true;
        }
        else {
            if (up && left && !down && !right) this.direction = Direction.UPLEFT;
            else if (up && right && !down && !left) this.direction = Direction.UPRIGHT;
            else if (down && left && !up && !right) this.direction = Direction.DOWNLEFT;
            else if (down && right && !up && !left) this.direction = Direction.DOWNRIGHT;
            else if (up && !right && !down && !left) this.direction = Direction.UP;
            else if (down && !right && !up && !left) this.direction = Direction.DOWN;
            else if (right && !up && !down && !left) this.direction = Direction.RIGHT;
            else if (left && !right && !down && !up) this.direction = Direction.LEFT;
            this.stopped=false;
        }

    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }
}
