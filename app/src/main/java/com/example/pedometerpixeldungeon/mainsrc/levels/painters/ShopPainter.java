package com.example.pedometerpixeldungeon.mainsrc.levels.painters;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Mob;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.npcs.ImpShopkeeper;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.npcs.Shopkeeper;
import com.example.pedometerpixeldungeon.mainsrc.items.Ankh;
import com.example.pedometerpixeldungeon.mainsrc.items.Generator;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.Torch;
import com.example.pedometerpixeldungeon.mainsrc.items.Weightstone;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.LeatherArmor;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.MailArmor;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.PlateArmor;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.ScaleArmor;
import com.example.pedometerpixeldungeon.mainsrc.items.bags.ScrollHolder;
import com.example.pedometerpixeldungeon.mainsrc.items.bags.SeedPouch;
import com.example.pedometerpixeldungeon.mainsrc.items.bags.WandHolster;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.OverpricedRation;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfHealing;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfIdentify;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfMagicMapping;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfRemoveCurse;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.BattleAxe;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Glaive;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Longsword;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Mace;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Quarterstaff;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Spear;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.Sword;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.WarHammer;
import com.example.pedometerpixeldungeon.mainsrc.levels.LastShopLevel;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Painter;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.utils.Point;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.ArrayList;

public class ShopPainter extends Painter {

    private static int pasWidth;
    private static int pasHeight;

    public static void paint( Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.EMPTY_SP );

        pasWidth = room.width() - 2;
        pasHeight = room.height() - 2;
        int per = pasWidth * 2 + pasHeight * 2;

        Item[] range = range();

        int pos = xy2p( room, room.entrance() ) + (per - range.length) / 2;
        for (int i=0; i < range.length; i++) {

            Point xy = p2xy( room, (pos + per) % per );
            int cell = xy.x + xy.y * Level.WIDTH;

            if (level.heaps.get( cell ) != null) {
                do {
                    cell = room.random();
                } while (level.heaps.get( cell ) != null);
            }

            level.drop( range[i], cell ).type = Heap.Type.FOR_SALE;

            pos++;
        }

        placeShopkeeper( level, room );

        for (Room.Door door : room.connected.values()) {
            door.set( Room.Door.Type.REGULAR );
        }
    }

    private static Item[] range() {

        ArrayList<Item> items = new ArrayList<Item>();

        switch (Dungeon.depth) {

            case 6:
                items.add( (Random.Int( 2 ) == 0 ? new Quarterstaff() : new Spear()).identify() );
                items.add( new LeatherArmor().identify() );
                items.add( new SeedPouch() );
                items.add( new Weightstone() );
                break;

            case 11:
                items.add( (Random.Int( 2 ) == 0 ? new Sword() : new Mace()).identify() );
                items.add( new MailArmor().identify() );
                items.add( new ScrollHolder() );
                items.add( new Weightstone() );
                break;

            case 16:
                items.add( (Random.Int( 2 ) == 0 ? new Longsword() : new BattleAxe()).identify() );
                items.add( new ScaleArmor().identify() );
                items.add( new WandHolster() );
                items.add( new Weightstone() );
                break;

            case 21:
                switch (Random.Int( 3 )) {
                    case 0:
                        items.add( new Glaive().identify() );
                        break;
                    case 1:
                        items.add( new WarHammer().identify() );
                        break;
                    case 2:
                        items.add( new PlateArmor().identify() );
                        break;
                }
                items.add( new Torch() );
                items.add( new Torch() );
                break;
        }

        items.add( new PotionOfHealing() );
        for (int i=0; i < 3; i++) {
            items.add( Generator.random( Generator.Category.POTION ) );
        }

        items.add( new ScrollOfIdentify() );
        items.add( new ScrollOfRemoveCurse() );
        items.add( new ScrollOfMagicMapping() );
        items.add( Generator.random( Generator.Category.SCROLL ) );

        items.add( new OverpricedRation() );
        items.add( new OverpricedRation() );

        items.add( new Ankh() );

        Item[] range =items.toArray( new Item[0] );
        Random.shuffle( range );

        return range;
    }

    private static void placeShopkeeper( Level level, Room room ) {

        int pos;
        do {
            pos = room.random();
        } while (level.heaps.get( pos ) != null);

        Mob shopkeeper = level instanceof LastShopLevel ? new ImpShopkeeper() : new Shopkeeper();
        shopkeeper.pos = pos;
        level.mobs.add( shopkeeper );

        if (level instanceof LastShopLevel) {
            for (int i=0; i < Level.NEIGHBOURS9.length; i++) {
                int p = shopkeeper.pos + Level.NEIGHBOURS9[i];
                if (level.map[p] == Terrain.EMPTY_SP) {
                    level.map[p] = Terrain.WATER;
                }
            }
        }
    }

    private static int xy2p( Room room, Point xy ) {
        if (xy.y == room.top) {

            return (xy.x - room.left - 1);

        } else if (xy.x == room.right) {

            return (xy.y - room.top - 1) + pasWidth;

        } else if (xy.y == room.bottom) {

            return (room.right - xy.x - 1) + pasWidth + pasHeight;

        } else /*if (xy.x == room.left)*/ {

            if (xy.y == room.top + 1) {
                return 0;
            } else {
                return (room.bottom - xy.y - 1) + pasWidth * 2 + pasHeight;
            }

        }
    }

    private static Point p2xy(Room room, int p ) {
        if (p < pasWidth) {

            return new Point( room.left + 1 + p, room.top + 1);

        } else if (p < pasWidth + pasHeight) {

            return new Point( room.right - 1, room.top + 1 + (p - pasWidth) );

        } else if (p < pasWidth * 2 + pasHeight) {

            return new Point( room.right - 1 - (p - (pasWidth + pasHeight)), room.bottom - 1 );

        } else {

            return new Point( room.left + 1, room.bottom - 1 - (p - (pasWidth * 2 + pasHeight)) );

        }
    }


}
