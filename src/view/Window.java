package view;

import controller.keyboard.OnKeyboardListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Window extends JFrame {
    private static Window instance = null;
    public static final int sourceX = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int sourceY = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static Window getInstance() {
        if (instance == null) instance = new Window();
        return instance;
    }

    private Window() {
        super();
        setTitle("Project for the session");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = new Dimension(
                screenSize.width / 2,
                screenSize.height / 2
        );
        Point windowLocation = new Point(
                screenSize.width / 4,
                screenSize.height / 4
        );
        setSize(windowSize);
        setLocation(windowLocation);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                OnKeyboardListener.actionPerformed(e);
            }
        });
    }
}
