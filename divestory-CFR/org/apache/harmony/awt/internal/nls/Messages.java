/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt.internal.nls;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    private static ResourceBundle bundle;

    static {
        try {
            bundle = Messages.setLocale(Locale.getDefault(), "org.apache.harmony.awt.internal.nls.messages");
            return;
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static String format(String string2, Object[] arrobject) {
        StringBuilder stringBuilder = new StringBuilder(string2.length() + arrobject.length * 20);
        int n = arrobject.length;
        String[] arrstring = new String[n];
        int n2 = 0;
        int n3 = 0;
        do {
            if (n3 >= arrobject.length) break;
            arrstring[n3] = arrobject[n3] == null ? "<null>" : arrobject[n3].toString();
            ++n3;
        } while (true);
        n3 = n2;
        do {
            int n4;
            if ((n4 = string2.indexOf(123, n3)) < 0) {
                if (n3 >= string2.length()) return stringBuilder.toString();
                stringBuilder.append(string2.substring(n3, string2.length()));
                return stringBuilder.toString();
            }
            if (n4 != 0 && string2.charAt(n2 = n4 - 1) == '\\') {
                if (n4 != 1) {
                    stringBuilder.append(string2.substring(n3, n2));
                }
                stringBuilder.append('{');
                n3 = n4 + 1;
                continue;
            }
            if (n4 > string2.length() - 3) {
                stringBuilder.append(string2.substring(n3, string2.length()));
                n3 = string2.length();
                continue;
            }
            n2 = n4 + 1;
            byte by = (byte)Character.digit(string2.charAt(n2), 10);
            if (by >= 0 && string2.charAt(n4 + 2) == '}') {
                stringBuilder.append(string2.substring(n3, n4));
                if (by >= n) {
                    stringBuilder.append("<missing argument>");
                } else {
                    stringBuilder.append(arrstring[by]);
                }
                n3 = n4 + 3;
                continue;
            }
            stringBuilder.append(string2.substring(n3, n2));
            n3 = n2;
        } while (true);
    }

    public static String getString(String string2) {
        ResourceBundle resourceBundle = bundle;
        if (resourceBundle == null) {
            return string2;
        }
        try {
            return resourceBundle.getString(string2);
        }
        catch (MissingResourceException missingResourceException) {
            StringBuilder stringBuilder = new StringBuilder("Missing message: ");
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }
    }

    public static String getString(String string2, char c) {
        return Messages.getString(string2, new Object[]{String.valueOf(c)});
    }

    public static String getString(String string2, int n) {
        return Messages.getString(string2, new Object[]{Integer.toString(n)});
    }

    public static String getString(String string2, Object object) {
        return Messages.getString(string2, new Object[]{object});
    }

    public static String getString(String string2, Object object, Object object2) {
        return Messages.getString(string2, new Object[]{object, object2});
    }

    public static String getString(String string2, Object[] arrobject) {
        ResourceBundle resourceBundle = bundle;
        String string3 = string2;
        if (resourceBundle == null) return Messages.format(string3, arrobject);
        try {
            string3 = resourceBundle.getString(string2);
        }
        catch (MissingResourceException missingResourceException) {
            string3 = string2;
            return Messages.format(string3, arrobject);
        }
        return Messages.format(string3, arrobject);
    }

    public static ResourceBundle setLocale(Locale object, String string2) {
        try {
            PrivilegedAction<Object> privilegedAction = new PrivilegedAction<Object>((Locale)object, null){
                private final /* synthetic */ ClassLoader val$loader;
                private final /* synthetic */ Locale val$locale;
                {
                    this.val$locale = locale;
                    this.val$loader = classLoader;
                }

                @Override
                public Object run() {
                    String string2 = String.this;
                    Locale locale = this.val$locale;
                    ClassLoader classLoader = this.val$loader;
                    if (classLoader != null) {
                        return ResourceBundle.getBundle(string2, locale, classLoader);
                    }
                    classLoader = ClassLoader.getSystemClassLoader();
                    return ResourceBundle.getBundle(string2, locale, classLoader);
                }
            };
            return (ResourceBundle)AccessController.doPrivileged(privilegedAction);
        }
        catch (MissingResourceException missingResourceException) {
            return null;
        }
    }

}

