package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Point;

public class MagicWellPainter extends Painter {

    private static final Class<?>[] WATERS =
            {
//                    WaterOfAwareness.class,
//                    WaterOfHealth.class,
//                    WaterOfTransmutation.class
            };

    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY );

        Point c = room.center();
        set( level, c.x, c.y, Terrain.WELL );

//        @SuppressWarnings("unchecked")
//        Class<? extends WellWater> waterClass =
//                (Class<? extends WellWater>) Random.element( WATERS );
//
//        WellWater water = (WellWater)level.blobs.get( waterClass );
//        if (water == null) {
//            try {
//                water = waterClass.newInstance();
//            } catch (Exception e) {
//                water = null;
//            }
//        }
//        water.seed( c.x + Level.WIDTH * c.y, 1 );
//        level.blobs.put( waterClass, water );

        room.entrance().set( Room.Door.Type.REGULAR );
    }
}