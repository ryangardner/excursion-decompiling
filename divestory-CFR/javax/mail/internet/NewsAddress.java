/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Address;
import javax.mail.internet.AddressException;

public class NewsAddress
extends Address {
    private static final long serialVersionUID = -4203797299824684143L;
    protected String host;
    protected String newsgroup;

    public NewsAddress() {
    }

    public NewsAddress(String string2) {
        this(string2, null);
    }

    public NewsAddress(String string2, String string3) {
        this.newsgroup = string2;
        this.host = string3;
    }

    public static NewsAddress[] parse(String object) throws AddressException {
        Object[] arrobject = new StringTokenizer((String)object, ",");
        object = new Vector();
        do {
            if (!arrobject.hasMoreTokens()) {
                int n = ((Vector)object).size();
                arrobject = new NewsAddress[n];
                if (n <= 0) return arrobject;
                ((Vector)object).copyInto(arrobject);
                return arrobject;
            }
            ((Vector)object).addElement(new NewsAddress(arrobject.nextToken()));
        } while (true);
    }

    public static String toString(Address[] arraddress) {
        if (arraddress == null) return null;
        if (arraddress.length == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(((NewsAddress)arraddress[0]).toString());
        int n = 1;
        while (n < arraddress.length) {
            stringBuffer.append(",");
            stringBuffer.append(((NewsAddress)arraddress[n]).toString());
            ++n;
        }
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NewsAddress)) {
            return false;
        }
        Object object2 = (NewsAddress)object;
        if (!this.newsgroup.equals(((NewsAddress)object2).newsgroup)) return false;
        if (this.host == null) {
            if (((NewsAddress)object2).host == null) return true;
        }
        if ((object = this.host) == null) return false;
        object2 = ((NewsAddress)object2).host;
        if (object2 == null) return false;
        if (!((String)object).equalsIgnoreCase((String)object2)) return false;
        return true;
    }

    public String getHost() {
        return this.host;
    }

    public String getNewsgroup() {
        return this.newsgroup;
    }

    @Override
    public String getType() {
        return "news";
    }

    public int hashCode() {
        String string2 = this.newsgroup;
        int n = 0;
        if (string2 != null) {
            n = 0 + string2.hashCode();
        }
        string2 = this.host;
        int n2 = n;
        if (string2 == null) return n2;
        return n + string2.toLowerCase(Locale.ENGLISH).hashCode();
    }

    public void setHost(String string2) {
        this.host = string2;
    }

    public void setNewsgroup(String string2) {
        this.newsgroup = string2;
    }

    @Override
    public String toString() {
        return this.newsgroup;
    }
}

