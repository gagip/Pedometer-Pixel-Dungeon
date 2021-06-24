package com.example.pedometerpixeldungeon.mainsrc.scenes;

import com.example.pedometerpixeldungeon.mainsrc.windows.WndStory;
import com.example.pedometerpixeldungeon.noosa.Game;

public class IntroScene extends PixelScene {

    private static final String TEXT =
            "Many heroes of all kinds ventured into the Dungeon before you. Some of them have returned with treasures and magical " +
                    "artifacts, most have never been heard of since. But none have succeeded in retrieving the Amulet of Yendor, " +
                    "which is told to be hidden in the depths of the Dungeon.\n\n" +
                    "" +
                    "You consider yourself ready for the challenge, but most importantly, you feel that fortune smiles on you. " +
                    "It's time to start your own adventure!";

    @Override
    public void create() {
        super.create();

        add( new WndStory( TEXT ) {
            @Override
            public void hide() {
                super.hide();
                Game.switchScene( InterlevelScene.class );
            }
        } );

        fadeIn();
    }
}
