package model;

import controller.OctopusAge;
import view.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Octopus extends Entity{
    private ArrayList<Octopus> children = new ArrayList<Octopus>();
    private OctopusAge age;
    public int fishToGrowUp;
    public int depth;

    public ArrayList<Octopus> getChildren() {
        return children;
    }

    public OctopusAge getAge() {
        return age;
    }

    public Octopus(int x, int y, OctopusAge age, int depth) {
        super(x, y);
        try {
//            File file = new File("C:\\Projects\\Project11Class\\src\\sprites\\Octopus.png");
            URL file = getClass().getResource("/Octopus.png");
            assert file != null;
            this.sprite = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.depth = depth;
        this.age = age;
        switch (this.age) {
            case HEAD, ADULT -> this.fishToGrowUp = -1;
            case CHILD -> this.fishToGrowUp = 10;
        }
    }

    public Octopus(int x, int y, int depth) {
        super(x, y);
        try {
            URL file = getClass().getResource("/Octopus.png");
            assert file != null;
            this.sprite = ImageIO.read(file);
            this.sprite = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.age = OctopusAge.HEAD;
        this.fishToGrowUp = -1;
        this.depth = depth;
    }

    public void addChildren(Octopus child) {
        this.children.add(child);
    }

    public void draw(Graphics g){
        for (Octopus child : this.children) child.draw(g);
        int ratio = 0; //a fraction of the window width
        switch (this.age) {
            case HEAD -> ratio = 4;
            case ADULT -> ratio = 8;
            case CHILD -> ratio = 12;
        }
        try {
            this.sprite = this.sprite.getScaledInstance(
                    Window.getInstance().getHeight() / ratio,
                    Window.getInstance().getHeight() / ratio,
                    Image.SCALE_DEFAULT);
            g.drawImage(this.sprite,
                    (this.x * Window.getInstance().getWidth() / (Window.sourceX / 2)) - Window.getInstance().getHeight() / (ratio * 2),
                    (this.y * Window.getInstance().getHeight() / (Window.sourceY / 2)) - Window.getInstance().getHeight() / (ratio * 2),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            g.setColor(new Color(247, 219, 255));
            g.fillOval(
                    (this.x * Window.getInstance().getWidth() / (Window.sourceX / 2)) - Window.getInstance().getHeight() / (ratio * 2),
                    (this.y * Window.getInstance().getHeight() / (Window.sourceY / 2)) - Window.getInstance().getHeight() / (ratio * 2),
                    Window.getInstance().getHeight() / 4,
                    Window.getInstance().getHeight() / 4);
        }
    }

    public void decrementFishToGrowUp(int val) { //mini - builder pattern
        if (this.fishToGrowUp > 0 && (this.fishToGrowUp - val <= 0)) {
            this.age = OctopusAge.ADULT;
        }
        this.fishToGrowUp -= val;
        if (this.fishToGrowUp < 0) { //for long game, when we can jump over int
            this.fishToGrowUp = -1;
        }
        for (Octopus child : this.children) child.decrementFishToGrowUp(val);
    }

    public void decrementFishToGrowUp() {
        decrementFishToGrowUp(1);
    }

    public void transformYatAll(int newY) {
        this.y += newY;
        for (Octopus child : this.children) child.transformYatAll(newY);
    }
}
