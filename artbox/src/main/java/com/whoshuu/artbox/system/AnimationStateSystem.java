package com.whoshuu.artbox.system;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.component.AnimationComponent;
import com.whoshuu.artbox.component.StateComponent;

public class AnimationStateSystem extends EntitySystem {

    @SuppressWarnings("unchecked")
    public AnimationStateSystem() {
        super(AnimationComponent.class, StateComponent.class);
    }

    @Override
    protected void initialize() {
        this.animations = new ComponentMapper<AnimationComponent>(AnimationComponent.class,
                this.world);
        this.states = new ComponentMapper<StateComponent>(StateComponent.class, this.world);
        super.initialize();
    }

    @Override
    protected void processEntity(Entity entity) {
        StateComponent stateComponent = states.get(entity);
        AnimationComponent animation = animations.get(entity);

        if (!animation.getState().equals(stateComponent.getState())) {
            animation.setState(stateComponent.getState());
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    private ComponentMapper<AnimationComponent> animations;
    private ComponentMapper<StateComponent> states;
}
