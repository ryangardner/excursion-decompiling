/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ntp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import org.apache.commons.net.ntp.NtpUtils;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeStamp;

public class NtpV3Impl
implements NtpV3Packet {
    private static final int LI_INDEX = 0;
    private static final int LI_SHIFT = 6;
    private static final int MODE_INDEX = 0;
    private static final int MODE_SHIFT = 0;
    private static final int ORIGINATE_TIMESTAMP_INDEX = 24;
    private static final int POLL_INDEX = 2;
    private static final int PRECISION_INDEX = 3;
    private static final int RECEIVE_TIMESTAMP_INDEX = 32;
    private static final int REFERENCE_ID_INDEX = 12;
    private static final int REFERENCE_TIMESTAMP_INDEX = 16;
    private static final int ROOT_DELAY_INDEX = 4;
    private static final int ROOT_DISPERSION_INDEX = 8;
    private static final int STRATUM_INDEX = 1;
    private static final int TRANSMIT_TIMESTAMP_INDEX = 40;
    private static final int VERSION_INDEX = 0;
    private static final int VERSION_SHIFT = 3;
    private final byte[] buf = new byte[48];
    private volatile DatagramPacket dp;

    private int getInt(int n) {
        int n2 = NtpV3Impl.ui(this.buf[n]);
        int n3 = NtpV3Impl.ui(this.buf[n + 1]);
        int n4 = NtpV3Impl.ui(this.buf[n + 2]);
        return NtpV3Impl.ui(this.buf[n + 3]) | (n2 << 24 | n3 << 16 | n4 << 8);
    }

    private long getLong(int n) {
        return NtpV3Impl.ul(this.buf[n]) << 56 | NtpV3Impl.ul(this.buf[n + 1]) << 48 | NtpV3Impl.ul(this.buf[n + 2]) << 40 | NtpV3Impl.ul(this.buf[n + 3]) << 32 | NtpV3Impl.ul(this.buf[n + 4]) << 24 | NtpV3Impl.ul(this.buf[n + 5]) << 16 | NtpV3Impl.ul(this.buf[n + 6]) << 8 | NtpV3Impl.ul(this.buf[n + 7]);
    }

    private TimeStamp getTimestamp(int n) {
        return new TimeStamp(this.getLong(n));
    }

    private String idAsHex() {
        return Integer.toHexString(this.getReferenceId());
    }

    private String idAsIPAddress() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(NtpV3Impl.ui(this.buf[12]));
        stringBuilder.append(".");
        stringBuilder.append(NtpV3Impl.ui(this.buf[13]));
        stringBuilder.append(".");
        stringBuilder.append(NtpV3Impl.ui(this.buf[14]));
        stringBuilder.append(".");
        stringBuilder.append(NtpV3Impl.ui(this.buf[15]));
        return stringBuilder.toString();
    }

    private String idAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n <= 3) {
            char c = (char)this.buf[n + 12];
            if (c == '\u0000') {
                return stringBuilder.toString();
            }
            stringBuilder.append(c);
            ++n;
        }
        return stringBuilder.toString();
    }

    private void setInt(int n, int n2) {
        int n3 = 3;
        while (n3 >= 0) {
            this.buf[n + n3] = (byte)(n2 & 255);
            n2 >>>= 8;
            --n3;
        }
    }

    private void setTimestamp(int n, TimeStamp timeStamp) {
        long l = timeStamp == null ? 0L : timeStamp.ntpValue();
        int n2 = 7;
        while (n2 >= 0) {
            this.buf[n + n2] = (byte)(255L & l);
            l >>>= 8;
            --n2;
        }
    }

    protected static final int ui(byte by) {
        return by & 255;
    }

    protected static final long ul(byte by) {
        return by & 255;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (NtpV3Impl)object;
        return Arrays.equals(this.buf, ((NtpV3Impl)object).buf);
    }

    @Override
    public DatagramPacket getDatagramPacket() {
        synchronized (this) {
            DatagramPacket datagramPacket;
            if (this.dp != null) return this.dp;
            this.dp = datagramPacket = new DatagramPacket(this.buf, this.buf.length);
            this.dp.setPort(123);
            return this.dp;
        }
    }

    @Override
    public int getLeapIndicator() {
        return NtpV3Impl.ui(this.buf[0]) >> 6 & 3;
    }

    @Override
    public int getMode() {
        return NtpV3Impl.ui(this.buf[0]) >> 0 & 7;
    }

    @Override
    public String getModeName() {
        return NtpUtils.getModeName(this.getMode());
    }

    @Override
    public TimeStamp getOriginateTimeStamp() {
        return this.getTimestamp(24);
    }

    @Override
    public int getPoll() {
        return this.buf[2];
    }

    @Override
    public int getPrecision() {
        return this.buf[3];
    }

    @Override
    public TimeStamp getReceiveTimeStamp() {
        return this.getTimestamp(32);
    }

    @Override
    public int getReferenceId() {
        return this.getInt(12);
    }

    @Override
    public String getReferenceIdString() {
        int n = this.getVersion();
        int n2 = this.getStratum();
        if (n == 3 || n == 4) {
            if (n2 == 0) return this.idAsString();
            if (n2 == 1) {
                return this.idAsString();
            }
            if (n == 4) {
                return this.idAsHex();
            }
        }
        if (n2 < 2) return this.idAsHex();
        return this.idAsIPAddress();
    }

    @Override
    public TimeStamp getReferenceTimeStamp() {
        return this.getTimestamp(16);
    }

    @Override
    public int getRootDelay() {
        return this.getInt(4);
    }

    @Override
    public double getRootDelayInMillisDouble() {
        return (double)this.getRootDelay() / 65.536;
    }

    @Override
    public int getRootDispersion() {
        return this.getInt(8);
    }

    @Override
    public long getRootDispersionInMillis() {
        return (long)this.getRootDispersion() * 1000L / 65536L;
    }

    @Override
    public double getRootDispersionInMillisDouble() {
        return (double)this.getRootDispersion() / 65.536;
    }

    @Override
    public int getStratum() {
        return NtpV3Impl.ui(this.buf[1]);
    }

    @Override
    public TimeStamp getTransmitTimeStamp() {
        return this.getTimestamp(40);
    }

    @Override
    public String getType() {
        return "NTP";
    }

    @Override
    public int getVersion() {
        return NtpV3Impl.ui(this.buf[0]) >> 3 & 7;
    }

    public int hashCode() {
        return Arrays.hashCode(this.buf);
    }

    @Override
    public void setDatagramPacket(DatagramPacket datagramPacket) {
        if (datagramPacket == null) throw new IllegalArgumentException();
        if (datagramPacket.getLength() < this.buf.length) throw new IllegalArgumentException();
        Object object = datagramPacket.getData();
        int n = datagramPacket.getLength();
        byte[] arrby = this.buf;
        int n2 = n;
        if (n > arrby.length) {
            n2 = arrby.length;
        }
        System.arraycopy(object, 0, this.buf, 0, n2);
        object = this.getDatagramPacket();
        ((DatagramPacket)object).setAddress(datagramPacket.getAddress());
        n2 = datagramPacket.getPort();
        if (n2 <= 0) {
            n2 = 123;
        }
        ((DatagramPacket)object).setPort(n2);
        ((DatagramPacket)object).setData(this.buf);
    }

    @Override
    public void setLeapIndicator(int n) {
        byte[] arrby = this.buf;
        arrby[0] = (byte)((n & 3) << 6 | arrby[0] & 63);
    }

    @Override
    public void setMode(int n) {
        byte[] arrby = this.buf;
        arrby[0] = (byte)(n & 7 | arrby[0] & 248);
    }

    @Override
    public void setOriginateTimeStamp(TimeStamp timeStamp) {
        this.setTimestamp(24, timeStamp);
    }

    @Override
    public void setPoll(int n) {
        this.buf[2] = (byte)(n & 255);
    }

    @Override
    public void setPrecision(int n) {
        this.buf[3] = (byte)(n & 255);
    }

    @Override
    public void setReceiveTimeStamp(TimeStamp timeStamp) {
        this.setTimestamp(32, timeStamp);
    }

    @Override
    public void setReferenceId(int n) {
        this.setInt(12, n);
    }

    @Override
    public void setReferenceTime(TimeStamp timeStamp) {
        this.setTimestamp(16, timeStamp);
    }

    @Override
    public void setRootDelay(int n) {
        this.setInt(4, n);
    }

    @Override
    public void setRootDispersion(int n) {
        this.setInt(8, n);
    }

    @Override
    public void setStratum(int n) {
        this.buf[1] = (byte)(n & 255);
    }

    @Override
    public void setTransmitTime(TimeStamp timeStamp) {
        this.setTimestamp(40, timeStamp);
    }

    @Override
    public void setVersion(int n) {
        byte[] arrby = this.buf;
        arrby[0] = (byte)((n & 7) << 3 | arrby[0] & 199);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[version:");
        stringBuilder.append(this.getVersion());
        stringBuilder.append(", mode:");
        stringBuilder.append(this.getMode());
        stringBuilder.append(", poll:");
        stringBuilder.append(this.getPoll());
        stringBuilder.append(", precision:");
        stringBuilder.append(this.getPrecision());
        stringBuilder.append(", delay:");
        stringBuilder.append(this.getRootDelay());
        stringBuilder.append(", dispersion(ms):");
        stringBuilder.append(this.getRootDispersionInMillisDouble());
        stringBuilder.append(", id:");
        stringBuilder.append(this.getReferenceIdString());
        stringBuilder.append(", xmitTime:");
        stringBuilder.append(this.getTransmitTimeStamp().toDateString());
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }
}

