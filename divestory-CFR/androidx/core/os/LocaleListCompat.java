/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.LocaleList
 */
package androidx.core.os;

import android.os.Build;
import android.os.LocaleList;
import androidx.core.os.LocaleListCompatWrapper;
import androidx.core.os.LocaleListInterface;
import androidx.core.os.LocaleListPlatformWrapper;
import java.util.Locale;

public final class LocaleListCompat {
    private static final LocaleListCompat sEmptyLocaleList = LocaleListCompat.create(new Locale[0]);
    private LocaleListInterface mImpl;

    private LocaleListCompat(LocaleListInterface localeListInterface) {
        this.mImpl = localeListInterface;
    }

    public static LocaleListCompat create(Locale ... arrlocale) {
        if (Build.VERSION.SDK_INT < 24) return new LocaleListCompat(new LocaleListCompatWrapper(arrlocale));
        return LocaleListCompat.wrap(new LocaleList(arrlocale));
    }

    static Locale forLanguageTagCompat(String string2) {
        Object object;
        if (string2.contains("-")) {
            object = string2.split("-", -1);
            if (((String[])object).length > 2) {
                return new Locale(object[0], object[1], object[2]);
            }
            if (((String[])object).length > 1) {
                return new Locale(object[0], object[1]);
            }
            if (((String[])object).length == 1) {
                return new Locale(object[0]);
            }
        } else {
            if (!string2.contains("_")) return new Locale(string2);
            object = string2.split("_", -1);
            if (((String[])object).length > 2) {
                return new Locale(object[0], object[1], (String)object[2]);
            }
            if (((String[])object).length > 1) {
                return new Locale(object[0], (String)object[1]);
            }
            if (((String[])object).length == 1) {
                return new Locale((String)object[0]);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Can not parse language tag: [");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("]");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static LocaleListCompat forLanguageTags(String object) {
        if (object == null) return LocaleListCompat.getEmptyLocaleList();
        if (((String)object).isEmpty()) {
            return LocaleListCompat.getEmptyLocaleList();
        }
        String[] arrstring = ((String)object).split(",", -1);
        int n = arrstring.length;
        Locale[] arrlocale = new Locale[n];
        int n2 = 0;
        while (n2 < n) {
            object = Build.VERSION.SDK_INT >= 21 ? Locale.forLanguageTag(arrstring[n2]) : LocaleListCompat.forLanguageTagCompat(arrstring[n2]);
            arrlocale[n2] = object;
            ++n2;
        }
        return LocaleListCompat.create(arrlocale);
    }

    public static LocaleListCompat getAdjustedDefault() {
        if (Build.VERSION.SDK_INT < 24) return LocaleListCompat.create(Locale.getDefault());
        return LocaleListCompat.wrap(LocaleList.getAdjustedDefault());
    }

    public static LocaleListCompat getDefault() {
        if (Build.VERSION.SDK_INT < 24) return LocaleListCompat.create(Locale.getDefault());
        return LocaleListCompat.wrap(LocaleList.getDefault());
    }

    public static LocaleListCompat getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    public static LocaleListCompat wrap(LocaleList localeList) {
        return new LocaleListCompat(new LocaleListPlatformWrapper(localeList));
    }

    @Deprecated
    public static LocaleListCompat wrap(Object object) {
        return LocaleListCompat.wrap((LocaleList)object);
    }

    public boolean equals(Object object) {
        if (!(object instanceof LocaleListCompat)) return false;
        if (!this.mImpl.equals(((LocaleListCompat)object).mImpl)) return false;
        return true;
    }

    public Locale get(int n) {
        return this.mImpl.get(n);
    }

    public Locale getFirstMatch(String[] arrstring) {
        return this.mImpl.getFirstMatch(arrstring);
    }

    public int hashCode() {
        return this.mImpl.hashCode();
    }

    public int indexOf(Locale locale) {
        return this.mImpl.indexOf(locale);
    }

    public boolean isEmpty() {
        return this.mImpl.isEmpty();
    }

    public int size() {
        return this.mImpl.size();
    }

    public String toLanguageTags() {
        return this.mImpl.toLanguageTags();
    }

    public String toString() {
        return this.mImpl.toString();
    }

    public Object unwrap() {
        return this.mImpl.getLocaleList();
    }
}

