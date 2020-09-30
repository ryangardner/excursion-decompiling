/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.tftp.TFTPPacket;
import org.apache.commons.net.tftp.TFTPPacketException;

public final class TFTPDataPacket
extends TFTPPacket {
    public static final int MAX_DATA_LENGTH = 512;
    public static final int MIN_DATA_LENGTH = 0;
    int _blockNumber;
    byte[] _data;
    int _length;
    int _offset;

    TFTPDataPacket(DatagramPacket datagramPacket) throws TFTPPacketException {
        super(3, datagramPacket.getAddress(), datagramPacket.getPort());
        this._data = datagramPacket.getData();
        this._offset = 4;
        int n = this.getType();
        byte[] arrby = this._data;
        if (n != arrby[1]) throw new TFTPPacketException("TFTP operator code does not match type.");
        this._blockNumber = (arrby[2] & 255) << 8 | arrby[3] & 255;
        this._length = n = datagramPacket.getLength() - 4;
        if (n <= 512) return;
        this._length = 512;
    }

    public TFTPDataPacket(InetAddress inetAddress, int n, int n2, byte[] arrby) {
        this(inetAddress, n, n2, arrby, 0, arrby.length);
    }

    public TFTPDataPacket(InetAddress inetAddress, int n, int n2, byte[] arrby, int n3, int n4) {
        super(3, inetAddress, n);
        this._blockNumber = n2;
        this._data = arrby;
        this._offset = n3;
        if (n4 > 512) {
            this._length = 512;
            return;
        }
        this._length = n4;
    }

    @Override
    DatagramPacket _newDatagram(DatagramPacket datagramPacket, byte[] arrby) {
        arrby[0] = (byte)(false ? 1 : 0);
        arrby[1] = (byte)this._type;
        int n = this._blockNumber;
        arrby[2] = (byte)((65535 & n) >> 8);
        arrby[3] = (byte)(n & 255);
        byte[] arrby2 = this._data;
        if (arrby != arrby2) {
            System.arraycopy(arrby2, this._offset, arrby, 4, this._length);
        }
        datagramPacket.setAddress(this._address);
        datagramPacket.setPort(this._port);
        datagramPacket.setData(arrby);
        datagramPacket.setLength(this._length + 4);
        return datagramPacket;
    }

    public int getBlockNumber() {
        return this._blockNumber;
    }

    public byte[] getData() {
        return this._data;
    }

    public int getDataLength() {
        return this._length;
    }

    public int getDataOffset() {
        return this._offset;
    }

    @Override
    public DatagramPacket newDatagram() {
        byte[] arrby = new byte[this._length + 4];
        arrby[0] = (byte)(false ? 1 : 0);
        arrby[1] = (byte)this._type;
        int n = this._blockNumber;
        arrby[2] = (byte)((65535 & n) >> 8);
        arrby[3] = (byte)(n & 255);
        System.arraycopy(this._data, this._offset, arrby, 4, this._length);
        return new DatagramPacket(arrby, this._length + 4, this._address, this._port);
    }

    public void setBlockNumber(int n) {
        this._blockNumber = n;
    }

    public void setData(byte[] arrby, int n, int n2) {
        this._data = arrby;
        this._offset = n;
        this._length = n2;
        if (n2 > 512) {
            this._length = 512;
            return;
        }
        this._length = n2;
    }
}

