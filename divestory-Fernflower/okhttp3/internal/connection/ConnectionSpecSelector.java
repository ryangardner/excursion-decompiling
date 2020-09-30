package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.ConnectionSpec;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\rH\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lokhttp3/internal/connection/ConnectionSpecSelector;", "", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "(Ljava/util/List;)V", "isFallback", "", "isFallbackPossible", "nextModeIndex", "", "configureSecureSocket", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "connectionFailed", "e", "Ljava/io/IOException;", "socket", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class ConnectionSpecSelector {
   private final List<ConnectionSpec> connectionSpecs;
   private boolean isFallback;
   private boolean isFallbackPossible;
   private int nextModeIndex;

   public ConnectionSpecSelector(List<ConnectionSpec> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "connectionSpecs");
      super();
      this.connectionSpecs = var1;
   }

   private final boolean isFallbackPossible(SSLSocket var1) {
      int var2 = this.nextModeIndex;

      for(int var3 = this.connectionSpecs.size(); var2 < var3; ++var2) {
         if (((ConnectionSpec)this.connectionSpecs.get(var2)).isCompatible(var1)) {
            return true;
         }
      }

      return false;
   }

   public final ConnectionSpec configureSecureSocket(SSLSocket var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      ConnectionSpec var2 = (ConnectionSpec)null;
      int var3 = this.nextModeIndex;
      int var4 = this.connectionSpecs.size();

      ConnectionSpec var5;
      while(true) {
         var5 = var2;
         if (var3 >= var4) {
            break;
         }

         var5 = (ConnectionSpec)this.connectionSpecs.get(var3);
         if (var5.isCompatible(var1)) {
            this.nextModeIndex = var3 + 1;
            break;
         }

         ++var3;
      }

      if (var5 == null) {
         StringBuilder var8 = new StringBuilder();
         var8.append("Unable to find acceptable protocols. isFallback=");
         var8.append(this.isFallback);
         var8.append(',');
         var8.append(" modes=");
         var8.append(this.connectionSpecs);
         var8.append(',');
         var8.append(" supported protocols=");
         String[] var6 = var1.getEnabledProtocols();
         if (var6 == null) {
            Intrinsics.throwNpe();
         }

         String var7 = Arrays.toString(var6);
         Intrinsics.checkExpressionValueIsNotNull(var7, "java.util.Arrays.toString(this)");
         var8.append(var7);
         throw (Throwable)(new UnknownServiceException(var8.toString()));
      } else {
         this.isFallbackPossible = this.isFallbackPossible(var1);
         var5.apply$okhttp(var1, this.isFallback);
         return var5;
      }
   }

   public final boolean connectionFailed(IOException var1) {
      Intrinsics.checkParameterIsNotNull(var1, "e");
      boolean var2 = true;
      this.isFallback = true;
      if (!this.isFallbackPossible || var1 instanceof ProtocolException || var1 instanceof InterruptedIOException || var1 instanceof SSLHandshakeException && var1.getCause() instanceof CertificateException || var1 instanceof SSLPeerUnverifiedException || !(var1 instanceof SSLException)) {
         var2 = false;
      }

      return var2;
   }
}
