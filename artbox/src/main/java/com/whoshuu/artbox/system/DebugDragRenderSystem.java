package com.whoshuu.artbox.system;

import org.jbox2d.common.Vec2;

import android.graphics.Color;
import android.graphics.Paint;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.component.DragComponent;
import com.whoshuu.artbox.component.RenderComponent;
import com.whoshuu.artbox.util.SizeUtil;

public class DebugDragRenderSystem extends RenderSystem {

    @SuppressWarnings("unchecked")
    public DebugDragRenderSystem() {
        super(DragComponent.class, RenderComponent.class);
    }

    @Override
    protected void initialize() {
        super.initialize();
        paint = new Paint();
        paint.setStrokeWidth(3.0f);
        paint.setColor(Color.CYAN);
        this.drags = new ComponentMapper<DragComponent>(DragComponent.class, this.world);
    }

    @Override
    protected void processEntity(Entity entity) {
        DragComponent drag = drags.get(entity);
        if (drag.isGrabbed()) {
            Vec2 startVec = drag.getStartVec();
            Vec2 endVec = drag.getEndVec();

            canvas.drawLine(SizeUtil.getRenderX(startVec.x),
                    SizeUtil.getRenderY(startVec.y),
                    SizeUtil.getRenderX(endVec.x),
                    SizeUtil.getRenderY(endVec.y),
                    paint);
        }
    }

    private Paint paint;
    private ComponentMapper<DragComponent> drags;
}
