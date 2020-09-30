package com.google.android.gms.internal.drive;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzjx {
   private static volatile boolean zzol;
   private static final Class<?> zzom = zzch();
   private static volatile zzjx zzon;
   static final zzjx zzoo = new zzjx(true);
   private final Map<zzjx.zza, zzkk.zzd<?, ?>> zzop;

   zzjx() {
      this.zzop = new HashMap();
   }

   private zzjx(boolean var1) {
      this.zzop = Collections.emptyMap();
   }

   static zzjx zzcg() {
      return zzki.zza(zzjx.class);
   }

   private static Class<?> zzch() {
      try {
         Class var0 = Class.forName("com.google.protobuf.Extension");
         return var0;
      } catch (ClassNotFoundException var1) {
         return null;
      }
   }

   public static zzjx zzci() {
      return zzjw.zzcf();
   }

   public static zzjx zzcj() {
      zzjx var0 = zzon;
      zzjx var1 = var0;
      if (var0 == null) {
         synchronized(zzjx.class){}

         Throwable var10000;
         boolean var10001;
         label206: {
            try {
               var0 = zzon;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label206;
            }

            var1 = var0;
            if (var0 == null) {
               try {
                  var1 = zzjw.zzcg();
                  zzon = var1;
               } catch (Throwable var20) {
                  var10000 = var20;
                  var10001 = false;
                  break label206;
               }
            }

            label193:
            try {
               return var1;
            } catch (Throwable var19) {
               var10000 = var19;
               var10001 = false;
               break label193;
            }
         }

         while(true) {
            Throwable var22 = var10000;

            try {
               throw var22;
            } catch (Throwable var18) {
               var10000 = var18;
               var10001 = false;
               continue;
            }
         }
      } else {
         return var1;
      }
   }

   public final <ContainingType extends zzlq> zzkk.zzd<ContainingType, ?> zza(ContainingType var1, int var2) {
      return (zzkk.zzd)this.zzop.get(new zzjx.zza(var1, var2));
   }

   static final class zza {
      private final int number;
      private final Object object;

      zza(Object var1, int var2) {
         this.object = var1;
         this.number = var2;
      }

      public final boolean equals(Object var1) {
         if (!(var1 instanceof zzjx.zza)) {
            return false;
         } else {
            zzjx.zza var2 = (zzjx.zza)var1;
            return this.object == var2.object && this.number == var2.number;
         }
      }

      public final int hashCode() {
         return System.identityHashCode(this.object) * '\uffff' + this.number;
      }
   }
}
