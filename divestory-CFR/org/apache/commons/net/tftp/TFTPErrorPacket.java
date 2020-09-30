/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.tftp.TFTPPacket;
import org.apache.commons.net.tftp.TFTPPacketException;

public final class TFTPErrorPacket
extends TFTPPacket {
    public static final int ACCESS_VIOLATION = 2;
    public static final int FILE_EXISTS = 6;
    public static final int FILE_NOT_FOUND = 1;
    public static final int ILLEGAL_OPERATION = 4;
    public static final int NO_SUCH_USER = 7;
    public static final int OUT_OF_SPACE = 3;
    public static final int UNDEFINED = 0;
    public static final int UNKNOWN_TID = 5;
    int _error;
    String _message;

    TFTPErrorPacket(DatagramPacket object) throws TFTPPacketException {
        super(5, ((DatagramPacket)object).getAddress(), ((DatagramPacket)object).getPort());
        byte[] arrby = ((DatagramPacket)object).getData();
        int n = ((DatagramPacket)object).getLength();
        if (this.getType() != arrby[1]) throw new TFTPPacketException("TFTP operator code does not match type.");
        this._error = (arrby[2] & 255) << 8 | arrby[3] & 255;
        if (n < 5) throw new TFTPPacketException("Bad error packet. No message.");
        object = new StringBuilder();
        for (int i = 4; i < n && arrby[i] != 0; ++i) {
            ((StringBuilder)object).append((char)arrby[i]);
        }
        this._message = ((StringBuilder)object).toString();
    }

    public TFTPErrorPacket(InetAddress inetAddress, int n, int n2, String string2) {
        super(5, inetAddress, n);
        this._error = n2;
        this._message = string2;
    }

    @Override
    DatagramPacket _newDatagram(DatagramPacket datagramPacket, byte[] arrby) {
        int n = this._message.length();
        arrby[0] = (byte)(false ? 1 : 0);
        arrby[1] = (byte)this._type;
        int n2 = this._error;
        arrby[2] = (byte)((65535 & n2) >> 8);
        arrby[3] = (byte)(n2 & 255);
        System.arraycopy(this._message.getBytes(), 0, arrby, 4, n);
        arrby[n += 4] = (byte)(false ? 1 : 0);
        datagramPacket.setAddress(this._address);
        datagramPacket.setPort(this._port);
        datagramPacket.setData(arrby);
        datagramPacket.setLength(n);
        return datagramPacket;
    }

    public int getError() {
        return this._error;
    }

    public String getMessage() {
        return this._message;
    }

    @Override
    public DatagramPacket newDatagram() {
        int n = this._message.length();
        int n2 = n + 5;
        byte[] arrby = new byte[n2];
        arrby[0] = (byte)(false ? 1 : 0);
        arrby[1] = (byte)this._type;
        int n3 = this._error;
        arrby[2] = (byte)((65535 & n3) >> 8);
        arrby[3] = (byte)(n3 & 255);
        System.arraycopy(this._message.getBytes(), 0, arrby, 4, n);
        arrby[n + 4] = (byte)(false ? 1 : 0);
        return new DatagramPacket(arrby, n2, this._address, this._port);
    }
}

