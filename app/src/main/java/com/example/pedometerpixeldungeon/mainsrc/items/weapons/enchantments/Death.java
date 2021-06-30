package com.example.pedometerpixeldungeon.mainsrc.items.weapons.enchantments;

import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.ShadowParticle;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.Weapon;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.utils.Random;

public class Death extends Weapon.Enchantment {

    private static final String TXT_GRIM	= "grim %s";

    private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

    @Override
    public boolean proc(Weapon weapon, Char attacker, Char defender, int damage ) {
        // lvl 0 - 8%
        // lvl 1 ~ 9%
        // lvl 2 ~ 10%
        int level = Math.max( 0, weapon.effectiveLevel() );

        if (Random.Int( level + 100 ) >= 92) {

            defender.damage( defender.HP, this );
            defender.sprite.emitter().burst( ShadowParticle.UP, 5 );

//            if (!defender.isAlive() && attacker instanceof Hero) {
//                Badges.validateGrimWeapon();
//            }

            return true;

        } else {

            return false;

        }
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return BLACK;
    }

    @Override
    public String name( String weaponName) {
        return String.format( TXT_GRIM, weaponName );
    }

}