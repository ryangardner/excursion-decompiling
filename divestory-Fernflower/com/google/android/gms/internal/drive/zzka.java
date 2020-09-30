package com.google.android.gms.internal.drive;

final class zzka {
   private static final zzjy<?> zzoq = new zzjz();
   private static final zzjy<?> zzor = zzck();

   private static zzjy<?> zzck() {
      try {
         zzjy var0 = (zzjy)Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor().newInstance();
         return var0;
      } catch (Exception var1) {
         return null;
      }
   }

   static zzjy<?> zzcl() {
      return zzoq;
   }

   static zzjy<?> zzcm() {
      zzjy var0 = zzor;
      if (var0 != null) {
         return var0;
      } else {
         throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
      }
   }
}
