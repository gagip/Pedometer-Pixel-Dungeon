package com.example.pedometerpixeldungeon.mainsrc.mechanics;

import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;

public class Ballistica {

    public static int[] trace = new int[Math.max( Level.WIDTH, Level.HEIGHT )];
    public static int distance;

    public static int cast( int from, int to, boolean magic, boolean hitChars ) {

        int w = Level.WIDTH;

        int x0 = from % w;
        int x1 = to % w;
        int y0 = from / w;
        int y1 = to / w;

        int dx = x1 - x0;
        int dy = y1 - y0;

        int stepX = dx > 0 ? +1 : -1;
        int stepY = dy > 0 ? +1 : -1;

        dx = Math.abs( dx );
        dy = Math.abs( dy );

        int stepA;
        int stepB;
        int dA;
        int dB;

        if (dx > dy) {

            stepA = stepX;
            stepB = stepY * w;
            dA = dx;
            dB = dy;

        } else {

            stepA = stepY * w;
            stepB = stepX;
            dA = dy;
            dB = dx;

        }

        distance = 1;
        trace[0] = from;

        int cell = from;

        int err = dA / 2;
        while (cell != to || magic) {

            cell += stepA;

            err += dB;
            if (err >= dA) {
                err = err - dA;
                cell = cell + stepB;
            }

            trace[distance++] = cell;

            if (!Level.passable[cell] && !Level.avoid[cell]) {
                return trace[--distance - 1];
            }

            if (Level.losBlocking[cell] || (hitChars && Actor.findChar( cell ) != null)) {
                return cell;
            }
        }

        trace[distance++] = cell;

        return to;
    }
}
