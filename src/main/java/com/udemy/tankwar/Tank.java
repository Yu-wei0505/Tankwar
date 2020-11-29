package com.udemy.tankwar;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

class Tank {

    private static final int MOVE_SPEED = 5;
    private int x;
    private int y;
    private Direction direction;
    private boolean enemy;
    private boolean live =true;
    private int Hp = 100;

    public int getHp() {
        return Hp;
    }

    public void setHp(int hp) {
        Hp = hp;
    }

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

    boolean isEnemy() {
        return enemy;
    }

    Tank(int x, int y, Direction direction) {
        this(x, y, direction, false);
    }

    Tank(int x, int y, Direction direction, boolean enemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }

    private void move() {
        if (this.stopped) return;
        x += direction.xFactor * MOVE_SPEED;
        y += direction.yFactor * MOVE_SPEED;
    }

    Image getImage() {
        String prefix = enemy ? "e" : "";
        return direction.getImage(prefix+"tank");
    }

    void draw(Graphics g) {
        int oldX = x, oldY = y;
        if(!this.enemy) {
            this.determineDirection();
        }
        this.move();

        if (x < 0) x = 0;
        else if (x > 800 - getImage().getWidth(null)) x = 800 - getImage().getWidth(null);
        if (y < 0) y = 0;
        else if (y > 600 - getImage().getHeight(null)) y = 600 - getImage().getHeight(null);

        Rectangle rec = this.getRectangle();
        for (Wall wall : GameClient.getInstance().getWalls()) {
            if (rec.intersects(wall.getRectangle())) {
                x = oldX;
                y = oldY;
                break;
            }
        }

        for (Tank tank : GameClient.getInstance().getEnemyTanks()) {
            if (tank != this && rec.intersects(tank.getRectangle())) {
                x = oldX;
                y = oldY;
                break;
            }
        }

        if (this.enemy && rec.intersects(GameClient.getInstance()
                .getPlayerTank().getRectangle())){
            x = oldX;
            y = oldY;
        }
        if(!enemy){
            g.setColor(Color.WHITE);
            g.fillRect(x,y-10,this.getImage().getWidth(null),10);

            g.setColor(Color.RED);
            int width = Hp * this.getImage().getWidth(null) / 100;
            g.fillRect(x,y-10,width,10);
        }

        g.drawImage(this.getImage(), this.x, this.y, null);

    }

    Rectangle getRectangle() {
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }

    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_Z:
                fire();
                break;
            case KeyEvent.VK_SPACE:
                superFire();
                break;
            case KeyEvent.VK_D:
                threeFire();
                break;
            case KeyEvent.VK_F2:
                GameClient.getInstance().restart();
                break;

        }
    }


    private void fire() {
        Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                y + getImage().getHeight(null) / 2 - 6, enemy, direction);
        GameClient.getInstance().add(missile);

        Tools.playAudio("shoot.wav");
    }

    private void threeFire(){
        for (int i = 0; i <3; i++) {
            Direction direction = Direction.values()[i];
            Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                    y + getImage().getHeight(null) / 2 - 6, enemy, direction);
            GameClient.getInstance().add(missile);
        }
        String audioFile = new Random().nextBoolean() ? "supershoot.aiff" : "supershoot.wav";
        Tools.playAudio(audioFile);

    }


    private void superFire() {
        for(Direction direction:Direction.values()) {
            Missile missile = new Missile(x + getImage().getWidth(null) / 2 - 6,
                    y + getImage().getHeight(null) / 2 - 6, enemy, direction);
            GameClient.getInstance().add(missile);
        }
        String audioFile = new Random().nextBoolean() ? "supershoot.aiff" : "supershoot.wav";
        Tools.playAudio(audioFile);
    }

    private boolean stopped;

    private void determineDirection() {
        if (!up && !left && !down && !right) {
            this.stopped = true;
        } else {
            if (up && left && !down && !right) this.direction = Direction.LEFT_UP;
            else if (up && right && !down && !left) this.direction = Direction.RIGHT_UP;
            else if (down && left && !up && !right) this.direction = Direction.LEFT_DOWN;
            else if (down && right && !up && !left) this.direction = Direction.RIGHT_DOWN;
            else if (up && !right && !down && !left) this.direction = Direction.UP;
            else if (down && !right && !up && !left) this.direction = Direction.DOWN;
            else if (right && !up && !down && !left) this.direction = Direction.RIGHT;
            else if (left && !right && !down && !up) this.direction = Direction.LEFT;
            this.stopped = false;
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

    private final Random random = new Random();

    private int step = new Random().nextInt(12) + 3;

    void actRandomly() {
        Direction[] dirs = Direction.values();
        if (step == 0) {
            step = random.nextInt(12) + 3;
            this.direction = dirs[random.nextInt(dirs.length)];
            if (random.nextBoolean()) {
                this.fire();
            }
        }
        step--;
    }
}
