package com.whoshuu.artbox;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class WorldView extends SurfaceView implements SurfaceHolder.Callback {

    public WorldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        engine = new Engine(getHolder(), context);
        setFocusable(true);
    }

    public Engine getEngine() {
        return engine;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        engine.setRunning(true);
        engine.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        engine.setRunning(false);
        while (retry) {
            try {
                engine.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    private Engine engine;
}
