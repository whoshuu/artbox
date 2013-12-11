package com.whoshuu.artbox.util;

public class SizeUtil {

    public static int w = 0;
    public static int h = 0;

    public static float getRenderX(float x) {
        return (float) w * (x / 10.0f);
    }

    public static float getRenderY(float y) {
        return (float) h * (1.0f - (y / 10.0f));
    }

    public static float getRenderLength(float l) {
        return getRenderX(l);
    }

    public static float getGameX(float x) {
        return 10.0f * (x / w);
    }

    public static float getGameY(float y) {
        return 10.0f * (1.0f - (y / h));
    }

    public static float getGameLength(float l) {
        return getGameX(l);
    }
}
