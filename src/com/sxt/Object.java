package com.sxt;

import java.awt.*;

public class Object {
    // spot
    int x;
    int y;
    // width and length
    int width;
    int height;

    // Image
    Image img;

    // can be moved or not
    boolean flag;
    int grivaty;
    int count;
    // type 1: gold, 2: rock
    int type;

    void paintSelf(Graphics g) {
    g.drawImage(img, x, y, null);
    }

    public int getWidth() {
        return width;
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}
