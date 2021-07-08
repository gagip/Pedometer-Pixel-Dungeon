package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Statue;
import com.example.pedometerpixeldungeon.mainsrc.items.keys.IronKey;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Point;

public class StatuePainter extends Painter {

    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY );

        Point c = room.center();
        int cx = c.x;
        int cy = c.y;

        Room.Door door = room.entrance();

        door.set( Room.Door.Type.LOCKED );
        level.addItemToSpawn( new IronKey() );

        if (door.x == room.left) {

            fill( level, room.right - 1, room.top + 1, 1, room.height() - 1 , Terrain.STATUE );
            cx = room.right - 2;

        } else if (door.x == room.right) {

            fill( level, room.left + 1, room.top + 1, 1, room.height() - 1 , Terrain.STATUE );
            cx = room.left + 2;

        } else if (door.y == room.top) {

            fill( level, room.left + 1, room.bottom - 1, room.width() - 1, 1 , Terrain.STATUE );
            cy = room.bottom - 2;

        } else if (door.y == room.bottom) {

            fill( level, room.left + 1, room.top + 1, room.width() - 1, 1 , Terrain.STATUE );
            cy = room.top + 2;

        }

        Statue statue = new Statue();
        statue.pos = cx + cy * Level.WIDTH;
        level.mobs.add( statue );
        Actor.occupyCell( statue );
    }
}
