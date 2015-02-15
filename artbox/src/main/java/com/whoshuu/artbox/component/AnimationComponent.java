package com.whoshuu.artbox.component;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Rect;

import com.whoshuu.artbox.artemis.utils.JSONComponent;

public class AnimationComponent extends JSONComponent {

    public AnimationComponent() {
        animations = new HashMap<String, Animation>();
    }

    public int getRows() {
        return current.getRows();
    }

    public int getColumns() {
        return current.getColumns();
    }

    public int getFrame() {
        return current.getFrame();
    }

    public void nextFrame() {
        current.nextFrame();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        this.current = animations.get(state);
        this.current.resetAnimation();
    }

    @Override
    public void fromJSON(JSONObject json, float x, float y, float angle) throws JSONException {
    	/* 
    	 * "type": "com.whoshuu.artbox.component.AnimationComponent",
         * "animations": [
         *   {
         *     "state": (string, "default") string name for the animation state,
         *     "frames": (int, 1) number of frames in animation,
         *     "rows": (int, required) number of rows in sprite,
         *     "columns": (int, required) number of columns in sprite
         *   },
         *   {
         *     ...
         *   }
         * ]
         */
        JSONArray jsonAnimations = json.getJSONArray("animations");
        for (int i = 0; i < jsonAnimations.length(); i++) {
            JSONObject jsonAnimation = jsonAnimations.getJSONObject(i);
            String state = jsonAnimation.optString("state", "default");
            animations.put(state, new Animation(jsonAnimation));
            if (i == 0) {
                setState(state);
            }
        }
    }

    private class Animation {
        public Animation(JSONObject json) throws JSONException {
            rows = json.getInt("rows");
            columns = json.getInt("columns");
            frame = 0;
            frames = json.optInt("frames", 1);
        }

        public int getRows() {
            return rows;
        }

        public int getColumns() {
            return columns;
        }

        public int getFrame() {
            return frame;
        }

        public void resetAnimation() {
            frame = 0;
        }

        public void nextFrame() {
            frame++;
            if (frame == frames) {
                frame = 0;
            }
        }

        private int rows;
        private int columns;
        private int frame;
        private int frames;
    }

    private HashMap<String, Animation> animations;
    private Animation current;
    private String state;
}
