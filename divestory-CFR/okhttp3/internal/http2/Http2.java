/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http2;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u000bJ\u0015\u0010\"\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u000bH\u0000\u00a2\u0006\u0002\b#J.\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u000b2\u0006\u0010(\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u000bR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\n\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\u0014\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2={"Lokhttp3/internal/http2/Http2;", "", "()V", "BINARY", "", "", "[Ljava/lang/String;", "CONNECTION_PREFACE", "Lokio/ByteString;", "FLAGS", "FLAG_ACK", "", "FLAG_COMPRESSED", "FLAG_END_HEADERS", "FLAG_END_PUSH_PROMISE", "FLAG_END_STREAM", "FLAG_NONE", "FLAG_PADDED", "FLAG_PRIORITY", "FRAME_NAMES", "INITIAL_MAX_FRAME_SIZE", "TYPE_CONTINUATION", "TYPE_DATA", "TYPE_GOAWAY", "TYPE_HEADERS", "TYPE_PING", "TYPE_PRIORITY", "TYPE_PUSH_PROMISE", "TYPE_RST_STREAM", "TYPE_SETTINGS", "TYPE_WINDOW_UPDATE", "formatFlags", "type", "flags", "formattedType", "formattedType$okhttp", "frameLog", "inbound", "", "streamId", "length", "okhttp"}, k=1, mv={1, 1, 16})
public final class Http2 {
    private static final String[] BINARY;
    public static final ByteString CONNECTION_PREFACE;
    private static final String[] FLAGS;
    public static final int FLAG_ACK = 1;
    public static final int FLAG_COMPRESSED = 32;
    public static final int FLAG_END_HEADERS = 4;
    public static final int FLAG_END_PUSH_PROMISE = 4;
    public static final int FLAG_END_STREAM = 1;
    public static final int FLAG_NONE = 0;
    public static final int FLAG_PADDED = 8;
    public static final int FLAG_PRIORITY = 32;
    private static final String[] FRAME_NAMES;
    public static final int INITIAL_MAX_FRAME_SIZE = 16384;
    public static final Http2 INSTANCE;
    public static final int TYPE_CONTINUATION = 9;
    public static final int TYPE_DATA = 0;
    public static final int TYPE_GOAWAY = 7;
    public static final int TYPE_HEADERS = 1;
    public static final int TYPE_PING = 6;
    public static final int TYPE_PRIORITY = 2;
    public static final int TYPE_PUSH_PROMISE = 5;
    public static final int TYPE_RST_STREAM = 3;
    public static final int TYPE_SETTINGS = 4;
    public static final int TYPE_WINDOW_UPDATE = 8;

    static {
        int n;
        int n2;
        Object[] arrobject;
        INSTANCE = new Http2();
        CONNECTION_PREFACE = ByteString.Companion.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
        FRAME_NAMES = new String[]{"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
        FLAGS = new String[64];
        Object object = new String[256];
        int n3 = 0;
        for (n2 = 0; n2 < 256; ++n2) {
            arrobject = Integer.toBinaryString(n2);
            Intrinsics.checkExpressionValueIsNotNull(arrobject, "Integer.toBinaryString(it)");
            object[n2] = StringsKt.replace$default(Util.format("%8s", new Object[]{arrobject}), ' ', '0', false, 4, null);
        }
        BINARY = object;
        object = FLAGS;
        object[0] = "";
        object[1] = "END_STREAM";
        arrobject = new int[]{1};
        object[8] = "PADDED";
        for (n2 = 0; n2 < 1; ++n2) {
            n = arrobject[n2];
            object = FLAGS;
            object[n | 8] = Intrinsics.stringPlus(object[n], "|PADDED");
        }
        object = FLAGS;
        object[4] = "END_HEADERS";
        object[32] = "PRIORITY";
        object[36] = "END_HEADERS|PRIORITY";
        for (n2 = 0; n2 < 3; ++n2) {
            int n4 = new int[]{4, 32, 36}[n2];
            for (n = 0; n < 1; ++n) {
                int n5 = arrobject[n];
                String[] arrstring = FLAGS;
                int n6 = n5 | n4;
                object = new StringBuilder();
                ((StringBuilder)object).append(FLAGS[n5]);
                ((StringBuilder)object).append("|");
                ((StringBuilder)object).append(FLAGS[n4]);
                arrstring[n6] = ((StringBuilder)object).toString();
                arrstring = FLAGS;
                object = new StringBuilder();
                ((StringBuilder)object).append(FLAGS[n5]);
                ((StringBuilder)object).append("|");
                ((StringBuilder)object).append(FLAGS[n4]);
                ((StringBuilder)object).append("|PADDED");
                arrstring[n6 | 8] = ((StringBuilder)object).toString();
            }
        }
        n = FLAGS.length;
        n2 = n3;
        while (n2 < n) {
            arrobject = FLAGS;
            if (arrobject[n2] == null) {
                arrobject[n2] = (int)BINARY[n2];
            }
            ++n2;
        }
    }

    private Http2() {
    }

    public final String formatFlags(int n, int n2) {
        if (n2 == 0) {
            return "";
        }
        if (n == 2) return BINARY[n2];
        if (n == 3) return BINARY[n2];
        if (n != 4 && n != 6) {
            Object object;
            if (n == 7) return BINARY[n2];
            if (n == 8) return BINARY[n2];
            Object object2 = FLAGS;
            if (n2 < ((String[])object2).length) {
                object2 = object = object2[n2];
                if (object == null) {
                    Intrinsics.throwNpe();
                    object2 = object;
                }
            } else {
                object2 = BINARY[n2];
            }
            object = object2;
            if (n == 5 && (n2 & 4) != 0) {
                return StringsKt.replace$default((String)object, "HEADERS", "PUSH_PROMISE", false, 4, null);
            }
            object2 = object;
            if (n != 0) return object2;
            object2 = object;
            if ((n2 & 32) == 0) return object2;
            return StringsKt.replace$default((String)object, "PRIORITY", "COMPRESSED", false, 4, null);
        }
        if (n2 != 1) return BINARY[n2];
        return "ACK";
    }

    public final String formattedType$okhttp(int n) {
        Object object = FRAME_NAMES;
        if (n >= ((String[])object).length) return Util.format("0x%02x", n);
        return object[n];
    }

    public final String frameLog(boolean bl, int n, int n2, int n3, int n4) {
        String string2 = this.formattedType$okhttp(n3);
        String string3 = this.formatFlags(n3, n4);
        String string4 = bl ? "<<" : ">>";
        return Util.format("%s 0x%08x %5d %-13s %s", string4, n, n2, string2, string3);
    }
}

