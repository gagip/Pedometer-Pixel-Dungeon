package com.example.pedometerpixeldungeon.mainsrc.elements;

import java.util.ArrayList;

public class Element {

    public enum Type {
        NONE,
        FIRE,
        WATER,
        GRASS,
        LIGHT,
        DARKNESS
    }

    public enum Matchup {
        STRONG,
        NORMAL,
        WEEK
    }

    public Type type = Type.NONE;

    public Matchup compareCounter(Type type1, Type type2) {
        ArrayList<Type> strong = new ArrayList<Type>();
        ArrayList<Type> week = new ArrayList<Type>();

        switch (type1) {
            case FIRE:
                strong.add(Type.GRASS);
                week.add(Type.WATER);
                break;
            case WATER:
                strong.add(Type.FIRE);
                week.add(Type.GRASS);
                break;
            case GRASS:
                strong.add(Type.WATER);
                week.add(Type.FIRE);
                break;
            case LIGHT:
                strong.add(Type.DARKNESS);
                break;
            case DARKNESS:
                week.add(Type.LIGHT);
                break;
        }

        if (strong.contains(type2))
            return Matchup.STRONG;
        else if (week.contains(type2))
            return Matchup.WEEK;
        else
            return Matchup.NORMAL;
    }

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

    public float attackCorrection(Type type1, Type type2) {
        return attackCorrection(compareCounter(type1, type2));
    }

    public void effect(Type enemyType) {
        if (compareCounter(type, enemyType) == Matchup.WEEK)
            return;
    }
}
