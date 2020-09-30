/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import org.apache.commons.net.DatagramSocketClient;

public final class TimeUDPClient
extends DatagramSocketClient {
    public static final int DEFAULT_PORT = 37;
    public static final long SECONDS_1900_TO_1970 = 2208988800L;
    private final byte[] __dummyData = new byte[1];
    private final byte[] __timeData = new byte[4];

    public Date getDate(InetAddress inetAddress) throws IOException {
        return new Date((this.getTime(inetAddress, 37) - 2208988800L) * 1000L);
    }

    public Date getDate(InetAddress inetAddress, int n) throws IOException {
        return new Date((this.getTime(inetAddress, n) - 2208988800L) * 1000L);
    }

    public long getTime(InetAddress inetAddress) throws IOException {
        return this.getTime(inetAddress, 37);
    }

    public long getTime(InetAddress arrby, int n) throws IOException {
        Object object = this.__dummyData;
        arrby = new DatagramPacket((byte[])object, ((byte[])object).length, (InetAddress)arrby, n);
        object = this.__timeData;
        object = new DatagramPacket((byte[])object, ((byte[])object).length);
        this._socket_.send((DatagramPacket)arrby);
        this._socket_.receive((DatagramPacket)object);
        arrby = this.__timeData;
        long l = (arrby[0] & 255) << 24;
        long l2 = (arrby[1] & 255) << 16;
        long l3 = (arrby[2] & 255) << 8;
        return (long)(arrby[3] & 255) & 0xFFFFFFFFL | (l & 0xFFFFFFFFL | 0L | l2 & 0xFFFFFFFFL | l3 & 0xFFFFFFFFL);
    }
}

