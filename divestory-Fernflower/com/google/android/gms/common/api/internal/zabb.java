package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.util.concurrent.NumberedThreadFactory;
import java.util.concurrent.ExecutorService;

public final class zabb {
   private static final ExecutorService zaa;

   static {
      zaa = com.google.android.gms.internal.base.zal.zaa().zaa(2, new NumberedThreadFactory("GAC_Executor"), com.google.android.gms.internal.base.zaq.zab);
   }

   public static ExecutorService zaa() {
      return zaa;
   }
}
