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
package com.example.pedometerpixeldungeon.mainsrc.actors.Blobs;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.DungeonTilemap;
import com.example.pedometerpixeldungeon.mainsrc.Journal;
import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.FlavourBuff;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Mob;
import com.example.pedometerpixeldungeon.mainsrc.effects.BlobEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.Flare;
import com.example.pedometerpixeldungeon.mainsrc.effects.Wound;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.SacrificialParticle;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfWipeOut;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.ui.BuffIndicator;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.utils.Bundle;
import com.example.pedometerpixeldungeon.utils.Random;

public class SacrificialFire extends Blob {
	
	private static final String TXT_WORTHY		= "\"Your sacrifice is worthy...\" ";
	private static final String TXT_UNWORTHY	= "\"Your sacrifice is unworthy...\" ";
	private static final String TXT_REWARD		= "\"Your sacrifice is worthy and so you are!\" ";
	
	protected int pos;
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		
		for (int i=0; i < LENGTH; i++) {
			if (cur[i] > 0) {
				pos = i;
				break;
			}
		}
	}
	
	@Override
	protected void evolve() {
		volume = off[pos] = cur[pos];
		Char ch = Actor.findChar( pos );
		if (ch != null) {
			if (Dungeon.visible[pos] && ch.buff( Marked.class ) == null) {
				ch.sprite.emitter().burst( SacrificialParticle.FACTORY, 20 );
				Sample.INSTANCE.play( Assets.SND_BURNING );
			}
			Buff.prolong( ch, Marked.class, Marked.DURATION );
		}
		if (Dungeon.visible[pos]) {
			Journal.add( Journal.Feature.SACRIFICIAL_FIRE );
		}
	}
	
	@Override
	public void seed( int cell, int amount ) {
		cur[pos] = 0;
		pos = cell;
		volume = cur[pos] = amount;
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		
		emitter.pour( SacrificialParticle.FACTORY, 0.04f );
	}
	
	public static void sacrifice( Char ch ) {
		
		Wound.hit( ch );
		
		SacrificialFire fire = (SacrificialFire)Dungeon.level.blobs.get( SacrificialFire.class );
		if (fire != null) {
			
			int exp = 0;
			if (ch instanceof Mob) {
				exp = ((Mob)ch).exp() * Random.IntRange( 1, 3 );
			} else if (ch instanceof Hero) {
				exp = ((Hero)ch).maxExp();
			}
			
			if (exp > 0) {
				
				int volume = fire.volume - exp;
				if (volume > 0) {
					fire.seed( fire.pos, volume );
					GLog.w( TXT_WORTHY );
				} else {
					fire.seed( fire.pos, 0 );
					Journal.remove( Journal.Feature.SACRIFICIAL_FIRE );
					
					GLog.w( TXT_REWARD );
					GameScene.effect( new Flare( 7, 32 ).color( 0x66FFFF, true ).show( ch.sprite.parent, DungeonTilemap.tileCenterToWorld( fire.pos ), 2f ) );
					Dungeon.level.drop( new ScrollOfWipeOut(), fire.pos ).sprite.drop();
				}
			} else {
				
				GLog.w( TXT_UNWORTHY );
				
			}
		}
	}
	
	@Override
	public String tileDesc() {
		return "Sacrificial fire burns here. Every creature touched by this fire is marked as an offering for the spirits of the dungeon.";
	}
	
	public static class Marked extends FlavourBuff {

		public static final float DURATION	= 5f;
		
		@Override
		public int icon() {
			return BuffIndicator.SACRIFICE;
		}
		
		@Override
		public String toString() {
			return "Marked for sacrifice";
		}
		
		@Override
		public void detach() {
			if (!target.isAlive()) {
				sacrifice( target );
			}
			super.detach();
		}
	}

}
