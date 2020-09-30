/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0001\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\fB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\r\u0010\u0002\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u0006R\u0013\u0010\u0002\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\r"}, d2={"Lokhttp3/TlsVersion;", "", "javaName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "()Ljava/lang/String;", "-deprecated_javaName", "TLS_1_3", "TLS_1_2", "TLS_1_1", "TLS_1_0", "SSL_3_0", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class TlsVersion
extends Enum<TlsVersion> {
    private static final /* synthetic */ TlsVersion[] $VALUES;
    public static final Companion Companion;
    public static final /* enum */ TlsVersion SSL_3_0;
    public static final /* enum */ TlsVersion TLS_1_0;
    public static final /* enum */ TlsVersion TLS_1_1;
    public static final /* enum */ TlsVersion TLS_1_2;
    public static final /* enum */ TlsVersion TLS_1_3;
    private final String javaName;

    static {
        TlsVersion tlsVersion;
        TlsVersion tlsVersion2;
        TlsVersion tlsVersion3;
        TlsVersion tlsVersion4;
        TlsVersion tlsVersion5;
        TLS_1_3 = tlsVersion3 = new TlsVersion("TLSv1.3");
        TLS_1_2 = tlsVersion2 = new TlsVersion("TLSv1.2");
        TLS_1_1 = tlsVersion = new TlsVersion("TLSv1.1");
        TLS_1_0 = tlsVersion4 = new TlsVersion("TLSv1");
        SSL_3_0 = tlsVersion5 = new TlsVersion("SSLv3");
        $VALUES = new TlsVersion[]{tlsVersion3, tlsVersion2, tlsVersion, tlsVersion4, tlsVersion5};
        Companion = new Companion(null);
    }

    private TlsVersion(String string3) {
        this.javaName = string3;
    }

    @JvmStatic
    public static final TlsVersion forJavaName(String string2) {
        return Companion.forJavaName(string2);
    }

    public static TlsVersion valueOf(String string2) {
        return Enum.valueOf(TlsVersion.class, string2);
    }

    public static TlsVersion[] values() {
        return (TlsVersion[])$VALUES.clone();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="javaName", imports={}))
    public final String -deprecated_javaName() {
        return this.javaName;
    }

    public final String javaName() {
        return this.javaName;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2={"Lokhttp3/TlsVersion$Companion;", "", "()V", "forJavaName", "Lokhttp3/TlsVersion;", "javaName", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final TlsVersion forJavaName(String object) {
            block12 : {
                block10 : {
                    block11 : {
                        Intrinsics.checkParameterIsNotNull(object, "javaName");
                        int n = object.hashCode();
                        if (n == 79201641) break block10;
                        if (n == 79923350) break block11;
                        switch (n) {
                            default: {
                                break block12;
                            }
                            case -503070501: {
                                if (object.equals("TLSv1.3")) {
                                    return TLS_1_3;
                                }
                                break block12;
                            }
                            case -503070502: {
                                if (object.equals("TLSv1.2")) {
                                    return TLS_1_2;
                                }
                                break block12;
                            }
                            case -503070503: {
                                if (object.equals("TLSv1.1")) {
                                    return TLS_1_1;
                                }
                                break block12;
                            }
                        }
                    }
                    if (object.equals("TLSv1")) {
                        return TLS_1_0;
                    }
                    break block12;
                }
                if (object.equals("SSLv3")) {
                    return SSL_3_0;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected TLS version: ");
            stringBuilder.append((String)object);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString());
        }
    }

}

