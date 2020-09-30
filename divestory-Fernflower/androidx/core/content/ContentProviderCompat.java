package androidx.core.content;

import android.content.ContentProvider;
import android.content.Context;

public final class ContentProviderCompat {
   private ContentProviderCompat() {
   }

   public static Context requireContext(ContentProvider var0) {
      Context var1 = var0.getContext();
      if (var1 != null) {
         return var1;
      } else {
         throw new IllegalStateException("Cannot find context from the provider.");
      }
   }
}
