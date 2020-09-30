/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import org.apache.commons.net.DatagramSocketFactory;

public class DefaultDatagramSocketFactory
implements DatagramSocketFactory {
    @Override
    public DatagramSocket createDatagramSocket() throws SocketException {
        return new DatagramSocket();
    }

    @Override
    public DatagramSocket createDatagramSocket(int n) throws SocketException {
        return new DatagramSocket(n);
    }

    @Override
    public DatagramSocket createDatagramSocket(int n, InetAddress inetAddress) throws SocketException {
        return new DatagramSocket(n, inetAddress);
    }
}

