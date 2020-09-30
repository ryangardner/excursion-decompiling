/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.tftp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import org.apache.commons.net.DatagramSocketClient;
import org.apache.commons.net.tftp.TFTPPacket;
import org.apache.commons.net.tftp.TFTPPacketException;
import org.apache.commons.net.tftp.TFTPRequestPacket;

public class TFTP
extends DatagramSocketClient {
    public static final int ASCII_MODE = 0;
    public static final int BINARY_MODE = 1;
    public static final int DEFAULT_PORT = 69;
    public static final int DEFAULT_TIMEOUT = 5000;
    public static final int IMAGE_MODE = 1;
    public static final int NETASCII_MODE = 0;
    public static final int OCTET_MODE = 1;
    static final int PACKET_SIZE = 516;
    private byte[] __receiveBuffer;
    private DatagramPacket __receiveDatagram;
    private DatagramPacket __sendDatagram;
    byte[] _sendBuffer;

    public TFTP() {
        this.setDefaultTimeout(5000);
        this.__receiveBuffer = null;
        this.__receiveDatagram = null;
    }

    public static final String getModeName(int n) {
        return TFTPRequestPacket._modeStrings[n];
    }

    public final void beginBufferedOps() {
        byte[] arrby = this.__receiveBuffer = new byte[516];
        this.__receiveDatagram = new DatagramPacket(arrby, arrby.length);
        this._sendBuffer = new byte[516];
        arrby = this._sendBuffer;
        this.__sendDatagram = new DatagramPacket(arrby, arrby.length);
    }

    public final TFTPPacket bufferedReceive() throws IOException, InterruptedIOException, SocketException, TFTPPacketException {
        this.__receiveDatagram.setData(this.__receiveBuffer);
        this.__receiveDatagram.setLength(this.__receiveBuffer.length);
        this._socket_.receive(this.__receiveDatagram);
        return TFTPPacket.newTFTPPacket(this.__receiveDatagram);
    }

    public final void bufferedSend(TFTPPacket tFTPPacket) throws IOException {
        this._socket_.send(tFTPPacket._newDatagram(this.__sendDatagram, this._sendBuffer));
    }

    public final void discardPackets() throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(new byte[516], 516);
        int n = this.getSoTimeout();
        this.setSoTimeout(1);
        try {
            do {
                this._socket_.receive(datagramPacket);
            } while (true);
        }
        catch (InterruptedIOException | SocketException iOException) {
            this.setSoTimeout(n);
            return;
        }
    }

    public final void endBufferedOps() {
        this.__receiveBuffer = null;
        this.__receiveDatagram = null;
        this._sendBuffer = null;
        this.__sendDatagram = null;
    }

    public final TFTPPacket receive() throws IOException, InterruptedIOException, SocketException, TFTPPacketException {
        DatagramPacket datagramPacket = new DatagramPacket(new byte[516], 516);
        this._socket_.receive(datagramPacket);
        return TFTPPacket.newTFTPPacket(datagramPacket);
    }

    public final void send(TFTPPacket tFTPPacket) throws IOException {
        this._socket_.send(tFTPPacket.newDatagram());
    }
}

