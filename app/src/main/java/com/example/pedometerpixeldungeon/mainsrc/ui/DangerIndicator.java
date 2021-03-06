package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Mob;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.noosa.BitmapText;
import com.example.pedometerpixeldungeon.noosa.Camera;
import com.example.pedometerpixeldungeon.noosa.Image;

public class DangerIndicator extends Tag {

    public static final int COLOR	= 0xFF4C4C;

    private BitmapText number;
    private Image icon;

    private int enemyIndex = 0;

    private int lastNumber = -1;

    public DangerIndicator() {
        super( 0xFF4C4C );

        setSize( 24, 16 );

        visible = false;
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        number = new BitmapText( PixelScene.font1x );
        add( number );

        icon = Icons.SKULL.get();
        add( icon );
    }

    @Override
    protected void layout() {
        super.layout();

        icon.x = right() - 10;
        icon.y = y + (height - icon.height) / 2;

        placeNumber();
    }

    private void placeNumber() {
        number.x = right() - 11 - number.width();
        number.y = PixelScene.align( y + (height - number.baseLine()) / 2 );
    }

    @Override
    public void update() {

        if (Dungeon.hero.isAlive()) {
            int v =  Dungeon.hero.visibleEnemies();
            if (v != lastNumber) {
                lastNumber = v;
                if (visible = lastNumber > 0) {
                    number.text( Integer.toString( lastNumber ) );
                    number.measure();
                    placeNumber();

                    flash();
                }
            }
        } else {
            visible = false;
        }

        super.update();
    }

    @Override
    protected void onClick() {

        Mob target = Dungeon.hero.visibleEnemy( enemyIndex++ );

        HealthIndicator.instance.target( target == HealthIndicator.instance.target() ? null : target );

        Camera.main.target = null;
        Camera.main.focusOn( target.sprite );
    }
}
