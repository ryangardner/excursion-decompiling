/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.tls;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;

@Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\t\u001a\u00020\nH\u0016J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2={"Lokhttp3/internal/tls/BasicCertificateChainCleaner;", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "(Lokhttp3/internal/tls/TrustRootIndex;)V", "clean", "", "Ljava/security/cert/Certificate;", "chain", "hostname", "", "equals", "", "other", "", "hashCode", "", "verifySignature", "toVerify", "Ljava/security/cert/X509Certificate;", "signingCert", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class BasicCertificateChainCleaner
extends CertificateChainCleaner {
    public static final Companion Companion = new Companion(null);
    private static final int MAX_SIGNERS = 9;
    private final TrustRootIndex trustRootIndex;

    public BasicCertificateChainCleaner(TrustRootIndex trustRootIndex) {
        Intrinsics.checkParameterIsNotNull(trustRootIndex, "trustRootIndex");
        this.trustRootIndex = trustRootIndex;
    }

    private final boolean verifySignature(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        boolean bl = Intrinsics.areEqual(x509Certificate.getIssuerDN(), x509Certificate2.getSubjectDN());
        boolean bl2 = true;
        if (bl ^ true) {
            return false;
        }
        try {
            x509Certificate.verify(x509Certificate2.getPublicKey());
            return bl2;
        }
        catch (GeneralSecurityException generalSecurityException) {
            return false;
        }
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public List<Certificate> clean(List<? extends Certificate> var1_1, String var2_2) throws SSLPeerUnverifiedException {
        Intrinsics.checkParameterIsNotNull(var1_1, "chain");
        Intrinsics.checkParameterIsNotNull(var2_2, "hostname");
        var2_2 = new ArrayDeque<E>((Collection)var1_1);
        var1_1 = new ArrayList<E>();
        var3_3 = var2_2.removeFirst();
        Intrinsics.checkExpressionValueIsNotNull(var3_3, "queue.removeFirst()");
        var1_1.add(var3_3);
        var4_7 = 0;
        var5_8 = false;
        block0 : do {
            block6 : {
                if (var4_7 >= 9) {
                    var2_2 = new StringBuilder();
                    var2_2.append("Certificate chain too long: ");
                    var2_2.append(var1_1);
                    throw (Throwable)new SSLPeerUnverifiedException(var2_2.toString());
                }
                var3_5 = var1_1.get(var1_1.size() - 1);
                if (var3_5 == null) throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
                var3_6 = (X509Certificate)var3_5;
                var6_9 = this.trustRootIndex.findByIssuerAndSignature(var3_6);
                if (var6_9 == null) break block6;
                if (var1_1.size() > 1 || Intrinsics.areEqual(var3_6, var6_9) ^ true) {
                    var1_1.add(var6_9);
                }
                if (this.verifySignature((X509Certificate)var6_9, (X509Certificate)var6_9)) {
                    return var1_1;
                }
                var5_8 = true;
                ** GOTO lbl42
            }
            var6_9 = var2_2.iterator();
            Intrinsics.checkExpressionValueIsNotNull(var6_9, "queue.iterator()");
            while (var6_9.hasNext()) {
                var7_11 = var6_9.next();
                if (var7_11 == null) throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
                var7_10 = (X509Certificate)var7_11;
                if (!this.verifySignature(var3_6, var7_10)) continue;
                var6_9.remove();
                var1_1.add(var7_10);
lbl42: // 2 sources:
                ++var4_7;
                continue block0;
            }
            break;
        } while (true);
        if (var5_8) {
            return var1_1;
        }
        var1_1 = new StringBuilder();
        var1_1.append("Failed to find a trusted cert that signed ");
        var1_1.append(var3_6);
        throw (Throwable)new SSLPeerUnverifiedException(var1_1.toString());
    }

    public boolean equals(Object object) {
        BasicCertificateChainCleaner basicCertificateChainCleaner = this;
        boolean bl = true;
        if (object == basicCertificateChainCleaner) {
            return bl;
        }
        if (!(object instanceof BasicCertificateChainCleaner)) return false;
        if (!Intrinsics.areEqual(((BasicCertificateChainCleaner)object).trustRootIndex, this.trustRootIndex)) return false;
        return bl;
    }

    public int hashCode() {
        return this.trustRootIndex.hashCode();
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lokhttp3/internal/tls/BasicCertificateChainCleaner$Companion;", "", "()V", "MAX_SIGNERS", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

}

