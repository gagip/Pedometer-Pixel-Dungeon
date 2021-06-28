package com.example.pedometerpixeldungeon.mainsrc.items.keys;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;

public class IronKey extends Key {
    private static final String TXT_FROM_DEPTH = "iron key from depth %d";

    public static int curDepthQuantity = 0;

    {
        name = "iron key";
//        image = ItemSpriteSheet.IRON_KEY;
    }

//    @Override
//    public boolean collect( Bag bag ) {
//        boolean result = super.collect( bag );
//        if (result && depth == Dungeon.depth && Dungeon.hero != null) {
//            Dungeon.hero.belongings.countIronKeys();
//        }
//        return result;
//    }

    @Override
    public void onDetach() {
        if (depth == Dungeon.depth) {
//            Dungeon.hero.belongings.countIronKeys();
        }
    }


    @Override
    public String toString() {
        return Utils.format( TXT_FROM_DEPTH, depth );
    }

    @Override
    public String info() {
        return
                "The notches on this ancient iron key are well worn; its leather lanyard " +
                        "is battered by age. What door might it open?";
    }
}
