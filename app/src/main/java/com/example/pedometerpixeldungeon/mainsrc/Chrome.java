package com.example.pedometerpixeldungeon.mainsrc;

import com.example.pedometerpixeldungeon.noosa.NinePatch;

public class Chrome {
    public enum  Type {
        TOAST,
        TOAST_TR,
        WINDOW,
        BUTTON,
        TAG,
        SCROLL,
        TAB_SET,
        TAB_SELECTED,
        TAB_UNSELECTED
    };

    public static NinePatch get( Type type ) {
        switch (type) {
            case WINDOW:
                return new NinePatch( Assets.CHROME, 0, 0, 22, 22, 7 );
            case TOAST:
                return new NinePatch( Assets.CHROME, 22, 0, 18, 18, 5 );
            case TOAST_TR:
                return new NinePatch( Assets.CHROME, 40, 0, 18, 18, 5 );
            case BUTTON:
                return new NinePatch( Assets.CHROME, 58, 0, 6, 6, 2 );
            case TAG:
                return new NinePatch( Assets.CHROME, 22, 18, 16, 14, 3 );
            case SCROLL:
                return new NinePatch( Assets.CHROME, 32, 32, 32, 32, 5, 11, 5, 11 );
            case TAB_SET:
                return new NinePatch( Assets.CHROME, 64, 0, 22, 22, 7, 7, 7, 7 );
            case TAB_SELECTED:
                return new NinePatch( Assets.CHROME, 64, 22, 10, 14, 4, 7, 4, 6 );
            case TAB_UNSELECTED:
                return new NinePatch( Assets.CHROME, 74, 22, 10, 14, 4, 7, 4, 6 );
            default:
                return null;
        }
    }
}
