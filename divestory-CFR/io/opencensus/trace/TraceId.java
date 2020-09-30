/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.internal.Utils;
import io.opencensus.trace.BigendianEncoding;
import java.util.Random;
import javax.annotation.Nullable;

public final class TraceId
implements Comparable<TraceId> {
    private static final int BASE16_SIZE = 32;
    public static final TraceId INVALID = new TraceId(0L, 0L);
    private static final long INVALID_ID = 0L;
    public static final int SIZE = 16;
    private final long idHi;
    private final long idLo;

    private TraceId(long l, long l2) {
        this.idHi = l;
        this.idLo = l2;
    }

    public static TraceId fromBytes(byte[] arrby) {
        Utils.checkNotNull(arrby, "src");
        boolean bl = arrby.length == 16;
        Utils.checkArgument(bl, "Invalid size: expected %s, got %s", 16, arrby.length);
        return TraceId.fromBytes(arrby, 0);
    }

    public static TraceId fromBytes(byte[] arrby, int n) {
        Utils.checkNotNull(arrby, "src");
        return new TraceId(BigendianEncoding.longFromByteArray(arrby, n), BigendianEncoding.longFromByteArray(arrby, n + 8));
    }

    public static TraceId fromLowerBase16(CharSequence charSequence) {
        Utils.checkNotNull(charSequence, "src");
        boolean bl = charSequence.length() == 32;
        Utils.checkArgument(bl, "Invalid size: expected %s, got %s", 32, charSequence.length());
        return TraceId.fromLowerBase16(charSequence, 0);
    }

    public static TraceId fromLowerBase16(CharSequence charSequence, int n) {
        Utils.checkNotNull(charSequence, "src");
        return new TraceId(BigendianEncoding.longFromBase16String(charSequence, n), BigendianEncoding.longFromBase16String(charSequence, n + 16));
    }

    public static TraceId generateRandomId(Random random) {
        long l;
        long l2;
        do {
            l2 = random.nextLong();
            l = random.nextLong();
            if (l2 != 0L) return new TraceId(l2, l);
        } while (l == 0L);
        return new TraceId(l2, l);
    }

    @Override
    public int compareTo(TraceId traceId) {
        long l = this.idHi;
        long l2 = traceId.idHi;
        int n = -1;
        if (l == l2) {
            l = this.idLo;
            l2 = traceId.idLo;
            if (l == l2) {
                return 0;
            }
            if (l >= l2) return 1;
            return n;
        }
        if (l >= l2) return 1;
        return n;
    }

    public void copyBytesTo(byte[] arrby, int n) {
        BigendianEncoding.longToByteArray(this.idHi, arrby, n);
        BigendianEncoding.longToByteArray(this.idLo, arrby, n + 8);
    }

    public void copyLowerBase16To(char[] arrc, int n) {
        BigendianEncoding.longToBase16String(this.idHi, arrc, n);
        BigendianEncoding.longToBase16String(this.idLo, arrc, n + 16);
    }

    public boolean equals(@Nullable Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof TraceId)) {
            return false;
        }
        object = (TraceId)object;
        if (this.idHi != ((TraceId)object).idHi) return false;
        if (this.idLo != ((TraceId)object).idLo) return false;
        return bl;
    }

    public byte[] getBytes() {
        byte[] arrby = new byte[16];
        BigendianEncoding.longToByteArray(this.idHi, arrby, 0);
        BigendianEncoding.longToByteArray(this.idLo, arrby, 8);
        return arrby;
    }

    public long getLowerLong() {
        long l;
        long l2 = l = this.idHi;
        if (l >= 0L) return l2;
        return -l;
    }

    public int hashCode() {
        long l = this.idHi;
        int n = (int)(l ^ l >>> 32);
        l = this.idLo;
        return (n + 31) * 31 + (int)(l ^ l >>> 32);
    }

    public boolean isValid() {
        if (this.idHi != 0L) return true;
        if (this.idLo != 0L) return true;
        return false;
    }

    public String toLowerBase16() {
        char[] arrc = new char[32];
        this.copyLowerBase16To(arrc, 0);
        return new String(arrc);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TraceId{traceId=");
        stringBuilder.append(this.toLowerBase16());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

