package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.DungeonTilemap;
import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Blob;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;
import com.example.pedometerpixeldungeon.noosa.BitmapTextMultiline;
import com.example.pedometerpixeldungeon.noosa.Image;

public class WndInfoCell extends Window {

    private static final float GAP	= 2;

    private static final int WIDTH = 120;

    private static final String TXT_NOTHING	= "There is nothing here.";

    public WndInfoCell( int cell ) {

        super();

        int tile = Dungeon.level.map[cell];
        if (Level.water[cell]) {
            tile = Terrain.WATER;
        } else if (Level.pit[cell]) {
            tile = Terrain.CHASM;
        }

        IconTitle titlebar = new IconTitle();
        if (tile == Terrain.WATER) {
            Image water = new Image( Dungeon.level.waterTex() );
            water.frame( 0, 0, DungeonTilemap.SIZE, DungeonTilemap.SIZE );
            titlebar.icon( water );
        } else {
            titlebar.icon( DungeonTilemap.tile( tile ) );
        }
        titlebar.label( Dungeon.level.tileName( tile ) );
        titlebar.setRect( 0, 0, WIDTH, 0 );
        add( titlebar );

        BitmapTextMultiline info = PixelScene.createMultiline( 6 );
        add( info );

        StringBuilder desc = new StringBuilder( Dungeon.level.tileDesc( tile ) );

        final char newLine = '\n';
        for (Blob blob:Dungeon.level.blobs.values()) {
            if (blob.cur[cell] > 0 && blob.tileDesc() != null) {
                if (desc.length() > 0) {
                    desc.append( newLine );
                }
                desc.append( blob.tileDesc() );
            }
        }

        info.text( desc.length() > 0 ? desc.toString() : TXT_NOTHING );
        info.maxWidth = WIDTH;
        info.measure();
        info.x = titlebar.left();
        info.y = titlebar.bottom() + GAP;

        resize( WIDTH, (int)(info.y + info.height()) );
    }
}