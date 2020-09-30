/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.time;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.apache.commons.net.SocketClient;

public final class TimeTCPClient
extends SocketClient {
    public static final int DEFAULT_PORT = 37;
    public static final long SECONDS_1900_TO_1970 = 2208988800L;

    public TimeTCPClient() {
        this.setDefaultPort(37);
    }

    public Date getDate() throws IOException {
        return new Date((this.getTime() - 2208988800L) * 1000L);
    }

    public long getTime() throws IOException {
        return (long)new DataInputStream(this._input_).readInt() & 0xFFFFFFFFL;
    }
}

