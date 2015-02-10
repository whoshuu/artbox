package com.whoshuu.artbox.component;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.GameContext;
import com.whoshuu.artbox.artemis.utils.JSONComponent;

public class BodyComponent extends JSONComponent {

    public BodyComponent() {
        this.body = null;
    }

    public BodyComponent(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return this.body;
    }

    @Override
    public void fromJSON(JSONObject json, float x, float y, float angle) throws JSONException {
    	/*
         * "type": "com.whoshuu.artbox.component.BodyComponent"
         * "rotate": (bool) whether to allow the rotation of the body
         * "shape": {
         *   "type": (string) type of body shape to create
         *   ...
         * }
         *
         * Depending on the type of shape, different parameters are available:
         *
         * [Circle]
         * "shape": {
         *   "type": "circle",
         *   "radius": (float) radius of the circle in game coordinates
         * }
         *
         * [Edge]
         * "shape": {
         *   "type": "edge",
         *   "x2": (float) x coordinate of the second point,
         *   "y2": (float) y coordinate of the second point
         * }
         *
         * [Box]
         * "shape": {
         *   "type": "box",
         *   "w": (float) entire width of the box,
         *   "h": (float) entire height of the box 
         * }
         *
         * [Polygon]
         * The points begin with the body point, then continue with the points array. It will loop
         * back automatically to the body point.
         * "shape": {
         *   "type": "polygon",
         *   "points": [
         *     {
         *       "x": (float) x coordinate of a point,
         *       "y": (float) y coordinate of a point
         *     },
         *     {
         *       "x": (float) x coordinate of a point,
         *       "y": (float) y coordinate of a point
         *     },
         *     ...
         *   ]
         * }
         *
         * [Chain]
         * The points begin with the body point, then continue with the points array, and will end
         * when all points are exhausted. This does not loop back.
         * "shape": {
         *   "type": "chain",
         *   "points": [
         *     {
         *       "x": (float) x coordinate of a point,
         *       "y": (float) y coordinate of a point
         *     },
         *     {
         *       "x": (float) x coordinate of a point,
         *       "y": (float) y coordinate of a point
         *     },
         *     ...
         *   ]
         * }
         *
         */
        BodyDef def = new BodyDef();
        def.type = json.optString("bodytype", "dynamic").equals("dynamic")
                ? BodyType.DYNAMIC : BodyType.STATIC;
        def.position.set(x, y);
        def.angle = angle;
        def.fixedRotation = !json.optBoolean("rotate", true);

        this.body = GameContext.get().getPhysics().createBody(def);
        if (body != null) {
            // Set shape, density, friction, and restitution
            FixtureDef fixture = new FixtureDef();
            JSONObject jsonShape = json.getJSONObject("shape");
            String shape = jsonShape.optString("type", "circle");
            if (shape.equals("circle")) {
                CircleShape circle = new CircleShape();
                circle.setRadius((float) jsonShape.optDouble("radius", 1.0));
                fixture.shape = circle;
            } else if (shape.equals("edge")) {
                EdgeShape edge = new EdgeShape();
                edge.set(new Vec2(0, 0),
                        new Vec2((float) jsonShape.optDouble("x2", 1.0) - def.position.x,
                                (float) jsonShape.optDouble("y2", 1.0) - def.position.y));
                fixture.shape = edge;
            } else if (shape.equals("box")) {
                PolygonShape box = new PolygonShape();
                box.setAsBox((float) (jsonShape.optDouble("w", 2.0) / 2.0),
                        (float) (jsonShape.optDouble("h", 2.0) / 2.0));
                fixture.shape = box;
            } else if (shape.equals("polygon")) {
                // TODO
            } else if (shape.equals("chain")) {
                // TODO
            }
            fixture.density = (float) json.optDouble("density", 1.0);
            fixture.friction = (float) json.optDouble("friction", 1.0);
            fixture.restitution = (float) json.optDouble("restitution", 1.0);
            this.body.createFixture(fixture);
        }
    }

    private Body body;
}
