/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.core.content;

import java.util.ArrayList;

public final class MimeTypeFilter {
    private MimeTypeFilter() {
    }

    public static String matches(String string2, String[] arrstring) {
        if (string2 == null) {
            return null;
        }
        String[] arrstring2 = string2.split("/");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            string2 = arrstring[n2];
            if (MimeTypeFilter.mimeTypeAgainstFilter(arrstring2, string2.split("/"))) {
                return string2;
            }
            ++n2;
        }
        return null;
    }

    public static String matches(String[] arrstring, String arrstring2) {
        if (arrstring == null) {
            return null;
        }
        arrstring2 = arrstring2.split("/");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String string2 = arrstring[n2];
            if (MimeTypeFilter.mimeTypeAgainstFilter(string2.split("/"), arrstring2)) {
                return string2;
            }
            ++n2;
        }
        return null;
    }

    public static boolean matches(String string2, String string3) {
        if (string2 != null) return MimeTypeFilter.mimeTypeAgainstFilter(string2.split("/"), string3.split("/"));
        return false;
    }

    public static String[] matchesMany(String[] arrstring, String arrstring2) {
        int n = 0;
        if (arrstring == null) {
            return new String[0];
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        arrstring2 = arrstring2.split("/");
        int n2 = arrstring.length;
        while (n < n2) {
            String string2 = arrstring[n];
            if (MimeTypeFilter.mimeTypeAgainstFilter(string2.split("/"), arrstring2)) {
                arrayList.add(string2);
            }
            ++n;
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    private static boolean mimeTypeAgainstFilter(String[] arrstring, String[] arrstring2) {
        if (arrstring2.length != 2) throw new IllegalArgumentException("Ill-formatted MIME type filter. Must be type/subtype.");
        if (arrstring2[0].isEmpty()) throw new IllegalArgumentException("Ill-formatted MIME type filter. Type or subtype empty.");
        if (arrstring2[1].isEmpty()) throw new IllegalArgumentException("Ill-formatted MIME type filter. Type or subtype empty.");
        if (arrstring.length != 2) {
            return false;
        }
        if (!"*".equals(arrstring2[0]) && !arrstring2[0].equals(arrstring[0])) {
            return false;
        }
        if ("*".equals(arrstring2[1])) return true;
        if (arrstring2[1].equals(arrstring[1])) return true;
        return false;
    }
}

