/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

public abstract class TransportAdapter
implements TransportListener {
    @Override
    public void messageDelivered(TransportEvent transportEvent) {
    }

    @Override
    public void messageNotDelivered(TransportEvent transportEvent) {
    }

    @Override
    public void messagePartiallyDelivered(TransportEvent transportEvent) {
    }
}

