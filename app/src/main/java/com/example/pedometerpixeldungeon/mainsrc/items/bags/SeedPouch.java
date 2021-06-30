package com.example.pedometerpixeldungeon.mainsrc.items.bags;

import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.plants.Plant;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;

public class SeedPouch extends Bag {

    {
        name = "seed pouch";
        image = ItemSpriteSheet.POUCH;

        size = 8;
    }

    @Override
    public boolean grab( Item item ) {
        return item instanceof Plant.Seed;
    }

    @Override
    public int price() {
        return 50;
    }

    @Override
    public String info() {
        return
                "This small velvet pouch allows you to store any number of seeds in it. Very convenient.";
    }
}
