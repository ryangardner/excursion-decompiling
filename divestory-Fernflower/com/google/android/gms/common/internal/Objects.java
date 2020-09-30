package com.google.android.gms.common.internal;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class Objects {
   private Objects() {
      throw new AssertionError("Uninstantiable");
   }

   public static boolean checkBundlesEquality(Bundle var0, Bundle var1) {
      if (var0 != null && var1 != null) {
         if (var0.size() != var1.size()) {
            return false;
         } else {
            Set var2 = var0.keySet();
            if (!var2.containsAll(var1.keySet())) {
               return false;
            } else {
               Iterator var3 = var2.iterator();

               String var4;
               do {
                  if (!var3.hasNext()) {
                     return true;
                  }

                  var4 = (String)var3.next();
               } while(equal(var0.get(var4), var1.get(var4)));

               return false;
            }
         }
      } else {
         return var0 == var1;
      }
   }

   public static boolean equal(Object var0, Object var1) {
      return var0 == var1 || var0 != null && var0.equals(var1);
   }

   public static int hashCode(Object... var0) {
      return Arrays.hashCode(var0);
   }

   public static Objects.ToStringHelper toStringHelper(Object var0) {
      return new Objects.ToStringHelper(var0, (zzu)null);
   }

   public static final class ToStringHelper {
      private final List<String> zza;
      private final Object zzb;

      private ToStringHelper(Object var1) {
         this.zzb = Preconditions.checkNotNull(var1);
         this.zza = new ArrayList();
      }

      // $FF: synthetic method
      ToStringHelper(Object var1, zzu var2) {
         this(var1);
      }

      public final Objects.ToStringHelper add(String var1, Object var2) {
         List var3 = this.zza;
         var1 = (String)Preconditions.checkNotNull(var1);
         String var5 = String.valueOf(var2);
         StringBuilder var4 = new StringBuilder(String.valueOf(var1).length() + 1 + String.valueOf(var5).length());
         var4.append(var1);
         var4.append("=");
         var4.append(var5);
         var3.add(var4.toString());
         return this;
      }

      public final String toString() {
         StringBuilder var1 = new StringBuilder(100);
         var1.append(this.zzb.getClass().getSimpleName());
         var1.append('{');
         int var2 = this.zza.size();

         for(int var3 = 0; var3 < var2; ++var3) {
            var1.append((String)this.zza.get(var3));
            if (var3 < var2 - 1) {
               var1.append(", ");
            }
         }

         var1.append('}');
         return var1.toString();
      }
   }
}
