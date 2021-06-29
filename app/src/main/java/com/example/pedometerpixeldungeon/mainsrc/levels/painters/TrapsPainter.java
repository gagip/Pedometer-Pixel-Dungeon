package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Random;

public class TrapsPainter extends Painter {

    public static void paint( Level level, Room room ) {

        Integer traps[] = {
                Terrain.TOXIC_TRAP, Terrain.TOXIC_TRAP, Terrain.TOXIC_TRAP,
                Terrain.PARALYTIC_TRAP, Terrain.PARALYTIC_TRAP,
                !Dungeon.bossLevel( Dungeon.depth + 1 ) ? Terrain.CHASM : Terrain.SUMMONING_TRAP };
        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Random.element( traps ) );

        Room.Door door = room.entrance();
        door.set( Room.Door.Type.REGULAR );

        int lastRow = level.map[room.left + 1 + (room.top + 1) * Level.WIDTH] == Terrain.CHASM ? Terrain.CHASM : Terrain.EMPTY;

        int x = -1;
        int y = -1;
        if (door.x == room.left) {
            x = room.right - 1;
            y = room.top + room.height() / 2;
            fill( level, x, room.top + 1, 1, room.height() - 1 , lastRow );
        } else if (door.x == room.right) {
            x = room.left + 1;
            y = room.top + room.height() / 2;
            fill( level, x, room.top + 1, 1, room.height() - 1 , lastRow );
        } else if (door.y == room.top) {
            x = room.left + room.width() / 2;
            y = room.bottom - 1;
            fill( level, room.left + 1, y, room.width() - 1, 1 , lastRow );
        } else if (door.y == room.bottom) {
            x = room.left + room.width() / 2;
            y = room.top + 1;
            fill( level, room.left + 1, y, room.width() - 1, 1 , lastRow );
        }

        int pos = x + y * Level.WIDTH;
        if (Random.Int( 3 ) == 0) {
            if (lastRow == Terrain.CHASM) {
                set( level, pos, Terrain.EMPTY );
            }
            level.drop( prize( level ), pos ).type = Heap.Type.CHEST;
        } else {
            set( level, pos, Terrain.PEDESTAL );
            level.drop( prize( level ), pos );
        }

//        level.addItemToSpawn( new PotionOfLevitation() );
    }

    private static Item prize( Level level ) {

        Item prize = level.itemToSpanAsPrize();
        if (prize != null) {
            return prize;
        }

//        prize = Generator.random( Random.oneOf(
//                Generator.Category.WEAPON,
//                Generator.Category.ARMOR
//        ) );

        for (int i=0; i < 3; i++) {
//            Item another = Generator.random( Random.oneOf(
//                    Generator.Category.WEAPON,
//                    Generator.Category.ARMOR
//            ) );
//            if (another.level() > prize.level()) {
//                prize = another;
//            }
        }

        return prize;
    }
}
