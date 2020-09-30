package okhttp3;

import java.io.IOException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0001\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\fB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\r"},
   d2 = {"Lokhttp3/Protocol;", "", "protocol", "", "(Ljava/lang/String;ILjava/lang/String;)V", "toString", "HTTP_1_0", "HTTP_1_1", "SPDY_3", "HTTP_2", "H2_PRIOR_KNOWLEDGE", "QUIC", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public enum Protocol {
   public static final Protocol.Companion Companion;
   H2_PRIOR_KNOWLEDGE,
   HTTP_1_0,
   HTTP_1_1,
   HTTP_2,
   QUIC,
   @Deprecated(
      message = "OkHttp has dropped support for SPDY. Prefer {@link #HTTP_2}."
   )
   SPDY_3;

   private final String protocol;

   static {
      Protocol var0 = new Protocol("HTTP_1_0", 0, "http/1.0");
      HTTP_1_0 = var0;
      Protocol var1 = new Protocol("HTTP_1_1", 1, "http/1.1");
      HTTP_1_1 = var1;
      Protocol var2 = new Protocol("SPDY_3", 2, "spdy/3.1");
      SPDY_3 = var2;
      Protocol var3 = new Protocol("HTTP_2", 3, "h2");
      HTTP_2 = var3;
      Protocol var4 = new Protocol("H2_PRIOR_KNOWLEDGE", 4, "h2_prior_knowledge");
      H2_PRIOR_KNOWLEDGE = var4;
      Protocol var5 = new Protocol("QUIC", 5, "quic");
      QUIC = var5;
      Companion = new Protocol.Companion((DefaultConstructorMarker)null);
   }

   private Protocol(String var3) {
      this.protocol = var3;
   }

   @JvmStatic
   public static final Protocol get(String var0) throws IOException {
      return Companion.get(var0);
   }

   public String toString() {
      return this.protocol;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
      d2 = {"Lokhttp3/Protocol$Companion;", "", "()V", "get", "Lokhttp3/Protocol;", "protocol", "", "okhttp"},
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

      @JvmStatic
      public final Protocol get(String var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "protocol");
         Protocol var3;
         if (Intrinsics.areEqual((Object)var1, (Object)Protocol.HTTP_1_0.protocol)) {
            var3 = Protocol.HTTP_1_0;
         } else if (Intrinsics.areEqual((Object)var1, (Object)Protocol.HTTP_1_1.protocol)) {
            var3 = Protocol.HTTP_1_1;
         } else if (Intrinsics.areEqual((Object)var1, (Object)Protocol.H2_PRIOR_KNOWLEDGE.protocol)) {
            var3 = Protocol.H2_PRIOR_KNOWLEDGE;
         } else if (Intrinsics.areEqual((Object)var1, (Object)Protocol.HTTP_2.protocol)) {
            var3 = Protocol.HTTP_2;
         } else if (Intrinsics.areEqual((Object)var1, (Object)Protocol.SPDY_3.protocol)) {
            var3 = Protocol.SPDY_3;
         } else {
            if (!Intrinsics.areEqual((Object)var1, (Object)Protocol.QUIC.protocol)) {
               StringBuilder var2 = new StringBuilder();
               var2.append("Unexpected protocol: ");
               var2.append(var1);
               throw (Throwable)(new IOException(var2.toString()));
            }

            var3 = Protocol.QUIC;
         }

         return var3;
      }
   }
}
