package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"},
   d2 = {"Lkotlin/text/MatchGroup;", "", "value", "", "range", "Lkotlin/ranges/IntRange;", "(Ljava/lang/String;Lkotlin/ranges/IntRange;)V", "getRange", "()Lkotlin/ranges/IntRange;", "getValue", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class MatchGroup {
   private final IntRange range;
   private final String value;

   public MatchGroup(String var1, IntRange var2) {
      Intrinsics.checkParameterIsNotNull(var1, "value");
      Intrinsics.checkParameterIsNotNull(var2, "range");
      super();
      this.value = var1;
      this.range = var2;
   }

   // $FF: synthetic method
   public static MatchGroup copy$default(MatchGroup var0, String var1, IntRange var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.value;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.range;
      }

      return var0.copy(var1, var2);
   }

   public final String component1() {
      return this.value;
   }

   public final IntRange component2() {
      return this.range;
   }

   public final MatchGroup copy(String var1, IntRange var2) {
      Intrinsics.checkParameterIsNotNull(var1, "value");
      Intrinsics.checkParameterIsNotNull(var2, "range");
      return new MatchGroup(var1, var2);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (!(var1 instanceof MatchGroup)) {
            return false;
         }

         MatchGroup var2 = (MatchGroup)var1;
         if (!Intrinsics.areEqual((Object)this.value, (Object)var2.value) || !Intrinsics.areEqual((Object)this.range, (Object)var2.range)) {
            return false;
         }
      }

      return true;
   }

   public final IntRange getRange() {
      return this.range;
   }

   public final String getValue() {
      return this.value;
   }

   public int hashCode() {
      String var1 = this.value;
      int var2 = 0;
      int var3;
      if (var1 != null) {
         var3 = var1.hashCode();
      } else {
         var3 = 0;
      }

      IntRange var4 = this.range;
      if (var4 != null) {
         var2 = var4.hashCode();
      }

      return var3 * 31 + var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("MatchGroup(value=");
      var1.append(this.value);
      var1.append(", range=");
      var1.append(this.range);
      var1.append(")");
      return var1.toString();
   }
}
