package com.udemy.tankwar;

import java.awt.*;

class Missile {

    private static final int SPEED = 12;

    private int x;

    private int y;

    private final Boolean enemy;

    private final Direction direction;

    Missile(int x, int y, Boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    Image getImage() {
        switch (direction) {
            case UP:
                return Tools.getImage("missileU.gif");
            case DOWN:
                return Tools.getImage("missileD.gif");
            case LEFT:
                return Tools.getImage("missileL.gif");
            case RIGHT:
                return Tools.getImage("missileR.gif");
            case UPLEFT:
                return Tools.getImage("missileLU.gif");
            case UPRIGHT:
                return Tools.getImage("missileRU.gif");
            case DOWNLEFT:
                return Tools.getImage("missileLD.gif");
            case DOWNRIGHT:
                return Tools.getImage("missileRD.gif");

        }
        return null;

    }

    void move() {
        switch (direction) {
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case UPLEFT:
                y -= SPEED;
                x -= SPEED;
                break;
            case UPRIGHT:
                y -= SPEED;
                x += SPEED;
                break;
            case DOWNLEFT:
                y += SPEED;
                x -= SPEED;
                break;
            case DOWNRIGHT:
                y += SPEED;
                x += SPEED;
                break;

        }
    }

    void draw(Graphics g) {
        move();
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            return;
        }
        g.drawImage(getImage(), x, y, null);
    }
}
