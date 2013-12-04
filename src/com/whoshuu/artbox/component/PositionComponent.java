package com.whoshuu.artbox.component;

import com.whoshuu.artbox.artemis.Component;

public class PositionComponent extends Component {

    public PositionComponent() {
        resetPosition();
    }

    public PositionComponent(float x, float y) {
        setPosition(x, y);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void resetPosition() {
        this.x = 0;
        this.y = 0;
    }

    private float x;
    private float y;
}
