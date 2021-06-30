package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.mainsrc.ui.HealthBar;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.noosa.BitmapTextMultiline;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.noosa.ui.Component;

public class IconTitle extends Component {

    private static final float FONT_SIZE = 9;

    private static final float GAP = 2;

    protected Image imIcon;
    protected BitmapTextMultiline tfLabel;
    protected HealthBar health;

    private float healthLvl = Float.NaN;

    public IconTitle() {
        super();
    }

    public IconTitle( Item item ) {
        this(
                new ItemSprite( item.image(), item.glowing() ),
                Utils.capitalize( item.toString() ) );
    }

    public IconTitle( Image icon, String label ) {
        super();

        icon( icon );
        label( label );
    }

    @Override
    protected void createChildren() {
        imIcon = new Image();
        add( imIcon );

        tfLabel = PixelScene.createMultiline( FONT_SIZE );
        tfLabel.hardlight( Window.TITLE_COLOR );
        add( tfLabel );

        health = new HealthBar();
        add( health );
    }

    @Override
    protected void layout() {

        health.visible = !Float.isNaN( healthLvl );

        imIcon.x = x;
        imIcon.y = y;

        tfLabel.x = PixelScene.align( PixelScene.uiCamera, imIcon.x + imIcon.width() + GAP );
        tfLabel.maxWidth = (int)(width - tfLabel.x);
        tfLabel.measure();
        tfLabel.y =  PixelScene.align( PixelScene.uiCamera,
                imIcon.height > tfLabel.height() ?
                        imIcon.y + (imIcon.height() - tfLabel.baseLine()) / 2 :
                        imIcon.y );

        if (health.visible) {
            health.setRect( tfLabel.x, Math.max( tfLabel.y + tfLabel.height(), imIcon.y + imIcon.height() - health.height() ), tfLabel.maxWidth, 0 );
            height = health.bottom();
        } else {
            height = Math.max( imIcon.y + imIcon.height(), tfLabel.y + tfLabel.height() );
        }
    }

    public void icon( Image icon ) {
        remove( imIcon );
        add( imIcon = icon );
    }

    public void label( String label ) {
        tfLabel.text( label );
    }

    public void label( String label, int color ) {
        tfLabel.text( label );
        tfLabel.hardlight( color );
    }

    public void color( int color ) {
        tfLabel.hardlight( color );
    }

    public void health( float value ) {
        health.level( healthLvl = value );
        layout();
    }
}