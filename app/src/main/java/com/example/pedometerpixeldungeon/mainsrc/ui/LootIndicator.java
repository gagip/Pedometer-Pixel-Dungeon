package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.Dungeon;
import com.example.pedometerpixeldungeon.mainsrc.items.Heap;
import com.example.pedometerpixeldungeon.mainsrc.items.Item;

public class LootIndicator extends Tag {

    private ItemSlot slot;

    private Item lastItem = null;
    private int lastQuantity = 0;

    public LootIndicator() {
        super( 0x1F75CC );

        setSize( 24, 22 );

        visible = false;
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        slot = new ItemSlot() {
            protected void onClick() {
                Dungeon.hero.handle( Dungeon.hero.pos );
            };
        };
        slot.showParams( false );
        add( slot );
    }

    @Override
    protected void layout() {
        super.layout();

        slot.setRect( x + 2, y + 3, width - 2, height - 6 );
    }

    @Override
    public void update() {

        if (Dungeon.hero.ready) {
            Heap heap = Dungeon.level.heaps.get( Dungeon.hero.pos );
            if (heap != null && heap.type != Heap.Type.HIDDEN) {

                Item item =
                        heap.type == Heap.Type.CHEST || heap.type == Heap.Type.MIMIC ? ItemSlot.CHEST :
                                heap.type == Heap.Type.LOCKED_CHEST ? ItemSlot.LOCKED_CHEST :
                                        heap.type == Heap.Type.TOMB ? ItemSlot.TOMB :
                                                heap.type == Heap.Type.SKELETON ? ItemSlot.SKELETON :
                                                        heap.peek();
                if (item != lastItem || item.quantity() != lastQuantity) {
                    lastItem = item;
                    lastQuantity = item.quantity();

                    slot.item( item );
                    flash();
                }
                visible = true;

            } else {

                lastItem = null;
                visible = false;

            }
        }

        slot.enable( visible && Dungeon.hero.ready );

        super.update();
    }
}
