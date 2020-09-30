/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.tftp.TFTPPacketException;
import org.apache.commons.net.tftp.TFTPRequestPacket;

public final class TFTPReadRequestPacket
extends TFTPRequestPacket {
    TFTPReadRequestPacket(DatagramPacket datagramPacket) throws TFTPPacketException {
        super(1, datagramPacket);
    }

    public TFTPReadRequestPacket(InetAddress inetAddress, int n, String string2, int n2) {
        super(inetAddress, n, 1, string2, n2);
    }
}

