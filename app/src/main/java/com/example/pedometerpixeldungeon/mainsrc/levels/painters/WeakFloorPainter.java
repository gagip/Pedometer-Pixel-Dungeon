package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Point;
import com.example.pedometerpixeldungeon.utils.Random;

public class WeakFloorPainter extends Painter {

    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.CHASM );

        Room.Door door = room.entrance();
        door.set( Room.Door.Type.REGULAR );

        if (door.x == room.left) {
            for (int i=room.top + 1; i < room.bottom; i++) {
                drawInside( level, room, new Point( room.left, i ), Random.IntRange( 1, room.width() - 2 ), Terrain.EMPTY_SP );
            }
        } else if (door.x == room.right) {
            for (int i=room.top + 1; i < room.bottom; i++) {
                drawInside( level, room, new Point( room.right, i ), Random.IntRange( 1, room.width() - 2 ), Terrain.EMPTY_SP );
            }
        } else if (door.y == room.top) {
            for (int i=room.left + 1; i < room.right; i++) {
                drawInside( level, room, new Point( i, room.top ), Random.IntRange( 1, room.height() - 2 ), Terrain.EMPTY_SP );
            }
        } else if (door.y == room.bottom) {
            for (int i=room.left + 1; i < room.right; i++) {
                drawInside( level, room, new Point( i, room.bottom ), Random.IntRange( 1, room.height() - 2 ), Terrain.EMPTY_SP );
            }
        }
    }
}
