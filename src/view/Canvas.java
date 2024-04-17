package view;

import controller.Animator;
import controller.GameState;
import controller.OctopusAge;
import model.FloatingEntityAdapter;
import model.Octopus;
import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class Canvas extends JPanel {
    private static Canvas instance = null;

    public static Canvas getInstance() {
        if (instance == null)
            instance = new Canvas();
        return instance;
    }

    private Canvas() {
        super(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(215, 255, 251, 255));
        g.fillRect(0, 0, getSize().width, getSize().height);

//        String state = ""; //debug for states
//        switch (Animator.getInstance().getGameState()) {
//            case DEFAULT -> state = "DEFAULT";
//            case START_MENU -> state = "START_MENU";
//            case GAME_OVER -> state = "GAME_OVER";
//            case MOVE_UP -> state = "MOVE_UP";
//            case MOVE_DOWN -> state = "MOVE_DOWN";
//        }
//        g.setColor(new Color(0, 0, 0, 255));
//        g.setFont(new Font(Font.DIALOG, Font.BOLD, (Window.getInstance().getWidth() * 64) / (Window.sourceX / 2)));
//        g.drawString(state, 0, 8 * Window.getInstance().getHeight() / 9);

        if (Animator.getInstance().getGameState() == GameState.START_MENU) {
            paintStartMenu(g);
            return;
        }
        if (Animator.getInstance().getGameState() == GameState.GAME_OVER) {
            paintGameOver(g);
            return;
        }
        g.setColor(new Color(137, 157, 255, 182));
        g.drawLine(
                Window.getInstance().getWidth() / 3,
                Window.getInstance().getHeight() / 3,
                Window.getInstance().getWidth(),
                Window.getInstance().getHeight() / 3);
        g.drawLine(
                Window.getInstance().getWidth() / 3,
                2 * Window.getInstance().getHeight() / 3,
                Window.getInstance().getWidth(),
                2 * Window.getInstance().getHeight() / 3);
        Animator.getInstance().getMainOctopus().draw(g);
        for (FloatingEntityAdapter i : Animator.getInstance().entities) {
            i.getEntity().draw(g);
        }
        g.setColor(new Color(210, 176, 0, 255));

        String scoreStr = Integer.toString(Animator.getInstance().score);
        Font scoreFont = new Font(Font.DIALOG, Font.BOLD, (Window.getInstance().getHeight() * 64) / (Window.sourceY / 2));
        g.setFont(scoreFont);
        FontMetrics m = g.getFontMetrics(scoreFont);
        g.drawString(scoreStr, 0, 3 * m.getHeight() / 5);

        for (int i = 0; i < Animator.getInstance().entities.size(); i++) {
            if (Animator.getInstance().entities.get(i).getEntity().isInOctopus(Animator.getInstance().getMainOctopus())) {
                if ((Animator.getInstance().entities.get(i).getEntity().value == 0)) {
                    boolean isChildAttachable = false;
                    ArrayDeque<Octopus> q = new ArrayDeque<Octopus>();
                    q.add(Animator.getInstance().getMainOctopus());
                    while (!q.isEmpty() && (Animator.getInstance().numberOfChildren < 6)) {
                        Octopus octopus = q.getFirst();
                        q.removeFirst();
                        if (octopus.getChildren().size() == 0 && octopus.getAge() != OctopusAge.CHILD) {
                            int xOct = octopus.x;
                            int yOct = octopus.y;
                            octopus.addChildren(new Octopus(xOct - 40, yOct, OctopusAge.CHILD, octopus.depth + 1));
                            Animator.getInstance().numberOfChildren++;
                            isChildAttachable = true;
                            break;
                        }
                        if (octopus.getChildren().size() == 1  && octopus.getAge() != OctopusAge.CHILD) {
                            int xOct = octopus.x;
                            int yOct = octopus.y;
                            int ratio = octopus.depth + 1;
                            octopus.getChildren().get(0).y -= 40 / ratio;
                            octopus.addChildren(new Octopus(xOct - 40, yOct + 40 / ratio, OctopusAge.CHILD, octopus.depth + 1));
                            Animator.getInstance().numberOfChildren++;
                            isChildAttachable = true;
                            break;
                        }
                        q.addAll(octopus.getChildren());
                    }
                    if (!isChildAttachable) {
                        Animator.getInstance().score += 25;
                        Animator.getInstance().getMainOctopus().decrementFishToGrowUp(25);
                    }
                }
                if (Animator.getInstance().entities.get(i).getEntity().value == 5) {
                    Animator.getInstance().getMainOctopus().decrementFishToGrowUp(5);
                }
                if (Animator.getInstance().entities.get(i).getEntity().value == -25) {
                    g.setColor(new Color(255, 0, 0, 77));
                    g.fillRect(0, 0, Window.getInstance().getWidth(), Window.getInstance().getHeight());
                }
                Animator.getInstance().score += Animator.getInstance().entities.get(i).getEntity().value;
                Animator.getInstance().entities.remove(i);
                break;
            }
        }
    }

    public void paintStartMenu(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(new Color(255, 255, 255, 255));
        g.fillRoundRect(
                Window.getInstance().getWidth() / 5,
                Window.getInstance().getHeight() / 3,
                Window.getInstance().getWidth() * 3 / 5,
                Window.getInstance().getHeight() / 3,
                100,
                100);
        g.setColor(new Color(255, 216, 33, 255));

        Font font = new Font(Font.DIALOG, Font.BOLD, (Window.getInstance().getWidth() * 32) / (Window.sourceX / 2));
        g.setFont(font);
        FontMetrics m = g.getFontMetrics(font);
        g.drawString("PRESS ENTER TO START GAME",
                Window.getInstance().getWidth() / 4,
                (Window.getInstance().getHeight() / 2) + (2 * m.getHeight() / 5));
        g.setColor(oldColor);
    }

    public void paintGameOver(Graphics g) {
        Color oldColor = g.getColor();

        g.setColor(new Color(105, 105, 105, 255));
        g.fillRoundRect(
                Window.getInstance().getWidth() / 5,
                Window.getInstance().getHeight() / 3,
                Window.getInstance().getWidth() * 3 / 5,
                Window.getInstance().getHeight() / 3,
                100,
                100);
        g.setColor(new Color(204, 0, 0, 255));
        Font font = new Font(Font.DIALOG, Font.BOLD, (Window.getInstance().getWidth() * 78) / (Window.sourceX / 2));
        g.setFont(font);
        FontMetrics m = g.getFontMetrics(font);
        g.drawString("GAME OVER",
                Window.getInstance().getWidth() / 4,
                (Window.getInstance().getHeight() / 2) + (2 * m.getHeight() / 7));
        g.setColor(oldColor);
    }

    public void onRepaint() {
        repaint();
    }
}
