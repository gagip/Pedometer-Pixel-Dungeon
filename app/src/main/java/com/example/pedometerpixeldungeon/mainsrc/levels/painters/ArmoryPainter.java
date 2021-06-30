package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.items.Bomb;
import com.example.pedometerpixeldungeon.mainsrc.items.Generator;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.keys.IronKey;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Point;
import com.example.pedometerpixeldungeon.utils.Random;

public class ArmoryPainter extends Painter {

    public static void paint(Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY );

        Room.Door entrance = room.entrance();
        Point statue = null;
        if (entrance.x == room.left) {
            statue = new Point( room.right-1, Random.Int( 2 ) == 0 ? room.top+1 : room.bottom-1 );
        } else if (entrance.x == room.right) {
            statue = new Point( room.left+1, Random.Int( 2 ) == 0 ? room.top+1 : room.bottom-1 );
        } else if (entrance.y == room.top) {
            statue = new Point( Random.Int( 2 ) == 0 ? room.left+1 : room.right-1, room.bottom-1 );
        } else if (entrance.y == room.bottom) {
            statue = new Point( Random.Int( 2 ) == 0 ? room.left+1 : room.right-1, room.top+1 );
        }
        if (statue != null) {
            set( level, statue, Terrain.STATUE );
        }

        int n = 3 + (Random.Int( 4 ) == 0 ? 1 : 0);
        for (int i=0; i < n; i++) {
            int pos;
            do {
                pos = room.random();
            } while (level.map[pos] != Terrain.EMPTY || level.heaps.get( pos ) != null);
            level.drop( prize( level ), pos );
        }

        entrance.set( Room.Door.Type.LOCKED );
        level.addItemToSpawn( new IronKey() );
    }


    private static Item prize(Level level ) {
        return Random.Int( 6 ) == 0 ?
                new Bomb().random() :
                Generator.random( Random.oneOf(
                        Generator.Category.ARMOR,
                        Generator.Category.WEAPON
                ) );
    }
}
