/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.internal.Utils;
import io.opencensus.trace.BigendianEncoding;
import java.util.Random;
import javax.annotation.Nullable;

public final class SpanId
implements Comparable<SpanId> {
    private static final int BASE16_SIZE = 16;
    public static final SpanId INVALID = new SpanId(0L);
    private static final long INVALID_ID = 0L;
    public static final int SIZE = 8;
    private final long id;

    private SpanId(long l) {
        this.id = l;
    }

    public static SpanId fromBytes(byte[] arrby) {
        Utils.checkNotNull(arrby, "src");
        boolean bl = arrby.length == 8;
        Utils.checkArgument(bl, "Invalid size: expected %s, got %s", 8, arrby.length);
        return SpanId.fromBytes(arrby, 0);
    }

    public static SpanId fromBytes(byte[] arrby, int n) {
        Utils.checkNotNull(arrby, "src");
        return new SpanId(BigendianEncoding.longFromByteArray(arrby, n));
    }

    public static SpanId fromLowerBase16(CharSequence charSequence) {
        Utils.checkNotNull(charSequence, "src");
        boolean bl = charSequence.length() == 16;
        Utils.checkArgument(bl, "Invalid size: expected %s, got %s", 16, charSequence.length());
        return SpanId.fromLowerBase16(charSequence, 0);
    }

    public static SpanId fromLowerBase16(CharSequence charSequence, int n) {
        Utils.checkNotNull(charSequence, "src");
        return new SpanId(BigendianEncoding.longFromBase16String(charSequence, n));
    }

    public static SpanId generateRandomId(Random random) {
        long l;
        while ((l = random.nextLong()) == 0L) {
        }
        return new SpanId(l);
    }

    @Override
    public int compareTo(SpanId spanId) {
        long l = this.id;
        long l2 = spanId.id;
        if (l < l2) {
            return -1;
        }
        if (l != l2) return 1;
        return 0;
    }

    public void copyBytesTo(byte[] arrby, int n) {
        BigendianEncoding.longToByteArray(this.id, arrby, n);
    }

    public void copyLowerBase16To(char[] arrc, int n) {
        BigendianEncoding.longToBase16String(this.id, arrc, n);
    }

    public boolean equals(@Nullable Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SpanId)) {
            return false;
        }
        object = (SpanId)object;
        if (this.id != ((SpanId)object).id) return false;
        return bl;
    }

    public byte[] getBytes() {
        byte[] arrby = new byte[8];
        BigendianEncoding.longToByteArray(this.id, arrby, 0);
        return arrby;
    }

    public int hashCode() {
        long l = this.id;
        return (int)(l ^ l >>> 32);
    }

    public boolean isValid() {
        if (this.id == 0L) return false;
        return true;
    }

    public String toLowerBase16() {
        char[] arrc = new char[16];
        this.copyLowerBase16To(arrc, 0);
        return new String(arrc);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SpanId{spanId=");
        stringBuilder.append(this.toLowerBase16());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

