/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicListHeaderIterator;
import org.apache.http.util.CharArrayBuffer;

public class HeaderGroup
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 2608834160639271617L;
    private final List headers = new ArrayList(16);

    public void addHeader(Header header) {
        if (header == null) {
            return;
        }
        this.headers.add(header);
    }

    public void clear() {
        this.headers.clear();
    }

    public Object clone() throws CloneNotSupportedException {
        HeaderGroup headerGroup = (HeaderGroup)super.clone();
        headerGroup.headers.clear();
        headerGroup.headers.addAll(this.headers);
        return headerGroup;
    }

    public boolean containsHeader(String string2) {
        int n = 0;
        while (n < this.headers.size()) {
            if (((Header)this.headers.get(n)).getName().equalsIgnoreCase(string2)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public HeaderGroup copy() {
        HeaderGroup headerGroup = new HeaderGroup();
        headerGroup.headers.addAll(this.headers);
        return headerGroup;
    }

    public Header[] getAllHeaders() {
        List list = this.headers;
        return list.toArray(new Header[list.size()]);
    }

    public Header getCondensedHeader(String string2) {
        Header[] arrheader = this.getHeaders(string2);
        if (arrheader.length == 0) {
            return null;
        }
        int n = arrheader.length;
        int n2 = 1;
        if (n == 1) {
            return arrheader[0];
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(128);
        charArrayBuffer.append(arrheader[0].getValue());
        while (n2 < arrheader.length) {
            charArrayBuffer.append(", ");
            charArrayBuffer.append(arrheader[n2].getValue());
            ++n2;
        }
        return new BasicHeader(string2.toLowerCase(Locale.ENGLISH), charArrayBuffer.toString());
    }

    public Header getFirstHeader(String string2) {
        int n = 0;
        while (n < this.headers.size()) {
            Header header = (Header)this.headers.get(n);
            if (header.getName().equalsIgnoreCase(string2)) {
                return header;
            }
            ++n;
        }
        return null;
    }

    public Header[] getHeaders(String string2) {
        ArrayList<Header> arrayList = new ArrayList<Header>();
        int n = 0;
        while (n < this.headers.size()) {
            Header header = (Header)this.headers.get(n);
            if (header.getName().equalsIgnoreCase(string2)) {
                arrayList.add(header);
            }
            ++n;
        }
        return arrayList.toArray(new Header[arrayList.size()]);
    }

    public Header getLastHeader(String string2) {
        int n = this.headers.size() - 1;
        while (n >= 0) {
            Header header = (Header)this.headers.get(n);
            if (header.getName().equalsIgnoreCase(string2)) {
                return header;
            }
            --n;
        }
        return null;
    }

    public HeaderIterator iterator() {
        return new BasicListHeaderIterator(this.headers, null);
    }

    public HeaderIterator iterator(String string2) {
        return new BasicListHeaderIterator(this.headers, string2);
    }

    public void removeHeader(Header header) {
        if (header == null) {
            return;
        }
        this.headers.remove(header);
    }

    public void setHeaders(Header[] arrheader) {
        this.clear();
        if (arrheader == null) {
            return;
        }
        int n = 0;
        while (n < arrheader.length) {
            this.headers.add(arrheader[n]);
            ++n;
        }
    }

    public String toString() {
        return this.headers.toString();
    }

    public void updateHeader(Header header) {
        if (header == null) {
            return;
        }
        int n = 0;
        do {
            if (n >= this.headers.size()) {
                this.headers.add(header);
                return;
            }
            if (((Header)this.headers.get(n)).getName().equalsIgnoreCase(header.getName())) {
                this.headers.set(n, header);
                return;
            }
            ++n;
        } while (true);
    }
}

