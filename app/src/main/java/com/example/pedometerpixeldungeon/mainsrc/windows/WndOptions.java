package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.ui.RedButton;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;
import com.example.pedometerpixeldungeon.noosa.BitmapTextMultiline;

public class WndOptions extends Window {

    private static final int WIDTH			= 120;
    private static final int MARGIN 		= 2;
    private static final int BUTTON_HEIGHT	= 20;

    public WndOptions( String title, String message, String... options ) {
        super();

        BitmapTextMultiline tfTitle = PixelScene.createMultiline( title, 9 );
        tfTitle.hardlight( TITLE_COLOR );
        tfTitle.x = tfTitle.y = MARGIN;
        tfTitle.maxWidth = WIDTH - MARGIN * 2;
        tfTitle.measure();
        add( tfTitle );

        BitmapTextMultiline tfMesage = PixelScene.createMultiline( message, 8 );
        tfMesage.maxWidth = WIDTH - MARGIN * 2;
        tfMesage.measure();
        tfMesage.x = MARGIN;
        tfMesage.y = tfTitle.y + tfTitle.height() + MARGIN;
        add( tfMesage );

        float pos = tfMesage.y + tfMesage.height() + MARGIN;

        for (int i=0; i < options.length; i++) {
            final int index = i;
            RedButton btn = new RedButton( options[i] ) {
                @Override
                protected void onClick() {
                    hide();
                    onSelect( index );
                }
            };
            btn.setRect( MARGIN, pos, WIDTH - MARGIN * 2, BUTTON_HEIGHT );
            add( btn );

            pos += BUTTON_HEIGHT + MARGIN;
        }

        resize( WIDTH, (int)pos );
    }

    protected void onSelect( int index ) {};
}
