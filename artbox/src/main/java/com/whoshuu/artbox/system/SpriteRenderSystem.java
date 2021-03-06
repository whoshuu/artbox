package com.whoshuu.artbox.system;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.component.SpriteComponent;
import com.whoshuu.artbox.component.PositionComponent;
import com.whoshuu.artbox.component.RenderComponent;
import com.whoshuu.artbox.util.SizeUtil;

public class SpriteRenderSystem extends RenderSystem {

    @SuppressWarnings("unchecked")
    public SpriteRenderSystem() {
        super(PositionComponent.class, SpriteComponent.class, RenderComponent.class);
    }

    @Override
    protected void initialize() {
        dest = new Rect();
        this.positions = new ComponentMapper<PositionComponent>(PositionComponent.class,
                this.world);
        this.sprites = new ComponentMapper<SpriteComponent>(SpriteComponent.class, this.world);
        super.initialize();
    }

    @Override
    protected void processEntity(Entity entity) {
        PositionComponent position = positions.get(entity);
        SpriteComponent spriteComponent = sprites.get(entity);
        Bitmap bitmap = spriteComponent.getBitmap();
        Rect renderWindow = spriteComponent.getRenderWindow();

        float x = SizeUtil.getRenderX(position.getX());
        float y = SizeUtil.getRenderY(position.getY());

        dest.set((int) x - (renderWindow.width() / 2),
                 (int) y - (renderWindow.height() / 2),
                 (int) x + (renderWindow.width() / 2),
                 (int) y + (renderWindow.height() / 2));

        canvas.rotate((float) - Math.toDegrees(position.getAngle()), x, y);
        canvas.drawBitmap(bitmap, renderWindow, dest, null); 
        canvas.rotate((float) Math.toDegrees(position.getAngle()), x, y);
    }

    private Rect dest;
    private ComponentMapper<PositionComponent> positions;
    private ComponentMapper<SpriteComponent> sprites;
}
