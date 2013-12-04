package com.whoshuu.artbox.system;

import android.graphics.Canvas;

import com.whoshuu.artbox.artemis.Component;
import com.whoshuu.artbox.artemis.EntitySystem;

public abstract class RenderSystem extends EntitySystem {

    public RenderSystem(Class<? extends Component>... types) {
        super(types);
    }

    public final void process(Canvas canvas) {
        this.canvas = canvas;
        process();
    }

    protected float getRenderX(float x) {
        return (float) canvas.getWidth() * (x / 10.0f);
    }

    protected float getRenderY(float y) {
        return (float) canvas.getHeight() * (1.0f - (y / 10.0f));
    }

    @Override
    protected void end() {
        this.canvas = null;
        super.end();
    }

    @Override
    protected boolean checkProcessing() {
        return this.canvas != null;
    }

    protected Canvas canvas;
}
