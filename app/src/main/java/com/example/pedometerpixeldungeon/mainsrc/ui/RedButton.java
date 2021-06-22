package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Chrome;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.noosa.BitmapText;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.noosa.NinePatch;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.noosa.ui.Button;

public class RedButton extends Button {
    protected NinePatch bg;
    protected BitmapText text;
    protected Image icon;

    public RedButton( String label ) {
        super();

        text.text( label );
        text.measure();
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        bg = Chrome.get( Chrome.Type.BUTTON );
        add( bg );

        text = PixelScene.createText( 9 );
        add( text );
    }

    @Override
    protected void layout() {

        super.layout();

        bg.x = x;
        bg.y = y;
        bg.size( width, height );

        text.x = x + (int)(width - text.width()) / 2;
        text.y = y + (int)(height - text.baseLine()) / 2;

        if (icon != null) {
            icon.x = x + text.x - icon.width() - 2;
            icon.y = y + (height - icon.height()) / 2;
        }
    };

    @Override
    protected void onTouchDown() {
        bg.brightness( 1.2f );
        Sample.INSTANCE.play( Assets.SND_CLICK );
    };

    @Override
    protected void onTouchUp() {
        bg.resetColor();
    };

    public void enable( boolean value ) {
        active = value;
        text.alpha( value ? 1.0f : 0.3f );
    }

    public void text( String value ) {
        text.text( value );
        text.measure();
        layout();
    }

    public void textColor( int value ) {
        text.hardlight( value );
    }

    public void icon( Image icon ) {
        if (this.icon != null) {
            remove( this.icon );
        }
        this.icon = icon;
        if (this.icon != null) {
            add( this.icon );
            layout();
        }
    }

    public float reqWidth() {
        return text.width() + 4;
    }

    public float reqHeight() {
        return text.baseLine() + 4;
    }
}
