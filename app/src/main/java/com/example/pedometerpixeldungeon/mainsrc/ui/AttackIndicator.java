package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Mob;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.CharSprite;
import com.example.pedometerpixeldungeon.utils.Random;

import java.util.ArrayList;

public class AttackIndicator extends Tag {

    private static final float ENABLED	= 1.0f;
    private static final float DISABLED	= 0.3f;

    private static AttackIndicator instance;

    private CharSprite sprite = null;

    private static Mob lastTarget = null;
    private ArrayList<Mob> candidates = new ArrayList<Mob>();

    public AttackIndicator() {
        super( DangerIndicator.COLOR );

        instance = this;

        setSize( 24, 24 );
        visible( false );
        enable( false );
    }

    @Override
    protected void createChildren() {
        super.createChildren();
    }

    @Override
    protected void layout() {
        super.layout();

        if (sprite != null) {
            sprite.x = x + (width - sprite.width()) / 2;
            sprite.y = y + (height - sprite.height()) / 2;
            PixelScene.align( sprite );
        }
    }

    @Override
    public void update() {
        super.update();

        if (Dungeon.hero.isAlive()) {

            if (!Dungeon.hero.ready) {
                enable( false );
            }

        } else {
            visible( false );
            enable( false );
        }
    }

    private void checkEnemies() {

        int heroPos = Dungeon.hero.pos;
        candidates.clear();
        int v = Dungeon.hero.visibleEnemies();
        for (int i=0; i < v; i++) {
            Mob mob = Dungeon.hero.visibleEnemy( i );
            if (Level.adjacent( heroPos, mob.pos )) {
                candidates.add( mob );
            }
        }

        if (!candidates.contains( lastTarget )) {
            if (candidates.isEmpty()) {
                lastTarget = null;
            } else {
                lastTarget = Random.element( candidates );
                updateImage();
                flash();
            }
        } else {
            if (!bg.visible) {
                flash();
            }
        }

        visible( lastTarget != null );
        enable( bg.visible );
    }

    private void updateImage() {

        if (sprite != null) {
            sprite.killAndErase();
            sprite = null;
        }

        try {
            sprite = lastTarget.spriteClass.newInstance();
            sprite.idle();
            sprite.paused = true;
            add( sprite );

            sprite.x = x + (width - sprite.width()) / 2 + 1;
            sprite.y = y + (height - sprite.height()) / 2;
            PixelScene.align( sprite );

        } catch (Exception e) {
        }
    }

    private boolean enabled = true;
    private void enable( boolean value ) {
        enabled = value;
        if (sprite != null) {
            sprite.alpha( value ? ENABLED : DISABLED );
        }
    }

    private void visible( boolean value ) {
        bg.visible = value;
        if (sprite != null) {
            sprite.visible = value;
        }
    }

    @Override
    protected void onClick() {
        if (enabled) {
            Dungeon.hero.handle( lastTarget.pos );
        }
    }

    public static void target( Char target ) {
        lastTarget = (Mob)target;
        instance.updateImage();

        HealthIndicator.instance.target( target );
    }

    public static void updateState() {
        instance.checkEnemies();
    }
}