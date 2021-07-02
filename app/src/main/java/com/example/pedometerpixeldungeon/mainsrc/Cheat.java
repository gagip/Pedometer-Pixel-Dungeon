package com.example.pedometerpixeldungeon.mainsrc;

/**
 * 개발 테스트 편의를 위해 개발된 치트 모드입니다.
 * @author gagip
 */
public class Cheat {
    private static boolean debugMode = false;
    public static boolean mapCheat = false;
    public static boolean statCheat = false;
    public static boolean destoryMobCheat = false;


    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean debugMode) {
        Cheat.debugMode = debugMode;
    }
}

