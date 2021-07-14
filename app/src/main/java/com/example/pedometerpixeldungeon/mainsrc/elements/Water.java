package com.example.pedometerpixeldungeon.mainsrc.elements;

public class Water extends Element {

    {
        type = Element.Type.WATER;
        strong.add(Type.FIRE);
        week.add(Type.GRASS);
    }

    @Override
    public void effect(Type enemyType) {

    }
}
