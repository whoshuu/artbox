package com.whoshuu.artbox.system;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

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
        super.initialize();
    }

    @Override
    protected void processEntity(Entity entity) {
        Body body = bodies.get(entity).getBody();
        if (body != null) {
            Vec2 bodyPosition = body.getPosition();
            PositionComponent position = positions.get(entity);

            position.setX(bodyPosition.x);
            position.setY(bodyPosition.y);
            position.setAngle(body.getAngle());
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    private ComponentMapper<BodyComponent> bodies;
    private ComponentMapper<PositionComponent> positions;
}
