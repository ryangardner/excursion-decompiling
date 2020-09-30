/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.CertificatePinner$check
 */
package okhttp3;

import java.io.Serializable;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import okhttp3.CertificatePinner;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.tls.CertificateChainCleaner;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 \"2\u00020\u0001:\u0003!\"#B!\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J)\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0011H\u0000\u00a2\u0006\u0002\b\u0014J)\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00170\u0016\"\u00020\u0017H\u0007\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0012J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u00122\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u001d\u001a\u00020\u001eH\u0016J\u0015\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u00a2\u0006\u0002\b R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006$"}, d2={"Lokhttp3/CertificatePinner;", "", "pins", "", "Lokhttp3/CertificatePinner$Pin;", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "(Ljava/util/Set;Lokhttp3/internal/tls/CertificateChainCleaner;)V", "getCertificateChainCleaner$okhttp", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "getPins", "()Ljava/util/Set;", "check", "", "hostname", "", "cleanedPeerCertificatesFn", "Lkotlin/Function0;", "", "Ljava/security/cert/X509Certificate;", "check$okhttp", "peerCertificates", "", "Ljava/security/cert/Certificate;", "(Ljava/lang/String;[Ljava/security/cert/Certificate;)V", "equals", "", "other", "findMatchingPins", "hashCode", "", "withCertificateChainCleaner", "withCertificateChainCleaner$okhttp", "Builder", "Companion", "Pin", "okhttp"}, k=1, mv={1, 1, 16})
public final class CertificatePinner {
    public static final Companion Companion = new Companion(null);
    public static final CertificatePinner DEFAULT = new Builder().build();
    private final CertificateChainCleaner certificateChainCleaner;
    private final Set<Pin> pins;

    public CertificatePinner(Set<Pin> set, CertificateChainCleaner certificateChainCleaner) {
        Intrinsics.checkParameterIsNotNull(set, "pins");
        this.pins = set;
        this.certificateChainCleaner = certificateChainCleaner;
    }

    public /* synthetic */ CertificatePinner(Set set, CertificateChainCleaner certificateChainCleaner, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            certificateChainCleaner = null;
        }
        this(set, certificateChainCleaner);
    }

    @JvmStatic
    public static final String pin(Certificate certificate) {
        return Companion.pin(certificate);
    }

    @JvmStatic
    public static final ByteString sha1Hash(X509Certificate x509Certificate) {
        return Companion.sha1Hash(x509Certificate);
    }

    @JvmStatic
    public static final ByteString sha256Hash(X509Certificate x509Certificate) {
        return Companion.sha256Hash(x509Certificate);
    }

    public final void check(String string2, List<? extends Certificate> list) throws SSLPeerUnverifiedException {
        Intrinsics.checkParameterIsNotNull(string2, "hostname");
        Intrinsics.checkParameterIsNotNull(list, "peerCertificates");
        this.check$okhttp(string2, (Function0<? extends List<? extends X509Certificate>>)new Function0<List<? extends X509Certificate>>(this, list, string2){
            final /* synthetic */ String $hostname;
            final /* synthetic */ List $peerCertificates;
            final /* synthetic */ CertificatePinner this$0;
            {
                this.this$0 = certificatePinner;
                this.$peerCertificates = list;
                this.$hostname = string2;
                super(0);
            }

            public final List<X509Certificate> invoke() {
                Object object = this.this$0.getCertificateChainCleaner$okhttp();
                if (object == null || (object = ((CertificateChainCleaner)object).clean(this.$peerCertificates, this.$hostname)) == null) {
                    object = this.$peerCertificates;
                }
                Object object2 = (Iterable)object;
                object = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault(object2, 10));
                Iterator<T> iterator2 = object2.iterator();
                while (iterator2.hasNext()) {
                    object2 = (Certificate)iterator2.next();
                    if (object2 == null) throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
                    object.add((Certificate)((X509Certificate)object2));
                }
                return object;
            }
        });
    }

    @Deprecated(message="replaced with {@link #check(String, List)}.", replaceWith=@ReplaceWith(expression="check(hostname, peerCertificates.toList())", imports={}))
    public final void check(String string2, Certificate ... arrcertificate) throws SSLPeerUnverifiedException {
        Intrinsics.checkParameterIsNotNull(string2, "hostname");
        Intrinsics.checkParameterIsNotNull(arrcertificate, "peerCertificates");
        this.check(string2, ArraysKt.toList(arrcertificate));
    }

    /*
     * Unable to fully structure code
     */
    public final void check$okhttp(String var1_1, Function0<? extends List<? extends X509Certificate>> var2_2) {
        block9 : {
            Intrinsics.checkParameterIsNotNull(var1_1, "hostname");
            Intrinsics.checkParameterIsNotNull(var2_2, "cleanedPeerCertificatesFn");
            var3_3 = this.findMatchingPins((String)var1_1);
            if (var3_3.isEmpty()) {
                return;
            }
            var4_4 = (List)var2_2.invoke();
            var5_5 = var4_4.iterator();
            block0 : do {
                if (!var5_5.hasNext()) {
                    var2_2 = new StringBuilder();
                    var2_2.append("Certificate pinning failure!");
                    var2_2.append("\n  Peer certificate chain:");
                    for (X509Certificate var10_19 : var4_4) {
                        var2_2.append("\n    ");
                        var2_2.append(CertificatePinner.Companion.pin(var10_19));
                        var2_2.append(": ");
                        var10_20 = var10_19.getSubjectDN();
                        Intrinsics.checkExpressionValueIsNotNull(var10_20, "element.subjectDN");
                        var2_2.append(var10_20.getName());
                    }
                    var2_2.append("\n  Pinned certificates for ");
                    var2_2.append((String)var1_1);
                    var2_2.append(":");
                    var1_1 = var3_3.iterator();
                    do {
                        if (!var1_1.hasNext()) {
                            var1_1 = var2_2.toString();
                            Intrinsics.checkExpressionValueIsNotNull(var1_1, "StringBuilder().apply(builderAction).toString()");
                            throw (Throwable)new SSLPeerUnverifiedException((String)var1_1);
                        }
                        var7_7 = (Pin)var1_1.next();
                        var2_2.append("\n    ");
                        var2_2.append(var7_7);
                    } while (true);
                }
                var6_6 = (X509Certificate)var5_5.next();
                var7_7 = null;
                var8_8 = var3_3.iterator();
                var2_2 = var7_7;
                do lbl-1000: // 3 sources:
                {
                    block10 : {
                        if (!var8_8.hasNext()) continue block0;
                        var9_9 = var8_8.next();
                        var10_11 = var9_9.getHashAlgorithm();
                        var11_21 = var10_11.hashCode();
                        if (var11_21 == -903629273) break block10;
                        if (var11_21 != 3528965 || !var10_11.equals("sha1")) break block9;
                        var10_12 = var2_2;
                        if (var2_2 == null) {
                            var10_13 = CertificatePinner.Companion.sha1Hash(var6_6);
                        }
                        var2_2 = var10_14;
                        if (!Intrinsics.areEqual(var9_9.getHash(), var10_14)) ** GOTO lbl-1000
                        return;
                    }
                    if (!var10_11.equals("sha256")) break block9;
                    var10_15 = var7_7;
                    if (var7_7 == null) {
                        var10_16 = CertificatePinner.Companion.sha256Hash(var6_6);
                    }
                    var7_7 = var10_17;
                } while (!Intrinsics.areEqual(var9_9.getHash(), var10_17));
                break;
            } while (true);
            return;
        }
        var1_1 = new StringBuilder();
        var1_1.append("unsupported hashAlgorithm: ");
        var1_1.append(var9_9.getHashAlgorithm());
        throw (Throwable)new AssertionError((Object)var1_1.toString());
    }

    public boolean equals(Object object) {
        if (!(object instanceof CertificatePinner)) return false;
        object = (CertificatePinner)object;
        if (!Intrinsics.areEqual(((CertificatePinner)object).pins, this.pins)) return false;
        if (!Intrinsics.areEqual(((CertificatePinner)object).certificateChainCleaner, this.certificateChainCleaner)) return false;
        return true;
    }

    public final List<Pin> findMatchingPins(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "hostname");
        List list = (List)((Object)this.pins);
        List list2 = CollectionsKt.emptyList();
        Iterator iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            Object t = iterator2.next();
            if (!((Pin)t).matchesHostname(string2)) continue;
            list = list2;
            if (list2.isEmpty()) {
                list = new ArrayList();
            }
            if (list == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<T>");
            TypeIntrinsics.asMutableList(list).add(t);
            list2 = list;
        }
        return list2;
    }

    public final CertificateChainCleaner getCertificateChainCleaner$okhttp() {
        return this.certificateChainCleaner;
    }

    public final Set<Pin> getPins() {
        return this.pins;
    }

    public int hashCode() {
        int n;
        int n2 = ((Object)this.pins).hashCode();
        CertificateChainCleaner certificateChainCleaner = this.certificateChainCleaner;
        if (certificateChainCleaner != null) {
            n = certificateChainCleaner.hashCode();
            return (1517 + n2) * 41 + n;
        }
        n = 0;
        return (1517 + n2) * 41 + n;
    }

    public final CertificatePinner withCertificateChainCleaner$okhttp(CertificateChainCleaner object) {
        Intrinsics.checkParameterIsNotNull(object, "certificateChainCleaner");
        if (!Intrinsics.areEqual(this.certificateChainCleaner, object)) return new CertificatePinner(this.pins, (CertificateChainCleaner)object);
        return this;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J'\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u000b\"\u00020\n\u00a2\u0006\u0002\u0010\fJ\u0006\u0010\r\u001a\u00020\u000eR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u000f"}, d2={"Lokhttp3/CertificatePinner$Builder;", "", "()V", "pins", "", "Lokhttp3/CertificatePinner$Pin;", "getPins", "()Ljava/util/List;", "add", "pattern", "", "", "(Ljava/lang/String;[Ljava/lang/String;)Lokhttp3/CertificatePinner$Builder;", "build", "Lokhttp3/CertificatePinner;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Builder {
        private final List<Pin> pins = new ArrayList();

        public final Builder add(String string2, String ... arrstring) {
            Intrinsics.checkParameterIsNotNull(string2, "pattern");
            Intrinsics.checkParameterIsNotNull(arrstring, "pins");
            Builder builder = this;
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String string3 = arrstring[n2];
                builder.pins.add(new Pin(string2, string3));
                ++n2;
            }
            return builder;
        }

        public final CertificatePinner build() {
            return new CertificatePinner(CollectionsKt.toSet((Iterable)this.pins), null, 2, null);
        }

        public final List<Pin> getPins() {
            return this.pins;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\f\u0010\t\u001a\u00020\n*\u00020\u000bH\u0007J\f\u0010\f\u001a\u00020\n*\u00020\u000bH\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lokhttp3/CertificatePinner$Companion;", "", "()V", "DEFAULT", "Lokhttp3/CertificatePinner;", "pin", "", "certificate", "Ljava/security/cert/Certificate;", "sha1Hash", "Lokio/ByteString;", "Ljava/security/cert/X509Certificate;", "sha256Hash", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final String pin(Certificate certificate) {
            Intrinsics.checkParameterIsNotNull(certificate, "certificate");
            if (!(certificate instanceof X509Certificate)) throw (Throwable)new IllegalArgumentException("Certificate pinning requires X509 certificates".toString());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sha256/");
            stringBuilder.append(this.sha256Hash((X509Certificate)certificate).base64());
            return stringBuilder.toString();
        }

        @JvmStatic
        public final ByteString sha1Hash(X509Certificate serializable) {
            Intrinsics.checkParameterIsNotNull(serializable, "$this$sha1Hash");
            ByteString.Companion companion = ByteString.Companion;
            serializable = ((Certificate)serializable).getPublicKey();
            Intrinsics.checkExpressionValueIsNotNull(serializable, "publicKey");
            serializable = serializable.getEncoded();
            Intrinsics.checkExpressionValueIsNotNull(serializable, "publicKey.encoded");
            return ByteString.Companion.of$default(companion, (byte[])serializable, 0, 0, 3, null).sha1();
        }

        @JvmStatic
        public final ByteString sha256Hash(X509Certificate serializable) {
            Intrinsics.checkParameterIsNotNull(serializable, "$this$sha256Hash");
            ByteString.Companion companion = ByteString.Companion;
            serializable = ((Certificate)serializable).getPublicKey();
            Intrinsics.checkExpressionValueIsNotNull(serializable, "publicKey");
            serializable = serializable.getEncoded();
            Intrinsics.checkExpressionValueIsNotNull(serializable, "publicKey.encoded");
            return ByteString.Companion.of$default(companion, (byte[])serializable, 0, 0, 3, null).sha256();
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0003J\b\u0010\u0018\u001a\u00020\u0003H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f\u00a8\u0006\u0019"}, d2={"Lokhttp3/CertificatePinner$Pin;", "", "pattern", "", "pin", "(Ljava/lang/String;Ljava/lang/String;)V", "hash", "Lokio/ByteString;", "getHash", "()Lokio/ByteString;", "hashAlgorithm", "getHashAlgorithm", "()Ljava/lang/String;", "getPattern", "equals", "", "other", "hashCode", "", "matchesCertificate", "certificate", "Ljava/security/cert/X509Certificate;", "matchesHostname", "hostname", "toString", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Pin {
        private final ByteString hash;
        private final String hashAlgorithm;
        private final String pattern;

        public Pin(String object, String charSequence) {
            Intrinsics.checkParameterIsNotNull(object, "pattern");
            Intrinsics.checkParameterIsNotNull(charSequence, "pin");
            boolean bl = StringsKt.startsWith$default((String)object, "*.", false, 2, null) && StringsKt.indexOf$default((CharSequence)object, "*", 1, false, 4, null) == -1 || StringsKt.startsWith$default((String)object, "**.", false, 2, null) && StringsKt.indexOf$default((CharSequence)object, "*", 2, false, 4, null) == -1 || StringsKt.indexOf$default((CharSequence)object, "*", 0, false, 6, null) == -1;
            if (!bl) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unexpected pattern: ");
                ((StringBuilder)charSequence).append((String)object);
                throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
            }
            Object object2 = HostnamesKt.toCanonicalHost((String)object);
            if (object2 == null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Invalid pattern: ");
                ((StringBuilder)charSequence).append((String)object);
                throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            this.pattern = object2;
            if (StringsKt.startsWith$default((String)charSequence, "sha1/", false, 2, null)) {
                this.hashAlgorithm = "sha1";
                object = ByteString.Companion;
                object2 = ((String)charSequence).substring(5);
                Intrinsics.checkExpressionValueIsNotNull(object2, "(this as java.lang.String).substring(startIndex)");
                object = ((ByteString.Companion)object).decodeBase64((String)object2);
                if (object != null) {
                    this.hash = object;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid pin hash: ");
                ((StringBuilder)object).append((String)charSequence);
                throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if (!StringsKt.startsWith$default((String)charSequence, "sha256/", false, 2, null)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("pins must start with 'sha256/' or 'sha1/': ");
                ((StringBuilder)object).append((String)charSequence);
                throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.hashAlgorithm = "sha256";
            object2 = ByteString.Companion;
            object = ((String)charSequence).substring(7);
            Intrinsics.checkExpressionValueIsNotNull(object, "(this as java.lang.String).substring(startIndex)");
            object = ((ByteString.Companion)object2).decodeBase64((String)object);
            if (object != null) {
                this.hash = object;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid pin hash: ");
            ((StringBuilder)object).append((String)charSequence);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Pin)) {
                return false;
            }
            String string2 = this.pattern;
            object = (Pin)object;
            if (Intrinsics.areEqual(string2, ((Pin)object).pattern) ^ true) {
                return false;
            }
            if (Intrinsics.areEqual(this.hashAlgorithm, ((Pin)object).hashAlgorithm) ^ true) {
                return false;
            }
            if (!(Intrinsics.areEqual(this.hash, ((Pin)object).hash) ^ true)) return true;
            return false;
        }

        public final ByteString getHash() {
            return this.hash;
        }

        public final String getHashAlgorithm() {
            return this.hashAlgorithm;
        }

        public final String getPattern() {
            return this.pattern;
        }

        public int hashCode() {
            return (this.pattern.hashCode() * 31 + this.hashAlgorithm.hashCode()) * 31 + this.hash.hashCode();
        }

        public final boolean matchesCertificate(X509Certificate x509Certificate) {
            Intrinsics.checkParameterIsNotNull(x509Certificate, "certificate");
            String string2 = this.hashAlgorithm;
            int n = string2.hashCode();
            if (n != -903629273) {
                if (n != 3528965) return false;
                if (!string2.equals("sha1")) return false;
                return Intrinsics.areEqual(this.hash, Companion.sha1Hash(x509Certificate));
            }
            if (!string2.equals("sha256")) return false;
            return Intrinsics.areEqual(this.hash, Companion.sha256Hash(x509Certificate));
        }

        public final boolean matchesHostname(String string2) {
            boolean bl;
            Intrinsics.checkParameterIsNotNull(string2, "hostname");
            String string3 = this.pattern;
            boolean bl2 = false;
            if (StringsKt.startsWith$default(string3, "**.", false, 2, null)) {
                int n = this.pattern.length() - 3;
                int n2 = string2.length() - n;
                bl = bl2;
                if (!StringsKt.regionMatches$default(string2, string2.length() - n, this.pattern, 3, n, false, 16, null)) return bl;
                if (n2 == 0) return true;
                bl = bl2;
                if (string2.charAt(n2 - 1) != '.') return bl;
                return true;
            } else {
                if (!StringsKt.startsWith$default(this.pattern, "*.", false, 2, null)) {
                    return Intrinsics.areEqual(string2, this.pattern);
                }
                int n = this.pattern.length() - 1;
                int n3 = string2.length();
                bl = bl2;
                if (!StringsKt.regionMatches$default(string2, string2.length() - n, this.pattern, 1, n, false, 16, null)) return bl;
                bl = bl2;
                if (StringsKt.lastIndexOf$default((CharSequence)string2, '.', n3 - n - 1, false, 4, null) != -1) return bl;
            }
            return true;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.hashAlgorithm);
            stringBuilder.append('/');
            stringBuilder.append(this.hash.base64());
            return stringBuilder.toString();
        }
    }

}

