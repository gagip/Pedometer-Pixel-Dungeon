package com.example.pedometerpixeldungeon.mainsrc.actors.hero;


import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.npcs.NPC;


public class HeroAction {

    public int dst;

    public static class Move extends HeroAction {
        public Move( int dst ) {
            this.dst = dst;
        }
    }

    public static class PickUp extends HeroAction {
        public PickUp( int dst ) {
            this.dst = dst;
        }
    }

    public static class OpenChest extends HeroAction {
        public OpenChest( int dst ) {
            this.dst = dst;
        }
    }

    public static class Buy extends HeroAction {
        public Buy( int dst ) {
            this.dst = dst;
        }
    }

    public static class Interact extends HeroAction {
        public NPC npc;
        public Interact( NPC npc ) {
            this.npc = npc;
        }
    }

    public static class Unlock extends HeroAction {
        public Unlock( int door ) {
            this.dst = door;
        }
    }

    public static class Descend extends HeroAction {
        public Descend( int stairs ) {
            this.dst = stairs;
        }
    }

    public static class Ascend extends HeroAction {
        public Ascend( int stairs ) {
            this.dst = stairs;
        }
    }

    public static class Cook extends HeroAction {
        public Cook( int pot ) {
            this.dst = pot;
        }
    }

    public static class Attack extends HeroAction {
        public Char target;
        public Attack( Char target ) {
            this.target = target;
        }
    }
}