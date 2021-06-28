package com.example.pedometerpixeldungeon.mainsrc.levels;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.utils.Random;
import com.example.pedometerpixeldungeon.utils.Rect;

import java.util.HashSet;

public class SewerLevel extends RegularLevel {

    {
        color1 = 0x48763c;
        color2 = 0x59994a;
    }

    @Override
    public String tilesTex() {
        return Assets.TILES_SEWERS;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_SEWERS;
    }

    protected boolean[] water() {
        return Patch.generate( feeling == Feeling.WATER ? 0.60f : 0.45f, 5 );
    }

    protected boolean[] grass() {
        return Patch.generate( feeling == Feeling.GRASS ? 0.60f : 0.40f, 4 );
    }

    protected boolean initRooms() {
        rooms = new HashSet<Room>();
        split( new Rect( 0, 0, WIDTH - 1, HEIGHT - 1 ) );

        if (rooms.size() < 8) {
            return false;
        }

        Room[] ra = rooms.toArray( new Room[0] );
        for (int i=0; i < ra.length-1; i++) {
            for (int j=i+1; j < ra.length; j++) {
                ra[i].addNeigbour( ra[j] );
            }
        }

        return true;
    }

    @Override
    protected void decorate() {

        for (int i=0; i < WIDTH; i++) {
            if (map[i] == Terrain.WALL &&
                    map[i + WIDTH] == Terrain.WATER &&
                    Random.Int( 4 ) == 0) {

                map[i] = Terrain.WALL_DECO;
            }
        }

        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.WALL &&
                    map[i - WIDTH] == Terrain.WALL &&
                    map[i + WIDTH] == Terrain.WATER &&
                    Random.Int( 2 ) == 0) {

                map[i] = Terrain.WALL_DECO;
            }
        }

        for (int i=WIDTH + 1; i < LENGTH - WIDTH - 1; i++) {
            if (map[i] == Terrain.EMPTY) {

                int count =
                        (map[i + 1] == Terrain.WALL ? 1 : 0) +
                                (map[i - 1] == Terrain.WALL ? 1 : 0) +
                                (map[i + WIDTH] == Terrain.WALL ? 1 : 0) +
                                (map[i - WIDTH] == Terrain.WALL ? 1 : 0);

                if (Random.Int( 16 ) < count * count) {
                    map[i] = Terrain.EMPTY_DECO;
                }
            }
        }

        while (true) {
            int pos = roomEntrance.random();
            if (pos != entrance) {
                map[pos] = Terrain.SIGN;
                break;
            }
        }
    }

    @Override
    protected void createMobs() {
        super.createMobs();

//        Ghost.Quest.spawn( this );
    }

    @Override
    protected void createItems() {

    }
}
