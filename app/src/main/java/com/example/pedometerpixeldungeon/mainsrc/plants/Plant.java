package com.example.pedometerpixeldungeon.mainsrc.plants;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Barkskin;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.HeroSubClass;
import com.example.pedometerpixeldungeon.mainsrc.effects.CellEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.LeafParticle;
import com.example.pedometerpixeldungeon.mainsrc.items.Dewdrop;
import com.example.pedometerpixeldungeon.mainsrc.items.Generator;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.mainsrc.sprites.PlantSprite;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.utils.Bundlable;
import com.example.pedometerpixeldungeon.utils.Bundle;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.ArrayList;

public class Plant implements Bundlable {
    public String plantName;

    public int image;
    public int pos;

    public PlantSprite sprite;

    public void activate( Char ch ) {

        if (ch instanceof Hero && ((Hero)ch).subClass == HeroSubClass.WARDEN) {
            Buff.affect( ch, Barkskin.class ).level( ch.HT / 3 );
        }

        wither();
    }

    public void wither() {
        Dungeon.level.uproot( pos );

        sprite.kill();
        if (Dungeon.visible[pos]) {
            CellEmitter.get( pos ).burst( LeafParticle.GENERAL, 6 );
        }

        if (Dungeon.hero.subClass == HeroSubClass.WARDEN) {
            if (Random.Int( 5 ) == 0) {
                Dungeon.level.drop( Generator.random( Generator.Category.SEED ), pos ).sprite.drop();
            }
            if (Random.Int( 5 ) == 0) {
                Dungeon.level.drop( new Dewdrop(), pos ).sprite.drop();
            }
        }
    }

    private static final String POS	= "pos";

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        pos = bundle.getInt( POS );
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        bundle.put( POS, pos );
    }

    public String desc() {
        return null;
    }

    public static class Seed extends Item {

        public static final String AC_PLANT	= "PLANT";

        private static final String TXT_INFO = "Throw this seed to the place where you want to grow %s.\n\n%s";

        private static final float TIME_TO_PLANT = 1f;

        {
            stackable = true;
            defaultAction = AC_THROW;
        }

        protected Class<? extends Plant> plantClass;
        protected String plantName;

        public Class<? extends Item> alchemyClass;

        @Override
        public ArrayList<String> actions(Hero hero ) {
            ArrayList<String> actions = super.actions( hero );
            actions.add( AC_PLANT );
            return actions;
        }

        @Override
        protected void onThrow( int cell ) {
            if (Dungeon.level.map[cell] == Terrain.ALCHEMY || Level.pit[cell]) {
                super.onThrow( cell );
            } else {
                Dungeon.level.plant( this, cell );
            }
        }

        @Override
        public void execute( Hero hero, String action ) {
            if (action.equals( AC_PLANT )) {

                hero.spend( TIME_TO_PLANT );
                hero.busy();
                ((Seed)detach( hero.belongings.backpack )).onThrow( hero.pos );

                hero.sprite.operate( hero.pos );

            } else {

                super.execute (hero, action );

            }
        }

        public Plant couch( int pos ) {
            try {
                if (Dungeon.visible[pos]) {
                    Sample.INSTANCE.play( Assets.SND_PLANT );
                }
                Plant plant = plantClass.newInstance();
                plant.pos = pos;
                return plant;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public boolean isUpgradable() {
            return false;
        }

        @Override
        public boolean isIdentified() {
            return true;
        }

        @Override
        public int price() {
            return 10 * quantity;
        }

        @Override
        public String info() {
            return String.format( TXT_INFO, Utils.indefinite( plantName ), desc() );
        }
    }
}
