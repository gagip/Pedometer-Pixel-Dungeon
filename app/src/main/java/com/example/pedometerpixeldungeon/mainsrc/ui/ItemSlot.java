package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.armors.Armor;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.Weapon;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.melee.MeleeWeapon;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.noosa.BitmapText;
import com.example.pedometerpixeldungeon.noosa.ui.Button;

public class ItemSlot extends Button {

    public static final int DEGRADED	= 0xFF4444;
    public static final int UPGRADED	= 0x44FF44;
    public static final int WARNING		= 0xFF8800;

    private static final float ENABLED	= 1.0f;
    private static final float DISABLED	= 0.3f;

    protected ItemSprite icon;
    protected BitmapText topLeft;
    protected BitmapText topRight;
    protected BitmapText bottomRight;

    private static final String TXT_STRENGTH	= ":%d";
    private static final String TXT_TYPICAL_STR	= "%d?";

    private static final String TXT_LEVEL	= "%+d";
    private static final String TXT_CURSED	= "";//"-";

    // Special "virtual items"
    public static final Item CHEST = new Item() {
        public int image() { return ItemSpriteSheet.CHEST; };
    };
    public static final Item LOCKED_CHEST = new Item() {
        public int image() { return ItemSpriteSheet.LOCKED_CHEST; };
    };
    public static final Item TOMB = new Item() {
        public int image() { return ItemSpriteSheet.TOMB; };
    };
    public static final Item SKELETON = new Item() {
        public int image() { return ItemSpriteSheet.BONES; };
    };

    public ItemSlot() {
        super();
    }

    public ItemSlot( Item item ) {
        this();
        item( item );
    }

    @Override
    protected void createChildren() {

        super.createChildren();

        icon = new ItemSprite();
        add( icon );

        topLeft = new BitmapText( PixelScene.font1x );
        add( topLeft );

        topRight = new BitmapText( PixelScene.font1x );
        add( topRight );

        bottomRight = new BitmapText( PixelScene.font1x );
        add( bottomRight );
    }

    @Override
    protected void layout() {
        super.layout();

        icon.x = x + (width - icon.width) / 2;
        icon.y = y + (height - icon.height) / 2;

        if (topLeft != null) {
            topLeft.x = x;
            topLeft.y = y;
        }

        if (topRight != null) {
            topRight.x = x + (width - topRight.width());
            topRight.y = y;
        }

        if (bottomRight != null) {
            bottomRight.x = x + (width - bottomRight.width());
            bottomRight.y = y + (height - bottomRight.height());
        }
    }

    public void item( Item item ) {
        if (item == null) {

            active = false;
            icon.visible = topLeft.visible = topRight.visible = bottomRight.visible = false;

        } else {

            active = true;
            icon.visible = topLeft.visible = topRight.visible = bottomRight.visible = true;

            icon.view( item.image(), item.glowing() );

            topLeft.text( item.status()  );

            boolean isArmor = item instanceof Armor;
            boolean isWeapon = item instanceof Weapon;
            if (isArmor || isWeapon) {

                if (item.levelKnown || (isWeapon && !(item instanceof MeleeWeapon))) {

                    int str = isArmor ? ((Armor)item).STR : ((Weapon)item).STR;
                    topRight.text( Utils.format( TXT_STRENGTH, str ) );
                    if (str > Dungeon.hero.STR()) {
                        topRight.hardlight( DEGRADED );
                    } else {
                        topRight.resetColor();
                    }

                } else {

                    topRight.text( Utils.format( TXT_TYPICAL_STR, isArmor ?
                            ((Armor)item).typicalSTR() :
                            ((MeleeWeapon)item).typicalSTR() ) );
                    topRight.hardlight( WARNING );

                }
                topRight.measure();

            } else {

                topRight.text( null );

            }

            int level = item.visiblyUpgraded();
            if (level != 0 || (item.cursed && item.cursedKnown)) {
                bottomRight.text( item.levelKnown ? Utils.format( TXT_LEVEL, level ) : TXT_CURSED );
                bottomRight.measure();
                bottomRight.hardlight( level > 0 ? (item.isBroken() ? WARNING : UPGRADED) : DEGRADED );
            } else {
                bottomRight.text( null );
            }

            layout();
        }
    }

    public void enable( boolean value ) {

        active = value;

        float alpha = value ? ENABLED : DISABLED;
        icon.alpha( alpha );
        topLeft.alpha( alpha );
        topRight.alpha( alpha );
        bottomRight.alpha( alpha );
    }

    public void showParams( boolean value ) {
        if (value) {
            add( topRight );
            add( bottomRight );
        } else {
            remove( topRight );
            remove( bottomRight );
        }
    }
}