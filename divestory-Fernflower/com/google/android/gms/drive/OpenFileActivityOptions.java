package com.google.android.gms.drive;

import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.FilterHolder;
import java.util.List;

public final class OpenFileActivityOptions {
   public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
   public final String zzba;
   public final String[] zzbb;
   public final DriveId zzbd;
   public final FilterHolder zzbe;

   private OpenFileActivityOptions(String var1, String[] var2, Filter var3, DriveId var4) {
      this.zzba = var1;
      this.zzbb = var2;
      FilterHolder var5;
      if (var3 == null) {
         var5 = null;
      } else {
         var5 = new FilterHolder(var3);
      }

      this.zzbe = var5;
      this.zzbd = var4;
   }

   // $FF: synthetic method
   OpenFileActivityOptions(String var1, String[] var2, Filter var3, DriveId var4, zzq var5) {
      this(var1, var2, var3, var4);
   }

   public static class Builder {
      private final OpenFileActivityBuilder zzbf = new OpenFileActivityBuilder();

      public OpenFileActivityOptions build() {
         this.zzbf.zzg();
         return new OpenFileActivityOptions(this.zzbf.getTitle(), this.zzbf.zzs(), this.zzbf.zzt(), this.zzbf.zzu(), (zzq)null);
      }

      public OpenFileActivityOptions.Builder setActivityStartFolder(DriveId var1) {
         this.zzbf.setActivityStartFolder(var1);
         return this;
      }

      public OpenFileActivityOptions.Builder setActivityTitle(String var1) {
         this.zzbf.setActivityTitle(var1);
         return this;
      }

      public OpenFileActivityOptions.Builder setMimeType(List<String> var1) {
         this.zzbf.setMimeType((String[])var1.toArray(new String[0]));
         return this;
      }

      public OpenFileActivityOptions.Builder setSelectionFilter(Filter var1) {
         this.zzbf.setSelectionFilter(var1);
         return this;
      }
   }
}
