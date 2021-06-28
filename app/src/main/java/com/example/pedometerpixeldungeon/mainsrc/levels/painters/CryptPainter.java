package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.items.keys.IronKey;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Point;

public class CryptPainter extends Painter {
    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY );

        Point c = room.center();
        int cx = c.x;
        int cy = c.y;

        Room.Door entrance = room.entrance();

        entrance.set( Room.Door.Type.LOCKED );
        level.addItemToSpawn( new IronKey() );

        if (entrance.x == room.left) {
            set( level, new Point( room.right-1, room.top+1 ), Terrain.STATUE );
            set( level, new Point( room.right-1, room.bottom-1 ), Terrain.STATUE );
            cx = room.right - 2;
        } else if (entrance.x == room.right) {
            set( level, new Point( room.left+1, room.top+1 ), Terrain.STATUE );
            set( level, new Point( room.left+1, room.bottom-1 ), Terrain.STATUE );
            cx = room.left + 2;
        } else if (entrance.y == room.top) {
            set( level, new Point( room.left+1, room.bottom-1 ), Terrain.STATUE );
            set( level, new Point( room.right-1, room.bottom-1 ), Terrain.STATUE );
            cy = room.bottom - 2;
        } else if (entrance.y == room.bottom) {
            set( level, new Point( room.left+1, room.top+1 ), Terrain.STATUE );
            set( level, new Point( room.right-1, room.top+1 ), Terrain.STATUE );
            cy = room.top + 2;
        }

//        level.drop( prize( level ), cx + cy * Level.WIDTH ).type = Type.TOMB;
    }

//    private static Item prize( Level level ) {
//
//        Item prize = Generator.random( Generator.Category.ARMOR );
//
//        for (int i=0; i < 3; i++) {
//            Item another = Generator.random( Generator.Category.ARMOR );
//            if (another.level() > prize.level()) {
//                prize = another;
//            }
//        }
//
//        return prize;
//    }
}
