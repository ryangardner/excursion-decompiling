package com.google.android.gms.internal.drive;

final class zzjw {
   private static final Class<?> zzok = zzce();

   private static Class<?> zzce() {
      try {
         Class var0 = Class.forName("com.google.protobuf.ExtensionRegistry");
         return var0;
      } catch (ClassNotFoundException var1) {
         return null;
      }
   }

   public static zzjx zzcf() {
      if (zzok != null) {
         try {
            zzjx var0 = zzn("getEmptyRegistry");
            return var0;
         } catch (Exception var1) {
         }
      }

      return zzjx.zzoo;
   }

   static zzjx zzcg() {
      zzjx var0;
      label24: {
         if (zzok != null) {
            try {
               var0 = zzn("loadGeneratedRegistry");
               break label24;
            } catch (Exception var2) {
            }
         }

         var0 = null;
      }

      zzjx var1 = var0;
      if (var0 == null) {
         var1 = zzjx.zzcg();
      }

      var0 = var1;
      if (var1 == null) {
         var0 = zzcf();
      }

      return var0;
   }

   private static final zzjx zzn(String var0) throws Exception {
      return (zzjx)zzok.getDeclaredMethod(var0).invoke((Object)null);
   }
}
