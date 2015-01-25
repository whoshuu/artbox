package com.whoshuu.artbox.system;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.component.SpriteComponent;

public class AnimationSystem extends EntitySystem {

    @SuppressWarnings("unchecked")
    public AnimationSystem() {
        super(SpriteComponent.class);
    }

    @Override
    protected void initialize() {
        this.sprites = new ComponentMapper<SpriteComponent>(SpriteComponent.class, this.world);
        super.initialize();
    }

    @Override
    protected void processEntity(Entity entity) {
        sprites.get(entity).nextFrame();
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    private ComponentMapper<SpriteComponent> sprites;
}
