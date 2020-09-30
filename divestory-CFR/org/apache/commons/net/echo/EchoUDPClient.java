/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.apache.commons.net.discard.DiscardUDPClient;

public final class EchoUDPClient
extends DiscardUDPClient {
    public static final int DEFAULT_PORT = 7;
    private final DatagramPacket __receivePacket = new DatagramPacket(new byte[0], 0);

    public int receive(byte[] arrby) throws IOException {
        return this.receive(arrby, arrby.length);
    }

    public int receive(byte[] arrby, int n) throws IOException {
        this.__receivePacket.setData(arrby);
        this.__receivePacket.setLength(n);
        this._socket_.receive(this.__receivePacket);
        return this.__receivePacket.getLength();
    }

    @Override
    public void send(byte[] arrby, int n, InetAddress inetAddress) throws IOException {
        this.send(arrby, n, inetAddress, 7);
    }

    @Override
    public void send(byte[] arrby, InetAddress inetAddress) throws IOException {
        this.send(arrby, arrby.length, inetAddress, 7);
    }
}

