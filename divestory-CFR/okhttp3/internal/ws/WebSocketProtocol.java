/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.ws;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0004J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001f\u001a\u00020\u0006J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001d\u001a\u00020$J\u000e\u0010%\u001a\u00020!2\u0006\u0010\u001f\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fXT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000fXT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0006XT\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u000fXT\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2={"Lokhttp3/internal/ws/WebSocketProtocol;", "", "()V", "ACCEPT_MAGIC", "", "B0_FLAG_FIN", "", "B0_FLAG_RSV1", "B0_FLAG_RSV2", "B0_FLAG_RSV3", "B0_MASK_OPCODE", "B1_FLAG_MASK", "B1_MASK_LENGTH", "CLOSE_CLIENT_GOING_AWAY", "CLOSE_MESSAGE_MAX", "", "CLOSE_NO_STATUS_CODE", "OPCODE_BINARY", "OPCODE_CONTINUATION", "OPCODE_CONTROL_CLOSE", "OPCODE_CONTROL_PING", "OPCODE_CONTROL_PONG", "OPCODE_FLAG_CONTROL", "OPCODE_TEXT", "PAYLOAD_BYTE_MAX", "PAYLOAD_LONG", "PAYLOAD_SHORT", "PAYLOAD_SHORT_MAX", "acceptHeader", "key", "closeCodeExceptionMessage", "code", "toggleMask", "", "cursor", "Lokio/Buffer$UnsafeCursor;", "", "validateCloseCode", "okhttp"}, k=1, mv={1, 1, 16})
public final class WebSocketProtocol {
    public static final String ACCEPT_MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    public static final int B0_FLAG_FIN = 128;
    public static final int B0_FLAG_RSV1 = 64;
    public static final int B0_FLAG_RSV2 = 32;
    public static final int B0_FLAG_RSV3 = 16;
    public static final int B0_MASK_OPCODE = 15;
    public static final int B1_FLAG_MASK = 128;
    public static final int B1_MASK_LENGTH = 127;
    public static final int CLOSE_CLIENT_GOING_AWAY = 1001;
    public static final long CLOSE_MESSAGE_MAX = 123L;
    public static final int CLOSE_NO_STATUS_CODE = 1005;
    public static final WebSocketProtocol INSTANCE = new WebSocketProtocol();
    public static final int OPCODE_BINARY = 2;
    public static final int OPCODE_CONTINUATION = 0;
    public static final int OPCODE_CONTROL_CLOSE = 8;
    public static final int OPCODE_CONTROL_PING = 9;
    public static final int OPCODE_CONTROL_PONG = 10;
    public static final int OPCODE_FLAG_CONTROL = 8;
    public static final int OPCODE_TEXT = 1;
    public static final long PAYLOAD_BYTE_MAX = 125L;
    public static final int PAYLOAD_LONG = 127;
    public static final int PAYLOAD_SHORT = 126;
    public static final long PAYLOAD_SHORT_MAX = 65535L;

    private WebSocketProtocol() {
    }

    public final String acceptHeader(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "key");
        ByteString.Companion companion = ByteString.Companion;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(ACCEPT_MAGIC);
        return companion.encodeUtf8(stringBuilder.toString()).sha1().base64();
    }

    public final String closeCodeExceptionMessage(int n) {
        CharSequence charSequence;
        if (n >= 1000 && n < 5000) {
            if (1004 > n || 1006 < n) {
                if (1015 > n) return null;
                if (2999 < n) return null;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Code ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" is reserved and may not be used.");
            return ((StringBuilder)charSequence).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Code must be in range [1000,5000): ");
        ((StringBuilder)charSequence).append(n);
        return ((StringBuilder)charSequence).toString();
    }

    public final void toggleMask(Buffer.UnsafeCursor unsafeCursor, byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(unsafeCursor, "cursor");
        Intrinsics.checkParameterIsNotNull(arrby, "key");
        int n = arrby.length;
        int n2 = 0;
        do {
            byte[] arrby2 = unsafeCursor.data;
            int n3 = unsafeCursor.start;
            int n4 = unsafeCursor.end;
            int n5 = n2;
            if (arrby2 != null) {
                do {
                    n5 = ++n2;
                    if (n3 >= n4) break;
                    arrby2[n3] = (byte)(arrby2[n3] ^ arrby[n2 %= n]);
                    ++n3;
                } while (true);
            }
            n2 = n5;
        } while (unsafeCursor.next() != -1);
    }

    public final void validateCloseCode(int n) {
        String string2 = this.closeCodeExceptionMessage(n);
        n = string2 == null ? 1 : 0;
        if (n != 0) return;
        if (string2 != null) throw (Throwable)new IllegalArgumentException(string2.toString());
        Intrinsics.throwNpe();
        throw (Throwable)new IllegalArgumentException(string2.toString());
    }
}

