package com.example.pedometerpixeldungeon.mainsrc.levels.items;

import com.example.pedometerpixeldungeon.utils.Bundlable;
import com.example.pedometerpixeldungeon.utils.Bundle;

import java.util.LinkedList;

public class Heap implements Bundlable {
    private static final String TXT_MIMIC = "This is a mimic!";

    private static final int SEEDS_TO_POTION = 3;

    private static final float FADE_TIME = 0.6f;

    public enum Type {
        HEAP,
        FOR_SALE,
        CHEST,
        LOCKED_CHEST,
        CRYSTAL_CHEST,
        TOMB,
        SKELETON,
        MIMIC,
        HIDDEN
    }
    public Type type = Type.HEAP;

    public int pos = 0;

//    public ItemSprite sprite;

    public LinkedList<Item> items = new LinkedList<Item>();

    public int image() {
        switch (type) {
            case HEAP:
            case FOR_SALE:
//                return size() > 0 ? items.peek().image() : 0;
            case CHEST:
            case MIMIC:
//                return ItemSpriteSheet.CHEST;
            case LOCKED_CHEST:
//                return ItemSpriteSheet.LOCKED_CHEST;
            case CRYSTAL_CHEST:
//                return ItemSpriteSheet.CRYSTAL_CHEST;
            case TOMB:
//                return ItemSpriteSheet.TOMB;
            case SKELETON:
//                return ItemSpriteSheet.BONES;
            case HIDDEN:
//                return ItemSpriteSheet.HIDDEN;
            default:
                return 0;
        }
    }

    public void drop( Item item ) {

//        if (item.stackable) {
//
//            Class<?> c = item.getClass();
//            for (Item i : items) {
//                if (i.getClass() == c) {
//                    i.quantity += item.quantity;
//                    item = i;
//                    break;
//                }
//            }
//            items.remove( item );
//
//        }
//
//        if (item instanceof Dewdrop) {
//            items.add( item );
//        } else {
//            items.addFirst( item );
//        }
//
//        if (sprite != null) {
//            sprite.view( image(), glowing() );
//        }
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {

    }

    @Override
    public void storeInBundle(Bundle bundle) {

    }
}
