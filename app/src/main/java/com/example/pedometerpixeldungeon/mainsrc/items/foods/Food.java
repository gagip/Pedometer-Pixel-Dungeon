package com.example.pedometerpixeldungeon.mainsrc.items.foods;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Statistics;
import com.example.pedometerpixeldungeon.mainsrc.actors.buffs.Hunger;
import com.example.pedometerpixeldungeon.mainsrc.actors.hero.Hero;
import com.example.pedometerpixeldungeon.mainsrc.effects.Speck;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.items.scrolls.ScrollOfRecharging;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSpriteSheet;
import com.example.pedometerpixeldungeon.mainsrc.sprites.SpellSprite;
import com.example.pedometerpixeldungeon.mainsrc.utils.GLog;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;

import java.util.ArrayList;

public class Food extends Item {

    private static final float TIME_TO_EAT	= 3f;

    public static final String AC_EAT	= "EAT";

    public float energy = Hunger.HUNGRY;
    public String message = "That food tasted delicious!";

    {
        stackable = true;
        name = "ration of food";
        image = ItemSpriteSheet.RATION;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_EAT );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        if (action.equals( AC_EAT )) {

            detach( hero.belongings.backpack );

            ((Hunger)hero.buff( Hunger.class )).satisfy( energy );
            GLog.i( message );

            switch (hero.heroClass) {
                case WARRIOR:
                    if (hero.HP < hero.HT) {
                        hero.HP = Math.min( hero.HP + 5, hero.HT );
                        hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
                    }
                    break;
                case MAGE:
                    hero.belongings.charge( false );
                    ScrollOfRecharging.charge( hero );
                    break;
                case ROGUE:
                case HUNTRESS:
                    break;
            }

            hero.sprite.operate( hero.pos );
            hero.busy();
            SpellSprite.show( hero, SpellSprite.FOOD );
            Sample.INSTANCE.play( Assets.SND_EAT );

            hero.spend( TIME_TO_EAT );

            Statistics.foodEaten++;
//            Badges.validateFoodEaten();

        } else {

            super.execute( hero, action );

        }
    }

    @Override
    public String info() {
        return
                "Nothing fancy here: dried meat, " +
                        "some biscuits - things like that.";
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
    public int price() {
        return 10 * quantity;
    }
}
