package com.example.pedometerpixeldungeon.mainsrc.windows;

import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.mainsrc.ui.Window;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;

public class WndPBox extends Window {

    private static final float BUTTON_WIDTH = 36;
    private static final float BUTTON_HEIGHT = 16;

    private static final float GAP = 3;

    private static final int WIDTH = 120;

    public WndPBox(final WndBag owner, final Item item) {

        super();
        IconTitle titlebar = new IconTitle();
        titlebar.icon(new ItemSprite(item.image(), item.glowing()));
        titlebar.label(Utils.capitalize(item.toString()));


    }
}
