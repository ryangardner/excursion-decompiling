/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.eventbus;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

public class DeadEvent {
    private final Object event;
    private final Object source;

    public DeadEvent(Object object, Object object2) {
        this.source = Preconditions.checkNotNull(object);
        this.event = Preconditions.checkNotNull(object2);
    }

    public Object getEvent() {
        return this.event;
    }

    public Object getSource() {
        return this.source;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("source", this.source).add("event", this.event).toString();
    }
}

