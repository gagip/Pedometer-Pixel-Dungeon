package com.example.pedometerpixeldungeon.mainsrc.items.potions;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.ItemStatusHandler;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.mainsrc.windows.WndOptions;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.utils.Bundle;

import java.util.ArrayList;
import java.util.HashSet;

public class Potion extends Item {

    public static final String AC_DRINK	= "DRINK";

    private static final String TXT_HARMFUL		= "Harmful potion!";
    private static final String TXT_BENEFICIAL	= "Beneficial potion";
    private static final String TXT_YES			= "Yes, I know what I'm doing";
    private static final String TXT_NO			= "No, I changed my mind";
    private static final String TXT_R_U_SURE_DRINK =
            "Are you sure you want to drink it? In most cases you should throw such potions at your enemies.";
    private static final String TXT_R_U_SURE_THROW =
            "Are you sure you want to throw it? In most cases it makes sense to drink it.";

    private static final float TIME_TO_DRINK = 1f;

    private static final Class<?>[] potions = {
            PotionOfHealing.class,
            PotionOfExperience.class,
            PotionOfToxicGas.class,
            PotionOfLiquidFlame.class,
            PotionOfStrength.class,
            PotionOfParalyticGas.class,
            PotionOfLevitation.class,
            PotionOfMindVision.class,
            PotionOfPurity.class,
            PotionOfInvisibility.class,
            PotionOfMight.class,
            PotionOfFrost.class
    };
    private static final String[] colors = {
            "turquoise", "crimson", "azure", "jade", "golden", "magenta",
            "charcoal", "ivory", "amber", "bistre", "indigo", "silver"};
    private static final Integer[] images = {
            ItemSpriteSheet.POTION_TURQUOISE,
            ItemSpriteSheet.POTION_CRIMSON,
            ItemSpriteSheet.POTION_AZURE,
            ItemSpriteSheet.POTION_JADE,
            ItemSpriteSheet.POTION_GOLDEN,
            ItemSpriteSheet.POTION_MAGENTA,
            ItemSpriteSheet.POTION_CHARCOAL,
            ItemSpriteSheet.POTION_IVORY,
            ItemSpriteSheet.POTION_AMBER,
            ItemSpriteSheet.POTION_BISTRE,
            ItemSpriteSheet.POTION_INDIGO,
            ItemSpriteSheet.POTION_SILVER};

    private static ItemStatusHandler<Potion> handler;

    private String color;

    {
        stackable = true;
        defaultAction = AC_DRINK;
    }

    @SuppressWarnings("unchecked")
    public static void initColors() {
        handler = new ItemStatusHandler<Potion>( (Class<? extends Potion>[])potions, colors, images );
    }

    public static void save( Bundle bundle ) {
        handler.save( bundle );
    }

    @SuppressWarnings("unchecked")
    public static void restore( Bundle bundle ) {
        handler = new ItemStatusHandler<Potion>( (Class<? extends Potion>[])potions, colors, images, bundle );
    }

    public Potion() {
        super();
        image = handler.image( this );
        color = handler.label( this );
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_DRINK );
        return actions;
    }

    @Override
    public void execute( final Hero hero, String action ) {
        if (action.equals( AC_DRINK )) {

            if (isKnown() && (
                    this instanceof PotionOfLiquidFlame ||
                            this instanceof PotionOfToxicGas ||
                            this instanceof PotionOfParalyticGas)) {

                GameScene.show(
                        new WndOptions( TXT_HARMFUL, TXT_R_U_SURE_DRINK, TXT_YES, TXT_NO ) {
                            @Override
                            protected void onSelect(int index) {
                                if (index == 0) {
                                    drink( hero );
                                }
                            };
                        }
                );

            } else {
                drink( hero );
            }

        } else {

            super.execute( hero, action );

        }
    }

    @Override
    public void doThrow( final Hero hero ) {

        if (isKnown() && (
                this instanceof PotionOfExperience ||
                        this instanceof PotionOfHealing ||
                        this instanceof PotionOfLevitation ||
                        this instanceof PotionOfMindVision ||
                        this instanceof PotionOfStrength ||
                        this instanceof PotionOfInvisibility ||
                        this instanceof PotionOfMight)) {

            GameScene.show(
                    new WndOptions( TXT_BENEFICIAL, TXT_R_U_SURE_THROW, TXT_YES, TXT_NO ) {
                        @Override
                        protected void onSelect(int index) {
                            if (index == 0) {
                                Potion.super.doThrow( hero );
                            }
                        };
                    }
            );

        } else {
            super.doThrow( hero );
        }
    }

    protected void drink( Hero hero ) {

        detach( hero.belongings.backpack );

        hero.spend( TIME_TO_DRINK );
        hero.busy();
        onThrow( hero.pos );

        Sample.INSTANCE.play( Assets.SND_DRINK );

        hero.sprite.operate( hero.pos );
    }

    @Override
    protected void onThrow( int cell ) {
        if (Dungeon.hero.pos == cell) {

            apply( Dungeon.hero );

        } else if (Dungeon.level.map[cell] == Terrain.WELL || Level.pit[cell]) {

            super.onThrow( cell );

        } else  {

            shatter( cell );

        }
    }

    protected void apply( Hero hero ) {
        shatter( hero.pos );
    }

    public void shatter( int cell ) {
        if (Dungeon.visible[cell]) {
            GLog.i( "The flask shatters and " + color() + " liquid splashes harmlessly" );
            Sample.INSTANCE.play( Assets.SND_SHATTER );
            splash( cell );
        }
    }

    public boolean isKnown() {
        return handler.isKnown( this );
    }

    public void setKnown() {
        if (!isKnown()) {
            handler.know( this );
        }
    }

    @Override
    public Item identify() {
        setKnown();
        return this;
    }

    protected String color() {
        return color;
    }

    @Override
    public String name() {
        return isKnown() ? name : color + " potion";
    }

    @Override
    public String info() {
        return isKnown() ?
                desc() :
                "This flask contains a swirling " + color + " liquid. " +
                        "Who knows what it will do when drunk or thrown?";
    }

    @Override
    public boolean isIdentified() {
        return isKnown();
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    public static HashSet<Class<? extends Potion>> getKnown() {
        return handler.known();
    }

    public static HashSet<Class<? extends Potion>> getUnknown() {
        return handler.unknown();
    }

    public static boolean allKnown() {
        return handler.known().size() == potions.length;
    }

    protected void splash( int cell ) {
//        final int color = ItemSprite.pick( image, 8, 10 );
//        Splash.at( cell, color, 5 );
    }

    @Override
    public int price() {
        return 20 * quantity;
    }
}
