package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Alchemy;
import com.example.pedometerpixeldungeon.mainsrc.items.Generator;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.keys.IronKey;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.Potion;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Point;
import com.example.pedometerpixeldungeon.utils.Random;

public class LaboratoryPainter extends Painter {
    public static void paint(Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY_SP );

        Room.Door entrance = room.entrance();

        Point pot = null;
        if (entrance.x == room.left) {
            pot = new Point( room.right-1, Random.Int( 2 ) == 0 ? room.top + 1 : room.bottom - 1 );
        } else if (entrance.x == room.right) {
            pot = new Point( room.left+1, Random.Int( 2 ) == 0 ? room.top + 1 : room.bottom - 1 );
        } else if (entrance.y == room.top) {
            pot = new Point( Random.Int( 2 ) == 0 ? room.left + 1 : room.right - 1, room.bottom-1 );
        } else if (entrance.y == room.bottom) {
            pot = new Point( Random.Int( 2 ) == 0 ? room.left + 1 : room.right - 1, room.top+1 );
        }
        set( level, pot, Terrain.ALCHEMY );

        Alchemy alchemy = new Alchemy();
        alchemy.seed( pot.x + Level.WIDTH * pot.y, 1 );
        level.blobs.put( Alchemy.class, alchemy );

        int n = Random.IntRange( 2, 3 );
        for (int i=0; i < n; i++) {
            int pos;
            do {
                pos = room.random();
            } while (
                    level.map[pos] != Terrain.EMPTY_SP ||
                            level.heaps.get( pos ) != null);
            level.drop( prize( level ), pos );
        }

        entrance.set( Room.Door.Type.LOCKED );
        level.addItemToSpawn( new IronKey() );
    }

    private static Item prize(Level level ) {

        Item prize = level.itemToSpanAsPrize();
        if (prize instanceof Potion) {
            return prize;
        } else if (prize != null) {
            level.addItemToSpawn( prize );
        }

        return Generator.random( Generator.Category.POTION );
    }
}
