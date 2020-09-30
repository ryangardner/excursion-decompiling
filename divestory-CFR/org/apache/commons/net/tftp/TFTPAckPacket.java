/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.tftp.TFTPPacket;
import org.apache.commons.net.tftp.TFTPPacketException;

public final class TFTPAckPacket
extends TFTPPacket {
    int _blockNumber;

    TFTPAckPacket(DatagramPacket arrby) throws TFTPPacketException {
        super(4, arrby.getAddress(), arrby.getPort());
        arrby = arrby.getData();
        if (this.getType() != arrby[1]) throw new TFTPPacketException("TFTP operator code does not match type.");
        byte by = arrby[2];
        this._blockNumber = arrby[3] & 255 | (by & 255) << 8;
    }

    public TFTPAckPacket(InetAddress inetAddress, int n, int n2) {
        super(4, inetAddress, n);
        this._blockNumber = n2;
    }

    @Override
    DatagramPacket _newDatagram(DatagramPacket datagramPacket, byte[] arrby) {
        arrby[0] = (byte)(false ? 1 : 0);
        arrby[1] = (byte)this._type;
        int n = this._blockNumber;
        arrby[2] = (byte)((65535 & n) >> 8);
        arrby[3] = (byte)(n & 255);
        datagramPacket.setAddress(this._address);
        datagramPacket.setPort(this._port);
        datagramPacket.setData(arrby);
        datagramPacket.setLength(4);
        return datagramPacket;
    }

    public int getBlockNumber() {
        return this._blockNumber;
    }

    @Override
    public DatagramPacket newDatagram() {
        byte by = (byte)this._type;
        int n = this._blockNumber;
        byte by2 = (byte)((65535 & n) >> 8);
        byte by3 = (byte)(n & 255);
        InetAddress inetAddress = this._address;
        n = this._port;
        return new DatagramPacket(new byte[]{0, by, by2, by3}, 4, inetAddress, n);
    }

    public void setBlockNumber(int n) {
        this._blockNumber = n;
    }
}

