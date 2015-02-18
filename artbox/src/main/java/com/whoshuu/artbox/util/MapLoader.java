package com.whoshuu.artbox.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.Engine;
import com.whoshuu.artbox.GameContext;

public class MapLoader {

    public static void fromJsonAsset(String filename) {
        InputStream file = null;
        try {
            file = GameContext.get().getAndroid().getAssets().open(filename);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int n;
            try {
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fromJson(new JSONObject(writer.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void fromJson(JSONObject json) throws JSONException {
        World physics = GameContext.get().getPhysics();
        Engine engine = GameContext.get().getEngine();
        if (json.has("gravity")) {
            // If there's gravity, set it
            JSONObject jsonGravity = json.getJSONObject("gravity");
            Vec2 gravity = new Vec2(physics.getGravity());
            gravity.x = (float) jsonGravity.optDouble("x", gravity.x);
            gravity.y = (float) jsonGravity.optDouble("y", gravity.y);
            physics.setGravity(gravity);
        }
        if (json.has("fps")) {
            // If there's fps, use it
            float fps = (float) json.getDouble("fps");
            engine.setFps(fps);
        }
        JSONArray jsonEntities = json.getJSONArray("entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
            JSONObject jsonEntity = jsonEntities.getJSONObject(i);
            if (jsonEntity.has("x") || jsonEntity.has("y")) {
                float x = (float) jsonEntity.optDouble("x", 0.0);
                float y = (float) jsonEntity.optDouble("y", 0.0);
                if (jsonEntity.has("angle")) {
                    float angle = (float) jsonEntity.getDouble("angle");
                    EntityLoader.fromJsonAsset(jsonEntity.getString("source"), x, y, angle);
                } else
                    EntityLoader.fromJsonAsset(jsonEntity.getString("source"), x, y);
            } else
                EntityLoader.fromJsonAsset(jsonEntity.getString("source"));
        }
    }
}
