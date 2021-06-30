package com.example.pedometerpixeldungeon.mainsrc.items.weapons.enchantments;

import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Burning;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.FlameParticle;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.Weapon;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.utils.Random;

public class Fire extends Weapon.Enchantment {

    private static final String TXT_BLAZING	= "blazing %s";

    private static ItemSprite.Glowing ORANGE = new ItemSprite.Glowing( 0xFF4400 );

    @Override
    public boolean proc(Weapon weapon, Char attacker, Char defender, int damage ) {
        // lvl 0 - 33%
        // lvl 1 - 50%
        // lvl 2 - 60%
        int level = Math.max( 0, weapon.effectiveLevel() );

        if (Random.Int( level + 3 ) >= 2) {

            if (Random.Int( 2 ) == 0) {
                Buff.affect( defender, Burning.class ).reignite( defender );
            }
            defender.damage( Random.Int( 1, level + 2 ), this );

            defender.sprite.emitter().burst( FlameParticle.FACTORY, level + 1 );

            return true;

        } else {

            return false;

        }
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return ORANGE;
    }

    @Override
    public String name( String weaponName ) {
        return String.format( TXT_BLAZING, weaponName );
    }

}
