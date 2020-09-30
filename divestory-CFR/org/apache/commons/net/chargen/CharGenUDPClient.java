/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.chargen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.apache.commons.net.DatagramSocketClient;

public final class CharGenUDPClient
extends DatagramSocketClient {
    public static final int CHARGEN_PORT = 19;
    public static final int DEFAULT_PORT = 19;
    public static final int NETSTAT_PORT = 15;
    public static final int QUOTE_OF_DAY_PORT = 17;
    public static final int SYSTAT_PORT = 11;
    private final byte[] __receiveData;
    private final DatagramPacket __receivePacket;
    private final DatagramPacket __sendPacket;

    public CharGenUDPClient() {
        byte[] arrby = this.__receiveData = new byte[512];
        this.__receivePacket = new DatagramPacket(arrby, arrby.length);
        this.__sendPacket = new DatagramPacket(new byte[0], 0);
    }

    public byte[] receive() throws IOException {
        this._socket_.receive(this.__receivePacket);
        int n = this.__receivePacket.getLength();
        byte[] arrby = new byte[n];
        System.arraycopy(this.__receiveData, 0, arrby, 0, n);
        return arrby;
    }

    public void send(InetAddress inetAddress) throws IOException {
        this.send(inetAddress, 19);
    }

    public void send(InetAddress inetAddress, int n) throws IOException {
        this.__sendPacket.setAddress(inetAddress);
        this.__sendPacket.setPort(n);
        this._socket_.send(this.__sendPacket);
    }
}

