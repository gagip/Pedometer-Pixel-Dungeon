package com.example.pedometerpixeldungeon.mainsrc;

import android.content.SharedPreferences;

import com.example.pedometerpixeldungeon.noosa.Game;

enum Preferences {
    INSTANCE;

    public static final String KEY_LANDSCAPE	= "landscape";
    public static final String KEY_IMMERSIVE	= "immersive";
    public static final String KEY_GOOGLE_PLAY	= "google_play";
    public static final String KEY_SCALE_UP		= "scaleup";
    public static final String KEY_MUSIC		= "music";
    public static final String KEY_SOUND_FX		= "soundfx";
    public static final String KEY_ZOOM			= "zoom";
    public static final String KEY_LAST_CLASS	= "last_class";
    public static final String KEY_CHALLENGES	= "challenges";
    public static final String KEY_DONATED		= "donated";
    public static final String KEY_INTRO		= "intro";
    public static final String KEY_BRIGHTNESS	= "brightness";

    private SharedPreferences prefs;

    private SharedPreferences get() {
        if (prefs == null) {
            prefs = Game.instance.getPreferences( Game.MODE_PRIVATE );
        }
        return prefs;
    }

    int getInt( String key, int defValue  ) {
        return get().getInt( key, defValue );
    }

    boolean getBoolean( String key, boolean defValue  ) {
        return get().getBoolean( key, defValue );
    }

    String getString( String key, String defValue  ) {
        return get().getString( key, defValue );
    }

    void put( String key, int value ) {
        get().edit().putInt( key, value ).commit();
    }

    void put( String key, boolean value ) {
        get().edit().putBoolean( key, value ).commit();
    }

    void put( String key, String value ) {
        get().edit().putString( key, value ).commit();
    }
}
