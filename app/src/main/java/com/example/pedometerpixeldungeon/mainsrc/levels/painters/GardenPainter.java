package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Foliage;
import com.example.pedometerpixeldungeon.mainsrc.items.Honeypot;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Random;

public class GardenPainter extends Painter {

    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.HIGH_GRASS );
        fill( level, room, 2, Terrain.GRASS );

        room.entrance().set( Room.Door.Type.REGULAR );

        if (Random.Int( 2 ) == 0) {
            level.drop( new Honeypot(), room.random() );
        } else {
            int bushes = (Random.Int( 5 ) == 0 ? 2 : 1);
            for (int i=0; i < bushes; i++) {
                int pos = room.random();
                set( level, pos, Terrain.GRASS );
//                level.plant( new Sungrass.Seed(), pos );
            }
        }

        Foliage light = (Foliage)level.blobs.get( Foliage.class );
        if (light == null) {
            light = new Foliage();
        }
        for (int i=room.top + 1; i < room.bottom; i++) {
            for (int j=room.left + 1; j < room.right; j++) {
                light.seed( j + Level.WIDTH * i, 1 );
            }
        }
        level.blobs.put( Foliage.class, light );
    }
}
