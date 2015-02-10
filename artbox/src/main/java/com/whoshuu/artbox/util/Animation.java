package com.whoshuu.artbox.util;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.GameContext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Animation {

    public Animation(JSONObject json) throws JSONException {
        /*
        * {
        *   "type": (string) string name for the animation type,
        *   "source": (string) location of asset,
        *   "frames": (int) number of frames in animation,
        *   "sw": (float) scale factor of rendering,
        *   "sh": (float) scale factor of rendering,
        *   "w": (int) width of frame,
        *   "h": (int) height of frame
        * }
        */
        InputStream stream = null;
        try {
            stream = GameContext.get().getAndroid().getAssets().open(json.getString("source"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap rawBitmap = BitmapFactory.decodeStream(stream);
        int width = rawBitmap.getWidth();
        int height = rawBitmap.getHeight();
        double scaleWidth = json.optDouble("sw", 1);
        double scaleHeight = json.optDouble("sh", 1);
        if (scaleWidth != 1 || scaleHeight != 1) {
            Matrix matrix = new Matrix();
            matrix.postScale((float) scaleWidth, (float) scaleHeight);
            Bitmap bitmap = Bitmap.createBitmap(rawBitmap, 0, 0, width, height, matrix, false);
            this.bitmap = bitmap;
        } else {
            this.bitmap = rawBitmap;
        }
        this.frame = 0;
        this.frames = json.optInt("frames", 1);
        this.source = new Rect(0, 0, (int) (json.optInt("w", width) * scaleWidth),
                (int) (json.optInt("h", height) * scaleHeight));
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
