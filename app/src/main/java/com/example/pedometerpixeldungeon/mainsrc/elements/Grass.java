package com.example.pedometerpixeldungeon.mainsrc.elements;

public class Grass extends Element {

    {
        type = Type.GRASS;
        strong.add(Type.WATER);
        week.add(Type.FIRE);
    }

    @Override
    public void effect(Type enemyType) {

    }
}
