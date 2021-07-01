package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.noosa.Image;

public class ResumeButton extends Tag {

    private Image icon;

    public ResumeButton() {
        super( 0xCDD5C0 );

        setSize( 24, 22 );

        visible = false;
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        icon = Icons.get( Icons.RESUME );
        add( icon );
    }

    @Override
    protected void layout() {
        super.layout();

        icon.x = PixelScene.align( PixelScene.uiCamera, x+1 + (width - icon.width) / 2 );
        icon.y = PixelScene.align( PixelScene.uiCamera, y + (height - icon.height) / 2 );
    }

    @Override
    public void update() {
        boolean prevVisible = visible;
//        visible = (Dungeon.hero.lastAction != null);
        if (visible && !prevVisible) {
            flash();
        }

        super.update();
    }

//    @Override
//    protected void onClick() {
//        Dungeon.hero.resume();
//    }
}