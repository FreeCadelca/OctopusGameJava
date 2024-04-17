package controller.keyboard;

import controller.Animator;
import controller.GameState;

import java.awt.event.KeyEvent;

public class OnKeyboardListener {
    public static void actionPerformed(KeyEvent event){
        String key = event.getKeyText(event.getKeyCode());
        GameState newState = null;
        System.out.println(key);
        if (Animator.getInstance().getGameState() == GameState.GAME_OVER) return;
        if ((Animator.getInstance().getGameState() == GameState.START_MENU) && key.equals("Enter")) {
            newState = GameState.DEFAULT;
        } else if ((Animator.getInstance().getGameState() == GameState.DEFAULT) && key.equals("W")) {
            newState = GameState.MOVE_UP;
        } else if ((Animator.getInstance().getGameState() == GameState.DEFAULT) && key.equals("S")) {
            newState = GameState.MOVE_DOWN;
        }
        if (newState != null) Animator.getInstance().setGameState(newState);
    }
}
