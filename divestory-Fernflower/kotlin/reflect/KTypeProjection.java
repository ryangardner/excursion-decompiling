package kotlin.reflect;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"},
   d2 = {"Lkotlin/reflect/KTypeProjection;", "", "variance", "Lkotlin/reflect/KVariance;", "type", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KVariance;Lkotlin/reflect/KType;)V", "getType", "()Lkotlin/reflect/KType;", "getVariance", "()Lkotlin/reflect/KVariance;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class KTypeProjection {
   public static final KTypeProjection.Companion Companion = new KTypeProjection.Companion((DefaultConstructorMarker)null);
   private static final KTypeProjection STAR = new KTypeProjection((KVariance)null, (KType)null);
   private final KType type;
   private final KVariance variance;

   public KTypeProjection(KVariance var1, KType var2) {
      this.variance = var1;
      this.type = var2;
   }

   // $FF: synthetic method
   public static KTypeProjection copy$default(KTypeProjection var0, KVariance var1, KType var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.variance;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.type;
      }

      return var0.copy(var1, var2);
   }

   public final KVariance component1() {
      return this.variance;
   }

   public final KType component2() {
      return this.type;
   }

   public final KTypeProjection copy(KVariance var1, KType var2) {
      return new KTypeProjection(var1, var2);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (!(var1 instanceof KTypeProjection)) {
            return false;
         }

         KTypeProjection var2 = (KTypeProjection)var1;
         if (!Intrinsics.areEqual((Object)this.variance, (Object)var2.variance) || !Intrinsics.areEqual((Object)this.type, (Object)var2.type)) {
            return false;
         }
      }

      return true;
   }

   public final KType getType() {
      return this.type;
   }

   public final KVariance getVariance() {
      return this.variance;
   }

   public int hashCode() {
      KVariance var1 = this.variance;
      int var2 = 0;
      int var3;
      if (var1 != null) {
         var3 = var1.hashCode();
      } else {
         var3 = 0;
      }

      KType var4 = this.type;
      if (var4 != null) {
         var2 = var4.hashCode();
      }

      return var3 * 31 + var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("KTypeProjection(variance=");
      var1.append(this.variance);
      var1.append(", type=");
      var1.append(this.type);
      var1.append(")");
      return var1.toString();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"},
      d2 = {"Lkotlin/reflect/KTypeProjection$Companion;", "", "()V", "STAR", "Lkotlin/reflect/KTypeProjection;", "getSTAR", "()Lkotlin/reflect/KTypeProjection;", "contravariant", "type", "Lkotlin/reflect/KType;", "covariant", "invariant", "kotlin-stdlib"},
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

      public final KTypeProjection contravariant(KType var1) {
         Intrinsics.checkParameterIsNotNull(var1, "type");
         return new KTypeProjection(KVariance.IN, var1);
      }

      public final KTypeProjection covariant(KType var1) {
         Intrinsics.checkParameterIsNotNull(var1, "type");
         return new KTypeProjection(KVariance.OUT, var1);
      }

      public final KTypeProjection getSTAR() {
         return KTypeProjection.STAR;
      }

      public final KTypeProjection invariant(KType var1) {
         Intrinsics.checkParameterIsNotNull(var1, "type");
         return new KTypeProjection(KVariance.INVARIANT, var1);
      }
   }
}
