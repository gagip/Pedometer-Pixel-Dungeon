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

import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Blob;
import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Web;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Poison;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Roots;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Terror;
import com.example.pedometerpixeldungeon.mainsrc.items.foods.MysteryMeat;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.mobsprites.SpinnerSprite;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.HashSet;

public class Spinner extends Mob {
	
	{
		name = "cave spinner";
		spriteClass = SpinnerSprite.class;
		
		HP = HT = 50;
		defenseSkill = 14;
		
		EXP = 9;
		maxLvl = 16;
		
		loot = new MysteryMeat();
		lootChance = 0.125f;
		
		FLEEING = new Fleeing();
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 12, 16 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 20;
	}
	
	@Override
	public int dr() {
		return 6;
	}
	
	@Override
	protected boolean act() {
		boolean result = super.act();
		
		if (state == FLEEING && buff( Terror.class ) == null) {
			if (enemy != null && enemySeen && enemy.buff( Poison.class ) == null) {
				state = HUNTING;
			}
		}
		return result;
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		if (Random.Int( 2 ) == 0) {
			Buff.affect( enemy, Poison.class ).set( Random.Int( 7, 9 ) * Poison.durationFactor( enemy ) );
			state = FLEEING;
		}
		
		return damage;
	}
	
	@Override
	public void move( int step ) {
		if (state == FLEEING) {
			GameScene.add( Blob.seed( pos, Random.Int( 5, 7 ), Web.class ) );
		}
		super.move( step );
	}
	
	@Override
	public String description() {		
		return 
			"These greenish furry cave spiders try to avoid direct combat, preferring to wait in the distance " +
			"while their victim, entangled in the spinner's excreted cobweb, slowly dies from their poisonous bite.";
	}
	
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add( Poison.class );
	}
	
	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add( Roots.class );
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff( Terror.class ) == null) {
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}
}
