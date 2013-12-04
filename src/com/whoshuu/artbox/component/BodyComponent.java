package com.whoshuu.artbox.component;

import org.jbox2d.dynamics.Body;

import com.whoshuu.artbox.artemis.Component;
import com.whoshuu.artbox.artemis.Entity;

public class BodyComponent extends Component {

    public BodyComponent(Body body, Entity entity) {
        this.body = body;
        this.body.setUserData(entity);
    }

    public Body getBody() {
        return this.body;
    }

    private Body body;
}
