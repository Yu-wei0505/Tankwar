package com.udemy.tankwar;

import java.awt.*;

class Blood {

    private int x;

    private int y;

    private final Image image;

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

    private boolean live = true;

    Blood(int x, int y) {
        this.x = x;
        this.y = y;
        this.image = Tools.getImage("blood.png");
    }

    void draw(Graphics g){
        g.drawImage(image,x,y,null);
    }

    public Rectangle getRectangle() {
        return new Rectangle(x,y,image.getWidth(null),image.getHeight(null));
    }
}
