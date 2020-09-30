package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.CompletionListener;
import com.google.android.gms.drive.events.DriveEvent;

final class zzeg extends zzir {
   private final Context zzgw;

   private zzeg(Looper var1, Context var2) {
      super(var1);
      this.zzgw = var2;
   }

   // $FF: synthetic method
   zzeg(Looper var1, Context var2, zzef var3) {
      this(var1, var2);
   }

   public final void handleMessage(Message var1) {
      if (var1.what != 1) {
         zzee.zzai().efmt("EventCallback", "Don't know how to handle this event in context %s", this.zzgw);
      } else {
         Pair var2 = (Pair)var1.obj;
         com.google.android.gms.drive.events.zzi var5 = (com.google.android.gms.drive.events.zzi)var2.first;
         DriveEvent var7 = (DriveEvent)var2.second;
         int var3 = var7.getType();
         if (var3 != 1) {
            if (var3 != 2) {
               if (var3 != 3) {
                  if (var3 != 4) {
                     if (var3 != 8) {
                        zzee.zzai().wfmt("EventCallback", "Unexpected event: %s", var7);
                     } else {
                        zze var9 = new zze(((com.google.android.gms.drive.events.zzr)var7).zzac());
                        ((com.google.android.gms.drive.events.zzl)var5).zza(var9);
                     }
                  } else {
                     ((com.google.android.gms.drive.events.zzd)var5).zza((com.google.android.gms.drive.events.zzb)var7);
                  }
               } else {
                  com.google.android.gms.drive.events.zzq var6 = (com.google.android.gms.drive.events.zzq)var5;
                  com.google.android.gms.drive.events.zzo var8 = (com.google.android.gms.drive.events.zzo)var7;
                  DataHolder var4 = var8.zzz();
                  if (var4 != null) {
                     var6.zza(new zzeh(new MetadataBuffer(var4)));
                  }

                  if (var8.zzaa()) {
                     var6.zzc(var8.zzab());
                  }

               }
            } else {
               ((CompletionListener)var5).onCompletion((CompletionEvent)var7);
            }
         } else {
            ((ChangeListener)var5).onChange((ChangeEvent)var7);
         }
      }
   }
}
