/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.net.ssl.SSLSocket;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.CipherSuite;
import okhttp3.TlsVersion;
import okhttp3.internal.Util;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\t\u0018\u0000 $2\u00020\u0001:\u0002#$B7\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\tJ\u001d\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0003H\u0000\u00a2\u0006\u0002\b\u0017J\u0015\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bH\u0007\u00a2\u0006\u0002\b\u0018J\u0013\u0010\u0019\u001a\u00020\u00032\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u000e\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u0015J\u0018\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0003H\u0002J\r\u0010\u0004\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b J\u0015\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u000bH\u0007\u00a2\u0006\u0002\b!J\b\u0010\"\u001a\u00020\u0007H\u0016R\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b8G\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\rR\u0018\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000eR\u0013\u0010\u0002\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u000fR\u0013\u0010\u0004\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000fR\u0019\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u000b8G\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\rR\u0018\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000e\u00a8\u0006%"}, d2={"Lokhttp3/ConnectionSpec;", "", "isTls", "", "supportsTlsExtensions", "cipherSuitesAsString", "", "", "tlsVersionsAsString", "(ZZ[Ljava/lang/String;[Ljava/lang/String;)V", "cipherSuites", "", "Lokhttp3/CipherSuite;", "()Ljava/util/List;", "[Ljava/lang/String;", "()Z", "tlsVersions", "Lokhttp3/TlsVersion;", "apply", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "isFallback", "apply$okhttp", "-deprecated_cipherSuites", "equals", "other", "hashCode", "", "isCompatible", "socket", "supportedSpec", "-deprecated_supportsTlsExtensions", "-deprecated_tlsVersions", "toString", "Builder", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class ConnectionSpec {
    private static final CipherSuite[] APPROVED_CIPHER_SUITES;
    public static final ConnectionSpec CLEARTEXT;
    public static final ConnectionSpec COMPATIBLE_TLS;
    public static final Companion Companion;
    public static final ConnectionSpec MODERN_TLS;
    private static final CipherSuite[] RESTRICTED_CIPHER_SUITES;
    public static final ConnectionSpec RESTRICTED_TLS;
    private final String[] cipherSuitesAsString;
    private final boolean isTls;
    private final boolean supportsTlsExtensions;
    private final String[] tlsVersionsAsString;

    static {
        Companion = new Companion(null);
        RESTRICTED_CIPHER_SUITES = new CipherSuite[]{CipherSuite.TLS_AES_128_GCM_SHA256, CipherSuite.TLS_AES_256_GCM_SHA384, CipherSuite.TLS_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256};
        APPROVED_CIPHER_SUITES = new CipherSuite[]{CipherSuite.TLS_AES_128_GCM_SHA256, CipherSuite.TLS_AES_256_GCM_SHA384, CipherSuite.TLS_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
        CipherSuite[] arrcipherSuite = new Builder(true);
        Object object = RESTRICTED_CIPHER_SUITES;
        RESTRICTED_TLS = arrcipherSuite.cipherSuites(Arrays.copyOf(object, ((CipherSuite[])object).length)).tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2).supportsTlsExtensions(true).build();
        object = new Builder(true);
        arrcipherSuite = APPROVED_CIPHER_SUITES;
        MODERN_TLS = ((Builder)object).cipherSuites(Arrays.copyOf(arrcipherSuite, arrcipherSuite.length)).tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2).supportsTlsExtensions(true).build();
        object = new Builder(true);
        arrcipherSuite = APPROVED_CIPHER_SUITES;
        COMPATIBLE_TLS = ((Builder)object).cipherSuites(Arrays.copyOf(arrcipherSuite, arrcipherSuite.length)).tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
        CLEARTEXT = new Builder(false).build();
    }

    public ConnectionSpec(boolean bl, boolean bl2, String[] arrstring, String[] arrstring2) {
        this.isTls = bl;
        this.supportsTlsExtensions = bl2;
        this.cipherSuitesAsString = arrstring;
        this.tlsVersionsAsString = arrstring2;
    }

    private final ConnectionSpec supportedSpec(SSLSocket object, boolean bl) {
        String[] arrstring;
        Object object2;
        if (this.cipherSuitesAsString != null) {
            object2 = ((SSLSocket)object).getEnabledCipherSuites();
            Intrinsics.checkExpressionValueIsNotNull(object2, "sslSocket.enabledCipherSuites");
            object2 = Util.intersect((String[])object2, this.cipherSuitesAsString, CipherSuite.Companion.getORDER_BY_NAME$okhttp());
        } else {
            object2 = ((SSLSocket)object).getEnabledCipherSuites();
        }
        if (this.tlsVersionsAsString != null) {
            arrstring = ((SSLSocket)object).getEnabledProtocols();
            Intrinsics.checkExpressionValueIsNotNull(arrstring, "sslSocket.enabledProtocols");
            arrstring = Util.intersect(arrstring, this.tlsVersionsAsString, ComparisonsKt.naturalOrder());
        } else {
            arrstring = ((SSLSocket)object).getEnabledProtocols();
        }
        String[] arrstring2 = ((SSLSocket)object).getSupportedCipherSuites();
        Intrinsics.checkExpressionValueIsNotNull(arrstring2, "supportedCipherSuites");
        int n = Util.indexOf(arrstring2, "TLS_FALLBACK_SCSV", CipherSuite.Companion.getORDER_BY_NAME$okhttp());
        object = object2;
        if (bl) {
            object = object2;
            if (n != -1) {
                Intrinsics.checkExpressionValueIsNotNull(object2, "cipherSuitesIntersection");
                object = arrstring2[n];
                Intrinsics.checkExpressionValueIsNotNull(object, "supportedCipherSuites[indexOfFallbackScsv]");
                object = Util.concat((String[])object2, (String)object);
            }
        }
        object2 = new Builder(this);
        Intrinsics.checkExpressionValueIsNotNull(object, "cipherSuitesIntersection");
        object = ((Builder)object2).cipherSuites(Arrays.copyOf(object, ((String[])object).length));
        Intrinsics.checkExpressionValueIsNotNull(arrstring, "tlsVersionsIntersection");
        return ((Builder)object).tlsVersions(Arrays.copyOf(arrstring, arrstring.length)).build();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="cipherSuites", imports={}))
    public final List<CipherSuite> -deprecated_cipherSuites() {
        return this.cipherSuites();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="supportsTlsExtensions", imports={}))
    public final boolean -deprecated_supportsTlsExtensions() {
        return this.supportsTlsExtensions;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="tlsVersions", imports={}))
    public final List<TlsVersion> -deprecated_tlsVersions() {
        return this.tlsVersions();
    }

    public final void apply$okhttp(SSLSocket sSLSocket, boolean bl) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "sslSocket");
        ConnectionSpec connectionSpec = this.supportedSpec(sSLSocket, bl);
        if (connectionSpec.tlsVersions() != null) {
            sSLSocket.setEnabledProtocols(connectionSpec.tlsVersionsAsString);
        }
        if (connectionSpec.cipherSuites() == null) return;
        sSLSocket.setEnabledCipherSuites(connectionSpec.cipherSuitesAsString);
    }

    public final List<CipherSuite> cipherSuites() {
        String[] arrstring = this.cipherSuitesAsString;
        if (arrstring == null) {
            return null;
        }
        Collection collection = new ArrayList(arrstring.length);
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String string2 = arrstring[n2];
            collection.add(CipherSuite.Companion.forJavaName(string2));
            ++n2;
        }
        return CollectionsKt.toList((List)collection);
    }

    public boolean equals(Object object) {
        if (!(object instanceof ConnectionSpec)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        boolean bl = this.isTls;
        object = (ConnectionSpec)object;
        if (bl != ((ConnectionSpec)object).isTls) {
            return false;
        }
        if (!bl) return true;
        if (!Arrays.equals(this.cipherSuitesAsString, ((ConnectionSpec)object).cipherSuitesAsString)) {
            return false;
        }
        if (!Arrays.equals(this.tlsVersionsAsString, ((ConnectionSpec)object).tlsVersionsAsString)) {
            return false;
        }
        if (this.supportsTlsExtensions == ((ConnectionSpec)object).supportsTlsExtensions) return true;
        return false;
    }

    public int hashCode() {
        if (!this.isTls) {
            return 17;
        }
        Object[] arrobject = this.cipherSuitesAsString;
        int n = 0;
        int n2 = arrobject != null ? Arrays.hashCode(arrobject) : 0;
        arrobject = this.tlsVersionsAsString;
        if (arrobject == null) return ((527 + n2) * 31 + n) * 31 + (this.supportsTlsExtensions ^ true);
        n = Arrays.hashCode(arrobject);
        return ((527 + n2) * 31 + n) * 31 + (this.supportsTlsExtensions ^ true);
    }

    public final boolean isCompatible(SSLSocket sSLSocket) {
        Intrinsics.checkParameterIsNotNull(sSLSocket, "socket");
        if (!this.isTls) {
            return false;
        }
        String[] arrstring = this.tlsVersionsAsString;
        if (arrstring != null && !Util.hasIntersection(arrstring, sSLSocket.getEnabledProtocols(), ComparisonsKt.naturalOrder())) {
            return false;
        }
        arrstring = this.cipherSuitesAsString;
        if (arrstring == null) return true;
        if (Util.hasIntersection(arrstring, sSLSocket.getEnabledCipherSuites(), CipherSuite.Companion.getORDER_BY_NAME$okhttp())) return true;
        return false;
    }

    public final boolean isTls() {
        return this.isTls;
    }

    public final boolean supportsTlsExtensions() {
        return this.supportsTlsExtensions;
    }

    public final List<TlsVersion> tlsVersions() {
        String[] arrstring = this.tlsVersionsAsString;
        if (arrstring == null) {
            return null;
        }
        List list = new ArrayList(arrstring.length);
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String string2 = arrstring[n2];
            list.add(TlsVersion.Companion.forJavaName(string2));
            ++n2;
        }
        return CollectionsKt.toList(list);
    }

    public String toString() {
        if (!this.isTls) {
            return "ConnectionSpec()";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ConnectionSpec(");
        stringBuilder.append("cipherSuites=");
        stringBuilder.append(Objects.toString(this.cipherSuites(), "[all enabled]"));
        stringBuilder.append(", ");
        stringBuilder.append("tlsVersions=");
        stringBuilder.append(Objects.toString(this.tlsVersions(), "[all enabled]"));
        stringBuilder.append(", ");
        stringBuilder.append("supportsTlsExtensions=");
        stringBuilder.append(this.supportsTlsExtensions);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0006\u0010\u0019\u001a\u00020\u0000J\u0006\u0010\u001a\u001a\u00020\u0000J\u0006\u0010\u001b\u001a\u00020\u0006J\u001f\u0010\b\u001a\u00020\u00002\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t\"\u00020\n\u00a2\u0006\u0002\u0010\u001cJ\u001f\u0010\b\u001a\u00020\u00002\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\t\"\u00020\u001d\u00a2\u0006\u0002\u0010\u001eJ\u0010\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0003H\u0007J\u001f\u0010\u0016\u001a\u00020\u00002\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t\"\u00020\n\u00a2\u0006\u0002\u0010\u001cJ\u001f\u0010\u0016\u001a\u00020\u00002\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001f0\t\"\u00020\u001f\u00a2\u0006\u0002\u0010 R$\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tX\u000e\u00a2\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u0003X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0012\"\u0004\b\u0015\u0010\u0004R$\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tX\u000e\u00a2\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u0017\u0010\f\"\u0004\b\u0018\u0010\u000e\u00a8\u0006!"}, d2={"Lokhttp3/ConnectionSpec$Builder;", "", "tls", "", "(Z)V", "connectionSpec", "Lokhttp3/ConnectionSpec;", "(Lokhttp3/ConnectionSpec;)V", "cipherSuites", "", "", "getCipherSuites$okhttp", "()[Ljava/lang/String;", "setCipherSuites$okhttp", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "supportsTlsExtensions", "getSupportsTlsExtensions$okhttp", "()Z", "setSupportsTlsExtensions$okhttp", "getTls$okhttp", "setTls$okhttp", "tlsVersions", "getTlsVersions$okhttp", "setTlsVersions$okhttp", "allEnabledCipherSuites", "allEnabledTlsVersions", "build", "([Ljava/lang/String;)Lokhttp3/ConnectionSpec$Builder;", "Lokhttp3/CipherSuite;", "([Lokhttp3/CipherSuite;)Lokhttp3/ConnectionSpec$Builder;", "Lokhttp3/TlsVersion;", "([Lokhttp3/TlsVersion;)Lokhttp3/ConnectionSpec$Builder;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Builder {
        private String[] cipherSuites;
        private boolean supportsTlsExtensions;
        private boolean tls;
        private String[] tlsVersions;

        public Builder(ConnectionSpec connectionSpec) {
            Intrinsics.checkParameterIsNotNull(connectionSpec, "connectionSpec");
            this.tls = connectionSpec.isTls();
            this.cipherSuites = connectionSpec.cipherSuitesAsString;
            this.tlsVersions = connectionSpec.tlsVersionsAsString;
            this.supportsTlsExtensions = connectionSpec.supportsTlsExtensions();
        }

        public Builder(boolean bl) {
            this.tls = bl;
        }

        public final Builder allEnabledCipherSuites() {
            Builder builder = this;
            if (!builder.tls) throw (Throwable)new IllegalArgumentException("no cipher suites for cleartext connections".toString());
            builder.cipherSuites = null;
            return builder;
        }

        public final Builder allEnabledTlsVersions() {
            Builder builder = this;
            if (!builder.tls) throw (Throwable)new IllegalArgumentException("no TLS versions for cleartext connections".toString());
            builder.tlsVersions = null;
            return builder;
        }

        public final ConnectionSpec build() {
            return new ConnectionSpec(this.tls, this.supportsTlsExtensions, this.cipherSuites, this.tlsVersions);
        }

        public final Builder cipherSuites(String ... object) {
            Intrinsics.checkParameterIsNotNull(object, "cipherSuites");
            Builder builder = this;
            if (!builder.tls) throw (Throwable)new IllegalArgumentException("no cipher suites for cleartext connections".toString());
            boolean bl = ((String[])object).length == 0;
            if (!(bl ^ true)) throw (Throwable)new IllegalArgumentException("At least one cipher suite is required".toString());
            if ((object = object.clone()) == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
            builder.cipherSuites = object;
            return builder;
        }

        public final Builder cipherSuites(CipherSuite ... arrobject) {
            Intrinsics.checkParameterIsNotNull(arrobject, "cipherSuites");
            Builder builder = this;
            if (!builder.tls) throw (Throwable)new IllegalArgumentException("no cipher suites for cleartext connections".toString());
            Collection collection = new ArrayList(arrobject.length);
            int n = arrobject.length;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    arrobject = ((Collection)((List)collection)).toArray(new String[0]);
                    if (arrobject == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    arrobject = (String[])arrobject;
                    return builder.cipherSuites((String[])Arrays.copyOf(arrobject, arrobject.length));
                }
                collection.add(arrobject[n2].javaName());
                ++n2;
            } while (true);
        }

        public final String[] getCipherSuites$okhttp() {
            return this.cipherSuites;
        }

        public final boolean getSupportsTlsExtensions$okhttp() {
            return this.supportsTlsExtensions;
        }

        public final boolean getTls$okhttp() {
            return this.tls;
        }

        public final String[] getTlsVersions$okhttp() {
            return this.tlsVersions;
        }

        public final void setCipherSuites$okhttp(String[] arrstring) {
            this.cipherSuites = arrstring;
        }

        public final void setSupportsTlsExtensions$okhttp(boolean bl) {
            this.supportsTlsExtensions = bl;
        }

        public final void setTls$okhttp(boolean bl) {
            this.tls = bl;
        }

        public final void setTlsVersions$okhttp(String[] arrstring) {
            this.tlsVersions = arrstring;
        }

        @Deprecated(message="since OkHttp 3.13 all TLS-connections are expected to support TLS extensions.\nIn a future release setting this to true will be unnecessary and setting it to false\nwill have no effect.")
        public final Builder supportsTlsExtensions(boolean bl) {
            Builder builder = this;
            if (!builder.tls) throw (Throwable)new IllegalArgumentException("no TLS extensions for cleartext connections".toString());
            builder.supportsTlsExtensions = bl;
            return builder;
        }

        public final Builder tlsVersions(String ... object) {
            Intrinsics.checkParameterIsNotNull(object, "tlsVersions");
            Builder builder = this;
            if (!builder.tls) throw (Throwable)new IllegalArgumentException("no TLS versions for cleartext connections".toString());
            boolean bl = ((String[])object).length == 0;
            if (!(bl ^ true)) throw (Throwable)new IllegalArgumentException("At least one TLS version is required".toString());
            if ((object = object.clone()) == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
            builder.tlsVersions = object;
            return builder;
        }

        public final Builder tlsVersions(TlsVersion ... arrobject) {
            Intrinsics.checkParameterIsNotNull(arrobject, "tlsVersions");
            Builder builder = this;
            if (!builder.tls) throw (Throwable)new IllegalArgumentException("no TLS versions for cleartext connections".toString());
            Collection collection = new ArrayList(arrobject.length);
            int n = arrobject.length;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    arrobject = ((Collection)((List)collection)).toArray(new String[0]);
                    if (arrobject == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    arrobject = (String[])arrobject;
                    return builder.tlsVersions((String[])Arrays.copyOf(arrobject, arrobject.length));
                }
                collection.add(arrobject[n2].javaName());
                ++n2;
            } while (true);
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\f\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lokhttp3/ConnectionSpec$Companion;", "", "()V", "APPROVED_CIPHER_SUITES", "", "Lokhttp3/CipherSuite;", "[Lokhttp3/CipherSuite;", "CLEARTEXT", "Lokhttp3/ConnectionSpec;", "COMPATIBLE_TLS", "MODERN_TLS", "RESTRICTED_CIPHER_SUITES", "RESTRICTED_TLS", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

}

