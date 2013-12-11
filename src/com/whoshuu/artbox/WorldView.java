package com.whoshuu.artbox;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.system.SystemType;

public class WorldView extends SurfaceView implements SurfaceHolder.Callback {

    public WorldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        engine = new Engine(getHolder());
        this.setOnTouchListener(engine);
        setFocusable(true);
    }

    public void addSystems(ArrayList<EntitySystem> systems, SystemType type) {
    for (EntitySystem system : systems)
        addSystems(system, type);
    }

    public void addSystems(EntitySystem system, SystemType type) {
        engine.addSystem(system, type);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        engine.initialize(getContext());
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        super.setMeasuredDimension(size, size);
    }

    private Engine engine;
}
