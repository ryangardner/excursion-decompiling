/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import java.util.EventListener;
import javax.mail.event.TransportEvent;

public interface TransportListener
extends EventListener {
    public void messageDelivered(TransportEvent var1);

    public void messageNotDelivered(TransportEvent var1);

    public void messagePartiallyDelivered(TransportEvent var1);
}

