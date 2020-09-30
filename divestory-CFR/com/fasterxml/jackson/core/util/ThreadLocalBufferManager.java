/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.util.BufferRecycler;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class ThreadLocalBufferManager {
    private final Object RELEASE_LOCK = new Object();
    private final ReferenceQueue<BufferRecycler> _refQueue = new ReferenceQueue();
    private final Map<SoftReference<BufferRecycler>, Boolean> _trackedRecyclers = new ConcurrentHashMap<SoftReference<BufferRecycler>, Boolean>();

    ThreadLocalBufferManager() {
    }

    public static ThreadLocalBufferManager instance() {
        return ThreadLocalBufferManagerHolder.manager;
    }

    private void removeSoftRefsClearedByGc() {
        SoftReference softReference;
        while ((softReference = (SoftReference)this._refQueue.poll()) != null) {
            this._trackedRecyclers.remove(softReference);
        }
    }

    public int releaseBuffers() {
        Object object = this.RELEASE_LOCK;
        synchronized (object) {
            int n = 0;
            this.removeSoftRefsClearedByGc();
            Iterator<SoftReference<BufferRecycler>> iterator2 = this._trackedRecyclers.keySet().iterator();
            while (iterator2.hasNext()) {
                iterator2.next().clear();
                ++n;
            }
            this._trackedRecyclers.clear();
            return n;
        }
    }

    public SoftReference<BufferRecycler> wrapAndTrack(BufferRecycler object) {
        object = new SoftReference<BufferRecycler>((BufferRecycler)object, this._refQueue);
        this._trackedRecyclers.put((SoftReference<BufferRecycler>)object, true);
        this.removeSoftRefsClearedByGc();
        return object;
    }

    private static final class ThreadLocalBufferManagerHolder {
        static final ThreadLocalBufferManager manager = new ThreadLocalBufferManager();

        private ThreadLocalBufferManagerHolder() {
        }
    }

}

