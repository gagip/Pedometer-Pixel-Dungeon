package com.example.pedometerpixeldungeon.mainsrc.items.bags;

import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.keys.Key;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;

public class Keyring extends Bag {

    {
        name = "key ring";
        image = ItemSpriteSheet.KEYRING;

        size = 12;
    }

    @Override
    public boolean grab( Item item ) {
        return item instanceof Key;
    }

    @Override
    public int price() {
        return 50;
    }

    @Override
    public String info() {
        return
                "This is a copper key ring, that lets you keep all your keys " +
                        "separately from the rest of your belongings.";
    }
}
