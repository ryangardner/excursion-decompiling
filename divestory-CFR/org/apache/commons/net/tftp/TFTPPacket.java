/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.tftp.TFTPAckPacket;
import org.apache.commons.net.tftp.TFTPDataPacket;
import org.apache.commons.net.tftp.TFTPErrorPacket;
import org.apache.commons.net.tftp.TFTPPacketException;
import org.apache.commons.net.tftp.TFTPReadRequestPacket;
import org.apache.commons.net.tftp.TFTPWriteRequestPacket;

public abstract class TFTPPacket {
    public static final int ACKNOWLEDGEMENT = 4;
    public static final int DATA = 3;
    public static final int ERROR = 5;
    static final int MIN_PACKET_SIZE = 4;
    public static final int READ_REQUEST = 1;
    public static final int SEGMENT_SIZE = 512;
    public static final int WRITE_REQUEST = 2;
    InetAddress _address;
    int _port;
    int _type;

    TFTPPacket(int n, InetAddress inetAddress, int n2) {
        this._type = n;
        this._address = inetAddress;
        this._port = n2;
    }

    public static final TFTPPacket newTFTPPacket(DatagramPacket object) throws TFTPPacketException {
        if (((DatagramPacket)object).getLength() < 4) throw new TFTPPacketException("Bad packet. Datagram data length is too short.");
        byte by = ((DatagramPacket)object).getData()[1];
        if (by == 1) {
            return new TFTPReadRequestPacket((DatagramPacket)object);
        }
        if (by == 2) {
            return new TFTPWriteRequestPacket((DatagramPacket)object);
        }
        if (by == 3) {
            return new TFTPDataPacket((DatagramPacket)object);
        }
        if (by == 4) return new TFTPAckPacket((DatagramPacket)object);
        if (by != 5) throw new TFTPPacketException("Bad packet.  Invalid TFTP operator code.");
        return new TFTPErrorPacket((DatagramPacket)object);
    }

    abstract DatagramPacket _newDatagram(DatagramPacket var1, byte[] var2);

    public final InetAddress getAddress() {
        return this._address;
    }

    public final int getPort() {
        return this._port;
    }

    public final int getType() {
        return this._type;
    }

    public abstract DatagramPacket newDatagram();

    public final void setAddress(InetAddress inetAddress) {
        this._address = inetAddress;
    }

    public final void setPort(int n) {
        this._port = n;
    }
}

