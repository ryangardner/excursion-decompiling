/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import java.io.InputStream;

public interface SharedInputStream {
    public long getPosition();

    public InputStream newStream(long var1, long var3);
}

