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
package com.example.pedometerpixeldungeon.mainsrc.items.scrolls;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Blindness;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Invisibility;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Bestiary;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Mob;
import com.example.pedometerpixeldungeon.mainsrc.effects.CellEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.Speck;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.HeroSprite;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.ArrayList;

public class ScrollOfWipeOut extends Item {

	private static final String TXT_BLINDED	= "You can't read a scroll while blinded";
	
	public static final String AC_READ	= "READ";
	
	protected static final float TIME_TO_READ	= 1f;
	
	{
		name = "Scroll of Wipe Out";
		image = ItemSpriteSheet.SCROLL_WIPE_OUT;
		
		stackable = true;		
		defaultAction = AC_READ;
	}
	
	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_READ );
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {
		if (action.equals( AC_READ )) {
			
			if (hero.buff( Blindness.class ) != null) {
				GLog.w( TXT_BLINDED );
			} else {
				curUser = hero;
				curItem = detach( hero.belongings.backpack );
				doRead();
			}
			
		} else {
		
			super.execute( hero, action );
			
		}
	}
	
	private void doRead() {
		GameScene.flash( 0xFF6644 );
		
		Invisibility.dispel();
		
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (!Bestiary.isBoss( mob )) {
				Sample.INSTANCE.play( Assets.SND_CURSED, 0.3f, 0.3f, Random.Float( 0.6f, 0.9f ) );
				mob.die( this );
			}
		}
		
		for (Heap heap : Dungeon.level.heaps.values()) {
			switch (heap.type) {
			case FOR_SALE:
				heap.type = Heap.Type.HEAP;
				if (Dungeon.visible[heap.pos]) {
					CellEmitter.center( heap.pos ).burst( Speck.factory( Speck.COIN ), 2 );
				}
				break;
			case MIMIC:
				heap.type = Heap.Type.HEAP;
				heap.sprite.link();
				Sample.INSTANCE.play( Assets.SND_CURSED, 0.3f, 0.3f, Random.Float( 0.6f, 0.9f ) );
				break;
			default:
			}
		}
		
		curUser.spend( TIME_TO_READ );
		curUser.busy();
		((HeroSprite)curUser.sprite).read();
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
	public String desc() {
		return
			"Read this scroll to unleash the wrath of the dungeon spirits, killing everything on the current level. " +
			"Well, almost everything. Some of the more powerful creatures may be not affected.";
	}
	
	@Override
	public int price() {
		return 100 * quantity;
	}
}
