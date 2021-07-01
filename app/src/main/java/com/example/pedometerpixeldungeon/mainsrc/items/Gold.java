package com.example.pedometerpixeldungeon.mainsrc.items;

import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;

public class Gold extends Item {

    private static final String TXT_COLLECT	= "Collect gold coins to spend them later in a shop.";
    private static final String TXT_INFO	= "A pile of %d gold coins. " + TXT_COLLECT;
    private static final String TXT_INFO_1	= "One gold coin. " + TXT_COLLECT;
    private static final String TXT_VALUE	= "%+d";

    {
        name = "gold";
        image = ItemSpriteSheet.GOLD;
        stackable = true;
    }

    public Gold() {
        this( 1 );
    }

    public Gold( int value ) {
        this.quantity = value;
    }

}
