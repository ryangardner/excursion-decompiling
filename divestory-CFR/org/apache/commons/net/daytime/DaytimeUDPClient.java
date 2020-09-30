/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.daytime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.apache.commons.net.DatagramSocketClient;

public final class DaytimeUDPClient
extends DatagramSocketClient {
    public static final int DEFAULT_PORT = 13;
    private final byte[] __dummyData = new byte[1];
    private final byte[] __timeData = new byte[256];

    public String getTime(InetAddress inetAddress) throws IOException {
        return this.getTime(inetAddress, 13);
    }

    public String getTime(InetAddress object, int n) throws IOException {
        Object object2 = this.__dummyData;
        object = new DatagramPacket((byte[])object2, ((byte[])object2).length, (InetAddress)object, n);
        object2 = this.__timeData;
        object2 = new DatagramPacket((byte[])object2, ((byte[])object2).length);
        this._socket_.send((DatagramPacket)object);
        this._socket_.receive((DatagramPacket)object2);
        return new String(((DatagramPacket)object2).getData(), 0, ((DatagramPacket)object2).getLength(), this.getCharsetName());
    }
}

