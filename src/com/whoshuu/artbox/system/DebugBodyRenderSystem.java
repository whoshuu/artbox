package com.whoshuu.artbox.system;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.Log;

import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.component.BodyComponent;
import com.whoshuu.artbox.component.PositionComponent;
import com.whoshuu.artbox.component.RenderComponent;
import com.whoshuu.artbox.util.SizeUtil;

public class DebugBodyRenderSystem extends RenderSystem {

    @SuppressWarnings("unchecked")
    public DebugBodyRenderSystem() {
        super(PositionComponent.class, BodyComponent.class, RenderComponent.class);
    }

    @Override
    protected void initialize() {
        paint = new Paint();
        paint.setStrokeWidth(1.0f);
        path = new Path();
        this.positions = new ComponentMapper<PositionComponent>(PositionComponent.class,
        this.world);
        this.bodies = new ComponentMapper<BodyComponent>(BodyComponent.class, this.world);
        super.initialize();
    }

    @Override
    protected void processEntity(Entity entity) {
        Body body = bodies.get(entity).getBody();
        if (body != null) {
            PositionComponent position = positions.get(entity);
            paint.setStyle(Style.FILL);
            // Support multiple fixtures
            Fixture fixture = body.getFixtureList();
            ShapeType type = fixture.getType();
            float cx = position.getX();
            float cy = position.getY();
            switch (type) {
                case CIRCLE:
                    paint.setColor(Color.RED);
                    paint.setAlpha(40);
                    float x = SizeUtil.getRenderX(cx);
                    float y = SizeUtil.getRenderY(cy);
                    float r = SizeUtil.getRenderLength(((CircleShape) fixture.getShape())
                            .getRadius());
                    canvas.drawCircle(x, y, r, paint);
                    paint.setStyle(Style.STROKE);
                    paint.setAlpha(10);
                    paint.setColor(Color.RED);
                    canvas.drawCircle(x, y, r, paint);
                    canvas.drawLine(x, y, x + r * (float) Math.cos(- body.getAngle()),
                            y + r * (float) Math.sin(- body.getAngle()), paint);
                case CHAIN:
                    break;
                case EDGE:
                    paint.setColor(Color.BLUE);
                    EdgeShape edge = (EdgeShape) fixture.getShape();
                    float x1 = SizeUtil.getRenderX(cx + edge.m_vertex1.x);
                    float y1 = SizeUtil.getRenderY(cy + edge.m_vertex1.x);
                    float x2 = SizeUtil.getRenderX(cx + edge.m_vertex2.x);
                    float y2 = SizeUtil.getRenderY(cy + edge.m_vertex2.y);
                    canvas.drawLine(x1, y1, x2, y2, paint);
                    break;
                case POLYGON:
                    paint.setColor(Color.GREEN);
                    paint.setAlpha(30);
                    PolygonShape poly = (PolygonShape) fixture.getShape();
                    Vec2[] vectors = poly.getVertices();
                    path.reset();
                    path.moveTo(SizeUtil.getRenderX(cx + vectors[0].x),
                            SizeUtil.getRenderY(cy + vectors[0].y));
                    for (int i = 1; i < poly.getVertexCount(); i++) {
                        path.lineTo(SizeUtil.getRenderX(cx + vectors[i].x),
                                SizeUtil.getRenderY(cy + vectors[i].y));
                    }
                    path.lineTo(SizeUtil.getRenderX(cx + vectors[0].x),
                            SizeUtil.getRenderY(cy + vectors[0].y));
                    canvas.rotate((float) - Math.toDegrees(body.getAngle()),
                            SizeUtil.getRenderX(cx),
                            SizeUtil.getRenderY(cy));
                    canvas.drawPath(path, paint);
                    paint.setStyle(Style.STROKE);
                    paint.setAlpha(255);
                    canvas.drawPath(path, paint);
                    canvas.rotate((float) Math.toDegrees(body.getAngle()),
                            SizeUtil.getRenderX(cx),
                            SizeUtil.getRenderY(cy));
                    break;
                default:
                    Log.d("DEBUG", "Shape not supported yet");
                    break;
                }
        }
    }

    private Paint paint;
    private Path path;
    private ComponentMapper<PositionComponent> positions;
    private ComponentMapper<BodyComponent> bodies;
}
