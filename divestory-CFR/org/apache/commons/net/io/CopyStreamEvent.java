/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.util.EventObject;

public class CopyStreamEvent
extends EventObject {
    public static final long UNKNOWN_STREAM_SIZE = -1L;
    private static final long serialVersionUID = -964927635655051867L;
    private final int bytesTransferred;
    private final long streamSize;
    private final long totalBytesTransferred;

    public CopyStreamEvent(Object object, long l, int n, long l2) {
        super(object);
        this.bytesTransferred = n;
        this.totalBytesTransferred = l;
        this.streamSize = l2;
    }

    public int getBytesTransferred() {
        return this.bytesTransferred;
    }

    public long getStreamSize() {
        return this.streamSize;
    }

    public long getTotalBytesTransferred() {
        return this.totalBytesTransferred;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[source=");
        stringBuilder.append(this.source);
        stringBuilder.append(", total=");
        stringBuilder.append(this.totalBytesTransferred);
        stringBuilder.append(", bytes=");
        stringBuilder.append(this.bytesTransferred);
        stringBuilder.append(", size=");
        stringBuilder.append(this.streamSize);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

