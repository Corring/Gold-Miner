package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameWin extends JFrame {

    // 0 not start, 1 gaming, 2 store, 3 failure, 4 success
    static int state;
    // store gold
    List<Object> objectList = new ArrayList<>();
    Bg bg = new Bg();
    Line line = new Line(this);

    void randomObject() {
        boolean isPlace = true;
        objectList = new ArrayList<>();
        for(int i = 0; i < 5; i ++) {
            double random = Math.random();
            Gold gold;
            if(random<0.3) gold = new GoldMini();
            else if(random > 0.7) gold = new GoldPlus();
            else gold = new Gold();

            for(Object obj: objectList) {
                if(gold.getRec().intersects(obj.getRec())){
                    isPlace = false;
                }
            }
            if(isPlace) objectList.add(gold);
            else {
                isPlace = true;
                i--;
            }

        }
        for(int i = 0; i < 3; i ++) {
            Rock rock = new Rock();
            for(Object obj: objectList) {
                if(rock.getRec().intersects(obj.getRec())){
                    isPlace = false;
                }
            }
            if(isPlace) objectList.add(rock);
            else {
                isPlace = true;
                i--;
            }
        }
    }

    Image offScreenImage;

    void setWindow() {
        this.setVisible(true);
        this.setSize(768, 1000);
        this.setLocationRelativeTo(null);
        this.setTitle("The Gold Miner");
    }

    boolean nextLevelTimer = false;

    void launch() {
        setWindow();
//        if(nextLevelTimer) {
//            bg.startTime = System.currentTimeMillis();
//            nextLevelTimer = false;
//        }
        randomObject();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch(state) {
                    case 0:
                        if(e.getButton() == 3 || e.getButton() == 1) {
                            bg.startTime = System.currentTimeMillis();
                            state = 1;
                        }
                        break;
                    case 1:
                        if(e.getButton() == 1) {
                            line.state = 1;
                            state = 2;
                        }
                        break;
                    case 2:
                        // catch and then go back
                        if(e.getButton() == 3 && line.state == 3 && Bg.portions > 0) {
                            Bg.portions --;
                            Bg.portionFlag = true;
                        }
                        if(line.length <= 150) {
                            state = 1;
                        }
                        break;
                    // fail
                    case 3:
                    // restart after fail or win
                    case 4:
                        if(e.getButton() == 1) {
                            state = 0;
                            bg.startTime = System.currentTimeMillis();
//                            nextLevelTimer = false;
                            bg.restart();
                            line.restart();
                        }
                        break;
                    case 5:
                        if(e.getButton() == 1) {
//                            randomObject();
                            state = 1;
                            line.restart();
                            bg.levelUp();
                            bg.startTime = System.currentTimeMillis();
                        }
                        break;
                    default:

                }
                // swing, and click to catch


            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        while(true) {
            repaint();
            nextLevel();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    // next level
    public void nextLevel() {
        if(state == 2) {
            if(bg.gameTime()) {
                if(Bg.count >= bg.goal) {
                    if(Bg.level == 5) {
                        state = 4;
                    } else{
                        Bg.level ++;
                        System.out.println("next level can access");
                        // refresh the window
//                        GameWin gamewini = new GameWin();
//                        gamewini.nextLevelTimer = true;
//                        gamewini.launch();
                        // refresh the page without
                        randomObject();
//                        bg.restart();
//                        line.restart();
                        state = 5;
//                        this.nextLevelTimer = true;

                    }
                }
            } else if(!bg.gameTime()) {
                if(Bg.count >= bg.goal) {
                    if(Bg.level == 5) {
                        state = 4;
                    } else {
                        Bg.level ++;

                        System.out.println("next level can access");
                        // refresh the window
//                        GameWin gamewini = new GameWin();
//                        gamewini.nextLevelTimer = true;
//                        gamewini.launch();
                        randomObject();
//                        bg.restart();
//                        line.restart();
//                        this.nextLevelTimer = true;
                    }
                } else {
                    state = 3;
                }
            }
        }
    }

    // paint the image in the window
    @Override
    public void paint(Graphics g) {
        offScreenImage = this.createImage(768, 1000);
        Graphics gImage = offScreenImage.getGraphics();

        bg.paintSelf(gImage);
        if(state == 1 || state == 2) {
            // draw the gold and rock
            for(Object gold: objectList) {
                gold.paintSelf(gImage);
            }
            // drwa the line
            line.paintSelf(gImage);
        }

        g.drawImage(offScreenImage, 0, 0, null);
    }


    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
