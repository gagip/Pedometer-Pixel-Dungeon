package com.example.pedometerpixeldungeon.mainsrc.items.bags;

import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.wands.Wand;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;

public class WandHolster extends Bag {

    {
        name = "wand holster";
        image = ItemSpriteSheet.HOLSTER;

        size = 12;
    }

    @Override
    public boolean grab( Item item ) {
        return item instanceof Wand;
    }

    @Override
    public boolean collect( Bag container ) {
        if (super.collect( container )) {
            if (owner != null) {
                for (Item item : items) {
                    ((Wand)item).charge( owner );
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDetach( ) {
        for (Item item : items) {
            ((Wand)item).stopCharging();
        }
    }

    @Override
    public int price() {
        return 50;
    }

    @Override
    public String info() {
        return
                "This slim holder is made of leather of some exotic animal. " +
                        "It allows to compactly carry up to " + size + " wands.";
    }
}