package com.whoshuu.artbox.artemis.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.artemis.Component;

public abstract class JSONComponent extends Component {
    public abstract void fromJSON(JSONObject json, float x, float y, float angle)
        throws JSONException;
}
