package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0017B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\u0011\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u000e\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0003H\u0016J\u0016\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003J\u001e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003J\b\u0010\u0014\u001a\u00020\u0015H\u0016J \u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lkotlin/KotlinVersion;", "", "major", "", "minor", "(II)V", "patch", "(III)V", "getMajor", "()I", "getMinor", "getPatch", "version", "compareTo", "other", "equals", "", "", "hashCode", "isAtLeast", "toString", "", "versionOf", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class KotlinVersion implements Comparable<KotlinVersion> {
   public static final KotlinVersion CURRENT = new KotlinVersion(1, 3, 72);
   public static final KotlinVersion.Companion Companion = new KotlinVersion.Companion((DefaultConstructorMarker)null);
   public static final int MAX_COMPONENT_VALUE = 255;
   private final int major;
   private final int minor;
   private final int patch;
   private final int version;

   public KotlinVersion(int var1, int var2) {
      this(var1, var2, 0);
   }

   public KotlinVersion(int var1, int var2, int var3) {
      this.major = var1;
      this.minor = var2;
      this.patch = var3;
      this.version = this.versionOf(var1, var2, var3);
   }

   private final int versionOf(int var1, int var2, int var3) {
      boolean var4;
      if (var1 >= 0 && 255 >= var1 && var2 >= 0 && 255 >= var2 && var3 >= 0 && 255 >= var3) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         return (var1 << 16) + (var2 << 8) + var3;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Version components are out of range: ");
         var5.append(var1);
         var5.append('.');
         var5.append(var2);
         var5.append('.');
         var5.append(var3);
         throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
      }
   }

   public int compareTo(KotlinVersion var1) {
      Intrinsics.checkParameterIsNotNull(var1, "other");
      return this.version - var1.version;
   }

   public boolean equals(Object var1) {
      KotlinVersion var2 = (KotlinVersion)this;
      boolean var3 = true;
      if (var2 == var1) {
         return true;
      } else {
         Object var5 = var1;
         if (!(var1 instanceof KotlinVersion)) {
            var5 = null;
         }

         KotlinVersion var4 = (KotlinVersion)var5;
         if (var4 != null) {
            if (this.version != var4.version) {
               var3 = false;
            }

            return var3;
         } else {
            return false;
         }
      }
   }

   public final int getMajor() {
      return this.major;
   }

   public final int getMinor() {
      return this.minor;
   }

   public final int getPatch() {
      return this.patch;
   }

   public int hashCode() {
      return this.version;
   }

   public final boolean isAtLeast(int var1, int var2) {
      int var3 = this.major;
      boolean var4;
      if (var3 > var1 || var3 == var1 && this.minor >= var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public final boolean isAtLeast(int var1, int var2, int var3) {
      boolean var5;
      label29: {
         int var4 = this.major;
         if (var4 <= var1) {
            if (var4 != var1) {
               break label29;
            }

            var1 = this.minor;
            if (var1 <= var2 && (var1 != var2 || this.patch < var3)) {
               break label29;
            }
         }

         var5 = true;
         return var5;
      }

      var5 = false;
      return var5;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.major);
      var1.append('.');
      var1.append(this.minor);
      var1.append('.');
      var1.append(this.patch);
      return var1.toString();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"},
      d2 = {"Lkotlin/KotlinVersion$Companion;", "", "()V", "CURRENT", "Lkotlin/KotlinVersion;", "MAX_COMPONENT_VALUE", "", "kotlin-stdlib"},
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
