/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ntp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.apache.commons.net.DatagramSocketClient;
import org.apache.commons.net.ntp.NtpV3Impl;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

public final class NTPUDPClient
extends DatagramSocketClient {
    public static final int DEFAULT_PORT = 123;
    private int _version = 3;

    public TimeInfo getTime(InetAddress inetAddress) throws IOException {
        return this.getTime(inetAddress, 123);
    }

    public TimeInfo getTime(InetAddress object, int n) throws IOException {
        if (!this.isOpen()) {
            this.open();
        }
        NtpV3Impl ntpV3Impl = new NtpV3Impl();
        ntpV3Impl.setMode(3);
        ntpV3Impl.setVersion(this._version);
        DatagramPacket datagramPacket = ntpV3Impl.getDatagramPacket();
        datagramPacket.setAddress((InetAddress)object);
        datagramPacket.setPort(n);
        NtpV3Impl ntpV3Impl2 = new NtpV3Impl();
        object = ntpV3Impl2.getDatagramPacket();
        ntpV3Impl.setTransmitTime(TimeStamp.getCurrentTime());
        this._socket_.send(datagramPacket);
        this._socket_.receive((DatagramPacket)object);
        return new TimeInfo((NtpV3Packet)ntpV3Impl2, System.currentTimeMillis(), false);
    }

    public int getVersion() {
        return this._version;
    }

    public void setVersion(int n) {
        this._version = n;
    }
}

