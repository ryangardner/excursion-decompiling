package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.common.R;

public class StringResourceValueReader {
   private final Resources zza;
   private final String zzb;

   public StringResourceValueReader(Context var1) {
      Preconditions.checkNotNull(var1);
      Resources var2 = var1.getResources();
      this.zza = var2;
      this.zzb = var2.getResourcePackageName(R.string.common_google_play_services_unknown_issue);
   }

   public String getString(String var1) {
      int var2 = this.zza.getIdentifier(var1, "string", this.zzb);
      return var2 == 0 ? null : this.zza.getString(var2);
   }
}
