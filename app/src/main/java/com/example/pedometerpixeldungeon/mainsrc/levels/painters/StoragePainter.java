package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;

public class StoragePainter extends Painter {

//    public static void paint( Level level, Room room ) {
//
//        final int floor = Terrain.EMPTY_SP;
//
//        fill( level, room, Terrain.WALL );
//        fill( level, room, 1, floor );
//
//        int n = Random.IntRange( 3, 4 );
//        for (int i=0; i < n; i++) {
//            int pos;
//            do {
//                pos = room.random();
//            } while (level.map[pos] != floor);
//            level.drop( prize( level ), pos );
//        }
//
//        room.entrance().set( Room.Door.Type.BARRICADE );
//        level.addItemToSpawn( new PotionOfLiquidFlame() );
//    }

//    private static Item prize( Level level ) {
//
//        Item prize = level.itemToSpanAsPrize();
//        if (prize != null) {
//            return prize;
//        }
//
//        return Generator.random( Random.oneOf(
//                Generator.Category.POTION,
//                Generator.Category.SCROLL,
//                Generator.Category.FOOD,
//                Generator.Category.GOLD,
//                Generator.Category.MISC
//        ) );
//    }
}