package com.whoshuu.artbox;

import org.jbox2d.dynamics.World;

import android.content.Context;

import com.whoshuu.artbox.artemis.GameWorld;

public class GameContext {

    private GameContext(Engine engine, World physics, GameWorld game, Context android) {
        this.engine = engine;
        this.physics = physics;
        this.game = game;
        setAndroid(android);
    }

    public static void createGameContext(Engine eng, World phys, GameWorld game, Context android) {
        if (context == null) {
            context = new GameContext(eng, phys, game, android);
        }
    }

    public static GameContext get() {
        return context;
    }

    public static void destroyContext() {
        context.engine = null;
        context.physics = null;
        context.game = null;
        context.android = null;
        context = null;
    }

    public Engine getEngine() {
        return this.engine;
    }

    public World getPhysics() {
        return this.physics;
    }

    public GameWorld getGame() {
        return this.game;
    }

    public Context getAndroid() {
        return this.android;
    }

    public void setAndroid(Context android) {
        this.android = android;
    }

    private static GameContext context;

    private Engine engine;
    private World physics;
    private GameWorld game;
    private Context android;
}
