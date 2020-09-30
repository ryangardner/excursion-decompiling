package com.google.android.gms.internal.drive;

import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;
import com.google.android.gms.drive.events.OnChangeListener;

// $FF: synthetic class
final class zzdj implements ChangeListener {
   private final OnChangeListener zzgi;

   private zzdj(OnChangeListener var1) {
      this.zzgi = var1;
   }

   static ChangeListener zza(OnChangeListener var0) {
      return new zzdj(var0);
   }

   public final void onChange(ChangeEvent var1) {
      this.zzgi.onChange(var1);
   }
}
