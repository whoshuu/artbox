package com.whoshuu.artbox.component;

import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.artemis.utils.JSONComponent;

public class PositionComponent extends JSONComponent {

    public PositionComponent() {
        this(0, 0, 0);
    }

    public PositionComponent(float x, float y, float angle) {
        setPosition(x, y, angle);
    }

    public void setPosition(float x, float y, float angle) {
        this.setX(x);
        this.setY(y);
        this.setAngle(angle);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getAngle() {
        return this.angle;
    }

    @Override
    public void fromJSON(JSONObject json, float x, float y, float angle) throws JSONException {
        this.setPosition(x, y, angle);
    }

    private float x;
    private float y;
    private float angle;
}
