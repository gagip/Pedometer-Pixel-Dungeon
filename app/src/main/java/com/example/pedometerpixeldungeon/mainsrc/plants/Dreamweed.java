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
package com.example.pedometerpixeldungeon.mainsrc.plants;

import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Blob;
import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.ConfusionGas;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfInvisibility;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;

public class Dreamweed extends Plant {

	private static final String TXT_DESC = 
		"Upon touching a Dreamweed it secretes a glittering cloud of confusing gas.";
	
	{
		image = 3;
		plantName = "Dreamweed";
	}
	
	@Override
	public void activate( Char ch ) {
		super.activate( ch );
		
		if (ch != null) {
			GameScene.add( Blob.seed( pos, 400, ConfusionGas.class ) );
		}
	}
	
	@Override
	public String desc() {
		return TXT_DESC;
	}
	
	public static class Seed extends Plant.Seed {
		{
			plantName = "Dreamweed";
			
			name = "seed of " + plantName;
			image = ItemSpriteSheet.SEED_DREAMWEED;
			
			plantClass = Dreamweed.class;
			alchemyClass = PotionOfInvisibility.class;
		}
		
		@Override
		public String desc() {
			return TXT_DESC;
		}
	}
}
