package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Log extends FloatingEntity{
    public Log(int x, int y) {
        super(x, y, -25);
        this.colorInCaseError = new Color(105, 44, 0, 255);
        try {
            URL file = getClass().getResource("/Log.png");
            assert file != null;
            this.sprite = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
