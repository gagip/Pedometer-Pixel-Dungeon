package com.example.pedometerpixeldungeon.mainsrc.scenes;

import com.example.pedometerpixeldungeon.input.Touchscreen.Touch;
import com.example.pedometerpixeldungeon.mainsrc.DungeonTilemap;
import com.example.pedometerpixeldungeon.mainsrc.PedometerPixelDungeon;
import com.example.pedometerpixeldungeon.noosa.TouchArea;
import com.example.pedometerpixeldungeon.utils.GameMath;
import com.example.pedometerpixeldungeon.utils.PointF;

public class CellSelector extends TouchArea {

    public Listener listener = null;

    public boolean enabled;

    private float dragThreshold;

    public CellSelector( DungeonTilemap map ) {
        super( map );
        camera = map.camera();

        dragThreshold = PixelScene.defaultZoom * DungeonTilemap.SIZE / 2;
    }

    @Override
    protected void onClick(Touch touch ) {
        if (dragging) {

            dragging = false;

        } else {

            select( ((DungeonTilemap)target).screenToTile(
                    (int)touch.current.x,
                    (int)touch.current.y ) );
        }
    }

    public void select( int cell ) {
        if (enabled && listener != null && cell != -1) {

            listener.onSelect( cell );
            GameScene.ready();

        } else {

            GameScene.cancel();

        }
    }

    private boolean pinching = false;
    private Touch another;
    private float startZoom;
    private float startSpan;

    @Override
    protected void onTouchDown( Touch t ) {

        if (t != touch && another == null) {

            if (!touch.down) {
                touch = t;
                onTouchDown( t );
                return;
            }

            pinching = true;

            another = t;
            startSpan = PointF.distance( touch.current, another.current );
            startZoom = camera.zoom;

            dragging = false;
        }
    }

    @Override
    protected void onTouchUp( Touch t ) {
        if (pinching && (t == touch || t == another)) {

            pinching = false;

            int zoom = Math.round( camera.zoom );
            camera.zoom( zoom );
            PedometerPixelDungeon.zoom( (int)(zoom - PixelScene.defaultZoom) );

            dragging = true;
            if (t == touch) {
                touch = another;
            }
            another = null;
            lastPos.set( touch.current );
        }
    }

    private boolean dragging = false;
    private PointF lastPos = new PointF();

    @Override
    protected void onDrag( Touch t ) {

        camera.target = null;

        if (pinching) {

            float curSpan = PointF.distance( touch.current, another.current );
            camera.zoom( GameMath.gate(
                    PixelScene.minZoom,
                    startZoom * curSpan / startSpan,
                    PixelScene.maxZoom ) );

        } else {

            if (!dragging && PointF.distance( t.current, t.start ) > dragThreshold) {

                dragging = true;
                lastPos.set( t.current );

            } else if (dragging) {
                camera.scroll.offset( PointF.diff( lastPos, t.current ).invScale( camera.zoom ) );
                lastPos.set( t.current );
            }
        }

    }

    public void cancel() {
        if (listener != null) {
            listener.onSelect( null );
        }

        GameScene.ready();
    }

    public interface Listener {
        void onSelect( Integer cell );
        String prompt();
    }

}
