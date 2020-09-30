package okhttp3.internal.tls;

import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\t\u001a\u00020\nH\u0016J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"},
   d2 = {"Lokhttp3/internal/tls/BasicCertificateChainCleaner;", "Lokhttp3/internal/tls/CertificateChainCleaner;", "trustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "(Lokhttp3/internal/tls/TrustRootIndex;)V", "clean", "", "Ljava/security/cert/Certificate;", "chain", "hostname", "", "equals", "", "other", "", "hashCode", "", "verifySignature", "toVerify", "Ljava/security/cert/X509Certificate;", "signingCert", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class BasicCertificateChainCleaner extends CertificateChainCleaner {
   public static final BasicCertificateChainCleaner.Companion Companion = new BasicCertificateChainCleaner.Companion((DefaultConstructorMarker)null);
   private static final int MAX_SIGNERS = 9;
   private final TrustRootIndex trustRootIndex;

   public BasicCertificateChainCleaner(TrustRootIndex var1) {
      Intrinsics.checkParameterIsNotNull(var1, "trustRootIndex");
      super();
      this.trustRootIndex = var1;
   }

   private final boolean verifySignature(X509Certificate var1, X509Certificate var2) {
      boolean var3 = Intrinsics.areEqual((Object)var1.getIssuerDN(), (Object)var2.getSubjectDN());
      boolean var4 = true;
      if (var3 ^ true) {
         return false;
      } else {
         try {
            var1.verify(var2.getPublicKey());
         } catch (GeneralSecurityException var5) {
            var4 = false;
         }

         return var4;
      }
   }

   public List<Certificate> clean(List<? extends Certificate> var1, String var2) throws SSLPeerUnverifiedException {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      Intrinsics.checkParameterIsNotNull(var2, "hostname");
      Deque var9 = (Deque)(new ArrayDeque((Collection)var1));
      var1 = (List)(new ArrayList());
      Object var3 = var9.removeFirst();
      Intrinsics.checkExpressionValueIsNotNull(var3, "queue.removeFirst()");
      var1.add(var3);
      int var4 = 0;

      for(boolean var5 = false; var4 < 9; ++var4) {
         var3 = var1.get(var1.size() - 1);
         if (var3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
         }

         X509Certificate var11 = (X509Certificate)var3;
         X509Certificate var6 = this.trustRootIndex.findByIssuerAndSignature(var11);
         if (var6 == null) {
            Iterator var12 = var9.iterator();
            Intrinsics.checkExpressionValueIsNotNull(var12, "queue.iterator()");

            X509Certificate var13;
            do {
               if (!var12.hasNext()) {
                  if (var5) {
                     return var1;
                  }

                  StringBuilder var8 = new StringBuilder();
                  var8.append("Failed to find a trusted cert that signed ");
                  var8.append(var11);
                  throw (Throwable)(new SSLPeerUnverifiedException(var8.toString()));
               }

               Object var7 = var12.next();
               if (var7 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
               }

               var13 = (X509Certificate)var7;
            } while(!this.verifySignature(var11, var13));

            var12.remove();
            var1.add(var13);
         } else {
            if (var1.size() > 1 || Intrinsics.areEqual((Object)var11, (Object)var6) ^ true) {
               var1.add(var6);
            }

            if (this.verifySignature(var6, var6)) {
               return var1;
            }

            var5 = true;
         }
      }

      StringBuilder var10 = new StringBuilder();
      var10.append("Certificate chain too long: ");
      var10.append(var1);
      throw (Throwable)(new SSLPeerUnverifiedException(var10.toString()));
   }

   public boolean equals(Object var1) {
      BasicCertificateChainCleaner var2 = (BasicCertificateChainCleaner)this;
      boolean var3 = true;
      if (var1 != var2 && (!(var1 instanceof BasicCertificateChainCleaner) || !Intrinsics.areEqual((Object)((BasicCertificateChainCleaner)var1).trustRootIndex, (Object)this.trustRootIndex))) {
         var3 = false;
      }

      return var3;
   }

   public int hashCode() {
      return this.trustRootIndex.hashCode();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"},
      d2 = {"Lokhttp3/internal/tls/BasicCertificateChainCleaner$Companion;", "", "()V", "MAX_SIGNERS", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }
}
