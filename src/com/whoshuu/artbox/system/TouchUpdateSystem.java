package com.whoshuu.artbox.system;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.component.BodyComponent;
import com.whoshuu.artbox.component.TouchComponent;
import com.whoshuu.artbox.util.SizeUtil;

public class TouchUpdateSystem extends EntitySystem implements TouchListener {

    @SuppressWarnings("unchecked")
    public TouchUpdateSystem() {
        super(TouchComponent.class, BodyComponent.class);
    }

    @Override
    protected void initialize() {
        this.down = false;
        this.up = false;
        this.touches = new ComponentMapper<TouchComponent>(TouchComponent.class, this.world);
        this.bodies = new ComponentMapper<BodyComponent>(BodyComponent.class, this.world);
        super.initialize();
    }

    @Override
    protected void end() {
        down = false;
        up = false;
    }

    @Override
    protected void processEntity(Entity entity) {
        // TODO: Support multiple fixtures
        Body body = bodies.get(entity).getBody();
        if (body != null) {
            TouchComponent touch = touches.get(entity);
            touch.setPosition(x, y);
            if (down) {
                if (body.getFixtureList().testPoint(new Vec2(SizeUtil.getGameX((float) xd),
                            SizeUtil.getGameY((float) yd)))) {
                    touch.setDown();
                    touch.setStart(xd, yd);
                }
            } else {
                touch.consumeDown();
            }
            if (up) {
                touch.setUp();
                touch.setEnd(xu, yu);
            } else {
                touch.consumeUp();
            }
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    public void onDown(int xd, int yd) {
        this.down = true;
        this.xd = xd;
        this.yd = yd;
        this.x = xd;
        this.y = yd;
    }

    @Override
    public void onUp(int xu, int yu) {
        this.up = true;
        this.xu = xu;
        this.yu = yu;
    }

    @Override
    public void onMove(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private boolean down;
    private boolean up;
    private int xd;
    private int yd;
    private int x;
    private int y;
    private int xu;
    private int yu;
    private ComponentMapper<TouchComponent> touches;
    private ComponentMapper<BodyComponent> bodies;
}
