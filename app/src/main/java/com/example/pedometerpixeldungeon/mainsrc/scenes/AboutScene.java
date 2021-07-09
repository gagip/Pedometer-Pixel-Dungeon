package com.example.pedometerpixeldungeon.mainsrc.scenes;

import android.content.Intent;
import android.net.Uri;

import com.example.pedometerpixeldungeon.input.Touchscreen;
import com.example.pedometerpixeldungeon.mainsrc.PedometerPixelDungeon;
import com.example.pedometerpixeldungeon.mainsrc.effects.Flare;
import com.example.pedometerpixeldungeon.mainsrc.ui.Archs;
import com.example.pedometerpixeldungeon.mainsrc.ui.ExitButton;
import com.example.pedometerpixeldungeon.mainsrc.ui.Icons;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;
import com.example.pedometerpixeldungeon.noosa.BitmapTextMultiline;
import com.example.pedometerpixeldungeon.noosa.Camera;
import com.example.pedometerpixeldungeon.noosa.Game;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.noosa.TouchArea;

public class AboutScene extends PixelScene {

        private Image TUTO;


private static final String TXT =
        "Code & graphics: Watabou\n" +
        "Music: Cube_Code\n\n" +
        "\n" +
                "If you need more information:";

private static final String LNK = "github.com/gagip/Pedometer-Pixel-Dungeon/releases";

@Override
public void create() {
        super.create();

        BitmapTextMultiline text = createMultiline( TXT, 8);
        text.maxWidth = Math.min( Camera.main.width, 100 );
        text.measure();
        add( text );

        text.x = align( (Camera.main.width - text.width()) / 2 );
        text.y = align( (Camera.main.height - text.height()) / 2 );

        BitmapTextMultiline link = createMultiline( LNK, 5 );
        link.maxWidth = Math.min( Camera.main.width, 120 );
        link.measure();
        link.hardlight( Window.TITLE_COLOR );
        add( link );

        link.x = text.x;
        link.y = text.y + text.height();

        TouchArea hotArea = new TouchArea( link ) {
@Override
protected void onClick( Touchscreen.Touch touch ) {
        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://" + LNK ) );
        Game.instance.startActivity( intent );
        }
        };
        add( hotArea );



        Image wata = Icons.WATA.get();
        wata.x = align( (Camera.main.width - wata.width) / 2 );
        wata.y = text.y - wata.height - 8;
        add( wata );



        new Flare( 7, 64 ).color( 0x112233, true ).show(wata, 0 ).angularSpeed = +20;

        Archs archs = new Archs();
        archs.setSize( Camera.main.width, Camera.main.height );
        addToBack( archs );

        ExitButton btnExit = new ExitButton();
        btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
        add( btnExit );

        fadeIn();
        }

@Override
protected void onBackPressed() {
        PedometerPixelDungeon.switchNoFade( TitleScene.class );
        }
        }