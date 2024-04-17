package model;

import view.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FloatingEntity extends Entity{
    public int value;
    protected Color colorInCaseError;

    public FloatingEntity(int x, int y, int value) {
        super(x, y);
        try {
            URL file = getClass().getResource("/ErrorImg.png");
            assert file != null;
            this.sprite = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.value = value;
        this.colorInCaseError = new Color(153, 0, 255, 255);
    }

    public void draw(Graphics g){
        try {
            this.sprite = this.sprite.getScaledInstance(
                    Window.getInstance().getHeight() / 6,
                    Window.getInstance().getHeight() / 6,
                    Image.SCALE_DEFAULT);
            g.drawImage(this.sprite,
                    (this.x * Window.getInstance().getWidth() / (Window.sourceX / 2)) - Window.getInstance().getHeight() / 12,
                    (this.y * Window.getInstance().getHeight() / (Window.sourceY / 2)) - Window.getInstance().getHeight() / 12,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            g.setColor(colorInCaseError);
            g.fillOval(
                    this.x - Window.getInstance().getHeight() / 12,
                    this.y - Window.getInstance().getHeight() / 12,
                    Window.getInstance().getHeight() / 6,
                    Window.getInstance().getHeight() / 6);
        }
    }

    public boolean isInOctopus(Octopus octopus) { //I could simplify, but this writing better for understand
        if (Math.abs(this.y - octopus.y) <= (Window.sourceY / 2) / 12) {
            if (this.x >= octopus.x && this.x - octopus.x <= (Window.sourceX / 2) / 12) {
                return true;
            } else if (this.x < octopus.x && octopus.x - this.x <= (Window.sourceX / 2) / 24) {
                return true;
            }
        }
        return false;
    }
}
