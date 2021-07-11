package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.items.Gold;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.mainsrc.ui.RedButton;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.noosa.BitmapText;
import com.example.pedometerpixeldungeon.noosa.Group;

import java.util.Locale;


/**
 * 발자국(FP) 관련 윈도우 창
 * @author gagip
 */
public class WndFootprint extends WndTabbed {

    private static final String TXT_REWARD = "reward";
    private static final String TXT_SHOP = "shop";

    private static final int WIDTH = 100;
    private static final int TAB_WIDTH = 40;

    RewardTab rewardTab;
    ShopTab shopTab;

    public WndFootprint() {
        super();


        // 탭 생성
        rewardTab = new RewardTab();
        add(rewardTab);
        shopTab = new ShopTab();
        add(shopTab);

        // 탭 등록
        add(new LabeledTab(TXT_REWARD) {
            @Override
            protected void select(boolean value) {
                super.select(value);
                rewardTab.visible = rewardTab.active = selected;
            }
        });
        add(new LabeledTab(TXT_SHOP){
            @Override
            protected void select(boolean value) {
                super.select(value);
                shopTab.visible = shopTab.active = selected;
            }
        });

        // 탭 크기 결정
        for (Tab tab : tabs) {
            tab.setSize(TAB_WIDTH, tabHeight());
        }

        resize( WIDTH, (int)Math.max( shopTab.height(), rewardTab.height() ) );

        // 기본 설정 탭
        select(0);
    }

    private class RewardTab extends Group {
        private static final String TXT_CAN_GET_REWARD = "You can have %d FP";

        private static final String TXT_BTN_REWARD = "Get Reward";
        private static final String TXT_BTN_AD = "View AD";

        private static final int GAP = 5;

        private float pos;

        public RewardTab() {
            Hero hero = Dungeon.hero;
            int reward = hero.calculateReward();
            // 받을 수 있는 보상 표시
            BitmapText title = PixelScene.createText(
                    Utils.format( TXT_CAN_GET_REWARD, reward).toUpperCase( Locale.ENGLISH ), 9 );
            title.hardlight( TITLE_COLOR );
            title.measure();
            add( title );
            

            // 보상 받기 버튼
            RedButton rewardBtn = new RedButton(TXT_BTN_REWARD) {
                @Override
                protected void onClick() {
                    super.onClick();
                    hero.acceptReward(reward);
                }
            };
            rewardBtn.setRect(0, title.y + title.height() + GAP,
                                rewardBtn.reqWidth()+4, rewardBtn.reqHeight()+4);
            add(rewardBtn);
            // 광고 버튼
            RedButton viewAdBtn = new RedButton(TXT_BTN_AD) {
                @Override
                protected void onClick() {
                    super.onClick();
                    // TODO 광고
                }
            };
            viewAdBtn.setRect(rewardBtn.right()+GAP, title.height()+GAP,
                                viewAdBtn.reqWidth()+4, viewAdBtn.reqHeight()+4);
            add(viewAdBtn);

            pos += rewardBtn.bottom() + GAP;
        }

        public float height() {
            return pos;
        }
    }


    private class ShopTab extends Group {



        private float pos;

        public ShopTab() {
            createItemInfo(new Gold(), 100);

        }

        public float height() {
            return pos;
        }

        public void createItemInfo(Item item, int price) {
            Image
        }
    }
}
