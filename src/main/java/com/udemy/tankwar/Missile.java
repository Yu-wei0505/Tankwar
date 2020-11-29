package com.udemy.tankwar;

import java.awt.*;

class Missile {

    private static final int SPEED = 12;

    private int x;

    private int y;

    private final Boolean enemy;

    private final Direction direction;

    private boolean live = true;

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

    Missile(int x, int y, Boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    Image getImage() {
        return direction.getImage("missile");
    }

    private void move() {
        x+=direction.xFactor*SPEED;
        y+=direction.yFactor*SPEED;
    }

    void draw(Graphics g) {
        move();
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            this.live=false;
            return;
        }
        Rectangle rectangle = this.getRectangle();
        for (Wall wall:GameClient.getInstance().getWalls()){
            if (rectangle.intersects(wall.getRectangle())){
                this.setLive(false);
                return;
            }
        }
        if (enemy){
            Tank playTank = GameClient.getInstance().getPlayerTank();
            if (rectangle.intersects(playTank.getRectangle())) {
                addExplosion();
                playTank.setHp(playTank.getHp() - 20);
                if (playTank.getHp()<=0){
                    playTank.setLive(false);
                }
                this.setLive(false);
            }
        }
        else {
            for (Tank tank: GameClient.getInstance().getEnemyTanks()){
                if (rectangle.intersects(tank.getRectangle())){
                    addExplosion();
                    tank.setLive(false);
                    this.setLive(false);
                    break;
                }
            }
        }

        g.drawImage(getImage(), x, y, null);
    }

    private void addExplosion(){
        GameClient.getInstance().addExplosion(new Explosion(x, y));
        Tools.playAudio("explode.wav");
    }

    Rectangle getRectangle(){
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }
}
