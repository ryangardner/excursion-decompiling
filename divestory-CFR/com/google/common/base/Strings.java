/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Strings {
    private Strings() {
    }

    public static String commonPrefix(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        int n2 = Math.min(charSequence.length(), charSequence2.length());
        for (n = 0; n < n2 && charSequence.charAt(n) == charSequence2.charAt(n); ++n) {
        }
        int n3 = n - 1;
        if (!Strings.validSurrogatePairAt(charSequence, n3)) {
            n2 = n;
            if (!Strings.validSurrogatePairAt(charSequence2, n3)) return charSequence.subSequence(0, n2).toString();
        }
        n2 = n - 1;
        return charSequence.subSequence(0, n2).toString();
    }

    public static String commonSuffix(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        int n2 = Math.min(charSequence.length(), charSequence2.length());
        for (n = 0; n < n2 && charSequence.charAt(charSequence.length() - n - 1) == charSequence2.charAt(charSequence2.length() - n - 1); ++n) {
        }
        if (!Strings.validSurrogatePairAt(charSequence, charSequence.length() - n - 1)) {
            n2 = n;
            if (!Strings.validSurrogatePairAt(charSequence2, charSequence2.length() - n - 1)) return charSequence.subSequence(charSequence.length() - n2, charSequence.length()).toString();
        }
        n2 = n - 1;
        return charSequence.subSequence(charSequence.length() - n2, charSequence.length()).toString();
    }

    @NullableDecl
    public static String emptyToNull(@NullableDecl String string2) {
        return Platform.emptyToNull(string2);
    }

    public static boolean isNullOrEmpty(@NullableDecl String string2) {
        return Platform.stringIsNullOrEmpty(string2);
    }

    public static String lenientFormat(@NullableDecl String arrobject, @NullableDecl Object ... object) {
        int n;
        String string2 = String.valueOf(arrobject);
        int n2 = 0;
        if (object == null) {
            arrobject = new Object[]{"(Object[])null"};
        } else {
            n = 0;
            do {
                arrobject = object;
                if (n >= ((Object[])object).length) break;
                object[n] = Strings.lenientToString(object[n]);
                ++n;
            } while (true);
        }
        object = new StringBuilder(string2.length() + arrobject.length * 16);
        int n3 = 0;
        n = n2;
        while (n < arrobject.length && (n2 = string2.indexOf("%s", n3)) != -1) {
            ((StringBuilder)object).append(string2, n3, n2);
            ((StringBuilder)object).append(arrobject[n]);
            n3 = n2 + 2;
            ++n;
        }
        ((StringBuilder)object).append(string2, n3, string2.length());
        if (n >= arrobject.length) return ((StringBuilder)object).toString();
        ((StringBuilder)object).append(" [");
        n3 = n + 1;
        ((StringBuilder)object).append(arrobject[n]);
        n = n3;
        do {
            if (n >= arrobject.length) {
                ((StringBuilder)object).append(']');
                return ((StringBuilder)object).toString();
            }
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(arrobject[n]);
            ++n;
        } while (true);
    }

    private static String lenientToString(@NullableDecl Object object) {
        try {
            return String.valueOf(object);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object.getClass().getName());
            stringBuilder.append('@');
            stringBuilder.append(Integer.toHexString(System.identityHashCode(object)));
            object = stringBuilder.toString();
            Logger logger = Logger.getLogger("com.google.common.base.Strings");
            Level level = Level.WARNING;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Exception during lenientFormat for ");
            stringBuilder.append((String)object);
            logger.log(level, stringBuilder.toString(), exception);
            stringBuilder = new StringBuilder();
            stringBuilder.append("<");
            stringBuilder.append((String)object);
            stringBuilder.append(" threw ");
            stringBuilder.append(exception.getClass().getName());
            stringBuilder.append(">");
            return stringBuilder.toString();
        }
    }

    public static String nullToEmpty(@NullableDecl String string2) {
        return Platform.nullToEmpty(string2);
    }

    public static String padEnd(String string2, int n, char c) {
        Preconditions.checkNotNull(string2);
        if (string2.length() >= n) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        stringBuilder.append(string2);
        int n2 = string2.length();
        while (n2 < n) {
            stringBuilder.append(c);
            ++n2;
        }
        return stringBuilder.toString();
    }

    public static String padStart(String string2, int n, char c) {
        Preconditions.checkNotNull(string2);
        if (string2.length() >= n) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        int n2 = string2.length();
        do {
            if (n2 >= n) {
                stringBuilder.append(string2);
                return stringBuilder.toString();
            }
            stringBuilder.append(c);
            ++n2;
        } while (true);
    }

    public static String repeat(String charSequence, int n) {
        Preconditions.checkNotNull(charSequence);
        boolean bl = true;
        if (n <= 1) {
            if (n < 0) {
                bl = false;
            }
            Preconditions.checkArgument(bl, "invalid count: %s", n);
            if (n != 0) return charSequence;
            return "";
        }
        int n2 = ((String)charSequence).length();
        long l = (long)n2 * (long)n;
        int n3 = (int)l;
        if ((long)n3 != l) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Required array size too large: ");
            ((StringBuilder)charSequence).append(l);
            throw new ArrayIndexOutOfBoundsException(((StringBuilder)charSequence).toString());
        }
        char[] arrc = new char[n3];
        ((String)charSequence).getChars(0, n2, arrc, 0);
        n = n2;
        do {
            if (n >= (n2 = n3 - n)) {
                System.arraycopy(arrc, 0, arrc, n, n2);
                return new String(arrc);
            }
            System.arraycopy(arrc, 0, arrc, n, n);
            n <<= 1;
        } while (true);
    }

    static boolean validSurrogatePairAt(CharSequence charSequence, int n) {
        boolean bl = true;
        if (n < 0) return false;
        if (n > charSequence.length() - 2) return false;
        if (!Character.isHighSurrogate(charSequence.charAt(n))) return false;
        if (!Character.isLowSurrogate(charSequence.charAt(n + 1))) return false;
        return bl;
    }
}

