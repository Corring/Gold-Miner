package com.sxt;

import java.awt.*;

public class Gold extends  Object{
    public Gold() {
        this.x = (int) (Math.random() * 700);
        this.y = (int) (Math.random() * 550 + 300);
        this.width = 105;
        this.height = 105;
        this.flag = false;
        this.grivaty = 40;
        this.count = 6;
        this.type = 1;
        this.img = Toolkit.getDefaultToolkit().getImage("imgs/gold1.gif");
    }
}

class GoldMini extends Gold {
    GoldMini() {
        this.width = 87;
        this.height = 87;
        this.grivaty = 30;
        this.count = 3;
        this.img = Toolkit.getDefaultToolkit().getImage("imgs/gold0.gif");
    }
}

class GoldPlus extends Gold {
    GoldPlus() {
        this.x = (int) (Math.random() * 650);
        this.width = 141;
        this.height = 141;
        this.grivaty = 65;
        this.count = 8;
        this.img = Toolkit.getDefaultToolkit().getImage("imgs/gold2.gif");
    }
}