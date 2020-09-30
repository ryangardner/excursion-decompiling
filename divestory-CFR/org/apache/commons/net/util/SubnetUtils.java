/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubnetUtils {
    private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
    private static final int NBITS = 32;
    private static final String SLASH_FORMAT = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,3})";
    private static final Pattern addressPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
    private static final Pattern cidrPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,3})");
    private int address = 0;
    private int broadcast = 0;
    private boolean inclusiveHostCount = false;
    private int netmask = 0;
    private int network = 0;

    public SubnetUtils(String string2) {
        this.calculate(string2);
    }

    public SubnetUtils(String string2, String string3) {
        this.calculate(this.toCidrNotation(string2, string3));
    }

    private void calculate(String string2) {
        Object object = cidrPattern.matcher(string2);
        if (!((Matcher)object).matches()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not parse [");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("]");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        this.address = this.matchAddress((Matcher)object);
        int n = Integer.parseInt(((Matcher)object).group(5));
        int n2 = 0;
        n = this.rangeCheck(n, 0, 32);
        do {
            if (n2 >= n) {
                n = this.address;
                n2 = this.netmask;
                this.network = n &= n2;
                this.broadcast = n | n2;
                return;
            }
            this.netmask |= 1 << 31 - n2;
            ++n2;
        } while (true);
    }

    private String format(int[] arrn) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < arrn.length) {
            stringBuilder.append(arrn[n]);
            if (n != arrn.length - 1) {
                stringBuilder.append(".");
            }
            ++n;
        }
        return stringBuilder.toString();
    }

    private int matchAddress(Matcher matcher) {
        int n = 1;
        int n2 = 0;
        while (n <= 4) {
            n2 |= (this.rangeCheck(Integer.parseInt(matcher.group(n)), 0, 255) & 255) << (4 - n) * 8;
            ++n;
        }
        return n2;
    }

    private int rangeCheck(int n, int n2, int n3) {
        if (n >= n2 && n <= n3) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value [");
        stringBuilder.append(n);
        stringBuilder.append("] not in range [");
        stringBuilder.append(n2);
        stringBuilder.append(",");
        stringBuilder.append(n3);
        stringBuilder.append("]");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private int[] toArray(int n) {
        int[] arrn = new int[4];
        int n2 = 3;
        while (n2 >= 0) {
            arrn[n2] = arrn[n2] | n >>> (3 - n2) * 8 & 255;
            --n2;
        }
        return arrn;
    }

    private String toCidrNotation(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("/");
        stringBuilder.append(this.pop(this.toInteger(string3)));
        return stringBuilder.toString();
    }

    private int toInteger(String string2) {
        Object object = addressPattern.matcher(string2);
        if (((Matcher)object).matches()) {
            return this.matchAddress((Matcher)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not parse [");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("]");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public final SubnetInfo getInfo() {
        return new SubnetInfo();
    }

    public boolean isInclusiveHostCount() {
        return this.inclusiveHostCount;
    }

    int pop(int n) {
        n -= n >>> 1 & 1431655765;
        n = (n & 858993459) + (n >>> 2 & 858993459);
        n = 252645135 & n + (n >>> 4);
        n += n >>> 8;
        return n + (n >>> 16) & 63;
    }

    public void setInclusiveHostCount(boolean bl) {
        this.inclusiveHostCount = bl;
    }

    public final class SubnetInfo {
        private static final long UNSIGNED_INT_MASK = 0xFFFFFFFFL;

        private SubnetInfo() {
        }

        private int address() {
            return SubnetUtils.this.address;
        }

        private int broadcast() {
            return SubnetUtils.this.broadcast;
        }

        private long broadcastLong() {
            return (long)SubnetUtils.this.broadcast & 0xFFFFFFFFL;
        }

        private int high() {
            if (SubnetUtils.this.isInclusiveHostCount()) {
                return this.broadcast();
            }
            if (this.broadcastLong() - this.networkLong() <= 1L) return 0;
            return this.broadcast() - 1;
        }

        private int low() {
            if (SubnetUtils.this.isInclusiveHostCount()) {
                return this.network();
            }
            if (this.broadcastLong() - this.networkLong() <= 1L) return 0;
            return this.network() + 1;
        }

        private int netmask() {
            return SubnetUtils.this.netmask;
        }

        private int network() {
            return SubnetUtils.this.network;
        }

        private long networkLong() {
            return (long)SubnetUtils.this.network & 0xFFFFFFFFL;
        }

        public int asInteger(String string2) {
            return SubnetUtils.this.toInteger(string2);
        }

        public String getAddress() {
            SubnetUtils subnetUtils = SubnetUtils.this;
            return subnetUtils.format(subnetUtils.toArray(this.address()));
        }

        @Deprecated
        public int getAddressCount() {
            long l = this.getAddressCountLong();
            if (l <= Integer.MAX_VALUE) {
                return (int)l;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Count is larger than an integer: ");
            stringBuilder.append(l);
            throw new RuntimeException(stringBuilder.toString());
        }

        public long getAddressCountLong() {
            long l = this.broadcastLong();
            long l2 = this.networkLong();
            int n = SubnetUtils.this.isInclusiveHostCount() ? 1 : -1;
            l2 = l = l - l2 + (long)n;
            if (l >= 0L) return l2;
            return 0L;
        }

        public String[] getAllAddresses() {
            int n = this.getAddressCount();
            String[] arrstring = new String[n];
            if (n == 0) {
                return arrstring;
            }
            int n2 = this.low();
            n = 0;
            while (n2 <= this.high()) {
                SubnetUtils subnetUtils = SubnetUtils.this;
                arrstring[n] = subnetUtils.format(subnetUtils.toArray(n2));
                ++n2;
                ++n;
            }
            return arrstring;
        }

        public String getBroadcastAddress() {
            SubnetUtils subnetUtils = SubnetUtils.this;
            return subnetUtils.format(subnetUtils.toArray(this.broadcast()));
        }

        public String getCidrSignature() {
            SubnetUtils subnetUtils = SubnetUtils.this;
            String string2 = subnetUtils.format(subnetUtils.toArray(this.address()));
            SubnetUtils subnetUtils2 = SubnetUtils.this;
            return subnetUtils.toCidrNotation(string2, subnetUtils2.format(subnetUtils2.toArray(this.netmask())));
        }

        public String getHighAddress() {
            SubnetUtils subnetUtils = SubnetUtils.this;
            return subnetUtils.format(subnetUtils.toArray(this.high()));
        }

        public String getLowAddress() {
            SubnetUtils subnetUtils = SubnetUtils.this;
            return subnetUtils.format(subnetUtils.toArray(this.low()));
        }

        public String getNetmask() {
            SubnetUtils subnetUtils = SubnetUtils.this;
            return subnetUtils.format(subnetUtils.toArray(this.netmask()));
        }

        public String getNetworkAddress() {
            SubnetUtils subnetUtils = SubnetUtils.this;
            return subnetUtils.format(subnetUtils.toArray(this.network()));
        }

        public boolean isInRange(int n) {
            long l = (long)n & 0xFFFFFFFFL;
            long l2 = this.low();
            long l3 = this.high();
            if (l < (l2 & 0xFFFFFFFFL)) return false;
            if (l > (0xFFFFFFFFL & l3)) return false;
            return true;
        }

        public boolean isInRange(String string2) {
            return this.isInRange(SubnetUtils.this.toInteger(string2));
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CIDR Signature:\t[");
            stringBuilder.append(this.getCidrSignature());
            stringBuilder.append("]");
            stringBuilder.append(" Netmask: [");
            stringBuilder.append(this.getNetmask());
            stringBuilder.append("]\n");
            stringBuilder.append("Network:\t[");
            stringBuilder.append(this.getNetworkAddress());
            stringBuilder.append("]\n");
            stringBuilder.append("Broadcast:\t[");
            stringBuilder.append(this.getBroadcastAddress());
            stringBuilder.append("]\n");
            stringBuilder.append("First Address:\t[");
            stringBuilder.append(this.getLowAddress());
            stringBuilder.append("]\n");
            stringBuilder.append("Last Address:\t[");
            stringBuilder.append(this.getHighAddress());
            stringBuilder.append("]\n");
            stringBuilder.append("# Addresses:\t[");
            stringBuilder.append(this.getAddressCount());
            stringBuilder.append("]\n");
            return stringBuilder.toString();
        }
    }

}

