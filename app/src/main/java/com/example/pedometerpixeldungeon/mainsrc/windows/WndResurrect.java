package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.Statistics;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.items.Ankh;
import com.example.pedometerpixeldungeon.mainsrc.scenes.InterlevelScene;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.mainsrc.ui.RedButton;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;
import com.example.pedometerpixeldungeon.noosa.BitmapTextMultiline;
import com.example.pedometerpixeldungeon.noosa.Game;

public class WndResurrect extends Window {

    private static final String TXT_MESSAGE	= "You died, but you were given another chance to win this dungeon. Will you take it?";
    private static final String TXT_YES		= "Yes, I will fight!";
    private static final String TXT_NO		= "No, I give up";

    private static final int WIDTH		= 120;
    private static final int BTN_HEIGHT	= 20;
    private static final float GAP		= 2;

    public static WndResurrect instance;
    public static Object causeOfDeath;

    public WndResurrect(final Ankh ankh, Object causeOfDeath ) {

        super();

        instance = this;
        WndResurrect.causeOfDeath = causeOfDeath;

        IconTitle titlebar = new IconTitle();
        titlebar.icon( new ItemSprite( ankh.image(), null ) );
        titlebar.label( ankh.name() );
        titlebar.setRect( 0, 0, WIDTH, 0 );
        add( titlebar );

        BitmapTextMultiline message = PixelScene.createMultiline( TXT_MESSAGE, 6 );
        message.maxWidth = WIDTH;
        message.measure();
        message.y = titlebar.bottom() + GAP;
        add( message );

        RedButton btnYes = new RedButton( TXT_YES ) {
            @Override
            protected void onClick() {
                hide();

                Statistics.ankhsUsed++;

                InterlevelScene.mode = InterlevelScene.Mode.RESURRECT;
                Game.switchScene( InterlevelScene.class );
            }
        };
        btnYes.setRect( 0, message.y + message.height() + GAP, WIDTH, BTN_HEIGHT );
        add( btnYes );

        RedButton btnNo = new RedButton( TXT_NO ) {
            @Override
            protected void onClick() {
                hide();

//                Rankings.INSTANCE.submit( false );
                Hero.reallyDie( WndResurrect.causeOfDeath );
            }
        };
        btnNo.setRect( 0, btnYes.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add( btnNo );

        resize( WIDTH, (int)btnNo.bottom() );
    }

    @Override
    public void destroy() {
        super.destroy();
        instance = null;
    }

    @Override
    public void onBackPressed() {
    }
}
