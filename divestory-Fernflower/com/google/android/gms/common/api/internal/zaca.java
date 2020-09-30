package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.util.concurrent.NumberedThreadFactory;
import java.util.concurrent.ExecutorService;

public final class zaca {
   private static final ExecutorService zaa;

   static {
      zaa = com.google.android.gms.internal.base.zal.zaa().zaa(new NumberedThreadFactory("GAC_Transform"), com.google.android.gms.internal.base.zaq.zaa);
   }

   public static ExecutorService zaa() {
      return zaa;
   }
}
