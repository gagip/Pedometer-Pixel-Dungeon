package com.example.pedometerpixeldungeon.mainsrc.items;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.Statistics;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Burning;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Frost;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Mimic;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Wraith;
import com.example.pedometerpixeldungeon.mainsrc.effects.CellEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.Speck;
import com.example.pedometerpixeldungeon.mainsrc.effects.Splash;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.ElmoParticle;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.FlameParticle;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.ShadowParticle;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.ChargrilledMeat;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.FrozenCarpaccio;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.MysteryMeat;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.Scroll;
import com.example.pedometerpixeldungeon.mainsrc.plants.Plant;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.noosa.tweeners.AlphaTweener;
import com.example.pedometerpixeldungeon.utils.Bundlable;
import com.example.pedometerpixeldungeon.utils.Bundle;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.Collection;
import java.util.LinkedList;

public class Heap implements Bundlable {

    private static final String TXT_MIMIC = "This is a mimic!";

    private static final int SEEDS_TO_POTION = 3;

    private static final float FADE_TIME = 0.6f;

    public enum Type {
        HEAP,
        FOR_SALE,
        CHEST,
        LOCKED_CHEST,
        CRYSTAL_CHEST,
        TOMB,
        SKELETON,
        MIMIC,
        HIDDEN
    }
    public Type type = Type.HEAP;

    public int pos = 0;

    public ItemSprite sprite;

    public LinkedList<Item> items = new LinkedList<Item>();

    public int image() {
        switch (type) {
            case HEAP:
            case FOR_SALE:
                return size() > 0 ? items.peek().image() : 0;
            case CHEST:
            case MIMIC:
                return ItemSpriteSheet.CHEST;
            case LOCKED_CHEST:
                return ItemSpriteSheet.LOCKED_CHEST;
            case CRYSTAL_CHEST:
                return ItemSpriteSheet.CRYSTAL_CHEST;
            case TOMB:
                return ItemSpriteSheet.TOMB;
            case SKELETON:
                return ItemSpriteSheet.BONES;
            case HIDDEN:
                return ItemSpriteSheet.HIDDEN;
            default:
                return 0;
        }
    }

    public ItemSprite.Glowing glowing() {
        return (type == Type.HEAP || type == Type.FOR_SALE) && items.size() > 0 ? items.peek().glowing() : null;
    }

    public void open( Hero hero ) {
        switch (type) {
            case MIMIC:
                if (Mimic.spawnAt( pos, items ) != null) {
                    GLog.n( TXT_MIMIC );
                    destroy();
                } else {
                    type = Type.CHEST;
                }
                break;
            case TOMB:
                Wraith.spawnAround( hero.pos );
                break;
            case SKELETON:
                CellEmitter.center( pos ).start( Speck.factory( Speck.RATTLE ), 0.1f, 3 );
                for (Item item : items) {
                    if (item.cursed) {
                        if (Wraith.spawnAt( pos ) == null) {
                            hero.sprite.emitter().burst( ShadowParticle.CURSE, 6 );
                            hero.damage( hero.HP / 2, this );
                        }
                        Sample.INSTANCE.play( Assets.SND_CURSED );
                        break;
                    }
                }
                break;
            case HIDDEN:
                sprite.alpha( 0 );
                sprite.parent.add( new AlphaTweener( sprite, 1, FADE_TIME ) );
                break;
            default:
        }

        if (type != Type.MIMIC) {
            type = Type.HEAP;
            sprite.link();
            sprite.drop();
        }
    }

    public int size() {
        return items.size();
    }

    public Item pickUp() {

        Item item = items.removeFirst();
        if (items.isEmpty()) {
            destroy();
        } else if (sprite != null) {
            sprite.view( image(), glowing() );
        }

        return item;
    }

    public Item peek() {
        return items.peek();
    }

    public void drop( Item item ) {

        if (item.stackable) {

            Class<?> c = item.getClass();
            for (Item i : items) {
                if (i.getClass() == c) {
                    i.quantity += item.quantity;
                    item = i;
                    break;
                }
            }
            items.remove( item );

        }

        if (item instanceof Dewdrop) {
            items.add( item );
        } else {
            items.addFirst( item );
        }

        if (sprite != null) {
            sprite.view( image(), glowing() );
        }
    }

    public void replace( Item a, Item b ) {
        int index = items.indexOf( a );
        if (index != -1) {
            items.remove( index );
            items.add( index, b );
        }
    }

    public void burn() {

        if (type == Type.MIMIC) {
            Mimic m = Mimic.spawnAt( pos, items );
            if (m != null) {
                Buff.affect( m, Burning.class ).reignite( m );
                m.sprite.emitter().burst( FlameParticle.FACTORY, 5 );
                destroy();
            }
        }
        if (type != Type.HEAP) {
            return;
        }

        boolean burnt = false;
        boolean evaporated = false;

        for (Item item : items.toArray( new Item[0] )) {
            if (item instanceof Scroll) {
                items.remove( item );
                burnt = true;
            }
            else if (item instanceof Dewdrop) {
                items.remove( item );
                evaporated = true;
            } else if (item instanceof MysteryMeat) {
                replace( item, ChargrilledMeat.cook( (MysteryMeat)item ) );
                burnt = true;
            }
        }

        if (burnt || evaporated) {

            if (Dungeon.visible[pos]) {
                if (burnt) {
                    burnFX( pos );
                } else {
                    evaporateFX( pos );
                }
            }

            if (isEmpty()) {
                destroy();
            } else if (sprite != null) {
                sprite.view( image(), glowing() );
            }

        }
    }

    public void freeze() {

        if (type == Type.MIMIC) {
            Mimic m = Mimic.spawnAt( pos, items );
            if (m != null) {
                Buff.prolong( m, Frost.class, Frost.duration( m ) * Random.Float( 1.0f, 1.5f ) );
                destroy();
            }
        }
        if (type != Type.HEAP) {
            return;
        }

        boolean frozen = false;
        for (Item item : items.toArray( new Item[0] )) {
            if (item instanceof MysteryMeat) {
                replace( item, FrozenCarpaccio.cook( (MysteryMeat)item ) );
                frozen = true;
            }
        }

        if (frozen) {
            if (isEmpty()) {
                destroy();
            } else if (sprite != null) {
                sprite.view( image(), glowing() );
            }
        }
    }

    public Item transmute() {

        CellEmitter.get( pos ).burst( Speck.factory( Speck.BUBBLE ), 3 );
        Splash.at( pos, 0xFFFFFF, 3 );

        float chances[] = new float[items.size()];
        int count = 0;

        int index = 0;
        for (Item item : items) {
            if (item instanceof Plant.Seed) {
                count += item.quantity;
                chances[index++] = item.quantity;
            } else {
                count = 0;
                break;
            }
        }

        if (count >= SEEDS_TO_POTION) {

            CellEmitter.get( pos ).burst( Speck.factory( Speck.WOOL ), 6 );
            Sample.INSTANCE.play( Assets.SND_PUFF );

            if (Random.Int( count ) == 0) {

                CellEmitter.center( pos ).burst( Speck.factory( Speck.EVOKE ), 3 );

                destroy();

                Statistics.potionsCooked++;
//                Badges.validatePotionsCooked();

                return Generator.random( Generator.Category.POTION );

            } else {

                Plant.Seed proto = (Plant.Seed) items.get( Random.chances( chances ) );
                Class<? extends Item> itemClass = proto.alchemyClass;

                destroy();

                Statistics.potionsCooked++;
//                Badges.validatePotionsCooked();

                if (itemClass == null) {
                    return Generator.random( Generator.Category.POTION );
                } else {
                    try {
                        return itemClass.newInstance();
                    } catch (Exception e) {
                        return null;
                    }
                }
            }

        } else {
            return null;
        }
    }

    public static void burnFX( int pos ) {
        CellEmitter.get( pos ).burst( ElmoParticle.FACTORY, 6 );
        Sample.INSTANCE.play( Assets.SND_BURNING );
    }

    public static void evaporateFX( int pos ) {
        CellEmitter.get( pos ).burst( Speck.factory( Speck.STEAM ), 5 );
    }

    public boolean isEmpty() {
        return items == null || items.size() == 0;
    }

    public void destroy() {
        Dungeon.level.heaps.remove( this.pos );
        if (sprite != null) {
            sprite.kill();
        }
        items.clear();
        items = null;
    }

    private static final String POS		= "pos";
    private static final String TYPE	= "type";
    private static final String ITEMS	= "items";

    @SuppressWarnings("unchecked")
    @Override
    public void restoreFromBundle( Bundle bundle ) {
        pos = bundle.getInt( POS );
        type = Type.valueOf( bundle.getString( TYPE ) );
        items = new LinkedList<Item>( (Collection<Item>) (Collection<?>) bundle.getCollection( ITEMS ) );
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        bundle.put( POS, pos );
        bundle.put( TYPE, type.toString() );
        bundle.put( ITEMS, items );
    }
}
