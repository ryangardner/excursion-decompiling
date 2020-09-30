/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http2;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0013\b\u0086\u0001\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0015B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014\u00a8\u0006\u0016"}, d2={"Lokhttp3/internal/http2/ErrorCode;", "", "httpCode", "", "(Ljava/lang/String;II)V", "getHttpCode", "()I", "NO_ERROR", "PROTOCOL_ERROR", "INTERNAL_ERROR", "FLOW_CONTROL_ERROR", "SETTINGS_TIMEOUT", "STREAM_CLOSED", "FRAME_SIZE_ERROR", "REFUSED_STREAM", "CANCEL", "COMPRESSION_ERROR", "CONNECT_ERROR", "ENHANCE_YOUR_CALM", "INADEQUATE_SECURITY", "HTTP_1_1_REQUIRED", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class ErrorCode
extends Enum<ErrorCode> {
    private static final /* synthetic */ ErrorCode[] $VALUES;
    public static final /* enum */ ErrorCode CANCEL;
    public static final /* enum */ ErrorCode COMPRESSION_ERROR;
    public static final /* enum */ ErrorCode CONNECT_ERROR;
    public static final Companion Companion;
    public static final /* enum */ ErrorCode ENHANCE_YOUR_CALM;
    public static final /* enum */ ErrorCode FLOW_CONTROL_ERROR;
    public static final /* enum */ ErrorCode FRAME_SIZE_ERROR;
    public static final /* enum */ ErrorCode HTTP_1_1_REQUIRED;
    public static final /* enum */ ErrorCode INADEQUATE_SECURITY;
    public static final /* enum */ ErrorCode INTERNAL_ERROR;
    public static final /* enum */ ErrorCode NO_ERROR;
    public static final /* enum */ ErrorCode PROTOCOL_ERROR;
    public static final /* enum */ ErrorCode REFUSED_STREAM;
    public static final /* enum */ ErrorCode SETTINGS_TIMEOUT;
    public static final /* enum */ ErrorCode STREAM_CLOSED;
    private final int httpCode;

    static {
        ErrorCode errorCode;
        ErrorCode errorCode2;
        ErrorCode errorCode3;
        ErrorCode errorCode4;
        ErrorCode errorCode5;
        ErrorCode errorCode6;
        ErrorCode errorCode7;
        ErrorCode errorCode8;
        ErrorCode errorCode9;
        ErrorCode errorCode10;
        ErrorCode errorCode11;
        ErrorCode errorCode12;
        ErrorCode errorCode13;
        ErrorCode errorCode14;
        NO_ERROR = errorCode10 = new ErrorCode(0);
        PROTOCOL_ERROR = errorCode14 = new ErrorCode(1);
        INTERNAL_ERROR = errorCode2 = new ErrorCode(2);
        FLOW_CONTROL_ERROR = errorCode7 = new ErrorCode(3);
        SETTINGS_TIMEOUT = errorCode12 = new ErrorCode(4);
        STREAM_CLOSED = errorCode9 = new ErrorCode(5);
        FRAME_SIZE_ERROR = errorCode5 = new ErrorCode(6);
        REFUSED_STREAM = errorCode = new ErrorCode(7);
        CANCEL = errorCode11 = new ErrorCode(8);
        COMPRESSION_ERROR = errorCode3 = new ErrorCode(9);
        CONNECT_ERROR = errorCode13 = new ErrorCode(10);
        ENHANCE_YOUR_CALM = errorCode8 = new ErrorCode(11);
        INADEQUATE_SECURITY = errorCode6 = new ErrorCode(12);
        HTTP_1_1_REQUIRED = errorCode4 = new ErrorCode(13);
        $VALUES = new ErrorCode[]{errorCode10, errorCode14, errorCode2, errorCode7, errorCode12, errorCode9, errorCode5, errorCode, errorCode11, errorCode3, errorCode13, errorCode8, errorCode6, errorCode4};
        Companion = new Companion(null);
    }

    private ErrorCode(int n2) {
        this.httpCode = n2;
    }

    public static ErrorCode valueOf(String string2) {
        return Enum.valueOf(ErrorCode.class, string2);
    }

    public static ErrorCode[] values() {
        return (ErrorCode[])$VALUES.clone();
    }

    public final int getHttpCode() {
        return this.httpCode;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2={"Lokhttp3/internal/http2/ErrorCode$Companion;", "", "()V", "fromHttp2", "Lokhttp3/internal/http2/ErrorCode;", "code", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ErrorCode fromHttp2(int n) {
            ErrorCode[] arrerrorCode = ErrorCode.values();
            int n2 = arrerrorCode.length;
            int n3 = 0;
            while (n3 < n2) {
                ErrorCode errorCode = arrerrorCode[n3];
                boolean bl = errorCode.getHttpCode() == n;
                if (bl) {
                    return errorCode;
                }
                ++n3;
            }
            return null;
        }
    }

}

