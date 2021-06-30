package com.example.pedometerpixeldungeon.mainsrc.items.armors;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Fury;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Invisibility;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Paralysis;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.HeroClass;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.HeroSubClass;
import com.example.pedometerpixeldungeon.mainsrc.effects.CellEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.Speck;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.mechanics.Ballistica;
import com.example.pedometerpixeldungeon.mainsrc.scenes.CellSelector;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.noosa.Camera;
import com.example.pedometerpixeldungeon.utils.Callback;

public class WarriorArmor extends ClassArmor {

    private static int LEAP_TIME	= 1;
    private static int SHOCK_TIME	= 3;

    private static final String AC_SPECIAL = "HEROIC LEAP";

    private static final String TXT_NOT_WARRIOR	= "Only warriors can use this armor!";

    {
        name = "warrior suit of armor";
        image = ItemSpriteSheet.ARMOR_WARRIOR;
    }

    @Override
    public String special() {
        return AC_SPECIAL;
    }

    @Override
    public void doSpecial() {
        GameScene.selectCell( leaper );
    }

    @Override
    public boolean doEquip( Hero hero ) {
        if (hero.heroClass == HeroClass.WARRIOR) {
            return super.doEquip( hero );
        } else {
            GLog.w( TXT_NOT_WARRIOR );
            return false;
        }
    }

    @Override
    public String desc() {
        return
                "While this armor looks heavy, it allows a warrior to perform heroic leap towards " +
                        "a targeted location, slamming down to stun all neighbouring enemies.";
    }

    protected static CellSelector.Listener leaper = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {
            if (target != null && target != curUser.pos) {

                int cell = Ballistica.cast( curUser.pos, target, false, true );
                if (Actor.findChar( cell ) != null && cell != curUser.pos) {
                    cell = Ballistica.trace[Ballistica.distance - 2];
                }

                curUser.HP -= (curUser.HP / 3);
                if (curUser.subClass == HeroSubClass.BERSERKER && curUser.HP <= curUser.HT * Fury.LEVEL) {
                    Buff.affect( curUser, Fury.class );
                }

                Invisibility.dispel();

                final int dest = cell;
                curUser.busy();
                curUser.sprite.jump( curUser.pos, cell, new Callback() {
                    @Override
                    public void call() {
                        curUser.move( dest );
                        Dungeon.level.press( dest, curUser );
                        Dungeon.observe();

                        for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
                            Char mob = Actor.findChar( curUser.pos + Level.NEIGHBOURS8[i] );
                            if (mob != null && mob != curUser) {
                                Buff.prolong( mob, Paralysis.class, SHOCK_TIME );
                            }
                        }

                        CellEmitter.center( dest ).burst( Speck.factory( Speck.DUST ), 10 );
                        Camera.main.shake( 2, 0.5f );

                        curUser.spendAndNext( LEAP_TIME );
                    }
                } );
            }
        }

        @Override
        public String prompt() {
            return "Choose direction to leap";
        }
    };
}