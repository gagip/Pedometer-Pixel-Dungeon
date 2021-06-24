package com.example.pedometerpixeldungeon.mainsrc.actors.mobs;

import com.example.pedometerpixeldungeon.mainsrc.actors.Char;

public class Mob extends Char {
    public AiState state;

    public boolean reset() {
        return false;
    }


    public interface AiState {
        public boolean act( boolean enemyInFOV, boolean justAlerted );
        public String status();
    }


}
