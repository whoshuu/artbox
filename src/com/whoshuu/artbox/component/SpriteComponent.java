package com.whoshuu.artbox.component;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.whoshuu.artbox.artemis.utils.JSONComponent;
import com.whoshuu.artbox.util.Animation;

public class SpriteComponent extends JSONComponent {

    public SpriteComponent() {
        animations = new HashMap<String, Animation>();
    }

    public void addAnimation(String type, Animation animation) {
        animations.put(type, animation);
    }

    public Bitmap getBitmap() {
        return this.animations.get(current).getBitmap();
    }

    public Rect getSource() {
        return this.animations.get(current).getSource();
    }

    public void nextFrame() {
        this.animations.get(current).nextFrame();
    }

    @Override
    public void fromJSON(JSONObject json, float x, float y, float angle) throws JSONException {
        /*
        * {
        *   "animations": [
        *     "type": (string) string name for the animation type,
        *     "source": (String) location of asset,
        *     "frames": (int) number of frames in animation,
        *     "w": width of frame,
        *     "h": height of frame
        *   ]
        * }
        */
        JSONArray jsonAnimations = json.getJSONArray("animations");
        for (int i = 0; i < jsonAnimations.length(); i++) {
            JSONObject jsonAnimation = jsonAnimations.getJSONObject(i);
            animations.put(jsonAnimation.optString("type", "default"),
                    new Animation(jsonAnimation));
        }
        current = jsonAnimations.getJSONObject(0).optString("type", "default");
    }

    private HashMap<String, Animation> animations;
    private String current;
}
