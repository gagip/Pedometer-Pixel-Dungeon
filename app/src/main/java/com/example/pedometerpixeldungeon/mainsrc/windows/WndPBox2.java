package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.ui.RedButton;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;

public class WndPBox2 extends Window {

    private static final String TXT_PBox = "Accept Box?";

    private static final int WIDTH		= 112;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP 		= 2;

    private RedButton btnPBox;

    public WndPBox2() {

        btnPBox = new RedButton( TXT_PBox ) {
            @Override
            protected void onClick() {
                super.onClick();

                // 추가 기능 작성 필요부분


            }
        };
        add( btnPBox.setRect( 0, btnPBox.bottom() + GAP, WIDTH, BTN_HEIGHT) );


    }

}