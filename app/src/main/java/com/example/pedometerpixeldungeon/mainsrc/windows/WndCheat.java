package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.Cheat;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.ui.CheckBox;
import com.example.pedometerpixeldungeon.mainsrc.ui.RedButton;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;

/**
 * 치트창
 * @author gagip
 */
public class WndCheat extends Window {

    private static final String TXT_CHEAT_MODE = "Cheat Mode";
    private static final String TXT_MAP_CHEAT = "Map Cheat";
    private static final String TXT_STATUS_CHEAT = "Status Cheat";
    private static final String TXT_DESTROY_MONSTER_CHEAT = "Destroy Monster";

    private static final int WIDTH		= 112;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP 		= 2;

    private CheckBox btnMapCheat;
    private CheckBox btnStatCheat;
    private RedButton btnDestroyMobCheat;

    public WndCheat() {
        super();

        btnMapCheat = new CheckBox( TXT_MAP_CHEAT ) {
            @Override
            protected void onClick() {
                super.onClick();
                Cheat.mapCheat = checked();
            }
        };
        btnMapCheat.checked(Cheat.mapCheat);
        add( btnMapCheat.setRect( 0, 0, WIDTH, BTN_HEIGHT) );

        btnStatCheat = new CheckBox( TXT_STATUS_CHEAT ) {
            @Override
            protected void onClick() {
                super.onClick();
                Cheat.statCheat = checked();
                Cheat.onStatCheat(Dungeon.hero);
            }
        };
        btnStatCheat.checked(Cheat.statCheat);
        add( btnStatCheat.setRect( 0, btnMapCheat.bottom() + GAP, WIDTH, BTN_HEIGHT));

        btnDestroyMobCheat = new RedButton( TXT_DESTROY_MONSTER_CHEAT ) {
            @Override
            protected void onClick() {
                super.onClick();
                // TODO 필드 위 몬스터 제거
//                Dungeon.level.reset();
            }
        };
        add( btnDestroyMobCheat.setRect( 0, btnStatCheat.bottom() + GAP, WIDTH, BTN_HEIGHT) );


        resize(WIDTH, (int)btnDestroyMobCheat.bottom());
    }

}
