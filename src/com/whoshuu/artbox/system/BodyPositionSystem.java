package com.whoshuu.artbox.system;

import org.jbox2d.common.Vec2;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.component.BodyComponent;
import com.whoshuu.artbox.component.PositionComponent;

public class BodyPositionSystem extends EntitySystem {

    @SuppressWarnings("unchecked")
    public BodyPositionSystem() {
        super(PositionComponent.class, BodyComponent.class);
    }

    @Override
    protected void initialize() {
        this.bodies = new ComponentMapper<BodyComponent>(BodyComponent.class, this.world);
        this.positions = new ComponentMapper<PositionComponent>(PositionComponent.class,
                this.world);
    }

    @Override
    protected void processEntity(Entity entity) {
        Vec2 bodyPosition = bodies.get(entity).getBody().getPosition();
        PositionComponent position = positions.get(entity);

        position.setX(bodyPosition.x);
        position.setY(bodyPosition.y);
    }

    @Override
    protected boolean checkProcessing() {
        // TODO Auto-generated method stub
        return true;
    }

    private ComponentMapper<BodyComponent> bodies;
    private ComponentMapper<PositionComponent> positions;
}
