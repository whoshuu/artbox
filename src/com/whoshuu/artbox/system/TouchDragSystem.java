package com.whoshuu.artbox.system;

import org.jbox2d.dynamics.Body;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.component.BodyComponent;
import com.whoshuu.artbox.component.DragComponent;
import com.whoshuu.artbox.component.TouchComponent;
import com.whoshuu.artbox.util.SizeUtil;

public class TouchDragSystem extends EntitySystem {

    @SuppressWarnings("unchecked")
    public TouchDragSystem () {
        super(TouchComponent.class, BodyComponent.class, DragComponent.class);
    }

    @Override
    protected void initialize() {
        touches = new ComponentMapper<TouchComponent>(TouchComponent.class, this.world);
        bodies = new ComponentMapper<BodyComponent>(BodyComponent.class, this.world);
        drags = new ComponentMapper<DragComponent>(DragComponent.class, this.world);
        super.initialize();
    }

    @Override
    protected void processEntity(Entity entity) {
        Body body = bodies.get(entity).getBody();
        if (body != null) {
            TouchComponent touch = touches.get(entity);
            DragComponent drag = drags.get(entity);

            if (drag.isGrabbed()) {
                if (touch.isUp()) {
                    drag.destroyJoint();
                } else {
                    float x = SizeUtil.getGameX((float) touch.getX());
                    float y = SizeUtil.getGameY((float) touch.getY());
                    drag.updateJoint(x, y);
                }
            } else {
                if (touch.isDown()) {
                    float x = SizeUtil.getGameX((float) touch.getDownX());
                    float y = SizeUtil.getGameY((float) touch.getDownY());
                    drag.createJoint(x, y, body);
                }
            }
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    private ComponentMapper<TouchComponent> touches;
    private ComponentMapper<BodyComponent> bodies;
    private ComponentMapper<DragComponent> drags;
}
