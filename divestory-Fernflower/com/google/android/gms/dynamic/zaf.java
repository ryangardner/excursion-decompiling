package com.google.android.gms.dynamic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

final class zaf implements OnClickListener {
   // $FF: synthetic field
   private final Context zaa;
   // $FF: synthetic field
   private final Intent zab;

   zaf(Context var1, Intent var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   public final void onClick(View var1) {
      try {
         this.zaa.startActivity(this.zab);
      } catch (ActivityNotFoundException var2) {
         Log.e("DeferredLifecycleHelper", "Failed to start resolution intent", var2);
      }
   }
}
