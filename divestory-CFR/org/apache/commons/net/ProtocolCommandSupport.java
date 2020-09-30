/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net;

import java.io.Serializable;
import java.util.EventListener;
import java.util.Iterator;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.util.ListenerList;

public class ProtocolCommandSupport
implements Serializable {
    private static final long serialVersionUID = -8017692739988399978L;
    private final ListenerList __listeners = new ListenerList();
    private final Object __source;

    public ProtocolCommandSupport(Object object) {
        this.__source = object;
    }

    public void addProtocolCommandListener(ProtocolCommandListener protocolCommandListener) {
        this.__listeners.addListener(protocolCommandListener);
    }

    public void fireCommandSent(String object, String object2) {
        object2 = new ProtocolCommandEvent(this.__source, (String)object, (String)object2);
        object = this.__listeners.iterator();
        while (object.hasNext()) {
            ((ProtocolCommandListener)((EventListener)object.next())).protocolCommandSent((ProtocolCommandEvent)object2);
        }
    }

    public void fireReplyReceived(int n, String object) {
        object = new ProtocolCommandEvent(this.__source, n, (String)object);
        Iterator<EventListener> iterator2 = this.__listeners.iterator();
        while (iterator2.hasNext()) {
            ((ProtocolCommandListener)iterator2.next()).protocolReplyReceived((ProtocolCommandEvent)object);
        }
    }

    public int getListenerCount() {
        return this.__listeners.getListenerCount();
    }

    public void removeProtocolCommandListener(ProtocolCommandListener protocolCommandListener) {
        this.__listeners.removeListener(protocolCommandListener);
    }
}

