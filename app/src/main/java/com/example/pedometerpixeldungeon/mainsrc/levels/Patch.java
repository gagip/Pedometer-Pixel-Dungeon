package com.example.pedometerpixeldungeon.mainsrc.levels;

import com.example.pedometerpixeldungeon.utils.Random;

public class Patch {

    private static boolean[] cur = new boolean[Level.LENGTH];
    private static boolean[] off = new boolean[Level.LENGTH];

    public static boolean[] generate( float seed, int nGen ) {

        int w = Level.WIDTH;
        int h = Level.HEIGHT;

        for (int i=0; i < Level.LENGTH; i++) {
            off[i] = Random.Float() < seed;
        }

        for (int i=0; i < nGen; i++) {

            for (int y=1; y < h-1; y++) {
                for (int x=1; x < w-1; x++) {

                    int pos = x + y * w;
                    int count = 0;
                    if (off[pos-w-1]) {
                        count++;
                    }
                    if (off[pos-w]) {
                        count++;
                    }
                    if (off[pos-w+1]) {
                        count++;
                    }
                    if (off[pos-1]) {
                        count++;
                    }
                    if (off[pos+1]) {
                        count++;
                    }
                    if (off[pos+w-1]) {
                        count++;
                    }
                    if (off[pos+w]) {
                        count++;
                    }
                    if (off[pos+w+1]) {
                        count++;
                    }

                    if (!off[pos] && count >= 5) {
                        cur[pos] = true;
                    } else if (off[pos] && count >= 4) {
                        cur[pos] = true;
                    } else {
                        cur[pos] = false;
                    }
                }
            }

            boolean[] tmp = cur;
            cur = off;
            off = tmp;
        }

        return off;
    }
}
