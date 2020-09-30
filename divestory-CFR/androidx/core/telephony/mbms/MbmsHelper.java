/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.LocaleList
 *  android.telephony.mbms.ServiceInfo
 */
package androidx.core.telephony.mbms;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.telephony.mbms.ServiceInfo;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public final class MbmsHelper {
    private MbmsHelper() {
    }

    public static CharSequence getBestNameForService(Context object, ServiceInfo serviceInfo) {
        int n = Build.VERSION.SDK_INT;
        Object var3_3 = null;
        if (n < 28) {
            return null;
        }
        LocaleList localeList = object.getResources().getConfiguration().getLocales();
        n = serviceInfo.getNamedContentLocales().size();
        if (n == 0) {
            return null;
        }
        object = new String[n];
        n = 0;
        Iterator iterator2 = serviceInfo.getNamedContentLocales().iterator();
        while (iterator2.hasNext()) {
            object[n] = ((Locale)iterator2.next()).toLanguageTag();
            ++n;
        }
        if ((object = localeList.getFirstMatch(object)) != null) return serviceInfo.getNameForLocale((Locale)object);
        return var3_3;
    }
}

