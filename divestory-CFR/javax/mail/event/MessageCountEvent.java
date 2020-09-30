/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.event.MailEvent;
import javax.mail.event.MessageCountListener;

public class MessageCountEvent
extends MailEvent {
    public static final int ADDED = 1;
    public static final int REMOVED = 2;
    private static final long serialVersionUID = -7447022340837897369L;
    protected transient Message[] msgs;
    protected boolean removed;
    protected int type;

    public MessageCountEvent(Folder folder, int n, boolean bl, Message[] arrmessage) {
        super(folder);
        this.type = n;
        this.removed = bl;
        this.msgs = arrmessage;
    }

    @Override
    public void dispatch(Object object) {
        if (this.type == 1) {
            ((MessageCountListener)object).messagesAdded(this);
            return;
        }
        ((MessageCountListener)object).messagesRemoved(this);
    }

    public Message[] getMessages() {
        return this.msgs;
    }

    public int getType() {
        return this.type;
    }

    public boolean isRemoved() {
        return this.removed;
    }
}

