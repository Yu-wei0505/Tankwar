package com.udemy.tankwar;

import javax.swing.*;
import java.awt.*;

public class Wall {

    private int x;

    private int y;

    private boolean horizontal;

    private int brick;

    public Wall(int x, int y,boolean horizontal ,int brick) {
        this.x = x;
        this.y = y;
        this.horizontal=horizontal;
        this.brick = brick;
    }

    public void draw(Graphics g){
        Image brickImage= Tools.getImage("brick.png");
        if (horizontal){
            for (int i=0;i<brick;i++){
                g.drawImage(brickImage,x+i*brickImage.getWidth(null),y,null);
            }
        }
        else {
            for (int i=0;i<brick;i++){
                g.drawImage(brickImage,x,y+i*brickImage.getHeight(null),null);
            }

        }
    }

}
