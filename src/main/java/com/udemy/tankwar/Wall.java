package com.udemy.tankwar;

import java.awt.*;

class Wall {

    private int x;

    private int y;

    private boolean horizontal;

    private int brick;

    private final Image brickImage;

     Wall(int x, int y,boolean horizontal ,int brick) {
        this.brickImage= Tools.getImage("brick.png");
        this.x = x;
        this.y = y;
        this.horizontal=horizontal;
        this.brick = brick;
    }

     Rectangle getRectangle(){
        return horizontal ? new Rectangle(x,y,brick * brickImage.getWidth(null),
                brickImage.getHeight(null)) :
                new Rectangle(x,y,brickImage.getWidth(null),
                        brick * brickImage.getHeight(null));
    }

     void draw(Graphics g){
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
