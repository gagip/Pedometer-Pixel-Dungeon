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
package com.example.pedometerpixeldungeon.mainsrc.items.potions;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.Blob;
import com.example.pedometerpixeldungeon.mainsrc.actors.Blobs.ToxicGas;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;

public class PotionOfToxicGas extends Potion {

	{
		name = "Potion of Toxic Gas";
	}
	
	@Override
	public void shatter( int cell ) {
		if (Dungeon.visible[cell]) {
			setKnown();
			
			splash( cell );
			Sample.INSTANCE.play( Assets.SND_SHATTER );
		}
		
		GameScene.add( Blob.seed( cell, 1000, ToxicGas.class ) );
	}
	
	@Override
	public String desc() {
		return
			"Uncorking or shattering this pressurized glass will cause " +
			"its contents to explode into a deadly cloud of toxic green gas. " +
			"You might choose to fling this potion at distant enemies " +
			"instead of uncorking it by hand.";
	}
	
	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}
}
