package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.DungeonTilemap;
import com.example.pedometerpixeldungeon.noosa.Camera;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.utils.PointF;

public class Compass extends Image {

    private static final float RAD_2_G	= 180f / 3.1415926f;
    private static final float RADIUS	= 12;

    private int cell;
    private PointF cellCenter;

    private PointF lastScroll = new PointF();

    public Compass( int cell ) {

        super();
        copy( Icons.COMPASS.get() );
        origin.set( width / 2, RADIUS );

        this.cell = cell;
        cellCenter = DungeonTilemap.tileCenterToWorld( cell );
        visible = false;
    }

    @Override
    public void update() {
        super.update();

        if (!visible) {
            visible = Dungeon.level.visited[cell] || Dungeon.level.mapped[cell];
        }

        if (visible) {
            PointF scroll = Camera.main.scroll;
            if (!scroll.equals( lastScroll )) {
                lastScroll.set( scroll );
                PointF center = Camera.main.center().offset( scroll );
                angle = (float)Math.atan2( cellCenter.x - center.x, center.y - cellCenter.y ) * RAD_2_G;
            }
        }
    }
}
