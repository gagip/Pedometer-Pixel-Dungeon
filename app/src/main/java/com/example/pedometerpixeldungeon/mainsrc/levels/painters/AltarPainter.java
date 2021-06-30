package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.SacrificialFire;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Point;

public class AltarPainter extends Painter {

    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Dungeon.bossLevel( Dungeon.depth + 1 ) ? Terrain.HIGH_GRASS : Terrain.CHASM );

        Point c = room.center();
        Room.Door door = room.entrance();
        if (door.x == room.left || door.x == room.right) {
            Point p = drawInside( level, room, door, Math.abs( door.x - c.x ) - 2, Terrain.EMPTY_SP );
            for (; p.y != c.y; p.y += p.y < c.y ? +1 : -1) {
                set( level, p, Terrain.EMPTY_SP );
            }
        } else {
            Point p = drawInside( level, room, door, Math.abs( door.y - c.y ) - 2, Terrain.EMPTY_SP );
            for (; p.x != c.x; p.x += p.x < c.x ? +1 : -1) {
                set( level, p, Terrain.EMPTY_SP );
            }
        }

        fill( level, c.x - 1, c.y - 1, 3, 3, Terrain.EMBERS );
        set( level, c, Terrain.PEDESTAL );

        SacrificialFire fire = (SacrificialFire)level.blobs.get( SacrificialFire.class );
        if (fire == null) {
            fire = new SacrificialFire();
        }
        fire.seed( c.x + c.y * Level.WIDTH, 5 + Dungeon.depth * 5 );
        level.blobs.put( SacrificialFire.class, fire );

        door.set( Room.Door.Type.EMPTY );
    }
}