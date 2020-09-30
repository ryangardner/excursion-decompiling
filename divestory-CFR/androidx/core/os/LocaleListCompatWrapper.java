/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.os;

import android.os.Build;
import androidx.core.os.LocaleListCompat;
import androidx.core.os.LocaleListInterface;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

final class LocaleListCompatWrapper
implements LocaleListInterface {
    private static final Locale EN_LATN;
    private static final Locale LOCALE_AR_XB;
    private static final Locale LOCALE_EN_XA;
    private static final Locale[] sEmptyList;
    private final Locale[] mList;
    private final String mStringRepresentation;

    static {
        sEmptyList = new Locale[0];
        LOCALE_EN_XA = new Locale("en", "XA");
        LOCALE_AR_XB = new Locale("ar", "XB");
        EN_LATN = LocaleListCompat.forLanguageTagCompat("en-Latn");
    }

    LocaleListCompatWrapper(Locale ... object) {
        if (((Locale[])object).length == 0) {
            this.mList = sEmptyList;
            this.mStringRepresentation = "";
            return;
        }
        Locale[] arrlocale = new Locale[((Locale[])object).length];
        HashSet<Locale> hashSet = new HashSet<Locale>();
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        do {
            if (n >= ((Locale[])object).length) {
                this.mList = arrlocale;
                this.mStringRepresentation = stringBuilder.toString();
                return;
            }
            Locale locale = object[n];
            if (locale == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("list[");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append("] is null");
                throw new NullPointerException(((StringBuilder)object).toString());
            }
            if (hashSet.contains(locale)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("list[");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append("] is a repetition");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            arrlocale[n] = locale = (Locale)locale.clone();
            LocaleListCompatWrapper.toLanguageTag(stringBuilder, locale);
            if (n < ((Object)object).length - 1) {
                stringBuilder.append(',');
            }
            hashSet.add(locale);
            ++n;
        } while (true);
    }

    private Locale computeFirstMatch(Collection<String> object, boolean bl) {
        int n = this.computeFirstMatchIndex((Collection<String>)object, bl);
        if (n != -1) return this.mList[n];
        return null;
    }

    private int computeFirstMatchIndex(Collection<String> object, boolean bl) {
        int n;
        block10 : {
            block9 : {
                Locale[] arrlocale = this.mList;
                if (arrlocale.length == 1) {
                    return 0;
                }
                if (arrlocale.length == 0) {
                    return -1;
                }
                if (!bl) break block9;
                n = this.findFirstMatchIndex(EN_LATN);
                if (n == 0) {
                    return 0;
                }
                if (n < Integer.MAX_VALUE) break block10;
            }
            n = Integer.MAX_VALUE;
        }
        object = object.iterator();
        do {
            if (!object.hasNext()) {
                if (n != Integer.MAX_VALUE) return n;
                return 0;
            }
            int n2 = this.findFirstMatchIndex(LocaleListCompat.forLanguageTagCompat((String)object.next()));
            if (n2 == 0) {
                return 0;
            }
            if (n2 >= n) continue;
            n = n2;
        } while (true);
    }

    private int findFirstMatchIndex(Locale locale) {
        Locale[] arrlocale;
        int n = 0;
        while (n < (arrlocale = this.mList).length) {
            if (LocaleListCompatWrapper.matchScore(locale, arrlocale[n]) > 0) {
                return n;
            }
            ++n;
        }
        return Integer.MAX_VALUE;
    }

    private static String getLikelyScript(Locale object) {
        if (Build.VERSION.SDK_INT < 21) return "";
        if (((String)(object = ((Locale)object).getScript())).isEmpty()) return "";
        return object;
    }

    private static boolean isPseudoLocale(Locale locale) {
        if (LOCALE_EN_XA.equals(locale)) return true;
        if (LOCALE_AR_XB.equals(locale)) return true;
        return false;
    }

    private static int matchScore(Locale object, Locale locale) {
        boolean bl = ((Locale)object).equals(locale);
        int n = 1;
        if (bl) {
            return 1;
        }
        if (!((Locale)object).getLanguage().equals(locale.getLanguage())) {
            return 0;
        }
        if (LocaleListCompatWrapper.isPseudoLocale((Locale)object)) return 0;
        if (LocaleListCompatWrapper.isPseudoLocale(locale)) {
            return 0;
        }
        String string2 = LocaleListCompatWrapper.getLikelyScript((Locale)object);
        if (!string2.isEmpty()) return (int)string2.equals(LocaleListCompatWrapper.getLikelyScript(locale));
        object = ((Locale)object).getCountry();
        int n2 = n;
        if (((String)object).isEmpty()) return n2;
        if (!((String)object).equals(locale.getCountry())) return 0;
        return n;
    }

    static void toLanguageTag(StringBuilder stringBuilder, Locale locale) {
        stringBuilder.append(locale.getLanguage());
        String string2 = locale.getCountry();
        if (string2 == null) return;
        if (string2.isEmpty()) return;
        stringBuilder.append('-');
        stringBuilder.append(locale.getCountry());
    }

    public boolean equals(Object arrlocale) {
        Locale[] arrlocale2;
        if (arrlocale == this) {
            return true;
        }
        if (!(arrlocale instanceof LocaleListCompatWrapper)) {
            return false;
        }
        arrlocale = ((LocaleListCompatWrapper)arrlocale).mList;
        if (this.mList.length != arrlocale.length) {
            return false;
        }
        int n = 0;
        while (n < (arrlocale2 = this.mList).length) {
            if (!arrlocale2[n].equals(arrlocale[n])) {
                return false;
            }
            ++n;
        }
        return true;
    }

    @Override
    public Locale get(int n) {
        if (n < 0) return null;
        Object object = this.mList;
        if (n >= ((Locale[])object).length) return null;
        return object[n];
    }

    @Override
    public Locale getFirstMatch(String[] arrstring) {
        return this.computeFirstMatch(Arrays.asList(arrstring), false);
    }

    @Override
    public Object getLocaleList() {
        return null;
    }

    public int hashCode() {
        Locale[] arrlocale;
        int n = 1;
        int n2 = 0;
        while (n2 < (arrlocale = this.mList).length) {
            n = n * 31 + arrlocale[n2].hashCode();
            ++n2;
        }
        return n;
    }

    @Override
    public int indexOf(Locale locale) {
        Locale[] arrlocale;
        int n = 0;
        while (n < (arrlocale = this.mList).length) {
            if (arrlocale[n].equals(locale)) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        if (this.mList.length != 0) return false;
        return true;
    }

    @Override
    public int size() {
        return this.mList.length;
    }

    @Override
    public String toLanguageTags() {
        return this.mStringRepresentation;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        int n = 0;
        do {
            Locale[] arrlocale;
            if (n >= (arrlocale = this.mList).length) {
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
            stringBuilder.append(arrlocale[n]);
            if (n < this.mList.length - 1) {
                stringBuilder.append(',');
            }
            ++n;
        } while (true);
    }
}

