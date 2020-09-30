package com.google.android.gms.drive;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaa;
import com.google.android.gms.internal.drive.zzhs;
import java.util.Iterator;

public final class MetadataBuffer extends AbstractDataBuffer<Metadata> {
   private MetadataBuffer.zza zzau;

   public MetadataBuffer(DataHolder var1) {
      super(var1);
      var1.getMetadata().setClassLoader(MetadataBuffer.class.getClassLoader());
   }

   public final Metadata get(int var1) {
      MetadataBuffer.zza var2 = this.zzau;
      MetadataBuffer.zza var3;
      if (var2 != null) {
         var3 = var2;
         if (var2.row == var1) {
            return var3;
         }
      }

      var3 = new MetadataBuffer.zza(this.mDataHolder, var1);
      this.zzau = var3;
      return var3;
   }

   @Deprecated
   public final String getNextPageToken() {
      return null;
   }

   public final void release() {
      if (this.mDataHolder != null) {
         com.google.android.gms.drive.metadata.internal.zzf.zza(this.mDataHolder);
      }

      super.release();
   }

   static final class zza extends Metadata {
      private final int row;
      private final DataHolder zzav;
      private final int zzaw;

      public zza(DataHolder var1, int var2) {
         this.zzav = var1;
         this.row = var2;
         this.zzaw = var1.getWindowIndex(var2);
      }

      // $FF: synthetic method
      public final Object freeze() {
         MetadataBundle var1 = MetadataBundle.zzbe();
         Iterator var2 = com.google.android.gms.drive.metadata.internal.zzf.zzbc().iterator();

         while(var2.hasNext()) {
            MetadataField var3 = (MetadataField)var2.next();
            if (var3 != zzhs.zzkq) {
               var3.zza(this.zzav, var1, this.row, this.zzaw);
            }
         }

         return new zzaa(var1);
      }

      public final boolean isDataValid() {
         return !this.zzav.isClosed();
      }

      public final <T> T zza(MetadataField<T> var1) {
         return var1.zza(this.zzav, this.row, this.zzaw);
      }
   }
}
