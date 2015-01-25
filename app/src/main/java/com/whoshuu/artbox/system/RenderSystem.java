package com.whoshuu.artbox.system;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import android.graphics.Canvas;

import com.whoshuu.artbox.artemis.Component;
import com.whoshuu.artbox.artemis.ComponentMapper;
import com.whoshuu.artbox.artemis.Entity;
import com.whoshuu.artbox.artemis.EntitySystem;
import com.whoshuu.artbox.artemis.utils.ImmutableBag;
import com.whoshuu.artbox.component.RenderComponent;

public abstract class RenderSystem extends EntitySystem {

    public RenderSystem(Class<? extends Component>... types) {
        super(types);
    }

    public final void process(Canvas canvas) {
        this.canvas = canvas;
        process();
    }

    protected void initialize() {
        this.orders = new ComponentMapper<RenderComponent>(RenderComponent.class, this.world);
        this.orderedEntities = new PriorityQueue<Entity>(10, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                return Integer.valueOf(orders.get(e2).getZOrder())
                        .compareTo(orders.get(e1).getZOrder());
            }
        });
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

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {
        orderedEntities.clear();
        for (int i = 0; i < entities.size(); i++) {
            orderedEntities.offer(entities.get(i));
        }
        while (orderedEntities.peek() != null) {
            processEntity(orderedEntities.poll());
        }
    }

    private ComponentMapper<RenderComponent> orders;
    private Queue<Entity> orderedEntities;
    protected Canvas canvas;
}
