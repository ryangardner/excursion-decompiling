package okhttp3.internal.tls;

import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\nJ\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0004H\u0002J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001c\u0010\u0012\u001a\u00020\u000e2\b\u0010\u0013\u001a\u0004\u0018\u00010\b2\b\u0010\u0014\u001a\u0004\u0018\u00010\bH\u0002J\u0018\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0017"},
   d2 = {"Lokhttp3/internal/tls/OkHostnameVerifier;", "Ljavax/net/ssl/HostnameVerifier;", "()V", "ALT_DNS_NAME", "", "ALT_IPA_NAME", "allSubjectAltNames", "", "", "certificate", "Ljava/security/cert/X509Certificate;", "getSubjectAltNames", "type", "verify", "", "host", "session", "Ljavax/net/ssl/SSLSession;", "verifyHostname", "hostname", "pattern", "verifyIpAddress", "ipAddress", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class OkHostnameVerifier implements HostnameVerifier {
   private static final int ALT_DNS_NAME = 2;
   private static final int ALT_IPA_NAME = 7;
   public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();

   private OkHostnameVerifier() {
   }

   private final List<String> getSubjectAltNames(X509Certificate var1, int var2) {
      boolean var10001;
      Collection var3;
      try {
         var3 = var1.getSubjectAlternativeNames();
      } catch (CertificateParsingException var10) {
         var10001 = false;
         return CollectionsKt.emptyList();
      }

      List var15;
      if (var3 == null) {
         try {
            var15 = CollectionsKt.emptyList();
            return var15;
         } catch (CertificateParsingException var6) {
            var10001 = false;
         }
      } else {
         Iterator var17;
         try {
            ArrayList var14 = new ArrayList();
            var15 = (List)var14;
            var17 = var3.iterator();
         } catch (CertificateParsingException var9) {
            var10001 = false;
            return CollectionsKt.emptyList();
         }

         while(true) {
            List var4;
            try {
               if (!var17.hasNext()) {
                  return var15;
               }

               var4 = (List)var17.next();
            } catch (CertificateParsingException var13) {
               var10001 = false;
               break;
            }

            if (var4 != null) {
               label71:
               try {
                  if (var4.size() >= 2) {
                     break label71;
                  }
                  continue;
               } catch (CertificateParsingException var12) {
                  var10001 = false;
                  break;
               }

               label65:
               try {
                  if (!(Intrinsics.areEqual((Object)var4.get(0), (Object)var2) ^ true)) {
                     break label65;
                  }
                  continue;
               } catch (CertificateParsingException var11) {
                  var10001 = false;
                  break;
               }

               Object var18;
               try {
                  var18 = var4.get(1);
               } catch (CertificateParsingException var8) {
                  var10001 = false;
                  break;
               }

               if (var18 != null) {
                  if (var18 == null) {
                     try {
                        TypeCastException var16 = new TypeCastException("null cannot be cast to non-null type kotlin.String");
                        throw var16;
                     } catch (CertificateParsingException var5) {
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     var15.add((String)var18);
                  } catch (CertificateParsingException var7) {
                     var10001 = false;
                     break;
                  }
               }
            }
         }
      }

      return CollectionsKt.emptyList();
   }

   private final boolean verifyHostname(String var1, String var2) {
      String var3 = var1;
      CharSequence var5 = (CharSequence)var1;
      boolean var4;
      if (var5 != null && var5.length() != 0) {
         var4 = false;
      } else {
         var4 = true;
      }

      if (!var4 && !StringsKt.startsWith$default(var3, ".", false, 2, (Object)null) && !StringsKt.endsWith$default(var3, "..", false, 2, (Object)null)) {
         var5 = (CharSequence)var2;
         if (var5 != null && var5.length() != 0) {
            var4 = false;
         } else {
            var4 = true;
         }

         if (!var4 && !StringsKt.startsWith$default(var2, ".", false, 2, (Object)null) && !StringsKt.endsWith$default(var2, "..", false, 2, (Object)null)) {
            var1 = var3;
            if (!StringsKt.endsWith$default(var3, ".", false, 2, (Object)null)) {
               StringBuilder var6 = new StringBuilder();
               var6.append(var3);
               var6.append(".");
               var1 = var6.toString();
            }

            var3 = var2;
            if (!StringsKt.endsWith$default(var2, ".", false, 2, (Object)null)) {
               StringBuilder var8 = new StringBuilder();
               var8.append(var2);
               var8.append(".");
               var3 = var8.toString();
            }

            Locale var7 = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(var7, "Locale.US");
            if (var3 != null) {
               var2 = var3.toLowerCase(var7);
               Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.String).toLowerCase(locale)");
               CharSequence var9 = (CharSequence)var2;
               if (!StringsKt.contains$default(var9, (CharSequence)"*", false, 2, (Object)null)) {
                  return Intrinsics.areEqual((Object)var1, (Object)var2);
               }

               if (StringsKt.startsWith$default(var2, "*.", false, 2, (Object)null) && StringsKt.indexOf$default(var9, '*', 1, false, 4, (Object)null) == -1) {
                  if (var1.length() < var2.length()) {
                     return false;
                  }

                  if (Intrinsics.areEqual((Object)"*.", (Object)var2)) {
                     return false;
                  }

                  if (var2 != null) {
                     var2 = var2.substring(1);
                     Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.String).substring(startIndex)");
                     if (!StringsKt.endsWith$default(var1, var2, false, 2, (Object)null)) {
                        return false;
                     }

                     int var10 = var1.length() - var2.length();
                     if (var10 > 0 && StringsKt.lastIndexOf$default((CharSequence)var1, '.', var10 - 1, false, 4, (Object)null) != -1) {
                        return false;
                     }

                     return true;
                  }

                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               return false;
            }

            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }
      }

      return false;
   }

   private final boolean verifyHostname(String var1, X509Certificate var2) {
      Locale var3 = Locale.US;
      Intrinsics.checkExpressionValueIsNotNull(var3, "Locale.US");
      if (var1 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         var1 = var1.toLowerCase(var3);
         Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).toLowerCase(locale)");
         Iterable var6 = (Iterable)this.getSubjectAltNames(var2, 2);
         boolean var4 = var6 instanceof Collection;
         boolean var5 = false;
         if (var4 && ((Collection)var6).isEmpty()) {
            var4 = var5;
         } else {
            Iterator var7 = var6.iterator();

            while(true) {
               var4 = var5;
               if (!var7.hasNext()) {
                  break;
               }

               String var8 = (String)var7.next();
               if (INSTANCE.verifyHostname(var1, var8)) {
                  var4 = true;
                  break;
               }
            }
         }

         return var4;
      }
   }

   private final boolean verifyIpAddress(String var1, X509Certificate var2) {
      var1 = HostnamesKt.toCanonicalHost(var1);
      Iterable var5 = (Iterable)this.getSubjectAltNames(var2, 7);
      boolean var3 = var5 instanceof Collection;
      boolean var4 = false;
      if (var3 && ((Collection)var5).isEmpty()) {
         var3 = var4;
      } else {
         Iterator var6 = var5.iterator();

         while(true) {
            var3 = var4;
            if (!var6.hasNext()) {
               break;
            }

            if (Intrinsics.areEqual((Object)var1, (Object)HostnamesKt.toCanonicalHost((String)var6.next()))) {
               var3 = true;
               break;
            }
         }
      }

      return var3;
   }

   public final List<String> allSubjectAltNames(X509Certificate var1) {
      Intrinsics.checkParameterIsNotNull(var1, "certificate");
      List var2 = this.getSubjectAltNames(var1, 7);
      List var3 = this.getSubjectAltNames(var1, 2);
      return CollectionsKt.plus((Collection)var2, (Iterable)var3);
   }

   public final boolean verify(String var1, X509Certificate var2) {
      Intrinsics.checkParameterIsNotNull(var1, "host");
      Intrinsics.checkParameterIsNotNull(var2, "certificate");
      boolean var3;
      if (Util.canParseAsIpAddress(var1)) {
         var3 = this.verifyIpAddress(var1, var2);
      } else {
         var3 = this.verifyHostname(var1, var2);
      }

      return var3;
   }

   public boolean verify(String var1, SSLSession var2) {
      Intrinsics.checkParameterIsNotNull(var1, "host");
      Intrinsics.checkParameterIsNotNull(var2, "session");
      boolean var3 = false;

      boolean var10001;
      Certificate var9;
      try {
         var9 = var2.getPeerCertificates()[0];
      } catch (SSLException var7) {
         var10001 = false;
         return var3;
      }

      if (var9 != null) {
         boolean var4;
         try {
            var4 = this.verify(var1, (X509Certificate)var9);
         } catch (SSLException var6) {
            var10001 = false;
            return var3;
         }

         var3 = var4;
      } else {
         try {
            TypeCastException var8 = new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
            throw var8;
         } catch (SSLException var5) {
            var10001 = false;
         }
      }

      return var3;
   }
}
