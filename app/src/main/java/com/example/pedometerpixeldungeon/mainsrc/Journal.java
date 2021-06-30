package com.example.pedometerpixeldungeon.mainsrc;

import com.example.pedometerpixeldungeon.utils.Bundlable;
import com.example.pedometerpixeldungeon.utils.Bundle;

import java.util.ArrayList;

public class Journal {

    public static enum Feature {
        WELL_OF_HEALTH			( "Well of Health" ),
        WELL_OF_AWARENESS		( "Well of Awareness" ),
        WELL_OF_TRANSMUTATION	( "Well of Transmutation" ),
        SACRIFICIAL_FIRE		( "Sacrificial chamber" ),
        ALCHEMY					( "Alchemy pot" ),
        GARDEN					( "Garden" ),
        STATUE					( "Animated statue" ),

        GHOST					( "Sad ghost" ),
        WANDMAKER				( "Old wandmaker" ),
        TROLL					( "Troll blacksmith" ),
        IMP						( "Ambitious imp" );

        public String desc;

        private Feature( String desc ) {
            this.desc = desc;
        }
    };

    public static class Record implements Comparable<Record>, Bundlable {

        private static final String FEATURE	= "feature";
        private static final String DEPTH	= "depth";

        public Feature feature;
        public int depth;

        public Record() {
        }

        public Record( Feature feature, int depth ) {
            this.feature = feature;
            this.depth = depth;
        }

        @Override
        public int compareTo( Record another ) {
            return another.depth - depth;
        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            feature = Feature.valueOf( bundle.getString( FEATURE ) );
            depth = bundle.getInt( DEPTH );
        }

        @Override
        public void storeInBundle( Bundle bundle ) {
            bundle.put( FEATURE, feature.toString() );
            bundle.put( DEPTH, depth );
        }
    }

    public static ArrayList<Record> records;

    public static void reset() {
        records = new ArrayList<Journal.Record>();
    }

    private static final String JOURNAL	= "journal";

    public static void storeInBundle( Bundle bundle ) {
        bundle.put( JOURNAL, records );
    }

    public static void restoreFromBundle( Bundle bundle ) {
        records = new ArrayList<Record>();
        for (Bundlable rec : bundle.getCollection( JOURNAL ) ) {
            records.add( (Record) rec );
        }
    }

    public static void add( Feature feature ) {
        int size = records.size();
        for (int i=0; i < size; i++) {
            Record rec = records.get( i );
            if (rec.feature == feature && rec.depth == Dungeon.depth) {
                return;
            }
        }

        records.add( new Record( feature, Dungeon.depth ) );
    }

    public static void remove( Feature feature ) {
        int size = records.size();
        for (int i=0; i < size; i++) {
            Record rec = records.get( i );
            if (rec.feature == feature && rec.depth == Dungeon.depth) {
                records.remove( i );
                return;
            }
        }
    }
}