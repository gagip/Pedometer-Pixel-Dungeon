package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.items.Gold;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.items.keys.IronKey;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Random;

public class TreasuryPainter extends Painter {
    public static void paint(Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY );

        set( level, room.center(), Terrain.STATUE );

        Heap.Type heapType = Random.Int( 2 ) == 0 ? Heap.Type.CHEST : Heap.Type.HEAP;

        int n = Random.IntRange( 2, 3 );
        for (int i=0; i < n; i++) {
            int pos;
            do {
                pos = room.random();
            } while (level.map[pos] != Terrain.EMPTY || level.heaps.get( pos ) != null);
            level.drop( new Gold().random(), pos ).type = (i == 0 && heapType == Heap.Type.CHEST ? Heap.Type.MIMIC : heapType);
        }

        if (heapType == Heap.Type.HEAP) {
            for (int i=0; i < 6; i++) {
                int pos;
                do {
                    pos = room.random();
                } while (level.map[pos] != Terrain.EMPTY);
                level.drop( new Gold( Random.IntRange( 1, 3 ) ), pos );
            }
        }

        room.entrance().set( Room.Door.Type.LOCKED );
        level.addItemToSpawn( new IronKey() );
    }
}
