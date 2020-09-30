/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Protocol;
import okhttp3.Response;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0007H\u0016R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lokhttp3/internal/http/StatusLine;", "", "protocol", "Lokhttp3/Protocol;", "code", "", "message", "", "(Lokhttp3/Protocol;ILjava/lang/String;)V", "toString", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class StatusLine {
    public static final Companion Companion = new Companion(null);
    public static final int HTTP_CONTINUE = 100;
    public static final int HTTP_MISDIRECTED_REQUEST = 421;
    public static final int HTTP_PERM_REDIRECT = 308;
    public static final int HTTP_TEMP_REDIRECT = 307;
    public final int code;
    public final String message;
    public final Protocol protocol;

    public StatusLine(Protocol protocol, int n, String string2) {
        Intrinsics.checkParameterIsNotNull((Object)protocol, "protocol");
        Intrinsics.checkParameterIsNotNull(string2, "message");
        this.protocol = protocol;
        this.code = n;
        this.message = string2;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        if (this.protocol == Protocol.HTTP_1_0) {
            charSequence.append("HTTP/1.0");
        } else {
            charSequence.append("HTTP/1.1");
        }
        charSequence.append(' ');
        charSequence.append(this.code);
        charSequence.append(' ');
        charSequence.append(this.message);
        charSequence = charSequence.toString();
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "StringBuilder().apply(builderAction).toString()");
        return charSequence;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lokhttp3/internal/http/StatusLine$Companion;", "", "()V", "HTTP_CONTINUE", "", "HTTP_MISDIRECTED_REQUEST", "HTTP_PERM_REDIRECT", "HTTP_TEMP_REDIRECT", "get", "Lokhttp3/internal/http/StatusLine;", "response", "Lokhttp3/Response;", "parse", "statusLine", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final StatusLine get(Response response) {
            Intrinsics.checkParameterIsNotNull(response, "response");
            return new StatusLine(response.protocol(), response.code(), response.message());
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        public final StatusLine parse(String var1_1) throws IOException {
            Intrinsics.checkParameterIsNotNull(var1_1, "statusLine");
            var2_2 = StringsKt.startsWith$default(var1_1, "HTTP/1.", false, 2, null);
            var3_3 = 9;
            if (!var2_2) ** GOTO lbl25
            if (var1_1.length() >= 9 && var1_1.charAt(8) == ' ') {
                var4_4 = var1_1.charAt(7) - 48;
                if (var4_4 == 0) {
                    var5_5 = Protocol.HTTP_1_0;
                } else {
                    if (var4_4 != 1) {
                        var5_6 = new StringBuilder();
                        var5_6.append("Unexpected status line: ");
                        var5_6.append(var1_1);
                        throw (Throwable)new ProtocolException(var5_6.toString());
                    }
                    var5_5 = Protocol.HTTP_1_1;
                }
            } else {
                var5_7 = new StringBuilder();
                var5_7.append("Unexpected status line: ");
                var5_7.append(var1_1);
                throw (Throwable)new ProtocolException(var5_7.toString());
lbl25: // 1 sources:
                if (!StringsKt.startsWith$default(var1_1, "ICY ", false, 2, null)) {
                    var5_10 = new StringBuilder();
                    var5_10.append("Unexpected status line: ");
                    var5_10.append(var1_1);
                    throw (Throwable)new ProtocolException(var5_10.toString());
                }
                var5_5 = Protocol.HTTP_1_0;
                var3_3 = 4;
            }
            var6_11 = var1_1.length();
            var4_4 = var3_3 + 3;
            if (var6_11 < var4_4) {
                var5_5 = new StringBuilder();
                var5_5.append("Unexpected status line: ");
                var5_5.append(var1_1);
                throw (Throwable)new ProtocolException(var5_5.toString());
            }
            try {
                var7_12 = var1_1.substring(var3_3, var4_4);
                Intrinsics.checkExpressionValueIsNotNull(var7_12, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                var6_11 = Integer.parseInt(var7_12);
            }
            catch (NumberFormatException var5_8) {
                var5_9 = new StringBuilder();
                var5_9.append("Unexpected status line: ");
                var5_9.append(var1_1);
                throw (Throwable)new ProtocolException(var5_9.toString());
            }
            if (var1_1.length() <= var4_4) {
                var1_1 = "";
                return new StatusLine((Protocol)var5_5, var6_11, var1_1);
            }
            if (var1_1.charAt(var4_4) == ' ') {
                var1_1 = var1_1.substring(var3_3 + 4);
                Intrinsics.checkExpressionValueIsNotNull(var1_1, "(this as java.lang.String).substring(startIndex)");
                return new StatusLine((Protocol)var5_5, var6_11, var1_1);
            }
            var5_5 = new StringBuilder();
            var5_5.append("Unexpected status line: ");
            var5_5.append(var1_1);
            throw (Throwable)new ProtocolException(var5_5.toString());
        }
    }

}

