/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package androidx.core.net;

import android.net.Uri;

public final class UriCompat {
    private UriCompat() {
    }

    public static String toSafeString(Uri object) {
        String string2;
        String string3;
        block7 : {
            CharSequence charSequence;
            block6 : {
                block8 : {
                    string3 = object.getScheme();
                    string2 = object.getSchemeSpecificPart();
                    charSequence = string2;
                    if (string3 == null) break block6;
                    if (string3.equalsIgnoreCase("tel") || string3.equalsIgnoreCase("sip") || string3.equalsIgnoreCase("sms") || string3.equalsIgnoreCase("smsto") || string3.equalsIgnoreCase("mailto") || string3.equalsIgnoreCase("nfc")) break block7;
                    if (string3.equalsIgnoreCase("http") || string3.equalsIgnoreCase("https") || string3.equalsIgnoreCase("ftp")) break block8;
                    charSequence = string2;
                    if (!string3.equalsIgnoreCase("rtsp")) break block6;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("//");
                charSequence = object.getHost();
                string2 = "";
                charSequence = charSequence != null ? object.getHost() : "";
                stringBuilder.append((String)charSequence);
                charSequence = string2;
                if (object.getPort() != -1) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(":");
                    ((StringBuilder)charSequence).append(object.getPort());
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                stringBuilder.append((String)charSequence);
                stringBuilder.append("/...");
                charSequence = stringBuilder.toString();
            }
            object = new StringBuilder(64);
            if (string3 != null) {
                ((StringBuilder)object).append(string3);
                ((StringBuilder)object).append(':');
            }
            if (charSequence == null) return ((StringBuilder)object).toString();
            ((StringBuilder)object).append((String)charSequence);
            return ((StringBuilder)object).toString();
        }
        object = new StringBuilder(64);
        ((StringBuilder)object).append(string3);
        ((StringBuilder)object).append(':');
        if (string2 == null) return ((StringBuilder)object).toString();
        int n = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (c != '-' && c != '@' && c != '.') {
                ((StringBuilder)object).append('x');
            } else {
                ((StringBuilder)object).append(c);
            }
            ++n;
        }
        return ((StringBuilder)object).toString();
    }
}

