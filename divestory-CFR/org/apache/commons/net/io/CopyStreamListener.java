/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.util.EventListener;
import org.apache.commons.net.io.CopyStreamEvent;

public interface CopyStreamListener
extends EventListener {
    public void bytesTransferred(long var1, int var3, long var4);

    public void bytesTransferred(CopyStreamEvent var1);
}

