package com.whoshuu.artbox.component;

import android.graphics.Bitmap;

import com.whoshuu.artbox.artemis.Component;

public class BitmapComponent extends Component {

    public BitmapComponent(Bitmap bitmap) {
        setBitmap(bitmap);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    private Bitmap bitmap;
}
