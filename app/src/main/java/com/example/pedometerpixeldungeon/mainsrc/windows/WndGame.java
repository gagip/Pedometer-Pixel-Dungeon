package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.PedometerPixelDungeon;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.scenes.InterlevelScene;
import com.example.pedometerpixeldungeon.mainsrc.scenes.TitleScene;
import com.example.pedometerpixeldungeon.mainsrc.ui.RedButton;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;
import com.example.pedometerpixeldungeon.noosa.Game;

import java.io.IOException;

public class WndGame extends Window {

    private static final String TXT_SETTINGS	= "Settings";
    private static final String TXT_CHALLEGES	= "Challenges";
    private static final String TXT_RANKINGS	= "Rankings";
    private static final String TXT_START		= "Start New Game";
    private static final String TXT_MENU		= "Main Menu";
    private static final String TXT_EXIT		= "Exit Game";
    private static final String TXT_RETURN		= "Return to Game";

    private static final int WIDTH		= 120;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP		= 2;

    private int pos;

    public WndGame() {

        super();

        addButton( new RedButton( TXT_SETTINGS ) {
            @Override
            protected void onClick() {
                hide();
                GameScene.show( new WndSettings( true ) );
            }
        } );

        if (Dungeon.challenges > 0) {
            addButton( new RedButton( TXT_CHALLEGES ) {
                @Override
                protected void onClick() {
                    hide();
//                    GameScene.show( new WndChallenges( Dungeon.challenges, false ) );
                }
            } );
        }

        if (!Dungeon.hero.isAlive()) {

            RedButton btnStart;
            addButton( btnStart = new RedButton( TXT_START ) {
                @Override
                protected void onClick() {
                    Dungeon.hero = null;
                    PedometerPixelDungeon.challenges( Dungeon.challenges );
                    InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                    InterlevelScene.noStory = true;
                    Game.switchScene( InterlevelScene.class );
                }
            } );
//            btnStart.icon( Icons.get( Dungeon.hero.heroClass ) );

            addButton( new RedButton( TXT_RANKINGS ) {
                @Override
                protected void onClick() {
                    InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
//                    Game.switchScene( RankingsScene.class );
                }
            } );
        }

        addButtons(
                new RedButton( TXT_MENU ) {
                    @Override
                    protected void onClick() {
                        try {
                            Dungeon.saveAll();
                        } catch (IOException e) {
                            // Do nothing
                        }
                        Game.switchScene( TitleScene.class );
                    }
                }, new RedButton( TXT_EXIT ) {
                    @Override
                    protected void onClick() {
                        Game.instance.finish();
                    }
                }
        );

        addButton( new RedButton( TXT_RETURN ) {
            @Override
            protected void onClick() {
                hide();
            }
        } );

        addButton( new RedButton( "Game Cheat" ) {
            @Override
            protected void onClick() {
                hide();
                GameScene.show( new WndCheat() );
            }
        } );

        resize( WIDTH, pos );
    }

    private void addButton( RedButton btn ) {
        add( btn );
        btn.setRect( 0, pos > 0 ? pos += GAP : 0, WIDTH, BTN_HEIGHT );
        pos += BTN_HEIGHT;
    }

    private void addButtons( RedButton btn1, RedButton btn2 ) {
        add( btn1 );
        btn1.setRect( 0, pos > 0 ? pos += GAP : 0, (WIDTH - GAP) / 2, BTN_HEIGHT );
        add( btn2 );
        btn2.setRect( btn1.right() + GAP, btn1.top(), WIDTH - btn1.right() - GAP, BTN_HEIGHT );
        pos += BTN_HEIGHT;
    }
}