package okhttp3.internal.tls;

import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lokhttp3/internal/tls/BasicTrustRootIndex;", "Lokhttp3/internal/tls/TrustRootIndex;", "caCerts", "", "Ljava/security/cert/X509Certificate;", "([Ljava/security/cert/X509Certificate;)V", "subjectToCaCerts", "", "Ljavax/security/auth/x500/X500Principal;", "", "equals", "", "other", "", "findByIssuerAndSignature", "cert", "hashCode", "", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class BasicTrustRootIndex implements TrustRootIndex {
   private final Map<X500Principal, Set<X509Certificate>> subjectToCaCerts;

   public BasicTrustRootIndex(X509Certificate... var1) {
      Intrinsics.checkParameterIsNotNull(var1, "caCerts");
      super();
      Map var2 = (Map)(new LinkedHashMap());
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         X509Certificate var5 = var1[var4];
         X500Principal var6 = var5.getSubjectX500Principal();
         Intrinsics.checkExpressionValueIsNotNull(var6, "caCert.subjectX500Principal");
         Object var7 = var2.get(var6);
         Object var8 = var7;
         if (var7 == null) {
            var8 = (Set)(new LinkedHashSet());
            var2.put(var6, var8);
         }

         ((Set)var8).add(var5);
      }

      this.subjectToCaCerts = var2;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 == (BasicTrustRootIndex)this || var1 instanceof BasicTrustRootIndex && Intrinsics.areEqual((Object)((BasicTrustRootIndex)var1).subjectToCaCerts, (Object)this.subjectToCaCerts)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public X509Certificate findByIssuerAndSignature(X509Certificate var1) {
      Intrinsics.checkParameterIsNotNull(var1, "cert");
      X500Principal var2 = var1.getIssuerX500Principal();
      Set var3 = (Set)this.subjectToCaCerts.get(var2);
      X509Certificate var8 = null;
      Object var4 = null;
      if (var3 != null) {
         Iterator var10 = ((Iterable)var3).iterator();

         boolean var6;
         Object var9;
         do {
            var9 = var4;
            if (!var10.hasNext()) {
               break;
            }

            var9 = var10.next();
            X509Certificate var5 = (X509Certificate)var9;

            try {
               var1.verify(var5.getPublicKey());
            } catch (Exception var7) {
               var6 = false;
               continue;
            }

            var6 = true;
         } while(!var6);

         var8 = (X509Certificate)var9;
      }

      return var8;
   }

   public int hashCode() {
      return this.subjectToCaCerts.hashCode();
   }
}
