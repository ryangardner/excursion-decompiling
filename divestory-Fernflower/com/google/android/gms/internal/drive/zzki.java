package com.google.android.gms.internal.drive;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class zzki<T extends zzjx> {
   private static final Logger logger = Logger.getLogger(zzjr.class.getName());
   private static String zzro = "com.google.protobuf.BlazeGeneratedExtensionRegistryLiteLoader";

   static <T extends zzjx> T zza(Class<T> var0) {
      ClassLoader var1 = zzki.class.getClassLoader();
      String var2;
      if (var0.equals(zzjx.class)) {
         var2 = zzro;
      } else {
         if (!var0.getPackage().equals(zzki.class.getPackage())) {
            throw new IllegalArgumentException(var0.getName());
         }

         var2 = String.format("%s.BlazeGenerated%sLoader", var0.getPackage().getName(), var0.getSimpleName());
      }

      try {
         Class var19 = Class.forName(var2, true, var1);

         IllegalStateException var18;
         zzki var21;
         try {
            var21 = (zzki)var19.getConstructor().newInstance();
         } catch (NoSuchMethodException var10) {
            var18 = new IllegalStateException(var10);
            throw var18;
         } catch (InstantiationException var11) {
            IllegalStateException var20 = new IllegalStateException(var11);
            throw var20;
         } catch (IllegalAccessException var12) {
            var18 = new IllegalStateException(var12);
            throw var18;
         } catch (InvocationTargetException var13) {
            var18 = new IllegalStateException(var13);
            throw var18;
         }

         return (zzjx)var0.cast(var21.zzcu());
      } catch (ClassNotFoundException var15) {
         Iterator var3 = ServiceLoader.load(zzki.class, var1).iterator();
         ArrayList var4 = new ArrayList();

         while(var3.hasNext()) {
            try {
               var4.add((zzjx)var0.cast(((zzki)var3.next()).zzcu()));
            } catch (ServiceConfigurationError var14) {
               Logger var6 = logger;
               Level var17 = Level.SEVERE;
               var2 = String.valueOf(var0.getSimpleName());
               if (var2.length() != 0) {
                  var2 = "Unable to load ".concat(var2);
               } else {
                  var2 = new String("Unable to load ");
               }

               var6.logp(var17, "com.google.protobuf.GeneratedExtensionRegistryLoader", "load", var2, var14);
            }
         }

         if (var4.size() == 1) {
            return (zzjx)var4.get(0);
         } else if (var4.size() == 0) {
            return null;
         } else {
            try {
               zzjx var16 = (zzjx)var0.getMethod("combine", Collection.class).invoke((Object)null, var4);
               return var16;
            } catch (NoSuchMethodException var7) {
               throw new IllegalStateException(var7);
            } catch (IllegalAccessException var8) {
               throw new IllegalStateException(var8);
            } catch (InvocationTargetException var9) {
               throw new IllegalStateException(var9);
            }
         }
      }
   }

   protected abstract T zzcu();
}
