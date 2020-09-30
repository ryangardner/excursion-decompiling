/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;

public abstract class ConnectionAdapter
implements ConnectionListener {
    @Override
    public void closed(ConnectionEvent connectionEvent) {
    }

    @Override
    public void disconnected(ConnectionEvent connectionEvent) {
    }

    @Override
    public void opened(ConnectionEvent connectionEvent) {
    }
}

