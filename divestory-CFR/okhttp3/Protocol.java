/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.io.IOException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0001\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\fB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\r"}, d2={"Lokhttp3/Protocol;", "", "protocol", "", "(Ljava/lang/String;ILjava/lang/String;)V", "toString", "HTTP_1_0", "HTTP_1_1", "SPDY_3", "HTTP_2", "H2_PRIOR_KNOWLEDGE", "QUIC", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class Protocol
extends Enum<Protocol> {
    private static final /* synthetic */ Protocol[] $VALUES;
    public static final Companion Companion;
    public static final /* enum */ Protocol H2_PRIOR_KNOWLEDGE;
    public static final /* enum */ Protocol HTTP_1_0;
    public static final /* enum */ Protocol HTTP_1_1;
    public static final /* enum */ Protocol HTTP_2;
    public static final /* enum */ Protocol QUIC;
    @Deprecated(message="OkHttp has dropped support for SPDY. Prefer {@link #HTTP_2}.")
    public static final /* enum */ Protocol SPDY_3;
    private final String protocol;

    static {
        Protocol protocol;
        Protocol protocol2;
        Protocol protocol3;
        Protocol protocol4;
        Protocol protocol5;
        Protocol protocol6;
        HTTP_1_0 = protocol5 = new Protocol("http/1.0");
        HTTP_1_1 = protocol3 = new Protocol("http/1.1");
        SPDY_3 = protocol = new Protocol("spdy/3.1");
        HTTP_2 = protocol6 = new Protocol("h2");
        H2_PRIOR_KNOWLEDGE = protocol2 = new Protocol("h2_prior_knowledge");
        QUIC = protocol4 = new Protocol("quic");
        $VALUES = new Protocol[]{protocol5, protocol3, protocol, protocol6, protocol2, protocol4};
        Companion = new Companion(null);
    }

    private Protocol(String string3) {
        this.protocol = string3;
    }

    @JvmStatic
    public static final Protocol get(String string2) throws IOException {
        return Companion.get(string2);
    }

    public static Protocol valueOf(String string2) {
        return Enum.valueOf(Protocol.class, string2);
    }

    public static Protocol[] values() {
        return (Protocol[])$VALUES.clone();
    }

    public String toString() {
        return this.protocol;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2={"Lokhttp3/Protocol$Companion;", "", "()V", "get", "Lokhttp3/Protocol;", "protocol", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final Protocol get(String object) throws IOException {
            Intrinsics.checkParameterIsNotNull(object, "protocol");
            if (Intrinsics.areEqual(object, HTTP_1_0.protocol)) {
                return HTTP_1_0;
            }
            if (Intrinsics.areEqual(object, HTTP_1_1.protocol)) {
                return HTTP_1_1;
            }
            if (Intrinsics.areEqual(object, H2_PRIOR_KNOWLEDGE.protocol)) {
                return H2_PRIOR_KNOWLEDGE;
            }
            if (Intrinsics.areEqual(object, HTTP_2.protocol)) {
                return HTTP_2;
            }
            if (Intrinsics.areEqual(object, SPDY_3.protocol)) {
                return SPDY_3;
            }
            if (Intrinsics.areEqual(object, QUIC.protocol)) {
                return QUIC;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected protocol: ");
            stringBuilder.append((String)object);
            throw (Throwable)new IOException(stringBuilder.toString());
        }
    }

}

