/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;

@Deprecated
public interface HostNameResolver {
    public InetAddress resolve(String var1) throws IOException;
}

