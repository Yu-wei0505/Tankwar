package com.udemy.tankwar;


import java.awt.*;
import java.awt.event.KeyEvent;

class Tank {

    private  int x;
    private  int y;
    private  Direction direction;
    private boolean enemy;

     Tank(int x, int y, Direction direction) {
        this(x,y,direction,false);
    }

     Tank(int x, int y, Direction direction, boolean enemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }

    private void move(){
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
                return Tools.getImage(prefix+"tankU.gif");
            case DOWN:
                return Tools.getImage(prefix+"tankD.gif");
            case LEFT:
                return Tools.getImage(prefix+"tankL.gif");
            case RIGHT:
                return Tools.getImage(prefix+"tankR.gif");
            case UPLEFT:
                return Tools.getImage(prefix+"tankLU.gif");
            case UPRIGHT:
                return Tools.getImage(prefix+"tankRU.gif");
            case DOWNLEFT:
                return Tools.getImage(prefix+"tankLD.gif");
            case DOWNRIGHT:
                return Tools.getImage(prefix+"tankRD.gif");

        }
        return null;
    }

    void draw(Graphics g){
        int oldX=x, oldY=y;

        this.determineDirection();
        this.move();

        if (x<0) x=0;
        else if (x>800-getImage().getWidth(null)) x=800-getImage().getWidth(null);
        if (y<0) y=0;
        else if (y>600-getImage().getHeight(null)) y=600-getImage().getHeight(null);

        Rectangle rec=this.getRectangle();
        for (Wall wall : GameClient.getInstance().getWalls()){
          if (rec.intersects(wall.getRectangle())){
              x=oldX;
              y=oldY;
              break;
          }
        }

        for (Tank tank:GameClient.getInstance().getEnemyTanks()){
            if (rec.intersects(tank.getRectangle())){
                x=oldX;
                y=oldY;
                break;
            }
        }
        g.drawImage(this.getImage(),this.x,this.y,null);

    }

    private Rectangle getRectangle(){
        return new Rectangle(x,y,getImage().getWidth(null),getImage().getHeight(null));
    }

    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    void keyPressed(KeyEvent e) {
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

    void keyReleased(KeyEvent e) {
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
