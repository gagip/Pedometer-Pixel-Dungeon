package com.example.pedometerpixeldungeon.mainsrc.elements;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Blob;
import com.example.pedometerpixeldungeon.mainsrc.effects.CellEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.FlameParticle;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;

public class Fire extends Element {

    private float prob = 0.2f;      // 발동 확률

    {
        type = Type.FIRE;
        strong.add(Type.GRASS);
        week.add(Type.WATER);
    }

    @Override
    public void effect(Type enemyType) {
        // 주변에 화염
        if (prob > Math.random()) {
            for (int pos: Level.NEIGHBOURS8){
                GameScene.add( Blob.seed( Dungeon.hero.pos + pos, 1, com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Fire.class ) );
                CellEmitter.get( Dungeon.hero.pos + pos ).burst( FlameParticle.FACTORY, 5 );
            }
        }
    }
}
