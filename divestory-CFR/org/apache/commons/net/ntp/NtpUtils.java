/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ntp;

import org.apache.commons.net.ntp.NtpV3Packet;

public final class NtpUtils {
    public static String getHostAddress(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n >>> 24 & 255);
        stringBuilder.append(".");
        stringBuilder.append(n >>> 16 & 255);
        stringBuilder.append(".");
        stringBuilder.append(n >>> 8 & 255);
        stringBuilder.append(".");
        stringBuilder.append(n >>> 0 & 255);
        return stringBuilder.toString();
    }

    public static String getModeName(int n) {
        switch (n) {
            default: {
                return "Unknown";
            }
            case 7: {
                return "Private";
            }
            case 6: {
                return "Control";
            }
            case 5: {
                return "Broadcast";
            }
            case 4: {
                return "Server";
            }
            case 3: {
                return "Client";
            }
            case 2: {
                return "Symmetric Passive";
            }
            case 1: {
                return "Symmetric Active";
            }
            case 0: 
        }
        return "Reserved";
    }

    public static String getRefAddress(NtpV3Packet ntpV3Packet) {
        int n;
        if (ntpV3Packet == null) {
            n = 0;
            return NtpUtils.getHostAddress(n);
        }
        n = ntpV3Packet.getReferenceId();
        return NtpUtils.getHostAddress(n);
    }

    public static String getReferenceClock(NtpV3Packet object) {
        if (object == null) {
            return "";
        }
        int n = object.getReferenceId();
        if (n == 0) {
            return "";
        }
        object = new StringBuilder(4);
        int n2 = 24;
        while (n2 >= 0) {
            char c = (char)(n >>> n2 & 255);
            if (c == '\u0000') {
                return ((StringBuilder)object).toString();
            }
            if (!Character.isLetterOrDigit(c)) {
                return "";
            }
            ((StringBuilder)object).append(c);
            n2 -= 8;
        }
        return ((StringBuilder)object).toString();
    }
}

