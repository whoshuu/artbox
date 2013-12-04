package com.whoshuu.artbox;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.artemis.GameWorld;
import com.whoshuu.artbox.artemis.SystemManager;
import com.whoshuu.artbox.component.BitmapComponent;
import com.whoshuu.artbox.component.BodyComponent;
import com.whoshuu.artbox.component.PositionComponent;
import com.whoshuu.artbox.system.BodyPositionSystem;
import com.whoshuu.artbox.system.RenderSystem;
import com.whoshuu.artbox.system.SpriteRenderSystem;

public class Engine extends Thread {
    public Engine(SurfaceHolder holder, Context context) {
        this.holder = holder;
        this.context = context;

        // Create the physics world
        Vec2 gravity = new Vec2(0.0f, -10.0f);
        boolean sleep = true;
        physics = new World(gravity);
        physics.setAllowSleep(sleep);

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(5, -10);
        Body groundBody = physics.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(50, 11);
        groundBody.createFixture(groundBox, 0);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(5, 7);
        body = physics.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(1.0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1.0f;
        body.createFixture(fixtureDef);

        game = new GameWorld();

        initializeMap();
        initializeSystems();
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        Canvas canvas;

        //double t = 0.0;
        final float timeStep = 1.0f/60.0f;
        //double currentTime = System.currentTimeMillis() / 1000.0;
        //double newTime = 0.0;
        //double frameTime = 0.0;
        //double accumulator = 0.0;
        int positionIterations = 10;
        int velocityIterations = 10;

        game.setDelta(0);
        while (run) {
            //newTime = System.currentTimeMillis() / 1000.0;
            //frameTime = newTime - currentTime;
            //currentTime = newTime;

            //accumulator += frameTime;

            // Physics
            //while (accumulator >= timeStep) {
            physics.step(timeStep, velocityIterations, positionIterations);
            //    accumulator -= timeStep;
            //    t += timeStep;
            //}

            game.loopStart();
            game.setDelta((int) (1000 * timeStep));

            // Logic systems
            for (EntitySystem logic : logicSystems) {
                logic.process();
            }

            // Render
            canvas = null;
            try {
                canvas = holder.lockCanvas(null);
                synchronized (holder) {
                    // Render systems go here
                    draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void initializeMap() {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_launcher);
        Entity android = game.createEntity();
        android.addComponent(new PositionComponent(5, 7));
        android.addComponent(new BodyComponent(body, android));
        android.addComponent(new BitmapComponent(bitmap));
        android.refresh();
    }

    private void initializeSystems() {
        SystemManager systemManager = game.getSystemManager();
        logicSystems = new ArrayList<EntitySystem>();
        logicSystems.add(new BodyPositionSystem());

        renderSystems = new ArrayList<RenderSystem>();
        renderSystems.add(new SpriteRenderSystem());

        for (EntitySystem system : logicSystems) {
            systemManager.setSystem(system);
        }

        for (RenderSystem system : renderSystems) {
            systemManager.setSystem(system);
        }

        systemManager.initializeAll();
    }

    private void draw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(Color.rgb(187,  255, 255));
            for (RenderSystem renderer : renderSystems) {
                renderer.process(canvas);
            }
        }
    }

    private SurfaceHolder holder;
    private boolean run = false;
    private World physics;
    private GameWorld game;
    private Body body;
    private ArrayList<EntitySystem> logicSystems;
    private ArrayList<RenderSystem> renderSystems;
    private Context context;

}
