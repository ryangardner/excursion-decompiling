/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package androidx.core.text;

import android.os.Build;
import android.text.TextUtils;
import androidx.core.text.ICUCompat;
import java.util.Locale;

public final class TextUtilsCompat {
    private static final String ARAB_SCRIPT_SUBTAG = "Arab";
    private static final String HEBR_SCRIPT_SUBTAG = "Hebr";
    private static final Locale ROOT = new Locale("", "");

    private TextUtilsCompat() {
    }

    private static int getLayoutDirectionFromFirstChar(Locale locale) {
        byte by = Character.getDirectionality(locale.getDisplayName(locale).charAt(0));
        if (by == 1) return 1;
        if (by == 2) return 1;
        return 0;
    }

    public static int getLayoutDirectionFromLocale(Locale locale) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.getLayoutDirectionFromLocale((Locale)locale);
        }
        if (locale == null) return 0;
        if (locale.equals(ROOT)) return 0;
        String string2 = ICUCompat.maximizeAndGetScript(locale);
        if (string2 == null) {
            return TextUtilsCompat.getLayoutDirectionFromFirstChar(locale);
        }
        if (string2.equalsIgnoreCase(ARAB_SCRIPT_SUBTAG)) return 1;
        if (!string2.equalsIgnoreCase(HEBR_SCRIPT_SUBTAG)) return 0;
        return 1;
    }

    public static String htmlEncode(String string2) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.htmlEncode((String)string2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (c != '\"') {
                if (c != '<') {
                    if (c != '>') {
                        if (c != '&') {
                            if (c != '\'') {
                                stringBuilder.append(c);
                            } else {
                                stringBuilder.append("&#39;");
                            }
                        } else {
                            stringBuilder.append("&amp;");
                        }
                    } else {
                        stringBuilder.append("&gt;");
                    }
                } else {
                    stringBuilder.append("&lt;");
                }
            } else {
                stringBuilder.append("&quot;");
            }
            ++n;
        }
        return stringBuilder.toString();
    }
}

