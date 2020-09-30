/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.internal.Utils;
import io.opencensus.trace.BigendianEncoding;
import java.util.Arrays;
import javax.annotation.Nullable;

public final class TraceOptions {
    private static final int BASE16_SIZE = 2;
    public static final TraceOptions DEFAULT = TraceOptions.fromByte((byte)0);
    private static final byte DEFAULT_OPTIONS = 0;
    private static final byte IS_SAMPLED = 1;
    public static final int SIZE = 1;
    private final byte options;

    private TraceOptions(byte by) {
        this.options = by;
    }

    public static Builder builder() {
        return new Builder(0);
    }

    public static Builder builder(TraceOptions traceOptions) {
        return new Builder(traceOptions.options);
    }

    public static TraceOptions fromByte(byte by) {
        return new TraceOptions(by);
    }

    @Deprecated
    public static TraceOptions fromBytes(byte[] arrby) {
        Utils.checkNotNull(arrby, "buffer");
        boolean bl = arrby.length == 1;
        Utils.checkArgument(bl, "Invalid size: expected %s, got %s", 1, arrby.length);
        return TraceOptions.fromByte(arrby[0]);
    }

    @Deprecated
    public static TraceOptions fromBytes(byte[] arrby, int n) {
        Utils.checkIndex(n, arrby.length);
        return TraceOptions.fromByte(arrby[n]);
    }

    public static TraceOptions fromLowerBase16(CharSequence charSequence, int n) {
        return new TraceOptions(BigendianEncoding.byteFromBase16String(charSequence, n));
    }

    private boolean hasOption(int n) {
        if ((n & this.options) == 0) return false;
        return true;
    }

    public void copyBytesTo(byte[] arrby, int n) {
        Utils.checkIndex(n, arrby.length);
        arrby[n] = this.options;
    }

    public void copyLowerBase16To(char[] arrc, int n) {
        BigendianEncoding.byteToBase16String(this.options, arrc, n);
    }

    public boolean equals(@Nullable Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof TraceOptions)) {
            return false;
        }
        object = (TraceOptions)object;
        if (this.options != ((TraceOptions)object).options) return false;
        return bl;
    }

    public byte getByte() {
        return this.options;
    }

    @Deprecated
    public byte[] getBytes() {
        return new byte[]{this.options};
    }

    byte getOptions() {
        return this.options;
    }

    public int hashCode() {
        return Arrays.hashCode(new byte[]{this.options});
    }

    public boolean isSampled() {
        return this.hasOption(1);
    }

    public String toLowerBase16() {
        char[] arrc = new char[2];
        this.copyLowerBase16To(arrc, 0);
        return new String(arrc);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TraceOptions{sampled=");
        stringBuilder.append(this.isSampled());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static final class Builder {
        private byte options;

        private Builder(byte by) {
            this.options = by;
        }

        public TraceOptions build() {
            return TraceOptions.fromByte(this.options);
        }

        @Deprecated
        public Builder setIsSampled() {
            return this.setIsSampled(true);
        }

        public Builder setIsSampled(boolean bl) {
            if (bl) {
                this.options = (byte)(this.options | 1);
                return this;
            }
            this.options = (byte)(this.options & -2);
            return this;
        }
    }

}

