package com.example.pedometerpixeldungeon.mainsrc.items.bags;

import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.Scroll;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;

public class ScrollHolder extends Bag {

    {
        name = "scroll holder";
        image = ItemSpriteSheet.HOLDER;

        size = 12;
    }

    @Override
    public boolean grab( Item item ) {
        return item instanceof Scroll;
    }

    @Override
    public int price() {
        return 50;
    }

    @Override
    public String info() {
        return
                "You can place any number of scrolls into this tubular container. " +
                        "It saves room in your backpack and protects scrolls from fire.";
    }
}
