package com.example.pedometerpixeldungeon.mainsrc;

public class Challenges {

    public static final int NO_FOOD				= 1;
    public static final int NO_ARMOR			= 2;
    public static final int NO_HEALING			= 4;
    public static final int NO_HERBALISM		= 8;
    public static final int SWARM_INTELLIGENCE	= 16;
    public static final int DARKNESS			= 32;
    public static final int NO_SCROLLS			= 64;

    public static final String[] NAMES = {
            "On diet",
            "Faith is my armor",
            "Pharmacophobia",
            "Barren land",
            "Swarm intelligence",
            "Into darkness",
            "Forbidden runes"
    };

    public static final int[] MASKS = {
            NO_FOOD, NO_ARMOR, NO_HEALING, NO_HERBALISM, SWARM_INTELLIGENCE, DARKNESS, NO_SCROLLS
    };

}