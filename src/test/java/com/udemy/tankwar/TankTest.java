package com.udemy.tankwar;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @Test
    void getImage() {
        for (Direction direction:Direction.values()){
            Tank tank=new Tank(0,0,direction,false);
            assertTrue(tank.getImage().getWidth(null)>0, direction+" can't get valid image!");
            Tank enemyTank=new Tank(0,0,direction,true);
            Image image=enemyTank.getImage();
            assertTrue(image.getWidth(null)>0, direction+" can't get valid image!");
        }
    }
}