/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.cookie;

import java.io.Serializable;
import java.util.Comparator;
import org.apache.http.cookie.Cookie;

public class CookieIdentityComparator
implements Serializable,
Comparator<Cookie> {
    private static final long serialVersionUID = 4466565437490631532L;

    @Override
    public int compare(Cookie object, Cookie object2) {
        CharSequence charSequence;
        CharSequence charSequence2;
        int n;
        int n2 = n = object.getName().compareTo(object2.getName());
        if (n == 0) {
            String string2 = object.getDomain();
            charSequence2 = "";
            if (string2 == null) {
                charSequence = "";
            } else {
                charSequence = string2;
                if (string2.indexOf(46) == -1) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append(".local");
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            }
            string2 = object2.getDomain();
            if (string2 != null) {
                if (string2.indexOf(46) == -1) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append(string2);
                    ((StringBuilder)charSequence2).append(".local");
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                } else {
                    charSequence2 = string2;
                }
            }
            n2 = ((String)charSequence).compareToIgnoreCase((String)charSequence2);
        }
        n = n2;
        if (n2 != 0) return n;
        charSequence2 = object.getPath();
        charSequence = "/";
        object = charSequence2;
        if (charSequence2 == null) {
            object = "/";
        }
        if ((object2 = object2.getPath()) != null) return ((String)object).compareTo((String)object2);
        object2 = charSequence;
        return ((String)object).compareTo((String)object2);
    }
}

