package com.whoshuu.artbox.system;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.component.AnimationComponent;
import com.whoshuu.artbox.component.SpriteComponent;

public class AnimationSystem extends EntitySystem {

    @SuppressWarnings("unchecked")
    public AnimationSystem() {
        super(AnimationComponent.class, SpriteComponent.class);
    }

    @Override
    protected void initialize() {
        this.animations = new ComponentMapper<AnimationComponent>(AnimationComponent.class,
                this.world);
        this.sprites = new ComponentMapper<SpriteComponent>(SpriteComponent.class, this.world);
        super.initialize();
    }

    @Override
    protected void processEntity(Entity entity) {
        SpriteComponent sprite = sprites.get(entity);
        AnimationComponent animation = animations.get(entity);

        Rect renderWindow = sprite.getRenderWindow();
        Bitmap bitmap = sprite.getBitmap();

        int frame = animation.getFrame();
        int rows = animation.getRows();
        int columns = animation.getColumns();
        int width = bitmap.getWidth() / columns;
        int height = bitmap.getHeight() / rows;

        int left = width * (frame % columns);
        int top = height * (frame / columns);

        renderWindow.set(left, top, left + width, top + height);

        animation.nextFrame();
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    private ComponentMapper<SpriteComponent> sprites;
    private ComponentMapper<AnimationComponent> animations;
}
