package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FloatingOctopusKid extends FloatingEntity{
    public FloatingOctopusKid(int x, int y) {
        super(x, y, 0);
        this.colorInCaseError = new Color(188, 142, 255);
        try {
            URL file = getClass().getResource("/Octopus.png");
            assert file != null;
            this.sprite = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
