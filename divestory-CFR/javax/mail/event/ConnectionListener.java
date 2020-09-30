/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import java.util.EventListener;
import javax.mail.event.ConnectionEvent;

public interface ConnectionListener
extends EventListener {
    public void closed(ConnectionEvent var1);

    public void disconnected(ConnectionEvent var1);

    public void opened(ConnectionEvent var1);
}

