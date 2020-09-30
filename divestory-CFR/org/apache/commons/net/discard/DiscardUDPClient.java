/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.discard;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.apache.commons.net.DatagramSocketClient;

public class DiscardUDPClient
extends DatagramSocketClient {
    public static final int DEFAULT_PORT = 9;
    DatagramPacket _sendPacket = new DatagramPacket(new byte[0], 0);

    public void send(byte[] arrby, int n, InetAddress inetAddress) throws IOException {
        this.send(arrby, n, inetAddress, 9);
    }

    public void send(byte[] arrby, int n, InetAddress inetAddress, int n2) throws IOException {
        this._sendPacket.setData(arrby);
        this._sendPacket.setLength(n);
        this._sendPacket.setAddress(inetAddress);
        this._sendPacket.setPort(n2);
        this._socket_.send(this._sendPacket);
    }

    public void send(byte[] arrby, InetAddress inetAddress) throws IOException {
        this.send(arrby, arrby.length, inetAddress, 9);
    }
}

