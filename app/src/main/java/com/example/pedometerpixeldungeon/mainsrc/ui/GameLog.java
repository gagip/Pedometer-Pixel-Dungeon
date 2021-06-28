package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.CharSprite;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.noosa.BitmapTextMultiline;
import com.example.pedometerpixeldungeon.noosa.ui.Component;
import com.example.pedometerpixeldungeon.utils.Signal;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class GameLog extends Component implements Signal.Listener<String> {
    private static final int MAX_LINES = 3;

    private static final Pattern PUNCTUATION = Pattern.compile( ".*[.,;?! ]$" );

    private BitmapTextMultiline lastEntry;
    private int lastColor;

    private static ArrayList<Entry> entries = new ArrayList<Entry>();

    public GameLog() {
        super();
        GLog.update.add( this );

        recreateLines();
    }

    private void recreateLines() {
        for (Entry entry : entries) {
            lastEntry = PixelScene.createMultiline( entry.text, 6 );
            lastEntry.hardlight( lastColor = entry.color );
            add( lastEntry );
        }
    }

    public void newLine() {
        lastEntry = null;
    }

    @Override
    public void onSignal( String text ) {

        int color = CharSprite.DEFAULT;
        if (text.startsWith( GLog.POSITIVE )) {
            text = text.substring( GLog.POSITIVE.length() );
            color = CharSprite.POSITIVE;
        } else
        if (text.startsWith( GLog.NEGATIVE )) {
            text = text.substring( GLog.NEGATIVE.length() );
            color = CharSprite.NEGATIVE;
        } else
        if (text.startsWith( GLog.WARNING )) {
            text = text.substring( GLog.WARNING.length() );
            color = CharSprite.WARNING;
        } else
        if (text.startsWith( GLog.HIGHLIGHT )) {
            text = text.substring( GLog.HIGHLIGHT.length() );
            color = CharSprite.NEUTRAL;
        }

        text = Utils.capitalize( text ) +
                (PUNCTUATION.matcher( text ).matches() ? "" : ".");

        if (lastEntry != null && color == lastColor && lastEntry.nLines < MAX_LINES) {

            String lastMessage = lastEntry.text();
            lastEntry.text( lastMessage.length() == 0 ? text : lastMessage + " " + text );
            lastEntry.measure();

            entries.get( entries.size() - 1 ).text = lastEntry.text();

        } else {

            lastEntry = PixelScene.createMultiline( text, 6 );
            lastEntry.hardlight( color );
            lastColor = color;
            add( lastEntry );

            entries.add( new Entry( text, color ) );

        }

        if (length > 0) {
            int nLines;
            do {
                nLines = 0;
                for (int i = 0; i < length; i++) {
                    nLines += ((BitmapTextMultiline) members.get(i)).nLines;
                }

                if (nLines > MAX_LINES) {
                    remove(members.get(0));

                    entries.remove( 0 );
                }
            } while (nLines > MAX_LINES);
            if (entries.isEmpty()) {
                lastEntry = null;
            }
        }

        layout();
    }

    @Override
    protected void layout() {
        float pos = y;
        for (int i=length-1; i >= 0; i--) {
            BitmapTextMultiline entry = (BitmapTextMultiline)members.get( i );
            entry.maxWidth = (int)width;
            entry.measure();
            entry.x = x;
            entry.y = pos - entry.height();
            pos -= entry.height();
        }
    }

    @Override
    public void destroy() {
        GLog.update.remove( this );
        super.destroy();
    }

    private static class Entry {
        public String text;
        public int color;
        public Entry( String text, int color ) {
            this.text = text;
            this.color = color;
        }
    }

    public static void wipe() {
        entries.clear();
    }
}
