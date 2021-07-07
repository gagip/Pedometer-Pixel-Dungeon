package com.example.pedometerpixeldungeon.mainsrc.items.scrolls;

import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Blindness;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.ItemStatusHandler;
import com.example.pedometerpixeldungeon.mainsrc.sprites.HeroSprite;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.utils.Bundle;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Scroll extends Item {

    private static final String TXT_BLINDED	= "You can't read a scroll while blinded";

    public static final String AC_READ	= "READ";

    protected static final float TIME_TO_READ	= 1f;

    private static final Class<?>[] scrolls = {
            ScrollOfIdentify.class,
            ScrollOfMagicMapping.class,
            ScrollOfRecharging.class,
            ScrollOfRemoveCurse.class,
            ScrollOfTeleportation.class,
            ScrollOfChallenge.class,
            ScrollOfTerror.class,
            ScrollOfLullaby.class,
            ScrollOfPsionicBlast.class,
            ScrollOfMirrorImage.class,
            ScrollOfUpgrade.class,
            ScrollOfEnchantment.class
    };
    private static final String[] runes =
            {"KAUNAN", "SOWILO", "LAGUZ", "YNGVI", "GYFU", "RAIDO", "ISAZ", "MANNAZ", "NAUDIZ", "BERKANAN", "ODAL", "TIWAZ"};
    private static final Integer[] images = {
            ItemSpriteSheet.SCROLL_KAUNAN,
            ItemSpriteSheet.SCROLL_SOWILO,
            ItemSpriteSheet.SCROLL_LAGUZ,
            ItemSpriteSheet.SCROLL_YNGVI,
            ItemSpriteSheet.SCROLL_GYFU,
            ItemSpriteSheet.SCROLL_RAIDO,
            ItemSpriteSheet.SCROLL_ISAZ,
            ItemSpriteSheet.SCROLL_MANNAZ,
            ItemSpriteSheet.SCROLL_NAUDIZ,
            ItemSpriteSheet.SCROLL_BERKANAN,
            ItemSpriteSheet.SCROLL_ODAL,
            ItemSpriteSheet.SCROLL_TIWAZ};

    private static ItemStatusHandler<Scroll> handler;

    private String rune;

    {
        stackable = true;
        defaultAction = AC_READ;
    }

    @SuppressWarnings("unchecked")
    public static void initLabels() {
        handler = new ItemStatusHandler<Scroll>( (Class<? extends Scroll>[])scrolls, runes, images );
    }

    public static void save( Bundle bundle ) {
        handler.save( bundle );
    }

    @SuppressWarnings("unchecked")
    public static void restore( Bundle bundle ) {
        handler = new ItemStatusHandler<Scroll>( (Class<? extends Scroll>[])scrolls, runes, images, bundle );
    }

    public Scroll() {
        super();
        image = handler.image( this );
        rune = handler.label( this );
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_READ );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        if (action.equals( AC_READ )) {

            if (hero.buff( Blindness.class ) != null) {
                GLog.w( TXT_BLINDED );
            } else {
                curUser = hero;
                curItem = detach( hero.belongings.backpack );
                doRead();
            }
            curUser = hero;
            curItem = detach( hero.belongings.backpack );
            doRead();
        } else {

            super.execute( hero, action );

        }
    }

    abstract protected void doRead();

    protected void readAnimation() {
        curUser.spend( TIME_TO_READ );
        curUser.busy();
        ((HeroSprite)curUser.sprite).read();
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
        return super.identify();
    }

    @Override
    public String name() {
        return isKnown() ? name : "scroll \"" + rune + "\"";
    }

    @Override
    public String info() {
        return isKnown() ?
                desc() :
                "This parchment is covered with indecipherable writing, and bears a title " +
                        "of rune " + rune + ". Who knows what it will do when read aloud?";
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return isKnown();
    }

    public static HashSet<Class<? extends Scroll>> getKnown() {
        return handler.known();
    }

    public static HashSet<Class<? extends Scroll>> getUnknown() {
        return handler.unknown();
    }

    public static boolean allKnown() {
        return handler.known().size() == scrolls.length;
    }

    @Override
    public int price() {
        return 15 * quantity;
    }
}