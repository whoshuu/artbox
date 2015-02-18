package com.whoshuu.artbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;

import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.artemis.GameWorld;
import com.whoshuu.artbox.artemis.SystemManager;
import com.whoshuu.artbox.system.RenderSystem;
import com.whoshuu.artbox.system.SystemType;
import com.whoshuu.artbox.system.TouchListener;
import com.whoshuu.artbox.util.MapLoader;
import com.whoshuu.artbox.util.SizeUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;

public class Engine extends Thread implements OnTouchListener {

    public Engine(SurfaceHolder holder) {
        this.holder = holder;

        // Create the physics world
        Vec2 gravity = new Vec2(0, -10.0f);
        boolean sleep = true;
        physics = new World(gravity);
        physics.setAllowSleep(sleep);

        // Create the game world
        game = new GameWorld();

        touchListeners = new ArrayList<TouchListener>();
        systems = new ArrayList<ArrayList<EntitySystem>>(SystemType.values().length);
        for (int i = 0; i < SystemType.values().length; i++) {
            systems.add(new ArrayList<EntitySystem>());
        }
    }

    public void initialize(Context context) {
        GameContext.createGameContext(this, physics, game, context);
        initializeMap();
        initializeSystems();
    }

    public void setFps(float fps) {
        timeStep = 1.0f/fps;
    }

    public void loadMap(String map) {
        this.map = map;
    }

    public void addTouchListener(TouchListener listener) {
        touchListeners.add(listener);
    }

    public void addSystem(EntitySystem system, SystemType type) {
        systems.get(type.ordinal()).add(system);
    }

    public void setRunning(boolean run) {
        if (run == false) {
            GameContext.destroyContext();
        }
        this.run = run;
    }

    @Override
    public void run() {
        Canvas canvas;

        int positionIterations = 10;
        int velocityIterations = 10;

        game.setDelta(0);
        while (run) {
            physics.step(timeStep, velocityIterations, positionIterations);

            game.loopStart();
            game.setDelta((int) (1000 * timeStep));

            // Logic systems
            for (int i = 0; i < systems.size(); i++) {
                if ((i == SystemType.PRE_LOGIC.ordinal() ||
                     i == SystemType.BASE_LOGIC.ordinal() ||
                     i == SystemType.POST_LOGIC.ordinal()) && !systems.get(i).isEmpty()) {
                    for (int j = 0; j < systems.get(i).size(); j++) {
                        systems.get(i).get(j).process();
                    }
                }
            }

            // Render
            canvas = null;
            try {
                canvas = holder.lockCanvas(null);
                draw(canvas);
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void initializeMap() {
        MapLoader.fromJsonAsset(map);
    }

    private void initializeSystems() {
        SystemManager systemManager = game.getSystemManager();

        for (int i = 0; i < systems.size(); i++) {
            for (int j = 0; j < systems.get(i).size(); j++) {
                systemManager.setSystem(systems.get(i).get(j));
            }
        }

        systemManager.initializeAll();
    }

    private void draw(Canvas canvas) {
        if (canvas != null) {
            SizeUtil.w = canvas.getWidth();
            SizeUtil.h = canvas.getHeight();
            canvas.drawColor(Color.rgb(200, 200, 200));
            for (int i = 0; i < systems.size(); i++) {
                if ((i == SystemType.PRE_RENDER.ordinal() ||
                     i == SystemType.BASE_RENDER.ordinal() ||
                     i == SystemType.POST_RENDER.ordinal()) && !systems.get(i).isEmpty()) {
                    for (int j = 0; j < systems.get(i).size(); j++) {
                        ((RenderSystem) systems.get(i).get(j)).process(canvas);
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < touchListeners.size(); i++)
                    touchListeners.get(i).onDown((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < touchListeners.size(); i++)
                    touchListeners.get(i).onUp((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < touchListeners.size(); i++)
                    touchListeners.get(i).onMove((int) event.getX(), (int) event.getY());
                break;
            default:
                break;
        }
        return true;
    }

    private SurfaceHolder holder;
    private boolean run = false;
    private World physics;
    private GameWorld game;
    private ArrayList<TouchListener> touchListeners;
    private ArrayList<ArrayList<EntitySystem>> systems;
    private String map;
    private float timeStep = 1.0f/30.0f;
}
