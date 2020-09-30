package com.google.android.gms.common.internal;

import java.util.concurrent.ConcurrentHashMap;

public class LibraryVersion {
   private static final GmsLogger zza = new GmsLogger("LibraryVersion", "");
   private static LibraryVersion zzb = new LibraryVersion();
   private ConcurrentHashMap<String, String> zzc = new ConcurrentHashMap();

   protected LibraryVersion() {
   }

   public static LibraryVersion getInstance() {
      return zzb;
   }

   public String getVersion(String param1) {
      // $FF: Couldn't be decompiled
   }
}
