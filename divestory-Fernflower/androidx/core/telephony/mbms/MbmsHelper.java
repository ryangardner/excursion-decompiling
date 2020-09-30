package androidx.core.telephony.mbms;

import android.content.Context;
import android.os.LocaleList;
import android.os.Build.VERSION;
import android.telephony.mbms.ServiceInfo;
import java.util.Iterator;
import java.util.Locale;

public final class MbmsHelper {
   private MbmsHelper() {
   }

   public static CharSequence getBestNameForService(Context var0, ServiceInfo var1) {
      int var2 = VERSION.SDK_INT;
      Object var3 = null;
      if (var2 < 28) {
         return null;
      } else {
         LocaleList var4 = var0.getResources().getConfiguration().getLocales();
         var2 = var1.getNamedContentLocales().size();
         if (var2 == 0) {
            return null;
         } else {
            String[] var6 = new String[var2];
            var2 = 0;

            for(Iterator var5 = var1.getNamedContentLocales().iterator(); var5.hasNext(); ++var2) {
               var6[var2] = ((Locale)var5.next()).toLanguageTag();
            }

            Locale var7 = var4.getFirstMatch(var6);
            CharSequence var8;
            if (var7 == null) {
               var8 = (CharSequence)var3;
            } else {
               var8 = var1.getNameForLocale(var7);
            }

            return var8;
         }
      }
   }
}
