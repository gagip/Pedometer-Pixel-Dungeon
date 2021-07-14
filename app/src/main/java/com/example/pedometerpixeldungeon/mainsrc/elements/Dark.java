package com.example.pedometerpixeldungeon.mainsrc.elements;

public class Dark extends Element {

    {
        type = Type.DARK;
        strong.add(Type.LIGHT);
    }

    @Override
    public void effect(Type enemyType) {

    }
}
