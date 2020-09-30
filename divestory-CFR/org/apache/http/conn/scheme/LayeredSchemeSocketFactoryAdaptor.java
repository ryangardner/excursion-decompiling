/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.SchemeSocketFactoryAdaptor;
import org.apache.http.conn.scheme.SocketFactory;

@Deprecated
class LayeredSchemeSocketFactoryAdaptor
extends SchemeSocketFactoryAdaptor
implements LayeredSchemeSocketFactory {
    private final LayeredSocketFactory factory;

    LayeredSchemeSocketFactoryAdaptor(LayeredSocketFactory layeredSocketFactory) {
        super(layeredSocketFactory);
        this.factory = layeredSocketFactory;
    }

    @Override
    public Socket createLayeredSocket(Socket socket, String string2, int n, boolean bl) throws IOException, UnknownHostException {
        return this.factory.createSocket(socket, string2, n, bl);
    }
}

