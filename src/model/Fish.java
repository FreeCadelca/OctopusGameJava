package model;

import view.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Fish extends FloatingEntity{
    public Fish(int x, int y) {
        super(x, y, 5);
        this.colorInCaseError = new Color(255, 218, 71);
        try {
            URL file = getClass().getResource("/Fish.png");
            assert file != null;
            this.sprite = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
