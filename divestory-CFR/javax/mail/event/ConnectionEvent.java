/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import javax.mail.event.ConnectionListener;
import javax.mail.event.MailEvent;

public class ConnectionEvent
extends MailEvent {
    public static final int CLOSED = 3;
    public static final int DISCONNECTED = 2;
    public static final int OPENED = 1;
    private static final long serialVersionUID = -1855480171284792957L;
    protected int type;

    public ConnectionEvent(Object object, int n) {
        super(object);
        this.type = n;
    }

    @Override
    public void dispatch(Object object) {
        int n = this.type;
        if (n == 1) {
            ((ConnectionListener)object).opened(this);
            return;
        }
        if (n == 2) {
            ((ConnectionListener)object).disconnected(this);
            return;
        }
        if (n != 3) return;
        ((ConnectionListener)object).closed(this);
    }

    public int getType() {
        return this.type;
    }
}

