package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;

public class ExitPainter extends Painter {

    public static void paint(Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY );

        for (Room.Door door : room.connected.values()) {
            door.set( Room.Door.Type.REGULAR );
        }

        level.exit = room.random( 1 );
        set( level, level.exit, Terrain.EXIT );
    }
}
