package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.items.keys.IronKey;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Random;

public class VaultPainter extends Painter {
    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY_SP );
        fill( level, room, 2, Terrain.EMPTY );

        int cx = (room.left + room.right) / 2;
        int cy = (room.top + room.bottom) / 2;
        int c = cx + cy * Level.WIDTH;

        switch (Random.Int( 3 )) {

//            case 0:
//                level.drop( prize( level ), c ).type = Type.LOCKED_CHEST;
//                level.addItemToSpawn( new GoldenKey() );
//                break;
//
//            case 1:
//                Item i1, i2;
//                do {
//                    i1 = prize( level );
//                    i2 = prize( level );
//                } while (i1.getClass() == i2.getClass());
//                level.drop( i1, c ).type = Type.CRYSTAL_CHEST;
//                level.drop( i2, c + Level.NEIGHBOURS8[Random.Int( 8 )]).type = Type.CRYSTAL_CHEST;
//                level.addItemToSpawn( new GoldenKey() );
//                break;
//
//            case 2:
//                level.drop( prize( level ), c );
//                set( level, c, Terrain.PEDESTAL );
//                break;
        }

        room.entrance().set( Room.Door.Type.LOCKED );
        level.addItemToSpawn( new IronKey() );
    }

//    private static Item prize( Level level ) {
//        return Generator.random( Random.oneOf(
//                Generator.Category.WAND,
//                Generator.Category.RING
//        ) );
//    }
}
