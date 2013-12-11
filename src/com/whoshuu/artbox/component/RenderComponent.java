package com.whoshuu.artbox.component;

import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.artemis.utils.JSONComponent;

public class RenderComponent extends JSONComponent {

    public RenderComponent() {
        this(0);
    }

    public RenderComponent(int zOrder) {
        setZOrder(zOrder);
    }

    public void setZOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    public int getZOrder() {
        return this.zOrder;
    }

    @Override
    public void fromJSON(JSONObject json, float x, float y, float angle) throws JSONException {
        this.zOrder = json.getInt("z");
    }

    private int zOrder;
}
