package com.whoshuu.artbox.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.whoshuu.artbox.GameContext;
import com.whoshuu.artbox.artemis.utils.JSONComponent;

public class SpriteComponent extends JSONComponent {

    public SpriteComponent() {
        sprites = new HashMap<String, Sprite>();
    }

    public Bitmap getBitmap() {
        return current.getBitmap();
    }

    public Rect getRenderWindow() {
        return current.getRenderWindow();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        this.current = sprites.get(state);
    }

    @Override
    public void fromJSON(JSONObject json, float x, float y, float angle) throws JSONException {
    	/* 
    	 * "type": "com.whoshuu.artbox.component.SpriteComponent",
         * "sprites": [
         *   {
         *     "source": (string, required) location of asset,
         *     "state": (string, "default") string name for the animation state,
         *     "w": (int, width of source) rendering width,
         *     "h": (int, height of source) rendering height
         *   },
         *   {
         *     ...
         *   }
         * ]
         */
        JSONArray jsonSprites = json.getJSONArray("sprites");
        for (int i = 0; i < jsonSprites.length(); i++) {
            JSONObject jsonSprite = jsonSprites.getJSONObject(i);
            String state = jsonSprite.optString("state", "default");
            sprites.put(state, new Sprite(jsonSprite));
            if (i == 0) {
                setState(state);
            }
        }
    }

    private class Sprite {
        private Bitmap bitmap;
        private Rect renderWindow;

        public Sprite(JSONObject json) throws JSONException {
            InputStream stream = null;
            try {
                stream = GameContext.get().getAndroid().getAssets().open(json.getString("source"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap rawBitmap = BitmapFactory.decodeStream(stream);
            int width = rawBitmap.getWidth();
            int height = rawBitmap.getHeight();

            if (json.has("w") || json.has("h")) {
                int scaleWidth = json.optInt("w", width);
                int scaleHeight = json.optInt("h", height);
                Matrix matrix = new Matrix();
                matrix.postScale((float) scaleWidth / width, (float) scaleHeight / height);
                Bitmap bitmap = Bitmap.createBitmap(rawBitmap, 0, 0, width, height, matrix, false);
                this.bitmap = bitmap;
                this.renderWindow = new Rect(0, 0, scaleWidth, scaleHeight);
            } else {
                this.bitmap = rawBitmap;
                this.renderWindow = new Rect(0, 0, width, height);
            }
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public Rect getRenderWindow() {
            return renderWindow;
        }

        public String getState() {
            return state;
        }
    }

    private HashMap<String, Sprite> sprites;
    private Sprite current;
    private String state;
}
