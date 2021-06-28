package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;

public class EntrancePainter extends Painter {

    public static void paint(Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY );

        for (Room.Door door : room.connected.values()) {
            door.set( Room.Door.Type.REGULAR );
        }

        level.entrance = room.random( 1 );
        set( level, level.entrance, Terrain.ENTRANCE );
    }
}
