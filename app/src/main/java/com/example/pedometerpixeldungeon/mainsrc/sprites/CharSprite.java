package com.example.pedometerpixeldungeon.mainsrc.sprites;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.DungeonTilemap;
import com.example.pedometerpixeldungeon.mainsrc.actors.Char;
import com.example.pedometerpixeldungeon.mainsrc.effects.EmoIcon;
import com.example.pedometerpixeldungeon.mainsrc.effects.FloatingText;
import com.example.pedometerpixeldungeon.mainsrc.effects.IceBlock;
import com.example.pedometerpixeldungeon.mainsrc.effects.Speck;
import com.example.pedometerpixeldungeon.mainsrc.effects.Splash;
import com.example.pedometerpixeldungeon.mainsrc.effects.TorchHalo;
import com.example.pedometerpixeldungeon.mainsrc.effects.particles.FlameParticle;
import com.example.pedometerpixeldungeon.mainsrc.items.potions.PotionOfInvisibility;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.utils.Utils;
import com.example.pedometerpixeldungeon.noosa.Game;
import com.example.pedometerpixeldungeon.noosa.MovieClip;
import com.example.pedometerpixeldungeon.noosa.Visual;
import com.example.pedometerpixeldungeon.noosa.audio.Sample;
import com.example.pedometerpixeldungeon.noosa.particles.Emitter;
import com.example.pedometerpixeldungeon.noosa.tweeners.PosTweener;
import com.example.pedometerpixeldungeon.noosa.tweeners.Tweener;
import com.example.pedometerpixeldungeon.utils.Callback;
import com.example.pedometerpixeldungeon.utils.PointF;
import com.example.pedometerpixeldungeon.utils.Random;

public class CharSprite extends MovieClip implements Tweener.Listener, MovieClip.Listener {

    public static final int DEFAULT		= 0xFFFFFF;
    public static final int POSITIVE	= 0x00FF00;
    public static final int NEGATIVE	= 0xFF0000;
    public static final int WARNING		= 0xFF8800;
    public static final int NEUTRAL		= 0xFFFF00;

    private static final float MOVE_INTERVAL	= 0.1f;
    private static final float FLASH_INTERVAL	= 0.05f;

    public enum State {
        BURNING, LEVITATING, INVISIBLE, PARALYSED, FROZEN, ILLUMINATED
    }

    protected Animation idle;
    protected Animation run;
    protected Animation attack;
    protected Animation operate;
    protected Animation zap;
    protected Animation die;

    protected Callback animCallback;

    protected Tweener motion;

    protected Emitter burning;
    protected Emitter levitation;

    protected IceBlock iceBlock;
    protected TorchHalo halo;

    protected EmoIcon emo;

    private Tweener jumpTweener;
    private Callback jumpCallback;

    private float flashTime = 0;

    protected boolean sleeping = false;

    public Char ch;

    public boolean isMoving = false;

    public CharSprite() {
        super();
        listener = this;
    }

    public void link( Char ch ) {
        this.ch = ch;
        ch.sprite = this;

        place( ch.pos );
        turnTo( ch.pos, Random.Int( Level.LENGTH ) );

        ch.updateSpriteState();
    }

    public PointF worldToCamera( int cell ) {

        final int csize = DungeonTilemap.SIZE;

        return new PointF(
                ((cell % Level.WIDTH) + 0.5f) * csize - width * 0.5f,
                ((cell / Level.WIDTH) + 1.0f) * csize - height
        );
    }

    public void place( int cell ) {
        point( worldToCamera( cell ) );
    }

    public void showStatus( int color, String text, Object... args ) {
        if (visible) {
            if (args.length > 0) {
                text = Utils.format( text, args );
            }
            if (ch != null) {
                FloatingText.show( x + width * 0.5f, y, ch.pos, text, color );
            } else {
                FloatingText.show( x + width * 0.5f, y, text, color );
            }
        }
    }

    public void idle() {
        play( idle );
    }

    public void move( int from, int to ) {
        play( run );

        motion = new PosTweener( this, worldToCamera( to ), MOVE_INTERVAL );
        motion.listener = this;
        parent.add( motion );

        isMoving = true;

        turnTo( from , to );

        if (visible && Level.water[from] && !ch.flying) {
            GameScene.ripple( from );
        }

        ch.onMotionComplete();
    }

    public void interruptMotion() {
        if (motion != null) {
            onComplete( motion );
        }
    }

    public void attack( int cell ) {
        turnTo( ch.pos, cell );
        play( attack );
    }

    public void attack( int cell, Callback callback ) {
        animCallback = callback;
        turnTo( ch.pos, cell );
        play( attack );
    }

    public void operate( int cell ) {
        turnTo( ch.pos, cell );
        play( operate );
    }

    public void zap( int cell ) {
        turnTo( ch.pos, cell );
        play( zap );
    }

    public void turnTo( int from, int to ) {
        int fx = from % Level.WIDTH;
        int tx = to % Level.WIDTH;
        if (tx > fx) {
            flipHorizontal = false;
        } else if (tx < fx) {
            flipHorizontal = true;
        }
    }

    public void jump( int from, int to, Callback callback ) {
        jumpCallback = callback;

        int distance = Level.distance( from, to );
        jumpTweener = new JumpTweener( this, worldToCamera( to ), distance * 4, distance * 0.1f );
        jumpTweener.listener = this;
        parent.add( jumpTweener );

        turnTo( from, to );
    }

    public void die() {
        sleeping = false;
        play( die );

        if (emo != null) {
            emo.killAndErase();
        }
    }

    public Emitter emitter() {
        Emitter emitter = GameScene.emitter();
        emitter.pos( this );
        return emitter;
    }

    public Emitter centerEmitter() {
        Emitter emitter = GameScene.emitter();
        emitter.pos( center() );
        return emitter;
    }

    public Emitter bottomEmitter() {
        Emitter emitter = GameScene.emitter();
        emitter.pos( x, y + height, width, 0 );
        return emitter;
    }

    public void burst( final int color, int n ) {
        if (visible) {
            Splash.at( center(), color, n );
        }
    }

    public void bloodBurstA( PointF from, int damage ) {
        if (visible) {
            PointF c = center();
            int n = (int)Math.min( 9 * Math.sqrt( (double)damage / ch.HT ), 9 );
            Splash.at( c, PointF.angle( from, c ), 3.1415926f / 2, blood(), n );
        }
    }

    public int blood() {
        return 0xFFBB0000;
    }

    public void flash() {
        ra = ba = ga = 1f;
        flashTime = FLASH_INTERVAL;
    }

    public void add( State state ) {
        switch (state) {
            case BURNING:
                burning = emitter();
                burning.pour( FlameParticle.FACTORY, 0.06f );
                if (visible) {
                    Sample.INSTANCE.play( Assets.SND_BURNING );
                }
                break;
            case LEVITATING:
                levitation = emitter();
                levitation.pour( Speck.factory( Speck.JET ), 0.02f );
                break;
            case INVISIBLE:
                PotionOfInvisibility.melt( ch );
                break;
            case PARALYSED:
                paused = true;
                break;
            case FROZEN:
                iceBlock = IceBlock.freeze( this );
                paused = true;
                break;
            case ILLUMINATED:
                GameScene.effect( halo = new TorchHalo( this ) );
                break;
        }
    }

    public void remove( State state ) {
        switch (state) {
            case BURNING:
                if (burning != null) {
                    burning.on = false;
                    burning = null;
                }
                break;
            case LEVITATING:
                if (levitation != null) {
                    levitation.on = false;
                    levitation = null;
                }
                break;
            case INVISIBLE:
                alpha( 1f );
                break;
            case PARALYSED:
                paused = false;
                break;
            case FROZEN:
                if (iceBlock != null) {
                    iceBlock.melt();
                    iceBlock = null;
                }
                paused = false;
                break;
            case ILLUMINATED:
                if (halo != null) {
                    halo.putOut();
                }
                break;
        }
    }

    @Override
    public void update() {

        super.update();

        if (paused && listener != null) {
            listener.onComplete( curAnim );
        }

        if (flashTime > 0 && (flashTime -= Game.elapsed) <= 0) {
            resetColor();
        }

        if (burning != null) {
            burning.visible = visible;
        }
        if (levitation != null) {
            levitation.visible = visible;
        }
        if (iceBlock != null) {
            iceBlock.visible = visible;
        }
        if (sleeping) {
            showSleep();
        } else {
            hideSleep();
        }
        if (emo != null) {
            emo.visible = visible;
        }
    }

    public void showSleep() {
        if (emo instanceof EmoIcon.Sleep) {

        } else {
            if (emo != null) {
                emo.killAndErase();
            }
            emo = new EmoIcon.Sleep( this );
        }
    }

    public void hideSleep() {
        if (emo instanceof EmoIcon.Sleep) {
            emo.killAndErase();
            emo = null;
        }
    }

    public void showAlert() {
        if (emo instanceof EmoIcon.Alert) {

        } else {
            if (emo != null) {
                emo.killAndErase();
            }
            emo = new EmoIcon.Alert( this );
        }
    }

    public void hideAlert() {
        if (emo instanceof EmoIcon.Alert) {
            emo.killAndErase();
            emo = null;
        }
    }

    @Override
    public void kill() {
        super.kill();

        if (emo != null) {
            emo.killAndErase();
            emo = null;
        }
    }

    @Override
    public void onComplete( Tweener tweener ) {
        if (tweener == jumpTweener) {

            if (visible && Level.water[ch.pos] && !ch.flying) {
                GameScene.ripple( ch.pos );
            }
            if (jumpCallback != null) {
                jumpCallback.call();
            }

        } else if (tweener == motion) {

            isMoving = false;

            motion.killAndErase();
            motion = null;
        }
    }

    @Override
    public void onComplete( Animation anim ) {

        if (animCallback != null) {
            animCallback.call();
            animCallback = null;
        } else {

            if (anim == attack) {

                idle();
                ch.onAttackComplete();

            } else if (anim == operate) {

                idle();
                ch.onOperateComplete();

            }

        }
    }

    private static class JumpTweener extends Tweener {

        public Visual visual;

        public PointF start;
        public PointF end;

        public float height;

        public JumpTweener(Visual visual, PointF pos, float height, float time ) {
            super( visual, time );

            this.visual = visual;
            start = visual.point();
            end = pos;

            this.height = height;
        }

        @Override
        protected void updateValues( float progress ) {
            visual.point( PointF.inter( start, end, progress ).offset( 0, -height * 4 * progress * (1 - progress) ) );
        }
    }
}
