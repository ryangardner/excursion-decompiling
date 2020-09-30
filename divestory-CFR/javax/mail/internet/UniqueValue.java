/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;

class UniqueValue {
    private static int id;

    UniqueValue() {
    }

    public static String getUniqueBoundaryValue() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("----=_Part_");
        stringBuffer.append(UniqueValue.getUniqueId());
        stringBuffer.append("_");
        stringBuffer.append(stringBuffer.hashCode());
        stringBuffer.append('.');
        stringBuffer.append(System.currentTimeMillis());
        return stringBuffer.toString();
    }

    private static int getUniqueId() {
        synchronized (UniqueValue.class) {
            int n = id;
            id = n + 1;
            return n;
        }
    }

    public static String getUniqueMessageIDValue(Session object) {
        object = (object = InternetAddress.getLocalAddress((Session)object)) != null ? ((InternetAddress)object).getAddress() : "javamailuser@localhost";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(stringBuffer.hashCode());
        stringBuffer.append('.');
        stringBuffer.append(UniqueValue.getUniqueId());
        stringBuffer.append('.');
        stringBuffer.append(System.currentTimeMillis());
        stringBuffer.append('.');
        stringBuffer.append("JavaMail.");
        stringBuffer.append((String)object);
        return stringBuffer.toString();
    }
}

