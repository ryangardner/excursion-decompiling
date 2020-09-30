package com.google.android.gms.dynamic;

import android.os.IBinder;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.reflect.Field;

public final class ObjectWrapper<T> extends IObjectWrapper.Stub {
   private final T zza;

   private ObjectWrapper(T var1) {
      this.zza = var1;
   }

   public static <T> T unwrap(IObjectWrapper var0) {
      if (var0 instanceof ObjectWrapper) {
         return ((ObjectWrapper)var0).zza;
      } else {
         IBinder var1 = var0.asBinder();
         Field[] var2 = var1.getClass().getDeclaredFields();
         Field var10 = null;
         int var3 = var2.length;
         int var4 = 0;

         int var5;
         int var7;
         for(var5 = 0; var4 < var3; var5 = var7) {
            Field var6 = var2[var4];
            var7 = var5;
            if (!var6.isSynthetic()) {
               var7 = var5 + 1;
               var10 = var6;
            }

            ++var4;
         }

         if (var5 == 1) {
            if (!((Field)Preconditions.checkNotNull(var10)).isAccessible()) {
               var10.setAccessible(true);

               try {
                  Object var12 = var10.get(var1);
                  return var12;
               } catch (NullPointerException var8) {
                  throw new IllegalArgumentException("Binder object is null.", var8);
               } catch (IllegalAccessException var9) {
                  throw new IllegalArgumentException("Could not access the field in remoteBinder.", var9);
               }
            } else {
               throw new IllegalArgumentException("IObjectWrapper declared field not private!");
            }
         } else {
            var4 = var2.length;
            StringBuilder var11 = new StringBuilder(64);
            var11.append("Unexpected number of IObjectWrapper declared fields: ");
            var11.append(var4);
            throw new IllegalArgumentException(var11.toString());
         }
      }
   }

   public static <T> IObjectWrapper wrap(T var0) {
      return new ObjectWrapper(var0);
   }
}
