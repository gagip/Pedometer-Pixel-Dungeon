package com.example.pedometerpixeldungeon.mainsrc.levels;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.DungeonTilemap;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.noosa.Game;
import com.example.pedometerpixeldungeon.noosa.Scene;
import com.example.pedometerpixeldungeon.noosa.particles.Emitter;
import com.example.pedometerpixeldungeon.noosa.particles.PixelParticle;
import com.example.pedometerpixeldungeon.utils.ColorMath;
import com.example.pedometerpixeldungeon.utils.PointF;
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

    @Override
    public void addVisuals( Scene scene ) {
        super.addVisuals( scene );
        addVisuals( this, scene );
    }

    public static void addVisuals( Level level, Scene scene ) {
        for (int i=0; i < LENGTH; i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                scene.add( new Sink( i ) );
            }
        }
    }

    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return "Murky water";
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.EMPTY_DECO:
                return "Wet yellowish moss covers the floor.";
            case Terrain.BOOKSHELF:
                return "The bookshelf is packed with cheap useless books. Might it burn?";
            default:
                return super.tileDesc( tile );
        }
    }

    private static class Sink extends Emitter {

        private int pos;
        private float rippleDelay = 0;

        private static final Emitter.Factory factory = new Factory() {

            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                WaterParticle p = (WaterParticle)emitter.recycle( WaterParticle.class );
                p.reset( x, y );
            }
        };

        public Sink( int pos ) {
            super();

            this.pos = pos;

            PointF p = DungeonTilemap.tileCenterToWorld( pos );
            pos( p.x - 2, p.y + 1, 4, 0 );

            pour( factory, 0.05f );
        }

        @Override
        public void update() {
            if (visible = Dungeon.visible[pos]) {

                super.update();

                if ((rippleDelay -= Game.elapsed) <= 0) {
                    GameScene.ripple( pos + WIDTH ).y -= DungeonTilemap.SIZE / 2;
                    rippleDelay = Random.Float( 0.2f, 0.3f );
                }
            }
        }
    }

    public static final class WaterParticle extends PixelParticle {

        public WaterParticle() {
            super();

            acc.y = 50;
            am = 0.5f;

            color( ColorMath.random( 0xb6ccc2, 0x3b6653 ) );
            size( 2 );
        }

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            speed.set( Random.Float( -2, +2 ), 0 );

            left = lifespan = 0.5f;
        }
    }
}
