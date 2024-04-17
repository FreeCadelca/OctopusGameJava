package controller;

import java.util.ArrayList;
import java.util.Random;

public class PatternOfMap {
    private static final int[][][] patternEasy = {
            {
                    {0, 0, 1, 0, 0, 0},
                    {0, 1, 2, 0, 0, 0},
                    {0, 0, 1, 0, 2, 0}
//                    {0, 1, 1, 1, 1, 0}, //map for debug
//                    {0, 0, 0, 0, 0, 0},
//                    {0, 3, 3, 3, 3, 0}
            }, {
                    {0, 0, 0, 0, 0, 0},
                    {0, 1, 2, 1, 0, 0},
                    {0, 0, 2, 0, 1, 0}
            }
    };
    private static final int[][][] patternMedium = {
            {
                    {0, 2, 0, 0, 0, 0},
                    {0, 0, 1, 2, 1, 0},
                    {0, 2, 0, 0, 0, 0}
            }, {
                    {0, 1, 2, 0, 0, 0},
                    {0, 0, 2, 1, 2, 0},
                    {0, 0, 0, 0, 0, 0}
            }
    };
    private static final int[][] patternHard =
            {
                    {0, 1, 0, 2, 2, 0},
                    {0, 2, 0, 2, 0, 0},
                    {0, 2, 0, 1, 0, 0}
            };
    private static final int[][] patternNewOctopus =
            {
                    {0, 0, 0, 0, 0, 0},
                    {0, 2, 2, 2, 2, 0},
                    {0, 2, 2, 1, 3, 0}
            };

    public static int[][] getNewMap(int absoluteScore) {
        Random random = new Random();
        ArrayList<int[][]> potentialMaps = new ArrayList<>();
        potentialMaps.add(patternEasy[0]);
        potentialMaps.add(patternEasy[1]);
        if (absoluteScore > 25) {
            potentialMaps.add(patternMedium[0]);
            potentialMaps.add(patternMedium[1]);
        }
        if (absoluteScore > 50) {
            potentialMaps.add(patternHard);
        }
        if (absoluteScore > 75) {
//            if (random.nextInt(2) == 1) { //in order for the probability of this map pattern to be not 1 to 6, but 1 to 12
                potentialMaps.add(patternNewOctopus);
//            }
        }
        return potentialMaps.get(random.nextInt(potentialMaps.size()));
    }
}

