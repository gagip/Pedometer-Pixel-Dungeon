package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.gltextures.SmartTexture;
import com.example.pedometerpixeldungeon.gltextures.TextureCache;
import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.Statistics;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;
import com.example.pedometerpixeldungeon.mainsrc.ui.RedButton;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.noosa.BitmapText;
import com.example.pedometerpixeldungeon.noosa.Group;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.noosa.TextureFilm;

import java.util.Locale;

public class WndHero extends WndTabbed {

    private static final String TXT_STATS	= "Stats";
    private static final String TXT_BUFFS	= "Buffs";

    private static final String TXT_EXP		= "Experience";
    private static final String TXT_STR		= "Strength";
    private static final String TXT_HEALTH	= "Health";
    private static final String TXT_GOLD	= "Gold Collected";
    private static final String TXT_DEPTH	= "Maximum Depth";

    private static final int WIDTH		= 100;
    private static final int TAB_WIDTH	= 40;

    private StatsTab stats;
    private BuffsTab buffs;

    private SmartTexture icons;
    private TextureFilm film;

    public WndHero() {

        super();

        icons = TextureCache.get( Assets.BUFFS_LARGE );
        film = new TextureFilm( icons, 16, 16 );

        stats = new StatsTab();
        add( stats );

        buffs = new BuffsTab();
        add( buffs );

        add( new LabeledTab( TXT_STATS ) {
            protected void select( boolean value ) {
                super.select( value );
                stats.visible = stats.active = selected;
            };
        } );
        add( new LabeledTab( TXT_BUFFS ) {
            protected void select( boolean value ) {
                super.select( value );
                buffs.visible = buffs.active = selected;
            };
        } );
        for (Tab tab : tabs) {
            tab.setSize( TAB_WIDTH, tabHeight() );
        }

        resize( WIDTH, (int)Math.max( stats.height(), buffs.height() ) );

        select( 0 );
    }

    private class StatsTab extends Group {

        private static final String TXT_TITLE		= "Level %d %s";
        private static final String TXT_CATALOGUS	= "Catalogus";
        private static final String TXT_JOURNAL		= "Journal";

        private static final int GAP = 5;

        private float pos;

        public StatsTab() {

            Hero hero = Dungeon.hero;

            BitmapText title = PixelScene.createText(
                    Utils.format( TXT_TITLE, hero.lvl, hero.className() ).toUpperCase( Locale.ENGLISH ), 9 );
            title.hardlight( TITLE_COLOR );
            title.measure();
            add( title );

            RedButton btnCatalogus = new RedButton( TXT_CATALOGUS ) {
                @Override
                protected void onClick() {
                    hide();
//                    GameScene.show( new WndCatalogus() );
                }
            };
            btnCatalogus.setRect( 0, title.y + title.height(), btnCatalogus.reqWidth() + 2, btnCatalogus.reqHeight() + 2 );
            add( btnCatalogus );

            RedButton btnJournal = new RedButton( TXT_JOURNAL ) {
                @Override
                protected void onClick() {
                    hide();
//                    GameScene.show( new WndJournal() );
                }
            };
            btnJournal.setRect(
                    btnCatalogus.right() + 1, btnCatalogus.top(),
                    btnJournal.reqWidth() + 2, btnJournal.reqHeight() + 2 );
            add( btnJournal );

            pos = btnCatalogus.bottom() + GAP;

            statSlot( TXT_STR, hero.STR() );
            statSlot( TXT_HEALTH, hero.HP + "/" + hero.HT );
            statSlot( TXT_EXP, hero.exp + "/" + hero.maxExp() );

            pos += GAP;

            statSlot( TXT_GOLD, Statistics.goldCollected );
            statSlot( TXT_DEPTH, Statistics.deepestFloor );

            pos += GAP;
        }

        private void statSlot( String label, String value ) {

            BitmapText txt = PixelScene.createText( label, 8 );
            txt.y = pos;
            add( txt );

            txt = PixelScene.createText( value, 8 );
            txt.measure();
            txt.x = PixelScene.align( WIDTH * 0.65f );
            txt.y = pos;
            add( txt );

            pos += GAP + txt.baseLine();
        }

        private void statSlot( String label, int value ) {
            statSlot( label, Integer.toString( value ) );
        }

        public float height() {
            return pos;
        }
    }

    private class BuffsTab extends Group {

        private static final int GAP = 2;

        private float pos;

        public BuffsTab() {
            for (Buff buff : Dungeon.hero.buffs()) {
                buffSlot( buff );
            }
        }

        private void buffSlot( Buff buff ) {

            int index = buff.icon();

            if (index != BuffIndicator.NONE) {

                Image icon = new Image( icons );
                icon.frame( film.get( index ) );
                icon.y = pos;
                add( icon );

                BitmapText txt = PixelScene.createText( buff.toString(), 8 );
                txt.x = icon.width + GAP;
                txt.y = pos + (int)(icon.height - txt.baseLine()) / 2;
                add( txt );

                pos += GAP + icon.height;
            }
        }

        public float height() {
            return pos;
        }
    }
}
