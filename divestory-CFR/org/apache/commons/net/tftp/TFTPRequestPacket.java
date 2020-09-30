/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Locale;
import org.apache.commons.net.tftp.TFTPPacket;
import org.apache.commons.net.tftp.TFTPPacketException;

public abstract class TFTPRequestPacket
extends TFTPPacket {
    private static final byte[][] _modeBytes;
    static final String[] _modeStrings;
    private final String _filename;
    private final int _mode;

    static {
        _modeStrings = new String[]{"netascii", "octet"};
        _modeBytes = new byte[][]{{110, 101, 116, 97, 115, 99, 105, 105, 0}, {111, 99, 116, 101, 116, 0}};
    }

    TFTPRequestPacket(int n, DatagramPacket object) throws TFTPPacketException {
        super(n, ((DatagramPacket)object).getAddress(), ((DatagramPacket)object).getPort());
        Object object2 = ((DatagramPacket)object).getData();
        if (this.getType() != object2[1]) throw new TFTPPacketException("TFTP operator code does not match type.");
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = ((DatagramPacket)object).getLength();
        for (n = 2; n < n2 && object2[n] != 0; ++n) {
            stringBuilder.append((char)object2[n]);
        }
        this._filename = stringBuilder.toString();
        if (n >= n2) throw new TFTPPacketException("Bad filename and mode format.");
        int n3 = 0;
        stringBuilder.setLength(0);
        ++n;
        while (n < n2 && object2[n] != 0) {
            stringBuilder.append((char)object2[n]);
            ++n;
        }
        object2 = stringBuilder.toString().toLowerCase(Locale.ENGLISH);
        int n4 = _modeStrings.length;
        n = 0;
        do {
            n2 = n3;
            if (n >= n4) break;
            if (((String)object2).equals(_modeStrings[n])) {
                n2 = n;
                break;
            }
            ++n;
        } while (true);
        this._mode = n2;
        if (n < n4) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unrecognized TFTP transfer mode: ");
        ((StringBuilder)object).append((String)object2);
        throw new TFTPPacketException(((StringBuilder)object).toString());
    }

    TFTPRequestPacket(InetAddress inetAddress, int n, int n2, String string2, int n3) {
        super(n2, inetAddress, n);
        this._filename = string2;
        this._mode = n3;
    }

    @Override
    final DatagramPacket _newDatagram(DatagramPacket datagramPacket, byte[] arrby) {
        int n = this._filename.length();
        int n2 = _modeBytes[this._mode].length;
        arrby[0] = (byte)(false ? 1 : 0);
        arrby[1] = (byte)this._type;
        System.arraycopy(this._filename.getBytes(), 0, arrby, 2, n);
        arrby[n + 2] = (byte)(false ? 1 : 0);
        System.arraycopy(_modeBytes[this._mode], 0, arrby, n + 3, n2);
        datagramPacket.setAddress(this._address);
        datagramPacket.setPort(this._port);
        datagramPacket.setData(arrby);
        datagramPacket.setLength(n + n2 + 3);
        return datagramPacket;
    }

    public final String getFilename() {
        return this._filename;
    }

    public final int getMode() {
        return this._mode;
    }

    @Override
    public final DatagramPacket newDatagram() {
        int n = this._filename.length();
        int n2 = _modeBytes[this._mode].length;
        int n3 = n + n2 + 4;
        byte[] arrby = new byte[n3];
        arrby[0] = (byte)(false ? 1 : 0);
        arrby[1] = (byte)this._type;
        System.arraycopy(this._filename.getBytes(), 0, arrby, 2, n);
        arrby[n + 2] = (byte)(false ? 1 : 0);
        System.arraycopy(_modeBytes[this._mode], 0, arrby, n + 3, n2);
        return new DatagramPacket(arrby, n3, this._address, this._port);
    }
}

