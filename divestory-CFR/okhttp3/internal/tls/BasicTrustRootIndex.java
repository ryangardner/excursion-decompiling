/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.tls;

import java.io.Serializable;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.tls.TrustRootIndex;

@Metadata(bv={1, 0, 3}, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\t0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lokhttp3/internal/tls/BasicTrustRootIndex;", "Lokhttp3/internal/tls/TrustRootIndex;", "caCerts", "", "Ljava/security/cert/X509Certificate;", "([Ljava/security/cert/X509Certificate;)V", "subjectToCaCerts", "", "Ljavax/security/auth/x500/X500Principal;", "", "equals", "", "other", "", "findByIssuerAndSignature", "cert", "hashCode", "", "okhttp"}, k=1, mv={1, 1, 16})
public final class BasicTrustRootIndex
implements TrustRootIndex {
    private final Map<X500Principal, Set<X509Certificate>> subjectToCaCerts;

    public BasicTrustRootIndex(X509Certificate ... arrx509Certificate) {
        Intrinsics.checkParameterIsNotNull(arrx509Certificate, "caCerts");
        Map map = new LinkedHashMap();
        int n = arrx509Certificate.length;
        int n2 = 0;
        do {
            Object v;
            if (n2 >= n) {
                this.subjectToCaCerts = map;
                return;
            }
            X509Certificate x509Certificate = arrx509Certificate[n2];
            X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
            Intrinsics.checkExpressionValueIsNotNull(x500Principal, "caCert.subjectX500Principal");
            Object object = v = map.get(x500Principal);
            if (v == null) {
                object = new LinkedHashSet();
                map.put(x500Principal, object);
            }
            ((Set)object).add(x509Certificate);
            ++n2;
        } while (true);
    }

    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof BasicTrustRootIndex)) return false;
        if (!Intrinsics.areEqual(((BasicTrustRootIndex)object).subjectToCaCerts, this.subjectToCaCerts)) return false;
        return true;
    }

    @Override
    public X509Certificate findByIssuerAndSignature(X509Certificate x509Certificate) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull(x509Certificate, "cert");
        Serializable serializable = x509Certificate.getIssuerX500Principal();
        Object object = this.subjectToCaCerts.get(serializable);
        serializable = null;
        Object var4_4 = null;
        if (object == null) return serializable;
        object = ((Iterable)object).iterator();
        do {
            serializable = var4_4;
            if (!object.hasNext()) return (X509Certificate)serializable;
            serializable = object.next();
            X509Certificate x509Certificate2 = (X509Certificate)serializable;
            try {
                x509Certificate.verify(x509Certificate2.getPublicKey());
                bl = true;
            }
            catch (Exception exception) {
                bl = false;
            }
        } while (!bl);
        return (X509Certificate)serializable;
    }

    public int hashCode() {
        return ((Object)this.subjectToCaCerts).hashCode();
    }
}

