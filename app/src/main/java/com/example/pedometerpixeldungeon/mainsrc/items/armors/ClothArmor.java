package com.example.pedometerpixeldungeon.mainsrc.items.armors;

import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;

public class ClothArmor extends Armor {

    {
        name = "cloth armor";
        image = ItemSpriteSheet.ARMOR_CLOTH;
    }

    public ClothArmor() {
        super( 1 );
    }

    @Override
    public String desc() {
        return "This lightweight armor offers basic protection.";
    }
}