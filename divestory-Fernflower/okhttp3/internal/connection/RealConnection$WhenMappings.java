package okhttp3.internal.connection;

import java.net.Proxy.Type;
import kotlin.Metadata;

// $FF: synthetic class
@Metadata(
   bv = {1, 0, 3},
   k = 3,
   mv = {1, 1, 16}
)
public final class RealConnection$WhenMappings {
   // $FF: synthetic field
   public static final int[] $EnumSwitchMapping$0;

   static {
      int[] var0 = new int[Type.values().length];
      $EnumSwitchMapping$0 = var0;
      var0[Type.DIRECT.ordinal()] = 1;
      $EnumSwitchMapping$0[Type.HTTP.ordinal()] = 2;
   }
}
