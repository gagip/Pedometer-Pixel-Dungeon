package com.example.pedometerpixeldungeon.mainsrc.items;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.Statistics;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.CharSprite;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.utils.Bundle;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.ArrayList;

public class Gold extends Item {

    private static final String TXT_COLLECT	= "Collect gold coins to spend them later in a shop.";
    private static final String TXT_INFO	= "A pile of %d gold coins. " + TXT_COLLECT;
    private static final String TXT_INFO_1	= "One gold coin. " + TXT_COLLECT;
    private static final String TXT_VALUE	= "%+d";

    {
        name = "gold";
        image = ItemSpriteSheet.GOLD;
        stackable = true;
    }

    public Gold() {
        this( 1 );
    }

    public Gold( int value ) {
        this.quantity = value;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        return new ArrayList<String>();
    }

    @Override
    public boolean doPickUp( Hero hero ) {

        Dungeon.gold += quantity;
        Statistics.goldCollected += quantity;
//        Badges.validateGoldCollected();

        GameScene.pickUp( this );
        hero.sprite.showStatus( CharSprite.NEUTRAL, TXT_VALUE, quantity );
        hero.spendAndNext( TIME_TO_PICK_UP );

        Sample.INSTANCE.play( Assets.SND_GOLD, 1, 1, Random.Float( 0.9f, 1.1f ) );

        return true;
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
    public String info() {
        switch (quantity) {
            case 0:
                return TXT_COLLECT;
            case 1:
                return TXT_INFO_1;
            default:
                return Utils.format( TXT_INFO, quantity );
        }
    }

    @Override
    public Item random() {
        quantity = Random.Int( 20 + Dungeon.depth * 10, 40 + Dungeon.depth * 20 );
        return this;
    }

    private static final String VALUE	= "value";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( VALUE, quantity );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        quantity = bundle.getInt( VALUE );
    }
}