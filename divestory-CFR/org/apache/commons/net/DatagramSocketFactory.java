/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public interface DatagramSocketFactory {
    public DatagramSocket createDatagramSocket() throws SocketException;

    public DatagramSocket createDatagramSocket(int var1) throws SocketException;

    public DatagramSocket createDatagramSocket(int var1, InetAddress var2) throws SocketException;
}

