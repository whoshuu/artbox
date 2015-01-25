package com.whoshuu.artbox.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.GameContext;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.artemis.utils.JSONComponent;

public class EntityLoader {

    public static JSONObject openJsonAsset(String filename) {
        InputStream file = null;
        try {
            file = GameContext.get().getAndroid().getAssets().open(filename);
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
            int n;
            try {
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        try {
            return new JSONObject(writer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Entity fromJsonAsset(String filename, float x, float y, float angle) {
        try {
            return fromJson(openJsonAsset(filename), x, y, angle);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Entity fromJsonAsset(String filename, float x, float y) {
        try {
            return fromJson(openJsonAsset(filename), x, y);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Entity fromJsonAsset(String filename) {
        try {
            return fromJson(openJsonAsset(filename));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Entity fromJson(JSONObject json, float x, float y) throws JSONException {
        float angle = (float) json.optDouble("angle", 0.0);
        return fromJson(json, x, y, angle);
    }

    public static Entity fromJson(JSONObject json) throws JSONException {
        float x = (float) json.optDouble("x", 0.0);
        float y = (float) json.optDouble("y", 0.0);
        return fromJson(json, x, y);
    }

    @SuppressWarnings("unchecked")
    public static Entity fromJson(JSONObject json, float x, float y, float angle)
            throws JSONException {
        Entity entity = GameContext.get().getGame().createEntity();
        JSONArray jsonComponents = json.getJSONArray("Components");
        for (int i = 0; i < jsonComponents.length(); i++) {
            JSONObject jsonComponent = jsonComponents.getJSONObject(i);
            JSONComponent component = null;
            try {
                Class<? extends JSONComponent> c =
                    (Class<? extends JSONComponent>) Class.forName(jsonComponent.getString("type"));
                    component = c.newInstance();
                    component.fromJSON(jsonComponent, x, y, angle);
            } catch (ClassNotFoundException e) {
            	e.printStackTrace();
            	entity.delete();
            	return null;
            } catch (InstantiationException e) {
            	e.printStackTrace();
            	entity.delete();
            	return null;
            } catch (IllegalAccessException e) {
            	e.printStackTrace();
            	entity.delete();
            	return null;
            } catch (JSONException e) {
            	e.printStackTrace();
            	entity.delete();
            	return null;
            }
            entity.addComponent(component);
        }

        entity.refresh();
        return entity;
    }
}
