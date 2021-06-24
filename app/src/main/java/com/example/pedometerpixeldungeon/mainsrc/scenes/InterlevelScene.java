package com.example.pedometerpixeldungeon.mainsrc.scenes;

import com.example.pedometerpixeldungeon.noosa.BitmapText;

public class InterlevelScene extends PixelScene {
    private static final float TIME_TO_FADE = 0.3f;

    private static final String TXT_DESCENDING	= "Descending...";
    private static final String TXT_ASCENDING	= "Ascending...";
    private static final String TXT_LOADING		= "Loading...";
    private static final String TXT_RESURRECTING= "Resurrecting...";
    private static final String TXT_RETURNING	= "Returning...";
    private static final String TXT_FALLING		= "Falling...";

    private static final String ERR_FILE_NOT_FOUND	= "File not found. For some reason.";
    private static final String ERR_GENERIC			= "Something went wrong..."	;

    public static enum Mode {
        DESCEND, ASCEND, CONTINUE, RESURRECT, RETURN, FALL, NONE
    };
    public static Mode mode;

    public static int returnDepth;
    public static int returnPos;

    public static boolean noStory = false;

    public static boolean fallIntoPit;

    private enum Phase {
        FADE_IN, STATIC, FADE_OUT
    };
    private Phase phase;
    private float timeLeft;

    private BitmapText message;

    private Thread thread;
    private String error = null;
}
