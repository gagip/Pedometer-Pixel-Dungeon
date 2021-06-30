package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.items.Generator;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.keys.IronKey;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Point;
import com.example.pedometerpixeldungeon.utils.Random;

public class PitPainter extends Painter {
    public static void paint(Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY );

        Room.Door entrance = room.entrance();
        entrance.set( Room.Door.Type.LOCKED );

        Point well = null;
        if (entrance.x == room.left) {
            well = new Point( room.right-1, Random.Int( 2 ) == 0 ? room.top + 1 : room.bottom - 1 );
        } else if (entrance.x == room.right) {
            well = new Point( room.left+1, Random.Int( 2 ) == 0 ? room.top + 1 : room.bottom - 1 );
        } else if (entrance.y == room.top) {
            well = new Point( Random.Int( 2 ) == 0 ? room.left + 1 : room.right - 1, room.bottom-1 );
        } else if (entrance.y == room.bottom) {
            well = new Point( Random.Int( 2 ) == 0 ? room.left + 1 : room.right - 1, room.top+1 );
        }
        set( level, well, Terrain.EMPTY_WELL );

        int remains = room.random();
        while (level.map[remains] == Terrain.EMPTY_WELL) {
            remains = room.random();
        }

        level.drop( new IronKey(), remains ).type = Heap.Type.SKELETON;

        if (Random.Int( 5 ) == 0) {
            level.drop( Generator.random( Generator.Category.RING ), remains );
        } else {
            level.drop( Generator.random( Random.oneOf(
                    Generator.Category.WEAPON,
                    Generator.Category.ARMOR
            ) ), remains );
        }

        int n = Random.IntRange( 1, 2 );
        for (int i=0; i < n; i++) {
            level.drop( prize( level ), remains );
        }
    }

    private static Item prize( Level level ) {

        Item prize = level.itemToSpanAsPrize();
        if (prize != null) {
            return prize;
        }

        return Generator.random( Random.oneOf(
                Generator.Category.POTION,
                Generator.Category.SCROLL,
                Generator.Category.FOOD,
                Generator.Category.GOLD
        ) );
    }
}
