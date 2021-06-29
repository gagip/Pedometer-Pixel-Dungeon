package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.gltextures.TextureCache;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.sprites.CharSprite;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.noosa.ui.Component;

public class HealthIndicator extends Component {

    private static final float HEIGHT	= 2;

    public static HealthIndicator instance;

    private Char target;

    private Image bg;
    private Image level;

    public HealthIndicator() {
        super();

        instance = this;
    }

    @Override
    protected void createChildren() {
        bg = new Image( TextureCache.createSolid( 0xFFcc0000 ) );
        bg.scale.y = HEIGHT;
        add( bg );

        level = new Image( TextureCache.createSolid( 0xFF00cc00 ) );
        level.scale.y = HEIGHT;
        add( level );
    }

    @Override
    public void update() {
        super.update();

        if (target != null && target.isAlive() && target.sprite.visible) {
            CharSprite sprite = target.sprite;
            bg.scale.x = sprite.width;
            level.scale.x = sprite.width * target.HP / target.HT;
            bg.x = level.x = sprite.x;
            bg.y = level.y = sprite.y - HEIGHT - 1;

            visible = true;
        } else {
            visible = false;
        }
    }

    public void target( Char ch ) {
        if (ch != null && ch.isAlive()) {
            target = ch;
        } else {
            target = null;
        }
    }

    public Char target() {
        return target;
    }
}
