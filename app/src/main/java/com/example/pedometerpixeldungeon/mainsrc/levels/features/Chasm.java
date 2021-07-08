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
package com.example.pedometerpixeldungeon.mainsrc.levels.features;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.ResultDescriptions;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Buff;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Cripple;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Mob;
import com.example.pedometerpixeldungeon.mainsrc.levels.RegularLevel;
import com.example.pedometerpixeldungeon.mainsrc.levels.Room;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.scenes.InterlevelScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.mobsprites.MobSprite;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.mainsrc.windows.WndOptions;
import com.example.pedometerpixeldungeon.noosa.Camera;
import com.example.pedometerpixeldungeon.noosa.Game;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.utils.Random;

public class Chasm {
	
	private static final String TXT_CHASM	= "Chasm";
	private static final String TXT_YES		= "Yes, I know what I'm doing";
	private static final String TXT_NO		= "No, I changed my mind";
	private static final String TXT_JUMP 	= 
		"Do you really want to jump into the chasm? You can probably die.";
	
	public static boolean jumpConfirmed = false;
	
	public static void heroJump( final Hero hero ) {
		GameScene.show(
			new WndOptions( TXT_CHASM, TXT_JUMP, TXT_YES, TXT_NO ) {
				@Override
				protected void onSelect( int index ) {
					if (index == 0) {
						jumpConfirmed = true;
						hero.resume();
					}
				};
			}
		);
	}
	
	public static void heroFall( int pos ) {
		
		jumpConfirmed = false;
				
		Sample.INSTANCE.play( Assets.SND_FALLING );
		
		if (Dungeon.hero.isAlive()) {
			Dungeon.hero.interrupt();
			InterlevelScene.mode = InterlevelScene.Mode.FALL;
			if (Dungeon.level instanceof RegularLevel) {
				Room room = ((RegularLevel)Dungeon.level).room( pos );
				InterlevelScene.fallIntoPit = room != null && room.type == Room.Type.WEAK_FLOOR;
			} else {
				InterlevelScene.fallIntoPit = false;
			}
			Game.switchScene( InterlevelScene.class );
		} else {
			Dungeon.hero.sprite.visible = false;
		}
	}
	
	public static void heroLand() {
		
		Hero hero = Dungeon.hero;
		
		hero.sprite.burst( hero.sprite.blood(), 10 );
		Camera.main.shake( 4, 0.2f );
		
		Buff.prolong( hero, Cripple.class, Cripple.DURATION );
		hero.damage( Random.IntRange( hero.HT / 3, hero.HT / 2 ), new Hero.Doom() {
			@Override
			public void onDeath() {
//				Badges.validateDeathFromFalling();
				
				Dungeon.fail( Utils.format( ResultDescriptions.FALL, Dungeon.depth ) );
				GLog.n( "You fell to death..." );
			}
		} );
	}

	public static void mobFall( Mob mob ) {
		mob.destroy();
		((MobSprite)mob.sprite).fall();
	}
}
