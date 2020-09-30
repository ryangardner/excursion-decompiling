/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.methods;

import java.io.IOException;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionReleaseTrigger;

public interface AbortableHttpRequest {
    public void abort();

    public void setConnectionRequest(ClientConnectionRequest var1) throws IOException;

    public void setReleaseTrigger(ConnectionReleaseTrigger var1) throws IOException;
}

