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
package com.example.pedometerpixeldungeon.mainsrc.items.quests;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Hunger;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Bat;
import com.example.pedometerpixeldungeon.mainsrc.effects.CellEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.Speck;
import com.example.pedometerpixeldungeon.mainsrc.items.weapons.Weapon;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;
import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.utils.Bundle;
import com.example.pedometerpixeldungeon.utils.Callback;

import java.util.ArrayList;

public class Pickaxe extends Weapon {
	
	public static final String AC_MINE	= "MINE";
	
	public static final float TIME_TO_MINE = 2;
	
	private static final String TXT_NO_VEIN = "There is no dark gold vein near you to mine";
	
	private static final ItemSprite.Glowing BLOODY = new ItemSprite.Glowing( 0x550000 );
	
	{
		name = "pickaxe";
		image = ItemSpriteSheet.PICKAXE;
		
		unique = true;
		
		defaultAction = AC_MINE;
		
		STR = 14;
	}
	
	public boolean bloodStained = false;
	
	@Override
	public int min() {
		return 3;
	}
	
	@Override
	public int max() {
		return 12;
	}
	
	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_MINE );
		return actions;
	}
	
	@Override
	public void execute( final Hero hero, String action ) {
		
		if (action == AC_MINE) {
			
			if (Dungeon.depth < 11 || Dungeon.depth > 15) {
				GLog.w( TXT_NO_VEIN );
				return;
			}
			
			for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
				
				final int pos = hero.pos + Level.NEIGHBOURS8[i];
				if (Dungeon.level.map[pos] == Terrain.WALL_DECO) {
				
					hero.spend( TIME_TO_MINE );
					hero.busy();
					
					hero.sprite.attack( pos, new Callback() {
						
						@Override
						public void call() {

							CellEmitter.center( pos ).burst( Speck.factory( Speck.STAR ), 7 );
							Sample.INSTANCE.play( Assets.SND_EVOKE );
							
							Level.set( pos, Terrain.WALL );
							GameScene.updateMap( pos );
							
							DarkGold gold = new DarkGold();
							if (gold.doPickUp( Dungeon.hero )) {
								GLog.i( Hero.TXT_YOU_NOW_HAVE, gold.name() );
							} else {
								Dungeon.level.drop( gold, hero.pos ).sprite.drop();
							}
							
							Hunger hunger = hero.buff( Hunger.class );
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy( -Hunger.STARVING / 10 );
								BuffIndicator.refreshHero();
							}
							
							hero.onOperateComplete();
						}
					} );
					
					return;
				}
			}
			
			GLog.w( TXT_NO_VEIN );
			
		} else {
			
			super.execute( hero, action );
			
		}
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public void proc(Char attacker, Char defender, int damage ) {
		if (!bloodStained && defender instanceof Bat && (defender.HP <= damage)) {
			bloodStained = true;
			updateQuickslot();
		}
	}
	
	private static final String BLOODSTAINED = "bloodStained";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		
		bundle.put( BLOODSTAINED, bloodStained );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		
		bloodStained = bundle.getBoolean( BLOODSTAINED );
	}
	
	@Override
	public ItemSprite.Glowing glowing() {
		return bloodStained ? BLOODY : null;
	}
	
	@Override
	public String info() {
		return
			"This is a large and sturdy tool for breaking rocks. Probably it can be used as a weapon.";
	}
}
