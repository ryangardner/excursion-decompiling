package com.google.android.gms.internal.drive;

final class zzln {
   private static final zzll zztz = zzeb();
   private static final zzll zzua = new zzlm();

   static zzll zzdz() {
      return zztz;
   }

   static zzll zzea() {
      return zzua;
   }

   private static zzll zzeb() {
      try {
         zzll var0 = (zzll)Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor().newInstance();
         return var0;
      } catch (Exception var1) {
         return null;
      }
   }
}
