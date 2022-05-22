package com.sxt;

import java.awt.*;

public class Bg {

    // level
    static int level = 1;
    // goal
    int goal = level * 8;
    // score
    static int count = 0;
    // portion remain
    static int portions = 3;
    // portion status, F not used, T used
    static boolean portionFlag = false;
    // start time
    long startTime;
    // end time
    long endTime;
    long tim = 20;

    Image bg = Toolkit.getDefaultToolkit().getImage("imgs/bg.jpg");
//    bg.getBestCursorSize
    Image bg1 = Toolkit.getDefaultToolkit().getImage("imgs/bg1.jpg");
    Image peo = Toolkit.getDefaultToolkit().getImage("imgs/peo.png");
    Image portion = Toolkit.getDefaultToolkit().getImage("imgs/info.png");
    Image contind = Toolkit.getDefaultToolkit().getImage("imgs/continue.png");

    void paintSelf(Graphics g) {
        g.drawImage(bg1, 0,0, null);
        g.drawImage(bg, 0,200, null);
        switch (GameWin.state) {
            case 0:
                drawWord(g, 80, "Clcik to Start" , 120, 500, Color.WHITE);
                break;
            case 1:
            case 2:
                g.drawImage(peo, 310,50, null);
                // score
                drawWord(g, 30, "Score: " + count , 30, 150, Color.BLACK);
                g.drawImage(portion, 450, 40, null);
                // portions
                drawWord(g, 30, "*" + portions , 550, 70, Color.BLACK);
                // level number
                drawWord(g, 20, "Level " + level , 30, 50, Color.BLACK);
                // goal
                drawWord(g, 30, "Goal: " + goal , 30, 110, Color.BLACK);
                //  clock
                endTime = System.currentTimeMillis();
                tim = 20 - (endTime - startTime)/ 1000;
                drawWord(g, 30, "Time: " + tim, 520, 150, Color.BLACK);
                break;
            case 3:
                drawWord(g, 80, "Failed" , 250, 300, Color.RED);
                drawWord(g, 80, "Score: " + count , 200, 450, Color.RED);
                break;
            case 4:
                drawWord(g, 80, "Success" , 250, 300, Color.GREEN);
                drawWord(g, 80, "Score: " + count , 200, 450, Color.GREEN);
                break;
            case 5:
                drawWord(g, 80, "Next Level" , 150, 400, Color.WHITE);
                drawWord(g, 50, "Score: " + count , 200, 550, Color.WHITE);
                g.drawImage(contind, 320,750, null);
                break;
            default:
        }
    }

    // time remain
    boolean gameTime() {
        if(tim > 0) {
            return true;
        }
        System.out.println("time up");
        System.out.println(tim);
//        System.out.println(endTime);

        return false;
    }

    // restart
    void restart() {
        level = 1;
        // goal
        goal = level * 8;
        // score
        count = 0;
        // portion remain
        portions = 3;
        // portion status, F not used, T used
        portionFlag = false;
        tim = 20;
    }

    void levelUp() {
        tim = 20;
        portionFlag = false;
        count = 0;
        goal = level * 5;
    }

    public static void drawWord(Graphics g, int size, String name, int x, int y, Color color) {
        g.setColor(color);
        g.setFont(new Font("Papyrus", Font.BOLD, size));
        g.drawString(name, x, y);
    }

}
