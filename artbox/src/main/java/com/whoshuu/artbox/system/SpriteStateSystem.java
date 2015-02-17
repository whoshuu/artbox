package com.whoshuu.artbox.system;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.component.SpriteComponent;
import com.whoshuu.artbox.component.StateComponent;

public class SpriteStateSystem extends EntitySystem {

    @SuppressWarnings("unchecked")
    public SpriteStateSystem() {
        super(SpriteComponent.class, StateComponent.class);
    }

    @Override
    protected void initialize() {
        this.sprites = new ComponentMapper<SpriteComponent>(SpriteComponent.class, this.world);
        this.states = new ComponentMapper<StateComponent>(StateComponent.class, this.world);
        super.initialize();
    }

    @Override
    protected void processEntity(Entity entity) {
        StateComponent stateComponent = states.get(entity);
        SpriteComponent sprite = sprites.get(entity);

        if (!sprite.getState().equals(stateComponent.getState())) {
            sprite.setState(stateComponent.getState());
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    private ComponentMapper<SpriteComponent> sprites;
    private ComponentMapper<StateComponent> states;
}
