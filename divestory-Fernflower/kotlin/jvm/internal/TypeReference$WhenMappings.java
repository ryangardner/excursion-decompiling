package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.reflect.KVariance;

// $FF: synthetic class
@Metadata(
   bv = {1, 0, 3},
   k = 3,
   mv = {1, 1, 16}
)
public final class TypeReference$WhenMappings {
   // $FF: synthetic field
   public static final int[] $EnumSwitchMapping$0;

   static {
      int[] var0 = new int[KVariance.values().length];
      $EnumSwitchMapping$0 = var0;
      var0[KVariance.INVARIANT.ordinal()] = 1;
      $EnumSwitchMapping$0[KVariance.IN.ordinal()] = 2;
      $EnumSwitchMapping$0[KVariance.OUT.ordinal()] = 3;
   }
}
