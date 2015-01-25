package com.whoshuu.artbox.component;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;
import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.GameContext;
import com.whoshuu.artbox.artemis.utils.JSONComponent;

public class DragComponent extends JSONComponent {

    public DragComponent() {
        this.anchorB = new Vec2();
        this.grabbed = false;
        this.joint = null;
    }

    public boolean isGrabbed() {
        return this.grabbed;
    }

    public Vec2 getStartVec() {
        this.joint.getAnchorB(anchorB);
        return anchorB;
    }

    public Vec2 getEndVec() {
        return this.joint.getTarget();
    }

    public void createJoint(float x, float y, Body body) {
        if (this.joint == null) {
            MouseJointDef def = new MouseJointDef();
            def.bodyA = body;
            def.bodyB = body;
            def.target.set(x, y);
            this.joint = (MouseJoint) GameContext.get().getPhysics().createJoint(def);
            this.joint.setMaxForce(1000.0f * body.getMass());
            this.grabbed = true;
            body.setAwake(true);
        }
    }

    public void updateJoint(float x, float y) {
        if (this.joint != null) {
            this.joint.setTarget(new Vec2(x, y));
        }
    }

    public void destroyJoint() {
        GameContext.get().getPhysics().destroyJoint(this.joint);
        this.joint = null;
        this.grabbed = false;
    }

    public MouseJoint getJoint() {
        return this.joint;
    }

    @Override
    public void fromJSON(JSONObject json, float x, float y, float angle) throws JSONException {
    	/*
         * "type": "com.whoshuu.artbox.component.DragComponent"
         */
    }

    private Vec2 anchorB;
    private boolean grabbed;
    private MouseJoint joint;
}
