package com.example.pedometerpixeldungeon.mainsrc;

import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.noosa.TextureFilm;
import com.example.pedometerpixeldungeon.noosa.Tilemap;
import com.example.pedometerpixeldungeon.noosa.tweeners.AlphaTweener;
import com.example.pedometerpixeldungeon.utils.Point;
import com.example.pedometerpixeldungeon.utils.PointF;

public class DungeonTilemap extends Tilemap {
    public static final int SIZE = 16;

    private static DungeonTilemap instance;

    public DungeonTilemap() {
        super(
                Dungeon.level.tilesTex(),
                new TextureFilm( Dungeon.level.tilesTex(), SIZE, SIZE ) );
        map( Dungeon.level.map, Level.WIDTH );

        instance = this;
    }

    public int screenToTile( int x, int y ) {
        Point p = camera().screenToCamera( x, y ).
                offset( this.point().negate() ).
                invScale( SIZE ).
                floor();
        return p.x >= 0 && p.x < Level.WIDTH && p.y >= 0 && p.y < Level.HEIGHT ? p.x + p.y * Level.WIDTH : -1;
    }

    @Override
    public boolean overlapsPoint( float x, float y ) {
        return true;
    }

    public void discover( int pos, int oldValue ) {

        final Image tile = tile( oldValue );
        tile.point( tileToWorld( pos ) );

        // For bright mode
        tile.rm = tile.gm = tile.bm = rm;
        tile.ra = tile.ga = tile.ba = ra;
        parent.add( tile );

        parent.add( new AlphaTweener( tile, 0, 0.6f ) {
            protected void onComplete() {
                tile.killAndErase();
                killAndErase();
            };
        } );
    }

    public static PointF tileToWorld(int pos ) {
        return new PointF( pos % Level.WIDTH, pos / Level.WIDTH  ).scale( SIZE );
    }

    public static PointF tileCenterToWorld( int pos ) {
        return new PointF(
                (pos % Level.WIDTH + 0.5f) * SIZE,
                (pos / Level.WIDTH + 0.5f) * SIZE );
    }

    public static Image tile( int index ) {
        Image img = new Image( instance.texture );
        img.frame( instance.tileset.get( index ) );
        return img;
    }

    @Override
    public boolean overlapsScreenPoint( int x, int y ) {
        return true;
    }
}
