package com.udemy.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameClient extends JComponent {

    private static final GameClient INSTANCE= new GameClient();

    static GameClient getInstance(){
        return INSTANCE;
    }

    private Tank playerTank;

    private List<Tank> enemyTanks;

    private final AtomicInteger enemyKilled = new AtomicInteger(0);

    private List<Wall> walls;

    private List<Missile> missiles;

    private List<Explosion> explosions;

    void addExplosion(Explosion explosion){
        explosions.add(explosion);
    }

    void add(Missile missile){
        missiles.add(missile);
    }

    Tank getPlayerTank() {
        return playerTank;
    }

    List<Wall> getWalls() {
         return walls;
    }

    List<Tank> getEnemyTanks() {
         return enemyTanks;
    }

    List<Missile> getMissiles() {
        return missiles;
    }

    private GameClient(){
        this.playerTank = new Tank(400, 100, Direction.DOWN);
        this.missiles = new CopyOnWriteArrayList<>();
        this.explosions = new ArrayList<>();
        this.walls = Arrays.asList(
                new Wall(200,140,true,15),
                new Wall(200,540,true,15),
                new Wall(100,160,false,12),
                new Wall(700,160,false,12)
        );
        this.initEnemyTank();
        this.setPreferredSize(new Dimension(800,600));
    }

    private void initEnemyTank() {
        this.enemyTanks =  new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                this.enemyTanks.add(new Tank(200 + 120 * j, 400 + 40 * i, Direction.UP, true));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,800,600);
        if (!this.playerTank.isLive()){
            g.setColor(Color.RED);
            g.setFont(new Font(null,Font.BOLD,100));
            g.drawString("GAME OVER",100,250);
            g.setFont(new Font(null,Font.BOLD,60));
            g.drawString("PRESS F2 TO RESTART",60,400);
        }
        else {
            g.setColor(Color.WHITE);
            g.setFont(new Font(null,Font.BOLD,16));
            g.drawString("Missiles : "+missiles.size(),10,50);
            g.drawString("Explosions : "+explosions.size(),10,70);
            g.drawString("Player Tank HP : "+playerTank.getHp(),10,90);
            g.drawString("Enemy Left : "+enemyTanks.size(),10,110);
            g.drawString("Enemy Killed : "+enemyKilled.get(),10,130);

            playerTank.draw(g);

            int count = enemyTanks.size();
            enemyTanks.removeIf(t -> !t.isLive());
            enemyKilled.addAndGet(count - enemyTanks.size());

            if (enemyTanks.isEmpty()) {
                this.initEnemyTank();
            }
            for (Tank t : enemyTanks) {
                t.draw(g);
            }
            for (Wall w : walls) {
                w.draw(g);
            }

            missiles.removeIf(m -> !m.isLive());
            for (Missile m : missiles) {
                m.draw(g);
            }

            explosions.removeIf(e -> !e.isLive());
            for (Explosion e : explosions) {
                e.draw(g);
            }
        }
    }

    public static void main(String[] args) {
        com.sun.javafx.application.PlatformImpl.startup(()->{});
        JFrame frame=new JFrame();
        frame.setTitle("First tank war");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        final GameClient client= GameClient.getInstance();
        client.repaint();
        frame.add(client);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                client.playerTank.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                client.playerTank.keyReleased(e);
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //noinspection InfiniteLoopStatement
        while (true){
            try {
                client.repaint();
                if(client.playerTank.isLive()) {
                   for (Tank tank : client.enemyTanks) {
                    tank.actRandomly();
                   }
                 }
                Thread.sleep(50);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    void restart() {
        if (!playerTank.isLive()){
            this.playerTank = new Tank(400, 100, Direction.DOWN);
        }
        this.initEnemyTank();
    }
}
