package com.example.pedometerpixeldungeon.mainsrc.ui;

import com.example.pedometerpixeldungeon.mainsrc.scenes.PixelScene;

public class CheckBox extends RedButton {

    private boolean checked = false;

    public CheckBox( String label ) {
        super( label );

        icon( Icons.get( Icons.UNCHECKED ) );
    }

    @Override
    protected void layout() {
        super.layout();

        float margin = (height - text.baseLine()) / 2;

        text.x = PixelScene.align( PixelScene.uiCamera, x + margin );
        text.y = PixelScene.align( PixelScene.uiCamera, y + margin );

        margin = (height - icon.height) / 2;

        icon.x = PixelScene.align( PixelScene.uiCamera, x + width - margin - icon.width );
        icon.y = PixelScene.align( PixelScene.uiCamera, y + margin );
    }

    public boolean checked() {
        return checked;
    }

    public void checked( boolean value ) {
        if (checked != value) {
            checked = value;
            icon.copy( Icons.get( checked ? Icons.CHECKED : Icons.UNCHECKED ) );
        }
    }

    @Override
    protected void onClick() {
        super.onClick();
        checked( !checked );
    }
}