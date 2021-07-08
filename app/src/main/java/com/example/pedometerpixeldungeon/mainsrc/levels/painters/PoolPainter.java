package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Piranha;
import com.example.pedometerpixeldungeon.mainsrc.items.Generator;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfInvisibility;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Random;

public class PoolPainter extends Painter {

    private static final int NPIRANHAS	= 3;

    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.WATER );

        Room.Door door = room.entrance();
        door.set( Room.Door.Type.REGULAR );

        int x = -1;
        int y = -1;
        if (door.x == room.left) {

            x = room.right - 1;
            y = room.top + room.height() / 2;

        } else if (door.x == room.right) {

            x = room.left + 1;
            y = room.top + room.height() / 2;

        } else if (door.y == room.top) {

            x = room.left + room.width() / 2;
            y = room.bottom - 1;

        } else if (door.y == room.bottom) {

            x = room.left + room.width() / 2;
            y = room.top + 1;

        }

        int pos = x + y * Level.WIDTH;
        level.drop( prize( level ), pos ).type =
                Random.Int( 3 ) == 0 ? Heap.Type.CHEST : Heap.Type.HEAP;
        set( level, pos, Terrain.PEDESTAL );

        level.addItemToSpawn( new PotionOfInvisibility() );

        for (int i=0; i < NPIRANHAS; i++) {
            Piranha piranha = new Piranha();
            do {
                piranha.pos = room.random();
            } while (level.map[piranha.pos] != Terrain.WATER|| Actor.findChar( piranha.pos ) != null);
            level.mobs.add( piranha );
            Actor.occupyCell( piranha );
        }
    }

    private static Item prize( Level level ) {

                Item prize = level.itemToSpanAsPrize();
                if (prize != null) {
                    return prize;
                }

                prize = Generator.random( Random.oneOf(
                        Generator.Category.WEAPON,
                        Generator.Category.ARMOR
                ) );

                for (int i=0; i < 4; i++) {
                    Item another = Generator.random( Random.oneOf(
                            Generator.Category.WEAPON,
                            Generator.Category.ARMOR
            ) );
            if (another.level() > prize.level()) {
                prize = another;
            }
        }

        return prize;
    }
}
