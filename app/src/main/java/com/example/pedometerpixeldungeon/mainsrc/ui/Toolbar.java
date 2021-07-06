package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.Assets;
import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.DungeonTilemap;
import com.example.pedometerpixeldungeon.mainsrc.actors.Actor;
import com.example.pedometerpixeldungeon.mainsrc.actors.mobs.Mob;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;
import com.example.pedometerpixeldungeon.mainsrc.levels.Level;
import com.example.pedometerpixeldungeon.mainsrc.plants.Plant;
import com.example.pedometerpixeldungeon.mainsrc.scenes.CellSelector;
import com.example.pedometerpixeldungeon.mainsrc.scenes.GameScene;
import com.example.pedometerpixeldungeon.mainsrc.sprites.ItemSprite;
import com.example.pedometerpixeldungeon.mainsrc.windows.WndBag;
import com.example.pedometerpixeldungeon.mainsrc.windows.WndCatalogus;
import com.example.pedometerpixeldungeon.mainsrc.windows.WndHero;
import com.example.pedometerpixeldungeon.mainsrc.windows.WndInfoCell;
import com.example.pedometerpixeldungeon.mainsrc.windows.WndInfoItem;
import com.example.pedometerpixeldungeon.mainsrc.windows.WndMessage;
import com.example.pedometerpixeldungeon.mainsrc.windows.WndTradeItem;
import com.example.pedometerpixeldungeon.noosa.Game;
import com.example.pedometerpixeldungeon.noosa.Gizmo;
import com.example.pedometerpixeldungeon.noosa.Image;
import com.example.pedometerpixeldungeon.noosa.ui.Button;
import com.example.pedometerpixeldungeon.noosa.ui.Component;

public class Toolbar extends Component {
    private Tool btnWait;
    private Tool btnSearch;
    private Tool btnInfo;
    private Tool btnInventory;
    private Tool btnPBox;
    private Tool btnPBox2;
    private Tool btnQuick1;
    private Tool btnQuick2;

    private PickedUpItem pickedUp;

    private boolean lastEnabled = true;

    private static Toolbar instance;

    public Toolbar() {
        super();

        instance = this;

        height = btnInventory.height();
    }

    @Override
    protected void createChildren() {

        add( btnWait = new Tool( 0, 7, 20, 25 ) {
            @Override
            protected void onClick() {
                Dungeon.hero.rest( false );
            };
            protected boolean onLongClick() {
                Dungeon.hero.rest( true );
                return true;
            };
        } );

        add( btnSearch = new Tool( 20, 7, 20, 25 ) {
            @Override
            protected void onClick() {
                Dungeon.hero.search( true );
            }
        } );

        add( btnInfo = new Tool( 40, 7, 21, 25 ) {
            @Override
            protected void onClick() {
                GameScene.selectCell( informer );
            }
        } );

        add( btnPBox = new Tool( 105, 7, 21, 25 ) {
            @Override
            protected void onClick() {
                Dungeon.hero.getReward();
            }
        } );

        add( btnPBox2 = new Tool( 127, 7, 21, 25 ) {
            @Override
            protected void onClick() {
               // PBox2 와 연결 필요 부분
            }
        } );



        add( btnInventory = new Tool( 60, 7, 23, 25 ) {
            private GoldIndicator gold;
            @Override
            protected void onClick() {
                GameScene.show( new WndBag( Dungeon.hero.belongings.backpack, null, WndBag.Mode.ALL, null ) );
            }
            protected boolean onLongClick() {
                GameScene.show( new WndCatalogus() );
                return true;
            };
            @Override
            protected void createChildren() {
                super.createChildren();
                gold = new GoldIndicator();
                add( gold );
            };
            @Override
            protected void layout() {
                super.layout();
                gold.fill( this );
            };
        } );

        add( btnQuick1 = new QuickslotTool( 83, 7, 22, 25, true ) );
        add( btnQuick2 = new QuickslotTool( 83, 7, 22, 25, false ) );
        btnQuick2.visible = (QuickSlot.secondaryValue != null);

        add( pickedUp = new PickedUpItem() );
    }

    @Override
    protected void layout() {
        btnWait.setPos( x, y );
        btnSearch.setPos( btnWait.right(), y );
        btnInfo.setPos( btnSearch.right(), y );
        btnPBox.setPos( btnInfo.right(), y);
        btnPBox2.setPos( btnPBox.right(), y);

        btnQuick1.setPos( width - btnQuick1.width(), y );
        if (btnQuick2.visible) {
            btnQuick2.setPos(btnQuick1.left() - btnQuick2.width(), y );
            btnInventory.setPos( btnQuick2.left() - btnInventory.width(), y );
        } else {
            btnInventory.setPos( btnQuick1.left() - btnInventory.width(), y );
        }
    }

    @Override
    public void update() {
        super.update();

        if (lastEnabled != Dungeon.hero.ready) {
            lastEnabled = Dungeon.hero.ready;

            for (Gizmo tool : members) {
                if (tool instanceof Tool) {
                    ((Tool)tool).enable( lastEnabled );
                }
            }
        }

        if (!Dungeon.hero.isAlive()) {
            btnInventory.enable( true );
        }
    }

    public void pickup( Item item ) {
        pickedUp.reset( item,
                btnInventory.centerX(),
                btnInventory.centerY() );
    }

    public static boolean secondQuickslot() {
        return instance.btnQuick2.visible;
    }

    public static void secondQuickslot( boolean value ) {
        instance.btnQuick2.visible =
                instance.btnQuick2.active =
                        value;
        instance.layout();
    }

    private static CellSelector.Listener informer = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer cell ) {

            if (cell == null) {
                return;
            }

            if (cell < 0 || cell > Level.LENGTH || (!Dungeon.level.visited[cell] && !Dungeon.level.mapped[cell])) {
                GameScene.show( new WndMessage( "You don't know what is there." ) ) ;
                return;
            }

            if (!Dungeon.visible[cell]) {
                GameScene.show( new WndInfoCell( cell ) );
                return;
            }

            if (cell == Dungeon.hero.pos) {
                GameScene.show( new WndHero() );
                return;
            }

            Mob mob = (Mob) Actor.findChar( cell );
            if (mob != null) {
//                GameScene.show( new WndInfoMob( mob ) );
                return;
            }

            Heap heap = Dungeon.level.heaps.get( cell );
            if (heap != null && heap.type != Heap.Type.HIDDEN) {
                if (heap.type == Heap.Type.FOR_SALE && heap.size() == 1 && heap.peek().price() > 0) {
                    GameScene.show( new WndTradeItem( heap, false ) );
                } else {
                    GameScene.show( new WndInfoItem( heap ) );
                }
                return;
            }

            Plant plant = Dungeon.level.plants.get( cell );
            if (plant != null) {
//                GameScene.show( new WndInfoPlant( plant ) );
                return;
            }

            GameScene.show( new WndInfoCell( cell ) );
        }
        @Override
        public String prompt() {
            return "Select a cell to examine";
        }
    };

    private static class Tool extends Button {

        private static final int BGCOLOR = 0x7B8073;

        protected Image base;

        public Tool( int x, int y, int width, int height ) {
            super();

            base.frame( x, y, width, height );

            this.width = width;
            this.height = height;
        }

        @Override
        protected void createChildren() {
            super.createChildren();

            base = new Image( Assets.TOOLBAR );
            add( base );
        }

        @Override
        protected void layout() {
            super.layout();

            base.x = x;
            base.y = y;
        }

        @Override
        protected void onTouchDown() {
            base.brightness( 1.4f );
        }

        @Override
        protected void onTouchUp() {
            if (active) {
                base.resetColor();
            } else {
                base.tint( BGCOLOR, 0.7f );
            }
        }

        public void enable( boolean value ) {
            if (value != active) {
                if (value) {
                    base.resetColor();
                } else {
                    base.tint( BGCOLOR, 0.7f );
                }
                active = value;
            }
        }
    }

    private static class QuickslotTool extends Tool {

        private QuickSlot slot;

        public QuickslotTool( int x, int y, int width, int height, boolean primary ) {
            super( x, y, width, height );
            if (primary) {
                slot.primary();
            } else {
                slot.secondary();
            }
        }

        @Override
        protected void createChildren() {
            super.createChildren();

            slot = new QuickSlot();
            add( slot );
        }

        @Override
        protected void layout() {
            super.layout();
            slot.setRect( x + 1, y + 2, width - 2, height - 2 );
        }

        @Override
        public void enable( boolean value ) {
            slot.enable( value );
            super.enable( value );
        }
    }

    private static class PickedUpItem extends ItemSprite {

        private static final float DISTANCE = DungeonTilemap.SIZE;
        private static final float DURATION = 0.2f;

        private float dstX;
        private float dstY;
        private float left;

        public PickedUpItem() {
            super();

            originToCenter();

            active =
                    visible =
                            false;
        }

        public void reset(Item item, float dstX, float dstY ) {
            view( item.image(), item.glowing() );

            active =
                    visible =
                            true;

            this.dstX = dstX - ItemSprite.SIZE / 2;
            this.dstY = dstY - ItemSprite.SIZE / 2;
            left = DURATION;

            x = this.dstX - DISTANCE;
            y = this.dstY - DISTANCE;
            alpha( 1 );
        }

        @Override
        public void update() {
            super.update();

            if ((left -= Game.elapsed) <= 0) {

                visible =
                        active =
                                false;

            } else {
                float p = left / DURATION;
                scale.set( (float)Math.sqrt( p ) );
                float offset = DISTANCE * p;
                x = dstX - offset;
                y = dstY - offset;
            }
        }
    }
}
