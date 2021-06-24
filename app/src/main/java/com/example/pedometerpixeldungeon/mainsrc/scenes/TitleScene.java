package com.example.pedometerpixeldungeon.mainsrc.scenes;

import android.opengl.GLES20;
import android.util.Log;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.PedometerPixelDungeon;
import com.example.pedometerpixeldungeon.mainsrc.effects.BannerSprites;
import com.example.pedometerpixeldungeon.mainsrc.ui.Archs;
import com.example.pedometerpixeldungeon.noosa.BitmapText;
import com.example.pedometerpixeldungeon.noosa.Camera;
import com.example.pedometerpixeldungeon.noosa.Game;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.noosa.audio.Music;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.noosa.ui.Button;

import javax.microedition.khronos.opengles.GL10;

public class TitleScene extends PixelScene {
    private static final String TXT_PLAY		= "Play";
    private static final String TXT_HIGHSCORES	= "Rankings";
    private static final String TXT_BADGES		= "Badges";
    private static final String TXT_ABOUT		= "About";

    @Override
    public void create() {

        super.create();

        Music.INSTANCE.play( Assets.THEME, true );
        Music.INSTANCE.volume( 1f );

        uiCamera.visible = false;

        int w = Camera.main.width;
        int h = Camera.main.height;
        Log.i(TAG, "Title Scene 실행");

        // Archs
        Archs archs = new Archs();
        archs.setSize( w, h );
        add( archs );


        // Title
        Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON );
        add( title );
        float height = title.height +
                (PedometerPixelDungeon.landscape() ? DashboardItem.SIZE : DashboardItem.SIZE * 2);
        title.x = (w - title.width()) / 2;
        title.y = (h - height) / 2;

//        placeTorch( title.x + 18, title.y + 20 );
//        placeTorch( title.x + title.width - 18, title.y + 20 );


        Image signs = new Image( BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON_SIGNS ) ) {
            private float time = 0;
            @Override
            public void update() {
                super.update();
                am = (float)Math.sin( -(time += Game.elapsed) );
            }
            @Override
            public void draw() {
                GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE );
                super.draw();
                GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
            }
        };
        signs.x = title.x;
        signs.y = title.y;
        add( signs );

        DashboardItem btnBadges = new DashboardItem( TXT_BADGES, 3 ) {
            @Override
            protected void onClick() {
//                PedometerPixelDungeon.switchNoFade( BadgesScene.class );
            }
        };
        add( btnBadges );

        DashboardItem btnAbout = new DashboardItem( TXT_ABOUT, 1 ) {
            @Override
            protected void onClick() {
//                PedometerPixelDungeon.switchNoFade( AboutScene.class );
            }
        };
        add( btnAbout );

        DashboardItem btnPlay = new DashboardItem( TXT_PLAY, 0 ) {
            @Override
            protected void onClick() {
                PedometerPixelDungeon.switchNoFade( StartScene.class );
            }
        };
        add( btnPlay );

        DashboardItem btnHighscores = new DashboardItem( TXT_HIGHSCORES, 2 ) {
            @Override
            protected void onClick() {
//                PedometerPixelDungeon.switchNoFade( RankingsScene.class );
            }
        };
        add( btnHighscores );

        if (PedometerPixelDungeon.landscape()) {
            float y = (h + height) / 2 - DashboardItem.SIZE;
            btnHighscores	.setPos( w / 2 - btnHighscores.width(), y );
            btnBadges		.setPos( w / 2, y );
            btnPlay			.setPos( btnHighscores.left() - btnPlay.width(), y );
            btnAbout		.setPos( btnBadges.right(), y );
        } else {
            btnBadges.setPos( w / 2 - btnBadges.width(), (h + height) / 2 - DashboardItem.SIZE );
            btnAbout.setPos( w / 2, (h + height) / 2 - DashboardItem.SIZE );
            btnPlay.setPos( w / 2 - btnPlay.width(), btnAbout.top() - DashboardItem.SIZE );
            btnHighscores.setPos( w / 2, btnPlay.top() );
        }

        BitmapText version = new BitmapText( "v " + Game.version, font1x );
        version.measure();
        version.hardlight( 0x888888 );
        version.x = w - version.width();
        version.y = h - version.height();
        add( version );

//        PrefsButton btnPrefs = new PrefsButton();
//        btnPrefs.setPos( 0, 0 );
//        add( btnPrefs );

//        ExitButton btnExit = new ExitButton();
//        btnExit.setPos( w - btnExit.width(), 0 );
//        add( btnExit );

        fadeIn();
    }

//    private void placeTorch( float x, float y ) {
//        Fireball fb = new Fireball();
//        fb.setPos( x, y );
//        add( fb );
//    }

    private static class DashboardItem extends Button {

        public static final float SIZE	= 48;

        private static final int IMAGE_SIZE	= 32;

        private Image image;
        private BitmapText label;

        public DashboardItem( String text, int index ) {
            super();

            image.frame( image.texture.uvRect( index * IMAGE_SIZE, 0, (index + 1) * IMAGE_SIZE, IMAGE_SIZE ) );
            this.label.text( text );
            this.label.measure();

            setSize( SIZE, SIZE );
        }

        @Override
        protected void createChildren() {
            super.createChildren();

            image = new Image( Assets.DASHBOARD );
            add( image );

            label = createText( 9 );
            add( label );
        }

        @Override
        protected void layout() {
            super.layout();

            image.x = align( x + (width - image.width()) / 2 );
            image.y = align( y );

            label.x = align( x + (width - label.width()) / 2 );
            label.y = align( image.y + image.height() +2 );
        }

        @Override
        protected void onTouchDown() {
            image.brightness( 1.5f );
            Sample.INSTANCE.play( Assets.SND_CLICK, 1, 1, 0.8f );
        }

        @Override
        protected void onTouchUp() {
            image.resetColor();
        }
    }
}
