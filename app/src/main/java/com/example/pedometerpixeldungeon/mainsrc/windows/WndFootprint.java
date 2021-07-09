package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.noosa.BitmapText;
import com.example.pedometerpixeldungeon.noosa.Group;

public class WndFootprint extends WndTabbed {

    private static final String TXT_CURRENT = "current";
    private static final String TXT_REWARD = "reward";

    private static final String TXT_CURRENT_FP = "You have %d FP";

    private static final int WIDTH = 100;
    private static final int TAB_WIDTH = 40;

    CurrentTab currentTab;
    RewardTab rewardTab;

    public WndFootprint() {
        super();

        BitmapText txt = PixelScene.createText(
                Utils.format(TXT_CURRENT_FP, Dungeon.hero.getFootprint()), 8 );
        add( txt );

        add(new LabeledTab(TXT_CURRENT){
            @Override
            protected void select(boolean value) {
                super.select(value);
                currentTab.visible = currentTab.active = selected;
            }
        });

        add(new LabeledTab(TXT_REWARD) {
            @Override
            protected void select(boolean value) {
                super.select(value);
                rewardTab.visible = rewardTab.active = selected;
            }
        });

        for (Tab tab : tabs) {
            tab.setSize(WIDTH, tabHeight());
        }

        resize( WIDTH, (int)Math.max( currentTab.height(), rewardTab.height() ) );

    }

    private class RewardTab extends Group {
        public float height() {
            return height;
        }
    }


    private class CurrentTab extends Group {
        public float height() {
            return height;
        }
    }
}
