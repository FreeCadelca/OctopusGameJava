package controller;

import model.*;
import view.Canvas;
import view.Window;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Animator extends Thread {
    private static Animator instance = null;
    private boolean isActive;
    private GameState gameState;
    private Octopus mainOctopus = null;
    public int currentStripe;
    public int score;
    public ArrayList<FloatingEntityAdapter> entities = new ArrayList<FloatingEntityAdapter>();
    private int borderOfTheLastMap;
    public int numberOfChildren = 0;

    public static Animator getInstance() {
        if (instance == null) instance = new Animator();
        return instance;
    }

    public Animator() {
        this.isActive = true;
        this.gameState = GameState.START_MENU;
        this.mainOctopus = new Octopus((Window.sourceX / 2) / 4, (Window.sourceY / 2) / 2, 0);
        this.currentStripe = 1;
        this.score = 0;
        this.borderOfTheLastMap = (Window.sourceX / 2) / 4;
    }

    public void run() {
        while (isActive) {
            try {
                if (this.gameState == GameState.START_MENU || this.gameState == GameState.GAME_OVER) {
                    Canvas.getInstance().onRepaint();
                    Thread.sleep(40);
                    continue;
                }
                if (this.gameState == GameState.MOVE_UP) {
                    if (this.currentStripe > 0) {
                        this.mainOctopus.transformYatAll(-(Window.sourceY / 2) / 3);
                        this.currentStripe--;
                    }
                    this.gameState = GameState.DEFAULT;
                } else if (this.gameState == GameState.MOVE_DOWN) {
                    if (this.currentStripe < 2) {
                        this.mainOctopus.transformYatAll((Window.sourceY / 2) / 3);
                        this.currentStripe++;
                    }
                    this.gameState = GameState.DEFAULT;
                }
                if (this.borderOfTheLastMap <= (Window.sourceX / 2) / 3 + 20) { //error rate error when scaling the window
                    generateNewMap();
                }
                this.borderOfTheLastMap -= (Window.sourceX / 2) / 96;
                ArrayList<Integer> indexesToRemove = new ArrayList<>();
                for (int i = 0; i < entities.size(); i++) {
                    entities.get(i).getEntity().x -= (Window.sourceX / 2) / 96;
                    if (entities.get(i).getEntity().x < 0) {
                        indexesToRemove.add(i);
                    }
                }
                if (indexesToRemove.size() > 0) {
                    for (int i = indexesToRemove.size() - 1; i > -1; i--) {
                        this.entities.remove((int) indexesToRemove.get(i));
                    }
                }
//                System.out.println("Current entities:");
//                for (FloatingEntityAdapter i : this.entities) {
//                    System.out.printf("x = %d, y = %d\n", i.getEntity().x, i.getEntity().y);
//                }
//                System.out.println();

//                ArrayDeque<Octopus> q = new ArrayDeque<Octopus>();
//                q.add(this.mainOctopus);
//                while (!q.isEmpty()) {
//                    Octopus octopus = q.getFirst();
//                    String octopusAge = switch (octopus.getAge()) {
//                        case HEAD -> "HEAD";
//                        case ADULT -> "ADULT";
//                        case CHILD -> "CHILD";
//                    };
//                    q.removeFirst();
//                    System.out.printf("Octopus: x = %d, y = %d, age = " + octopusAge + ", fishToGrow = %d\n", octopus.x, octopus.y, octopus.fishToGrowUp);
//                    q.addAll(octopus.getChildren());
//                }
//                System.out.println();

                if (this.score < 0) {
                    this.gameState = GameState.GAME_OVER;
                }
                Canvas.getInstance().onRepaint();
                Thread.sleep(40);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addEntity(FloatingEntityAdapter entity) {
        this.entities.add(entity);
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Octopus getMainOctopus() {
        return mainOctopus;
    }

    private void generateNewMap() {
        int[][] newMap = PatternOfMap.getNewMap(score);
//        System.out.println("Generating new map");
//        for (int[] i : newMap) {
//            for (int j : i) {
//                System.out.printf("%d ", j);
//            }
//            System.out.println();
//        }
        for (int line = 0; line < 3; line++) {
            for (int i = 0; i < 6; i++) {
                if (newMap[line][i] != 0) {
                    int newCordX = (Window.sourceX / 2) + (i * (Window.sourceX / 2)) / 8;
                    int newCordY = (Window.sourceY / 2) / 6 + (Window.sourceY / 2) * line / 3;
                    FloatingEntity newFloatingEntity = null;
                    if (newMap[line][i] == 1) {
//                        System.out.printf("added new entity: Fish, x = %d, y = %d\n", newCordX, newCordY);
                        newFloatingEntity = new Fish(newCordX, newCordY);
                    } else if (newMap[line][i] == 2) {
//                        System.out.printf("added new entity: Log, x = %d, y = %d\n", newCordX, newCordY);
                        newFloatingEntity = new Log(newCordX, newCordY);
                    } else if (newMap[line][i] == 3) {
//                        System.out.printf("added new entity: SmallOct, x = %d, y = %d\n", newCordX, newCordY);
                        newFloatingEntity = new FloatingOctopusKid(newCordX, newCordY);
                    }
                    addEntity(new FloatingEntityAdapter(newFloatingEntity));
                }
            }
        }
//        System.out.println();
        this.borderOfTheLastMap += 3 * (Window.sourceX / 2) / 4;
    }
}
