/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ntp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeStamp;

public class TimeInfo {
    private List<String> _comments;
    private Long _delay;
    private boolean _detailsComputed;
    private final NtpV3Packet _message;
    private Long _offset;
    private final long _returnTime;

    public TimeInfo(NtpV3Packet ntpV3Packet, long l) {
        this(ntpV3Packet, l, null, true);
    }

    public TimeInfo(NtpV3Packet ntpV3Packet, long l, List<String> list) {
        this(ntpV3Packet, l, list, true);
    }

    public TimeInfo(NtpV3Packet ntpV3Packet, long l, List<String> list, boolean bl) {
        if (ntpV3Packet == null) throw new IllegalArgumentException("message cannot be null");
        this._returnTime = l;
        this._message = ntpV3Packet;
        this._comments = list;
        if (!bl) return;
        this.computeDetails();
    }

    public TimeInfo(NtpV3Packet ntpV3Packet, long l, boolean bl) {
        this(ntpV3Packet, l, null, bl);
    }

    public void addComment(String string2) {
        if (this._comments == null) {
            this._comments = new ArrayList<String>();
        }
        this._comments.add(string2);
    }

    public void computeDetails() {
        long l;
        TimeStamp timeStamp;
        long l2;
        long l3;
        long l4;
        TimeStamp timeStamp2;
        block8 : {
            block12 : {
                long l5;
                block10 : {
                    block13 : {
                        long l6;
                        block11 : {
                            block9 : {
                                if (this._detailsComputed) {
                                    return;
                                }
                                this._detailsComputed = true;
                                if (this._comments == null) {
                                    this._comments = new ArrayList<String>();
                                }
                                TimeStamp timeStamp3 = this._message.getOriginateTimeStamp();
                                l3 = timeStamp3.getTime();
                                timeStamp2 = this._message.getReceiveTimeStamp();
                                l4 = timeStamp2.getTime();
                                timeStamp = this._message.getTransmitTimeStamp();
                                l = timeStamp.getTime();
                                l5 = timeStamp3.ntpValue();
                                l2 = 0L;
                                if (l5 == 0L) {
                                    if (timeStamp.ntpValue() != 0L) {
                                        this._offset = l - this._returnTime;
                                        this._comments.add("Error: zero orig time -- cannot compute delay");
                                        return;
                                    }
                                    this._comments.add("Error: zero orig time -- cannot compute delay/offset");
                                    return;
                                }
                                if (timeStamp2.ntpValue() == 0L || timeStamp.ntpValue() == 0L) break block8;
                                l5 = this._returnTime - l3;
                                if (l >= l4) break block9;
                                this._comments.add("Error: xmitTime < rcvTime");
                                break block10;
                            }
                            l6 = l - l4;
                            if (l6 > l5) break block11;
                            l2 = l5 - l6;
                            break block12;
                        }
                        if (l6 - l5 != 1L) break block13;
                        if (l5 == 0L) break block10;
                        this._comments.add("Info: processing time > total network time by 1 ms -> assume zero delay");
                        break block12;
                    }
                    this._comments.add("Warning: processing time > total network time");
                }
                l2 = l5;
            }
            this._delay = l2;
            if (l3 > this._returnTime) {
                this._comments.add("Error: OrigTime > DestRcvTime");
            }
            this._offset = (l4 - l3 + (l - this._returnTime)) / 2L;
            return;
        }
        this._comments.add("Warning: zero rcvNtpTime or xmitNtpTime");
        l2 = this._returnTime;
        if (l3 > l2) {
            this._comments.add("Error: OrigTime > DestRcvTime");
        } else {
            this._delay = l2 - l3;
        }
        if (timeStamp2.ntpValue() != 0L) {
            this._offset = l4 - l3;
            return;
        }
        if (timeStamp.ntpValue() == 0L) return;
        this._offset = l - this._returnTime;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (TimeInfo)object;
        if (this._returnTime != ((TimeInfo)object)._returnTime) return false;
        if (!this._message.equals(((TimeInfo)object)._message)) return false;
        return bl;
    }

    public InetAddress getAddress() {
        DatagramPacket datagramPacket = this._message.getDatagramPacket();
        if (datagramPacket != null) return datagramPacket.getAddress();
        return null;
    }

    public List<String> getComments() {
        return this._comments;
    }

    public Long getDelay() {
        return this._delay;
    }

    public NtpV3Packet getMessage() {
        return this._message;
    }

    public Long getOffset() {
        return this._offset;
    }

    public long getReturnTime() {
        return this._returnTime;
    }

    public int hashCode() {
        return (int)this._returnTime * 31 + this._message.hashCode();
    }
}

