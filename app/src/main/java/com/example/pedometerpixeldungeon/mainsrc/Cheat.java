package com.example.pedometerpixeldungeon.mainsrc;

import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;

/**
 * 개발 테스트 편의를 위해 개발된 치트 모드입니다.
 * @author gagip
 */
public class Cheat {
    private static boolean debugMode = false;
    public static boolean mapCheat = false;
    public static boolean statCheat = false;
    public static boolean destoryMobCheat = false;

    private static boolean isHeroStatCheat = false;

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean debugMode) {
        Cheat.debugMode = debugMode;
    }

    public static void onStatCheat(Hero hero) {
        if (hero != null) {
            if (statCheat & !isHeroStatCheat) {
                hero.HP += 9999;
                hero.HT += 9999;
                hero.STR += 9999;
                isHeroStatCheat = true;
            } else if (!statCheat & isHeroStatCheat) {
                hero.HP -= 9999;
                hero.HT -= 9999;
                hero.STR -= 9999;
                isHeroStatCheat = false;
            }
        }
    }
}

