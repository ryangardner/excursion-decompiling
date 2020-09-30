/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import javax.mail.Message;
import javax.mail.event.MailEvent;
import javax.mail.event.MessageChangedListener;

public class MessageChangedEvent
extends MailEvent {
    public static final int ENVELOPE_CHANGED = 2;
    public static final int FLAGS_CHANGED = 1;
    private static final long serialVersionUID = -4974972972105535108L;
    protected transient Message msg;
    protected int type;

    public MessageChangedEvent(Object object, int n, Message message) {
        super(object);
        this.msg = message;
        this.type = n;
    }

    @Override
    public void dispatch(Object object) {
        ((MessageChangedListener)object).messageChanged(this);
    }

    public Message getMessage() {
        return this.msg;
    }

    public int getMessageChangeType() {
        return this.type;
    }
}

