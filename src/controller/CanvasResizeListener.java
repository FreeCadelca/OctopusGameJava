package controller;

import model.FloatingEntityAdapter;
import view.Window;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CanvasResizeListener extends ComponentAdapter {
    @Override
    public void componentResized(ComponentEvent e) {
//        System.out.println("window resized");
//        Animator.getInstance().getMainOctopus().x = Window.getInstance().getWidth() / 4;
//        switch (Animator.getInstance().currentStripe){
//            case 0 -> Animator.getInstance().getMainOctopus().y = Window.getInstance().getHeight() / 6;
//            case 1 -> Animator.getInstance().getMainOctopus().y = Window.getInstance().getHeight() / 2;
//            case 2 -> Animator.getInstance().getMainOctopus().y = 5 * Window.getInstance().getHeight() / 6;
//        }
        Animator.getInstance().getMainOctopus().x =
                (Animator.getInstance().getMainOctopus().x * Window.getInstance().getWidth()) / (Window.sourceX / 2);
        Animator.getInstance().getMainOctopus().y =
                (Animator.getInstance().getMainOctopus().y * Window.getInstance().getHeight()) / (Window.sourceY / 2);

        System.out.printf("\nSourceX=%d, SourceY=%d\nNewX=%d, NewY=%d\n",
                Window.sourceX / 2,
                Window.sourceY / 2,
                Window.getInstance().getWidth(),
                Window.getInstance().getHeight());

        for (FloatingEntityAdapter i : Animator.getInstance().entities) {
            i.getEntity().x = (i.getEntity().x * Window.getInstance().getWidth()) / (Window.sourceX / 2);
            i.getEntity().y = (i.getEntity().y * Window.getInstance().getHeight()) / (Window.sourceY / 2);
        }

        super.componentResized(e);
    }
}
