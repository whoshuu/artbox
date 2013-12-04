package com.whoshuu.artbox.system;

import android.graphics.Bitmap;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.component.BitmapComponent;
import com.whoshuu.artbox.component.PositionComponent;

public class SpriteRenderSystem extends RenderSystem {

    @SuppressWarnings("unchecked")
    public SpriteRenderSystem() {
        super(PositionComponent.class, BitmapComponent.class);
    }

    @Override
    protected void initialize() {
        this.positions = new ComponentMapper<PositionComponent>(PositionComponent.class,
                this.world);
        this.bitmaps = new ComponentMapper<BitmapComponent>(BitmapComponent.class, this.world);
    }

    @Override
    protected void processEntity(Entity entity) {
        PositionComponent position = positions.get(entity);
        Bitmap bitmap = bitmaps.get(entity).getBitmap();

        float x = getRenderX(position.getX());
        float y = getRenderY(position.getY());
        Bitmap bm = Bitmap.createScaledBitmap(bitmap, 50, 50, false);

        canvas.drawBitmap(bm, x - (bm.getWidth() / 2), y - (bm.getHeight() / 2), null);
    }

    private ComponentMapper<PositionComponent> positions;
    private ComponentMapper<BitmapComponent> bitmaps;
}
