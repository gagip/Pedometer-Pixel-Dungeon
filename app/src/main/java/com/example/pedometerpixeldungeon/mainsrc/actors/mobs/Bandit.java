/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.example.pedometerpixeldungeon.mainsrc.actors.mobs;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Blindness;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.sprites.mobsprites.BanditSprite;
import com.example.pedometerpixeldungeon.utils.Random;

public class Bandit extends Thief {
	
	public Item item;
	
	{
		name = "crazy bandit";
		spriteClass = BanditSprite.class;
	}
	
	@Override
	protected boolean steal( Hero hero ) {
		if (super.steal( hero )) {
			
			Buff.prolong( hero, Blindness.class, Random.Int( 5, 12 ) );
			Dungeon.observe();
			
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void die( Object cause ) {
		super.die( cause );
//		Badges.validateRare( this );
	}
}
