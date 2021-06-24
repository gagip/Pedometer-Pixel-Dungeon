package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.PedometerPixelDungeon;
import com.example.pedometerpixeldungeon.mainsrc.scenes.TitleScene;
import com.example.pedometerpixeldungeon.noosa.Game;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.noosa.ui.Button;

public class ExitButton extends Button {
    private Image image;

    public ExitButton() {
        super();

        width = image.width;
        height = image.height;
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        image = Icons.EXIT.get();
        add( image );
    }

    @Override
    protected void layout() {
        super.layout();

        image.x = x;
        image.y = y;
    }

    @Override
    protected void onTouchDown() {
        image.brightness( 1.5f );
        Sample.INSTANCE.play( Assets.SND_CLICK );
    }

    @Override
    protected void onTouchUp() {
        image.resetColor();
    }

    @Override
    protected void onClick() {
        if (Game.scene() instanceof TitleScene) {
            Game.instance.finish();
        } else {
            PedometerPixelDungeon.switchNoFade( TitleScene.class );
        }
    }
}
