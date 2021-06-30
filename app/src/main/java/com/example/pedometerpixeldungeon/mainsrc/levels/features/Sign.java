package com.example.pedometerpixeldungeon.mainsrc.levels.features;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.effects.CellEmitter;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.ElmoParticle;
import com.example.pedometerpixeldungeon.mainsrc.levels.DeadEndLevel;
import com.example.pedometerpixeldungeon.mainsrc.levels.Terrain;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.mainsrc.windows.WndMessage;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;

public class Sign {

    private static final String TXT_DEAD_END =
            "What are you doing here?!";

    private static final String[] TIPS = {
            "Don't overestimate your strength, use weapons and armor you can handle.",
            "Not all doors in the dungeon are visible at first sight. If you are stuck, search for hidden doors.",
            "Remember, that raising your strength is not the only way to access better equipment. You can go " +
                    "the other way, lowering its strength requirement with Scrolls of Upgrade.",
            "You can spend your gold in shops on deeper levels of the dungeon. The first one is on the 6th level.",

            "Beware of Goo!",

            "Pixel-Mart - all you need for successful adventure!",
            "Identify your potions and scrolls as soon as possible. Don't put it off to the moment " +
                    "when you actually need them.",
            "Being hungry doesn't hurt, but starving does hurt.",
            "Surprise attack has a better chance to hit. For example, you can ambush your enemy behind " +
                    "a closed door when you know it is approaching.",

            "Don't let The Tengu out!",

            "Pixel-Mart. Spend money. Live longer.",
            "When you're attacked by several monsters at the same time, try to retreat behind a door.",
            "If you are burning, you can't put out the fire in the water while levitating.",
            "There is no sense in possessing more than one Ankh at the same time, because you will lose them upon resurrecting.",

            "DANGER! Heavy machinery can cause injury, loss of limbs or death!",

            "Pixel-Mart. A safer life in dungeon.",
            "When you upgrade an enchanted weapon, there is a chance to destroy that enchantment.",
            "Weapons and armors deteriorate faster than wands and rings, but there are more ways to fix them.",
            "The only way to obtain a Scroll of Wipe Out is to receive it as a gift from the dungeon spirits.",

            "No weapons allowed in the presence of His Majesty!",

            "Pixel-Mart. Special prices for demon hunters!"
    };

    private static final String TXT_BURN =
            "As you try to read the sign it bursts into greenish flames.";

    public static void read( int pos ) {

        if (Dungeon.level instanceof DeadEndLevel) {

            GameScene.show( new WndMessage( TXT_DEAD_END ) );

        } else {

            int index = Dungeon.depth - 1;

            if (index < TIPS.length) {
                GameScene.show( new WndMessage( TIPS[index] ) );
            } else {

                Dungeon.level.destroy( pos );
                GameScene.updateMap( pos );
                GameScene.discoverTile( pos, Terrain.SIGN );

                CellEmitter.get( pos ).burst( ElmoParticle.FACTORY, 6 );
                Sample.INSTANCE.play( Assets.SND_BURNING );

                GLog.w( TXT_BURN );

            }
        }
    }
}