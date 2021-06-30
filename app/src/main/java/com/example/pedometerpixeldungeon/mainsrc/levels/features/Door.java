package com.example.pedometerpixeldungeon.mainsrc.levels.features;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;

public class Door {

    public static void enter( int pos ) {
        Level.set( pos, Terrain.OPEN_DOOR );
        GameScene.updateMap( pos );
        Dungeon.observe();

        if (Dungeon.visible[pos]) {
            Sample.INSTANCE.play( Assets.SND_OPEN );
        }
    }

    public static void leave( int pos ) {
        if (Dungeon.level.heaps.get( pos ) == null) {
            Level.set( pos, Terrain.DOOR );
            GameScene.updateMap( pos );
            Dungeon.observe();
        }
    }
}