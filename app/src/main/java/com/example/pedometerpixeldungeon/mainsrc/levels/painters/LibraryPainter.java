package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.items.keys.IronKey;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Point;
import com.example.pedometerpixeldungeon.utils.Random;

public class LibraryPainter extends Painter {
    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY );

        Room.Door entrance = room.entrance();
        Point a = null;
        Point b = null;

        if (entrance.x == room.left) {
            a = new Point( room.left+1, entrance.y-1 );
            b = new Point( room.left+1, entrance.y+1 );
            fill( level, room.right - 1, room.top + 1, 1, room.height() - 1 , Terrain.BOOKSHELF );
        } else if (entrance.x == room.right) {
            a = new Point( room.right-1, entrance.y-1 );
            b = new Point( room.right-1, entrance.y+1 );
            fill( level, room.left+1, room.top + 1, 1, room.height() - 1 , Terrain.BOOKSHELF );
        } else if (entrance.y == room.top) {
            a = new Point( entrance.x+1, room.top+1 );
            b = new Point( entrance.x-1, room.top+1 );
            fill( level, room.left + 1, room.bottom-1, room.width() - 1, 1 , Terrain.BOOKSHELF );
        } else if (entrance.y == room.bottom) {
            a = new Point( entrance.x+1, room.bottom-1 );
            b = new Point( entrance.x-1, room.bottom-1 );
            fill( level, room.left + 1, room.top+1, room.width() - 1, 1 , Terrain.BOOKSHELF );
        }
        if (a != null && level.map[a.x + a.y * Level.WIDTH] == Terrain.EMPTY) {
            set( level, a, Terrain.STATUE );
        }
        if (b != null && level.map[b.x + b.y * Level.WIDTH] == Terrain.EMPTY) {
            set( level, b, Terrain.STATUE );
        }

        int n = Random.IntRange( 2, 3 );
        for (int i=0; i < n; i++) {
            int pos;
            do {
                pos = room.random();
            } while (level.map[pos] != Terrain.EMPTY || level.heaps.get( pos ) != null);
//            level.drop( prize( level), pos );
        }

        entrance.set( Room.Door.Type.LOCKED );
        level.addItemToSpawn( new IronKey() );
    }

//    private static Item prize( Level level ) {
//
//        Item prize = level.itemToSpanAsPrize();
//        if (prize instanceof Scroll) {
//            return prize;
//        } else if (prize != null) {
//            level.addItemToSpawn( prize );
//        }
//
//        return Generator.random( Generator.Category.SCROLL );
//    }
}
