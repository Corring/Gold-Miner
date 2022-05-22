package com.sxt;

import java.awt.*;

import static com.sxt.Bg.drawWord;

public class Line {
    // start location
    int x = 380;
    int y = 180;

    // end location
    int endx = 500;
    int endy = 500;

    double length = 100;
    double Min_length = 100;
    double Max_length = 750;
    double angel = 0;
    // direction
    int dir = 1;
    // status 0 swing, 1 catch, 2 go back, 3 catch and go back
    int state = 0;

    Image hook = Toolkit.getDefaultToolkit().getImage("imgs/hook.png");

    GameWin frame;

    Line(GameWin frame) {
        this.frame = frame;
    }

    void logic() {
        for(Object obj: frame.objectList)
        if(endx>obj.x && endx < obj.x + obj.width
                && endy > obj.y && endy< obj.y + obj.height) {
            state = 3;
            obj.flag = true;
        }
    }

    void linesUpdating(Graphics g) {
        endx = (int) (x + length * Math.cos(angel * Math.PI));
        endy = (int) (y + length * Math.sin(angel * Math.PI));
        g.setColor(Color.white);
        g.drawLine(x - 1, y, endx - 1, endy);
        g.drawLine(x, y, endx, endy);
        g.drawLine(x + 1, y, endx + 1, endy);
        g.drawImage(hook, endx - 36, endy - 2, null);
    }

    void paintSelf(Graphics g) {
        logic();
        switch(state) {
            case 0:
                if(angel < 0.1) {
                    dir = 1;
                } else if(angel > 0.9){
                    dir = -1;
                }
                angel += 0.005 * dir;
                linesUpdating(g);
                break;
            case 1:
                if(length < Max_length) {
                    length = length + 10;
                    linesUpdating(g);
                } else {
                    state = 2;
                }
                break;
            case 2:
                if(length > Min_length) {
                    length -= 10;
                    linesUpdating(g);
                } else {
                    state = 0;
                }
                break;
            case 3:
                int gravity = 1;
                if(length > Min_length) {
                    length -= 5;
                    linesUpdating(g);
                    for(Object obj: frame.objectList){
                        if(!obj.flag) continue;
                        gravity = obj.grivaty;
                        obj.x = endx - obj.getWidth() / 2;
                        obj.y = endy;
                        if(length <= Min_length) {
                            obj.x = -150;
                            obj.y = -150;
                            state = 0;
                            obj.flag = false;
                            // score system
                            Bg.count += obj.count;
                            Bg.portionFlag = false;
                        }
                        if(Bg.portionFlag) {
                            if(obj.type == 1) {
                                drawWord(g, 80, "POWER!!" , 200,250, Color.GREEN);
                                gravity=1;
                            }
                            else {
                                drawWord(g, 50, "BOOM!" , obj.x - 25, obj.y, Color.red);
                                obj.x = -150;
                                obj.y = -150;
                                obj.flag = false;
                                Bg.portionFlag = false;
                                state = 2;
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(gravity);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }

    void restart() {
        angel = 0;
        length = 100;
    }
}
