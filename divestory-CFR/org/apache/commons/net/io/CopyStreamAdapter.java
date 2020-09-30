/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.util.EventListener;
import java.util.Iterator;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.commons.net.util.ListenerList;

public class CopyStreamAdapter
implements CopyStreamListener {
    private final ListenerList internalListeners = new ListenerList();

    public void addCopyStreamListener(CopyStreamListener copyStreamListener) {
        this.internalListeners.addListener(copyStreamListener);
    }

    @Override
    public void bytesTransferred(long l, int n, long l2) {
        Iterator<EventListener> iterator2 = this.internalListeners.iterator();
        while (iterator2.hasNext()) {
            ((CopyStreamListener)iterator2.next()).bytesTransferred(l, n, l2);
        }
    }

    @Override
    public void bytesTransferred(CopyStreamEvent copyStreamEvent) {
        Iterator<EventListener> iterator2 = this.internalListeners.iterator();
        while (iterator2.hasNext()) {
            ((CopyStreamListener)iterator2.next()).bytesTransferred(copyStreamEvent);
        }
    }

    public void removeCopyStreamListener(CopyStreamListener copyStreamListener) {
        this.internalListeners.removeListener(copyStreamListener);
    }
}

