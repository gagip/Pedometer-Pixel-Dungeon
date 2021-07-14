package com.example.pedometerpixeldungeon.mainsrc.elements;

import java.util.ArrayList;

/**
 * 원소 시스템
 * @author gagip
 */
public abstract class Element {

    // 원소 종류
    public enum Type {
        NONE,
        FIRE,
        WATER,
        GRASS,
        LIGHT,
        DARK
    }

    // 상성 결과
    public enum Matchup {
        STRONG,
        NORMAL,
        WEEK
    }

    public Type type = Type.NONE;

    protected ArrayList<Type> strong = new ArrayList<Type>();
    protected ArrayList<Type> week = new ArrayList<Type>();

    /**
     * 상성 비교 메소드
     * @param type
     * @return type1이 type2에 대한 상성 관계
     */
    public Matchup compareCounter(Type type) {
        if (strong.contains(type))
            return Matchup.STRONG;
        else if (week.contains(type))
            return Matchup.WEEK;
        else
            return Matchup.NORMAL;
    }


    /**
     * 상성 관계에 따른 데미지 보정 수치
     * @param matchup
     * @return
     */
    public float attackCorrection(Matchup matchup) {
        switch (matchup) {
            case NORMAL:
                return 1f;
            case STRONG:
                return 1.5f;
            case WEEK:
                return 0.75f;
        }
        return 1f;
    }

    public abstract void effect(Type enemyType);


    /**
     * 상성 시스템 실행
     * @param damage
     * @param enemyType
     * @return
     */
    public int execute(int damage, Type enemyType) {
        // 상성 관계 파악
        Matchup matchup = compareCounter(enemyType);

        // 데미지 보정
        int resultDamage = (int) (damage * attackCorrection(matchup));

        // 상성 유리할 시 특수스킬 발동
        if (compareCounter(enemyType) == Matchup.STRONG){
            effect(enemyType);
        }

        return resultDamage;
    }
}
