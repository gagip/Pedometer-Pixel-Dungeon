package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.items.Gold;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Random;

public class RatKingPainter extends Painter {

    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY_SP );

        Room.Door entrance = room.entrance();
        entrance.set( Room.Door.Type.HIDDEN );
        int door = entrance.x + entrance.y * Level.WIDTH;

        for (int i=room.left + 1; i < room.right; i++) {
            addChest( level, (room.top + 1) * Level.WIDTH + i, door );
            addChest( level, (room.bottom - 1) * Level.WIDTH + i, door );
        }

        for (int i=room.top + 2; i < room.bottom - 1; i++) {
            addChest( level, i * Level.WIDTH + room.left + 1, door );
            addChest( level, i * Level.WIDTH + room.right - 1, door );
        }

        while (true) {
            Heap chest = level.heaps.get( room.random() );
            if (chest != null) {
                chest.type = Heap.Type.MIMIC;
                break;
            }
        }

//        RatKing king = new RatKing();
//        king.pos = room.random( 1 );
//        level.mobs.add( king );
    }

    private static void addChest( Level level, int pos, int door ) {

        if (pos == door - 1 ||
                pos == door + 1 ||
                pos == door - Level.WIDTH ||
                pos == door + Level.WIDTH) {
            return;
        }

        Item prize;
        switch (Random.Int( 10 )) {
//            case 0:
//                prize = Generator.random( Generator.Category.WEAPON );
//                if (prize instanceof MissileWeapon) {
//                    prize.quantity( 1 );
//                } else {
//                    prize.degrade( Random.Int( 3 ) );
//                }
//                break;
//            case 1:
//                prize = Generator.random( Generator.Category.ARMOR ).degrade( Random.Int( 3 ) );
//                break;
            default:
                prize = new Gold( Random.IntRange( 1, 5 ) );
                break;
        }

        level.drop( prize, pos ).type = Heap.Type.CHEST;
    }
}