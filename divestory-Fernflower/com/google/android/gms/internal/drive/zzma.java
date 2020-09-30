package com.google.android.gms.internal.drive;

final class zzma {
   private static final zzly zzuu = zzei();
   private static final zzly zzuv = new zzlz();

   static zzly zzeg() {
      return zzuu;
   }

   static zzly zzeh() {
      return zzuv;
   }

   private static zzly zzei() {
      try {
         zzly var0 = (zzly)Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor().newInstance();
         return var0;
      } catch (Exception var1) {
         return null;
      }
   }
}
