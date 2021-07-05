package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.mainsrc.ui.ItemSlot;
import com.example.pedometerpixeldungeon.mainsrc.ui.RedButton;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.noosa.BitmapTextMultiline;

public class WndItem extends Window {

    private static final float BUTTON_WIDTH		= 36;
    private static final float BUTTON_HEIGHT	= 16;

    private static final float GAP	= 3;

    private static final int WIDTH = 120;

    public WndItem( final WndBag owner, final Item item ) {

        super();

        IconTitle titlebar = new IconTitle();
        titlebar.icon( new ItemSprite( item.image(), item.glowing() ) );
        titlebar.label( Utils.capitalize( item.toString() ) );
        if (item.isUpgradable() && item.levelKnown) {
            titlebar.health( (float)item.durability() / item.maxDurability() );
        }
        titlebar.setRect( 0, 0, WIDTH, 0 );
        add( titlebar );

        if (item.levelKnown) {
            if (item.level() < 0) {
                titlebar.color( ItemSlot.DEGRADED );
            } else if (item.level() > 0) {
                titlebar.color( item.isBroken() ? ItemSlot.WARNING : ItemSlot.UPGRADED );
            }
        }

        BitmapTextMultiline info = PixelScene.createMultiline( item.info(), 6 );
        info.maxWidth = WIDTH;
        info.measure();
        info.x = titlebar.left();
        info.y = titlebar.bottom() + GAP;
        add( info );

        float y = info.y + info.height() + GAP;
        float x = 0;

        if (Dungeon.hero.isAlive() && owner != null) {
            for (final String action:item.actions( Dungeon.hero )) {

                RedButton btn = new RedButton( action ) {
                    @Override
                    protected void onClick() {
                        item.execute( Dungeon.hero, action );
                        hide();
                        owner.hide();
                    };
                };
                btn.setSize( Math.max( BUTTON_WIDTH, btn.reqWidth() ), BUTTON_HEIGHT );
                if (x + btn.width() > WIDTH) {
                    x = 0;
                    y += BUTTON_HEIGHT + GAP;
                }
                btn.setPos( x, y );
                add( btn );

                if (action == item.defaultAction) {
                    btn.textColor( TITLE_COLOR );
                }

                x += btn.width() + GAP;
            }
        }

        resize( WIDTH, (int)(y + (x > 0 ? BUTTON_HEIGHT : 0)) );
    }
}