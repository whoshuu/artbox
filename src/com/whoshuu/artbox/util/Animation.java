package com.whoshuu.artbox.util;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.GameContext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Animation {

    public Animation(JSONObject json) throws JSONException {
        /*
        * {
        *   "source": (String) location of asset,
        *   "frames": (int) number of frames in animation,
        *   "w": width of frame,
        *   "h": height of frame
        * }
        */
        InputStream stream = null;
        try {
            stream = GameContext.get().getAndroid().getAssets().open(json.getString("source"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        this.frame = 0;
        this.bitmap = bitmap;
        this.frames = json.optInt("frames", 1);
        this.source = new Rect(0, 0, json.optInt("w", bitmap.getWidth()),
                json.optInt("h", bitmap.getHeight()));
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public Rect getSource() {
        return this.source;
    }

    public void nextFrame() {
        if (frames != 1) {
            frame++;
            if (frame == frames) {
                frame = 0;
                source.offsetTo(0, 0);
            } else {
                if (source.right == bitmap.getWidth()) {
                    source.offsetTo(0, source.top + source.height());
                } else {
                    source.offset(source.width(), 0);
                }
            }
        }
    }

    private Bitmap bitmap;
    private int frame;
    private int frames;
    private Rect source;
}
