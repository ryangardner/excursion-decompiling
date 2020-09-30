/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.ThreadLocalBufferManager;
import java.lang.ref.SoftReference;

public class BufferRecyclers {
    public static final String SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS = "com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers";
    private static final ThreadLocalBufferManager _bufferRecyclerTracker;
    protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef;

    static {
        ThreadLocalBufferManager threadLocalBufferManager = "true".equals(System.getProperty(SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS)) ? ThreadLocalBufferManager.instance() : null;
        _bufferRecyclerTracker = threadLocalBufferManager;
        _recyclerRef = new ThreadLocal();
    }

    public static BufferRecycler getBufferRecycler() {
        Object object = _recyclerRef.get();
        object = object == null ? null : ((SoftReference)object).get();
        Object object2 = object;
        if (object != null) return object2;
        object2 = new BufferRecycler();
        object = _bufferRecyclerTracker;
        object = object != null ? ((ThreadLocalBufferManager)object).wrapAndTrack((BufferRecycler)object2) : new SoftReference<Object>(object2);
        _recyclerRef.set((SoftReference<BufferRecycler>)object);
        return object2;
    }

    public static int releaseBuffers() {
        ThreadLocalBufferManager threadLocalBufferManager = _bufferRecyclerTracker;
        if (threadLocalBufferManager == null) return -1;
        return threadLocalBufferManager.releaseBuffers();
    }
}

