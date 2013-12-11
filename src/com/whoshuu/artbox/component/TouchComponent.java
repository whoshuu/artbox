package com.whoshuu.artbox.component;

import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.artemis.utils.JSONComponent;

public class TouchComponent extends JSONComponent {

    public TouchComponent() {
        this.consumeDown();
        this.consumeUp();
        this.setStart(0, 0);
        this.setPosition(0, 0);
        this.setEnd(0, 0);
    }

    public void consumeDown() {
        this.down = false;
    }

    public void setDown() {
        this.down = true;
    }

    public void consumeUp() {
        this.up = false;
    }

    public void setUp() {
        this.up = true;
    }

    public boolean isDown() {
        return this.down;
    }

    public boolean isUp() {
        return this.up;
    }

    public void setStart(int xd, int yd) {
        this.xd = xd;
        this.yd = yd;
    }

    public void setEnd(int xu, int yu) {
        this.xu = xu;
        this.yu = yu;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDownX() {
        return this.xd;
    }

    public int getDownY() {
        return this.yd;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getUpX() {
        return this.xu;
    }

    public int getUpY() {
        return this.yu;
    }

    @Override
    public void fromJSON(JSONObject json, float x, float y, float angle) throws JSONException {

    }

    private boolean down;
    private boolean up;
    private int xd;
    private int yd;
    private int x;
    private int y;
    private int xu;
    private int yu;
}
