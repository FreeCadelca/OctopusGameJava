import controller.Animator;
import controller.CanvasResizeListener;
import view.Canvas;
import view.Window;

import java.awt.*;

public class Main {
    public static void main(String[] argc) {
        Window.getInstance().setVisible(true);
        Window.getInstance().add(Canvas.getInstance(), BorderLayout.CENTER);
//        Canvas.getInstance().addComponentListener(new CanvasResizeListener());
        Animator.getInstance().start();
    }
}
