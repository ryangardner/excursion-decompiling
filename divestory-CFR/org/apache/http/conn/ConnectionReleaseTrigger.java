/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn;

import java.io.IOException;

public interface ConnectionReleaseTrigger {
    public void abortConnection() throws IOException;

    public void releaseConnection() throws IOException;
}

