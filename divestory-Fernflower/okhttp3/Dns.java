package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&\u0082\u0002\u0007\n\u0005\b\u0091F0\u0001¨\u0006\b"},
   d2 = {"Lokhttp3/Dns;", "", "lookup", "", "Ljava/net/InetAddress;", "hostname", "", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public interface Dns {
   Dns.Companion Companion = new Dns.Companion((DefaultConstructorMarker)null);
   Dns SYSTEM = (Dns)(new Dns.Companion.DnsSystem());

   List<InetAddress> lookup(String var1) throws UnknownHostException;

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004ø\u0001\u0000¢\u0006\u0002\n\u0000¨\u0006\u0001\u0082\u0002\u0007\n\u0005\b\u0091F0\u0001¨\u0006\u0006"},
      d2 = {"Lokhttp3/Dns$Companion;", "", "()V", "SYSTEM", "Lokhttp3/Dns;", "DnsSystem", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      // $FF: synthetic field
      static final Dns.Companion $$INSTANCE;

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      @Metadata(
         bv = {1, 0, 3},
         d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"},
         d2 = {"Lokhttp3/Dns$Companion$DnsSystem;", "Lokhttp3/Dns;", "()V", "lookup", "", "Ljava/net/InetAddress;", "hostname", "", "okhttp"},
         k = 1,
         mv = {1, 1, 16}
      )
      private static final class DnsSystem implements Dns {
         public DnsSystem() {
         }

         public List<InetAddress> lookup(String var1) {
            Intrinsics.checkParameterIsNotNull(var1, "hostname");

            try {
               InetAddress[] var2 = InetAddress.getAllByName(var1);
               Intrinsics.checkExpressionValueIsNotNull(var2, "InetAddress.getAllByName(hostname)");
               List var6 = ArraysKt.toList(var2);
               return var6;
            } catch (NullPointerException var4) {
               StringBuilder var3 = new StringBuilder();
               var3.append("Broken system behaviour for dns lookup of ");
               var3.append(var1);
               UnknownHostException var5 = new UnknownHostException(var3.toString());
               var5.initCause((Throwable)var4);
               throw (Throwable)var5;
            }
         }
      }
   }
}
