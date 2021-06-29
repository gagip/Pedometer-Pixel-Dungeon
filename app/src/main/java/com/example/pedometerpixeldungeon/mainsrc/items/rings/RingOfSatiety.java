package com.example.pedometerpixeldungeon.mainsrc.items.rings;

public class RingOfSatiety extends Ring {

    {
        name = "Ring of Satiety";
    }

    @Override
    protected RingBuff buff( ) {
        return new Satiety();
    }

    @Override
    public String desc() {
        return isKnown() ?
                "Wearing this ring you can go without food longer. Degraded rings of satiety will cause the opposite effect." :
                super.desc();
    }

    public class Satiety extends RingBuff {
    }
}
