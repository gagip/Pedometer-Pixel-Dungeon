package com.example.pedometerpixeldungeon.mainsrc.elements;

public class Light extends Element {

    {
        type = Type.LIGHT;
        strong.add(Type.DARK);
    }

    @Override
    public void effect(Type enemyType) {

    }
}
