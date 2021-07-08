package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.npcs.Blacksmith;
import com.example.pedometerpixeldungeon.mainsrc.items.Generator;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Random;

public class BlacksmithPainter extends Painter {
    public static void paint(Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.FIRE_TRAP );
        fill( level, room, 2, Terrain.EMPTY_SP );

        for (int i=0; i < 2; i++) {
            int pos;
            do {
                pos = room.random();
            } while (level.map[pos] != Terrain.EMPTY_SP);
            level.drop(
                    Generator.random( Random.oneOf(
                            Generator.Category.ARMOR,
                            Generator.Category.WEAPON
                    ) ), pos );
        }

        for (Room.Door door : room.connected.values()) {
            door.set( Room.Door.Type.UNLOCKED );
            drawInside( level, room, door, 1, Terrain.EMPTY );
        }

        Blacksmith npc = new Blacksmith();
        do {
            npc.pos = room.random( 1 );
        } while (level.heaps.get( npc.pos ) != null);
        level.mobs.add( npc );
        Actor.occupyCell( npc );
    }
}
