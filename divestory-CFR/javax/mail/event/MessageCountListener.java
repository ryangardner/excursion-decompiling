/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.event;

import java.util.EventListener;
import javax.mail.event.MessageCountEvent;

public interface MessageCountListener
extends EventListener {
    public void messagesAdded(MessageCountEvent var1);

    public void messagesRemoved(MessageCountEvent var1);
}

