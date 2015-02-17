package com.whoshuu.artbox;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.whoshuu.artbox.R;
import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.system.AnimationSystem;
import com.whoshuu.artbox.system.BodyPositionSystem;
import com.whoshuu.artbox.system.DebugBodyRenderSystem;
import com.whoshuu.artbox.system.SpriteRenderSystem;
import com.whoshuu.artbox.system.SystemType;
import com.whoshuu.artbox.system.TouchUpdateSystem;

public class WorldView extends SurfaceView implements SurfaceHolder.Callback {

    public WorldView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WorldView);

        String map = "maps/level.json";
        try {
            map = a.getString(R.styleable.WorldView_map);
        } finally {
            a.recycle();
        }

        getHolder().addCallback(this);
        engine = new Engine(getHolder());
        this.setOnTouchListener(engine);

        TouchUpdateSystem touchSystem = new TouchUpdateSystem();
        engine.addTouchListener(touchSystem);
        addSystem(touchSystem, SystemType.BASE_LOGIC);

        setFocusable(true);

        addSystem(new BodyPositionSystem(), SystemType.BASE_LOGIC);
        addSystem(new AnimationSystem(), SystemType.BASE_LOGIC);
        addSystem(new SpriteRenderSystem(), SystemType.BASE_RENDER);
        addSystem(new DebugBodyRenderSystem(), SystemType.BASE_RENDER);

        engine.loadMap(map);
    }

    public void addSystems(ArrayList<EntitySystem> systems, SystemType type) {
        for (EntitySystem system : systems)
            addSystem(system, type);
    }

    public void addSystem(EntitySystem system, SystemType type) {
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
