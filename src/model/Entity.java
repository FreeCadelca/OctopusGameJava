package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public int x;
    public int y;
    public int xForRendering;
    public int yForRendering;
    protected Image sprite = null;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.xForRendering = x;
        this.yForRendering = y;
    }

    public void draw(Graphics g){}

    public Image getSprite() {
        return sprite;
    }
}
