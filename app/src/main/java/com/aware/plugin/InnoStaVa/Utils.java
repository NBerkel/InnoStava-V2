package com.aware.plugin.InnoStaVa;

/**
 * Created by aku on 06/01/17.
 */

public class Utils {
    public static String getActivityName(int activity) {
        switch (activity) {
            case -1:
                return "Unknown";
            case 0:
                return "In vehicle";
            case 1:
                return "On bicycle";
            case 2:
                return "On foot";
            case 3:
                return "Tilting (e.g. phone in hand)";
            case 4:
                return "Unknown";
            case 7:
                return "Walking";
            case 8:
                return "Running";
            default:
                return "Unknown";
        }
    }
}
