package com.example.pedometerpixeldungeon.mainsrc.sprites;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.DungeonTilemap;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.plants.Plant;
import com.example.pedometerpixeldungeon.noosa.Game;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.noosa.TextureFilm;

public class PlantSprite extends Image {

    private static final float DELAY = 0.2f;

    private static enum State {
        GROWING, NORMAL, WITHERING
    }
    private State state = State.NORMAL;
    private float time;

    private static TextureFilm frames;

    private int pos = -1;

    public PlantSprite() {
        super( Assets.PLANTS );

        if (frames == null) {
            frames = new TextureFilm( texture, 16, 16 );
        }

        origin.set( 8, 12 );
    }

    public PlantSprite( int image ) {
        this();
        reset( image );
    }

    public void reset( Plant plant ) {

        revive();

        reset( plant.image );
        alpha( 1f );

        pos = plant.pos;
        x = (pos % Level.WIDTH) * DungeonTilemap.SIZE;
        y = (pos / Level.WIDTH) * DungeonTilemap.SIZE;

        state = State.GROWING;
        time = DELAY;
    }

    public void reset( int image ) {
        frame( frames.get( image ) );
    }

    @Override
    public void update() {
        super.update();

        visible = pos == -1 || Dungeon.visible[pos];

        switch (state) {
            case GROWING:
                if ((time -= Game.elapsed) <= 0) {
                    state = State.NORMAL;
                    scale.set( 1 );
                } else {
                    scale.set( 1 - time / DELAY );
                }
                break;
            case WITHERING:
                if ((time -= Game.elapsed) <= 0) {
                    super.kill();
                } else {
                    alpha( time / DELAY );
                }
                break;
            default:
        }
    }

    @Override
    public void kill() {
        state = State.WITHERING;
        time = DELAY;
    }
}
