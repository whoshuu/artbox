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
        JSONArray jsonEntities = json.getJSONArray("Entities");
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
