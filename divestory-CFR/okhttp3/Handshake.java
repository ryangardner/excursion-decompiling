/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.Handshake$Companion$get
 *  okhttp3.Handshake$Companion$handshake
 *  okhttp3.Handshake$peerCertificates
 */
package okhttp3;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.CipherSuite;
import okhttp3.Handshake;
import okhttp3.TlsVersion;
import okhttp3.internal.Util;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 &2\u00020\u0001:\u0001&B9\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n\u00a2\u0006\u0002\u0010\u000bJ\r\u0010\u0004\u001a\u00020\u0005H\u0007\u00a2\u0006\u0002\b\u001aJ\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0013\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007\u00a2\u0006\u0002\b J\u000f\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007\u00a2\u0006\u0002\b!J\u0013\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007\u00a2\u0006\u0002\b\"J\u000f\u0010\u0014\u001a\u0004\u0018\u00010\u000fH\u0007\u00a2\u0006\u0002\b#J\r\u0010\u0002\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b$J\b\u0010%\u001a\u00020\u0017H\u0016R\u0013\u0010\u0004\u001a\u00020\u00058\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\fR\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\rR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f8G\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u00078GX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0011\u0010\rR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u000f8G\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010R\u0013\u0010\u0002\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0015R\u0018\u0010\u0016\u001a\u00020\u0017*\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006'"}, d2={"Lokhttp3/Handshake;", "", "tlsVersion", "Lokhttp3/TlsVersion;", "cipherSuite", "Lokhttp3/CipherSuite;", "localCertificates", "", "Ljava/security/cert/Certificate;", "peerCertificatesFn", "Lkotlin/Function0;", "(Lokhttp3/TlsVersion;Lokhttp3/CipherSuite;Ljava/util/List;Lkotlin/jvm/functions/Function0;)V", "()Lokhttp3/CipherSuite;", "()Ljava/util/List;", "localPrincipal", "Ljava/security/Principal;", "()Ljava/security/Principal;", "peerCertificates", "peerCertificates$delegate", "Lkotlin/Lazy;", "peerPrincipal", "()Lokhttp3/TlsVersion;", "name", "", "getName", "(Ljava/security/cert/Certificate;)Ljava/lang/String;", "-deprecated_cipherSuite", "equals", "", "other", "hashCode", "", "-deprecated_localCertificates", "-deprecated_localPrincipal", "-deprecated_peerCertificates", "-deprecated_peerPrincipal", "-deprecated_tlsVersion", "toString", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class Handshake {
    public static final Companion Companion = new Companion(null);
    private final CipherSuite cipherSuite;
    private final List<Certificate> localCertificates;
    private final Lazy peerCertificates$delegate;
    private final TlsVersion tlsVersion;

    public Handshake(TlsVersion tlsVersion, CipherSuite cipherSuite, List<? extends Certificate> list, Function0<? extends List<? extends Certificate>> function0) {
        Intrinsics.checkParameterIsNotNull((Object)tlsVersion, "tlsVersion");
        Intrinsics.checkParameterIsNotNull(cipherSuite, "cipherSuite");
        Intrinsics.checkParameterIsNotNull(list, "localCertificates");
        Intrinsics.checkParameterIsNotNull(function0, "peerCertificatesFn");
        this.tlsVersion = tlsVersion;
        this.cipherSuite = cipherSuite;
        this.localCertificates = list;
        this.peerCertificates$delegate = LazyKt.lazy((Function0)new Function0<List<? extends Certificate>>(function0){
            final /* synthetic */ Function0 $peerCertificatesFn;
            {
                this.$peerCertificatesFn = function0;
                super(0);
            }

            public final List<Certificate> invoke() {
                try {
                    return (List)this.$peerCertificatesFn.invoke();
                }
                catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
                    return CollectionsKt.emptyList();
                }
            }
        });
    }

    @JvmStatic
    public static final Handshake get(SSLSession sSLSession) throws IOException {
        return Companion.get(sSLSession);
    }

    @JvmStatic
    public static final Handshake get(TlsVersion tlsVersion, CipherSuite cipherSuite, List<? extends Certificate> list, List<? extends Certificate> list2) {
        return Companion.get(tlsVersion, cipherSuite, list, list2);
    }

    private final String getName(Certificate object) {
        if (object instanceof X509Certificate) {
            return ((Object)((X509Certificate)object).getSubjectDN()).toString();
        }
        object = ((Certificate)object).getType();
        Intrinsics.checkExpressionValueIsNotNull(object, "type");
        return object;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="cipherSuite", imports={}))
    public final CipherSuite -deprecated_cipherSuite() {
        return this.cipherSuite;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="localCertificates", imports={}))
    public final List<Certificate> -deprecated_localCertificates() {
        return this.localCertificates;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="localPrincipal", imports={}))
    public final Principal -deprecated_localPrincipal() {
        return this.localPrincipal();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="peerCertificates", imports={}))
    public final List<Certificate> -deprecated_peerCertificates() {
        return this.peerCertificates();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="peerPrincipal", imports={}))
    public final Principal -deprecated_peerPrincipal() {
        return this.peerPrincipal();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="tlsVersion", imports={}))
    public final TlsVersion -deprecated_tlsVersion() {
        return this.tlsVersion;
    }

    public final CipherSuite cipherSuite() {
        return this.cipherSuite;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Handshake)) return false;
        object = (Handshake)object;
        if (((Handshake)object).tlsVersion != this.tlsVersion) return false;
        if (!Intrinsics.areEqual(((Handshake)object).cipherSuite, this.cipherSuite)) return false;
        if (!Intrinsics.areEqual(((Handshake)object).peerCertificates(), this.peerCertificates())) return false;
        if (!Intrinsics.areEqual(((Handshake)object).localCertificates, this.localCertificates)) return false;
        return true;
    }

    public int hashCode() {
        return (((527 + this.tlsVersion.hashCode()) * 31 + this.cipherSuite.hashCode()) * 31 + ((Object)this.peerCertificates()).hashCode()) * 31 + ((Object)this.localCertificates).hashCode();
    }

    public final List<Certificate> localCertificates() {
        return this.localCertificates;
    }

    public final Principal localPrincipal() {
        Serializable serializable = CollectionsKt.firstOrNull(this.localCertificates);
        boolean bl = serializable instanceof X509Certificate;
        Object var3_3 = null;
        if (!bl) {
            serializable = null;
        }
        X509Certificate x509Certificate = (X509Certificate)serializable;
        serializable = var3_3;
        if (x509Certificate == null) return (Principal)((Object)serializable);
        serializable = x509Certificate.getSubjectX500Principal();
        return (Principal)((Object)serializable);
    }

    public final List<Certificate> peerCertificates() {
        return (List)this.peerCertificates$delegate.getValue();
    }

    public final Principal peerPrincipal() {
        Serializable serializable = CollectionsKt.firstOrNull(this.peerCertificates());
        boolean bl = serializable instanceof X509Certificate;
        Object var3_3 = null;
        if (!bl) {
            serializable = null;
        }
        X509Certificate x509Certificate = (X509Certificate)serializable;
        serializable = var3_3;
        if (x509Certificate == null) return (Principal)((Object)serializable);
        serializable = x509Certificate.getSubjectX500Principal();
        return (Principal)((Object)serializable);
    }

    public final TlsVersion tlsVersion() {
        return this.tlsVersion;
    }

    public String toString() {
        Object object = this.peerCertificates();
        Object object2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(object, 10));
        object = object.iterator();
        while (object.hasNext()) {
            object2.add(this.getName((Certificate)object.next()));
        }
        object = ((List)object2).toString();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Handshake{");
        ((StringBuilder)object2).append("tlsVersion=");
        ((StringBuilder)object2).append((Object)this.tlsVersion);
        ((StringBuilder)object2).append(' ');
        ((StringBuilder)object2).append("cipherSuite=");
        ((StringBuilder)object2).append(this.cipherSuite);
        ((StringBuilder)object2).append(' ');
        ((StringBuilder)object2).append("peerCertificates=");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(' ');
        ((StringBuilder)object2).append("localCertificates=");
        Object object3 = this.localCertificates;
        object = new ArrayList(CollectionsKt.collectionSizeOrDefault(object3, 10));
        object3 = object3.iterator();
        do {
            if (!object3.hasNext()) {
                ((StringBuilder)object2).append((List)object);
                ((StringBuilder)object2).append('}');
                return ((StringBuilder)object2).toString();
            }
            object.add(this.getName((Certificate)object3.next()));
        } while (true);
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a2\u0006\u0002\b\u0007J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0007J\u0011\u0010\u0010\u001a\u00020\u0004*\u00020\u0006H\u0007\u00a2\u0006\u0002\b\u0003J!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\r*\f\u0012\u0006\b\u0001\u0012\u00020\u000e\u0018\u00010\u0012H\u0002\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0014"}, d2={"Lokhttp3/Handshake$Companion;", "", "()V", "get", "Lokhttp3/Handshake;", "sslSession", "Ljavax/net/ssl/SSLSession;", "-deprecated_get", "tlsVersion", "Lokhttp3/TlsVersion;", "cipherSuite", "Lokhttp3/CipherSuite;", "peerCertificates", "", "Ljava/security/cert/Certificate;", "localCertificates", "handshake", "toImmutableList", "", "([Ljava/security/cert/Certificate;)Ljava/util/List;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final List<Certificate> toImmutableList(Certificate[] object) {
            if (object == null) return CollectionsKt.emptyList();
            return Util.immutableListOf(Arrays.copyOf(object, ((Certificate[])object).length));
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="sslSession.handshake()", imports={}))
        public final Handshake -deprecated_get(SSLSession sSLSession) throws IOException {
            Intrinsics.checkParameterIsNotNull(sSLSession, "sslSession");
            return this.get(sSLSession);
        }

        @JvmStatic
        public final Handshake get(SSLSession object) throws IOException {
            Intrinsics.checkParameterIsNotNull(object, "$this$handshake");
            List<Certificate> list = object.getCipherSuite();
            if (list == null) throw (Throwable)new IllegalStateException("cipherSuite == null".toString());
            int n = ((String)((Object)list)).hashCode();
            if (n != 1019404634 ? n != 1208658923 || !((String)((Object)list)).equals("SSL_NULL_WITH_NULL_NULL") : !((String)((Object)list)).equals("TLS_NULL_WITH_NULL_NULL")) {
                CipherSuite cipherSuite = CipherSuite.Companion.forJavaName((String)((Object)list));
                list = object.getProtocol();
                if (list == null) throw (Throwable)new IllegalStateException("tlsVersion == null".toString());
                if (Intrinsics.areEqual("NONE", list)) throw (Throwable)new IOException("tlsVersion == NONE");
                TlsVersion tlsVersion = TlsVersion.Companion.forJavaName((String)((Object)list));
                try {
                    list = this.toImmutableList(object.getPeerCertificates());
                    return new Handshake(tlsVersion, cipherSuite, this.toImmutableList(object.getLocalCertificates()), (Function0<? extends List<? extends Certificate>>)new Function0<List<? extends Certificate>>(list){
                        final /* synthetic */ List $peerCertificatesCopy;
                        {
                            this.$peerCertificatesCopy = list;
                            super(0);
                        }

                        public final List<Certificate> invoke() {
                            return this.$peerCertificatesCopy;
                        }
                    });
                }
                catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
                    list = CollectionsKt.emptyList();
                }
                return new Handshake(tlsVersion, cipherSuite, this.toImmutableList(object.getLocalCertificates()), (Function0<? extends List<? extends Certificate>>)new /* invalid duplicate definition of identical inner class */);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("cipherSuite == ");
            ((StringBuilder)object).append((String)((Object)list));
            throw (Throwable)new IOException(((StringBuilder)object).toString());
        }

        @JvmStatic
        public final Handshake get(TlsVersion tlsVersion, CipherSuite cipherSuite, List<? extends Certificate> list, List<? extends Certificate> list2) {
            Intrinsics.checkParameterIsNotNull((Object)tlsVersion, "tlsVersion");
            Intrinsics.checkParameterIsNotNull(cipherSuite, "cipherSuite");
            Intrinsics.checkParameterIsNotNull(list, "peerCertificates");
            Intrinsics.checkParameterIsNotNull(list2, "localCertificates");
            list = Util.toImmutableList(list);
            return new Handshake(tlsVersion, cipherSuite, Util.toImmutableList(list2), (Function0<? extends List<? extends Certificate>>)new Function0<List<? extends Certificate>>(list){
                final /* synthetic */ List $peerCertificatesCopy;
                {
                    this.$peerCertificatesCopy = list;
                    super(0);
                }

                public final List<Certificate> invoke() {
                    return this.$peerCertificatesCopy;
                }
            });
        }
    }

}

