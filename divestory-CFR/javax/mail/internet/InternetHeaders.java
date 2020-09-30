/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import com.sun.mail.util.LineInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.mail.Header;
import javax.mail.MessagingException;

public class InternetHeaders {
    protected List headers;

    public InternetHeaders() {
        ArrayList<InternetHeader> arrayList;
        this.headers = arrayList = new ArrayList<InternetHeader>(40);
        arrayList.add(new InternetHeader("Return-Path", null));
        this.headers.add(new InternetHeader("Received", null));
        this.headers.add(new InternetHeader("Resent-Date", null));
        this.headers.add(new InternetHeader("Resent-From", null));
        this.headers.add(new InternetHeader("Resent-Sender", null));
        this.headers.add(new InternetHeader("Resent-To", null));
        this.headers.add(new InternetHeader("Resent-Cc", null));
        this.headers.add(new InternetHeader("Resent-Bcc", null));
        this.headers.add(new InternetHeader("Resent-Message-Id", null));
        this.headers.add(new InternetHeader("Date", null));
        this.headers.add(new InternetHeader("From", null));
        this.headers.add(new InternetHeader("Sender", null));
        this.headers.add(new InternetHeader("Reply-To", null));
        this.headers.add(new InternetHeader("To", null));
        this.headers.add(new InternetHeader("Cc", null));
        this.headers.add(new InternetHeader("Bcc", null));
        this.headers.add(new InternetHeader("Message-Id", null));
        this.headers.add(new InternetHeader("In-Reply-To", null));
        this.headers.add(new InternetHeader("References", null));
        this.headers.add(new InternetHeader("Subject", null));
        this.headers.add(new InternetHeader("Comments", null));
        this.headers.add(new InternetHeader("Keywords", null));
        this.headers.add(new InternetHeader("Errors-To", null));
        this.headers.add(new InternetHeader("MIME-Version", null));
        this.headers.add(new InternetHeader("Content-Type", null));
        this.headers.add(new InternetHeader("Content-Transfer-Encoding", null));
        this.headers.add(new InternetHeader("Content-MD5", null));
        this.headers.add(new InternetHeader(":", null));
        this.headers.add(new InternetHeader("Content-Length", null));
        this.headers.add(new InternetHeader("Status", null));
    }

    public InternetHeaders(InputStream inputStream2) throws MessagingException {
        this.headers = new ArrayList(40);
        this.load(inputStream2);
    }

    public void addHeader(String string2, String string3) {
        int n = this.headers.size();
        boolean bl = string2.equalsIgnoreCase("Received") || string2.equalsIgnoreCase("Return-Path");
        if (bl) {
            n = 0;
        }
        int n2 = this.headers.size() - 1;
        do {
            if (n2 < 0) {
                this.headers.add(n, new InternetHeader(string2, string3));
                return;
            }
            InternetHeader internetHeader = (InternetHeader)this.headers.get(n2);
            if (string2.equalsIgnoreCase(internetHeader.getName())) {
                if (!bl) {
                    this.headers.add(n2 + 1, new InternetHeader(string2, string3));
                    return;
                }
                n = n2;
            }
            if (internetHeader.getName().equals(":")) {
                n = n2;
            }
            --n2;
        } while (true);
    }

    public void addHeaderLine(String string2) {
        try {
            char c = string2.charAt(0);
            if (c != ' ' && c != '\t') {
                List list = this.headers;
                InternetHeader internetHeader = new InternetHeader(string2);
                list.add(internetHeader);
                return;
            }
            InternetHeader internetHeader = (InternetHeader)this.headers.get(this.headers.size() - 1);
            String string3 = internetHeader.line;
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string3));
            stringBuilder.append("\r\n");
            stringBuilder.append(string2);
            internetHeader.line = stringBuilder.toString();
            return;
        }
        catch (StringIndexOutOfBoundsException | NoSuchElementException runtimeException) {
            return;
        }
    }

    public Enumeration getAllHeaderLines() {
        return this.getNonMatchingHeaderLines(null);
    }

    public Enumeration getAllHeaders() {
        return new matchEnum(this.headers, null, false, false);
    }

    public String getHeader(String arrstring, String string2) {
        if ((arrstring = this.getHeader((String)arrstring)) == null) {
            return null;
        }
        int n = arrstring.length;
        int n2 = 1;
        if (n == 1) return arrstring[0];
        if (string2 == null) {
            return arrstring[0];
        }
        StringBuffer stringBuffer = new StringBuffer(arrstring[0]);
        while (n2 < arrstring.length) {
            stringBuffer.append(string2);
            stringBuffer.append(arrstring[n2]);
            ++n2;
        }
        return stringBuffer.toString();
    }

    public String[] getHeader(String string2) {
        Iterator iterator2 = this.headers.iterator();
        ArrayList<String> arrayList = new ArrayList<String>();
        do {
            if (!iterator2.hasNext()) {
                if (arrayList.size() != 0) return arrayList.toArray(new String[arrayList.size()]);
                return null;
            }
            InternetHeader internetHeader = (InternetHeader)iterator2.next();
            if (!string2.equalsIgnoreCase(internetHeader.getName()) || internetHeader.line == null) continue;
            arrayList.add(internetHeader.getValue());
        } while (true);
    }

    public Enumeration getMatchingHeaderLines(String[] arrstring) {
        return new matchEnum(this.headers, arrstring, true, true);
    }

    public Enumeration getMatchingHeaders(String[] arrstring) {
        return new matchEnum(this.headers, arrstring, true, false);
    }

    public Enumeration getNonMatchingHeaderLines(String[] arrstring) {
        return new matchEnum(this.headers, arrstring, false, true);
    }

    public Enumeration getNonMatchingHeaders(String[] arrstring) {
        return new matchEnum(this.headers, arrstring, false, false);
    }

    public void load(InputStream object) throws MessagingException {
        LineInputStream lineInputStream = new LineInputStream((InputStream)object);
        StringBuffer stringBuffer = new StringBuffer();
        Object object2 = null;
        try {
            int n;
            do {
                String string2;
                if ((string2 = lineInputStream.readLine()) != null && (string2.startsWith(" ") || string2.startsWith("\t"))) {
                    object = object2;
                    if (object2 != null) {
                        stringBuffer.append((String)object2);
                        object = null;
                    }
                    stringBuffer.append("\r\n");
                    stringBuffer.append(string2);
                } else {
                    if (object2 != null) {
                        this.addHeaderLine((String)object2);
                    } else if (stringBuffer.length() > 0) {
                        this.addHeaderLine(stringBuffer.toString());
                        stringBuffer.setLength(0);
                    }
                    object = string2;
                }
                if (string2 == null) return;
                n = string2.length();
                object2 = object;
            } while (n > 0);
            return;
        }
        catch (IOException iOException) {
            throw new MessagingException("Error in input stream", iOException);
        }
    }

    public void removeHeader(String string2) {
        int n = 0;
        while (n < this.headers.size()) {
            InternetHeader internetHeader = (InternetHeader)this.headers.get(n);
            if (string2.equalsIgnoreCase(internetHeader.getName())) {
                internetHeader.line = null;
            }
            ++n;
        }
        return;
    }

    public void setHeader(String string2, String string3) {
        int n = 0;
        int n2 = 0;
        do {
            if (n >= this.headers.size()) {
                if (n2 != 0) return;
                this.addHeader(string2, string3);
                return;
            }
            InternetHeader internetHeader = (InternetHeader)this.headers.get(n);
            int n3 = n;
            int n4 = n2;
            if (string2.equalsIgnoreCase(internetHeader.getName())) {
                if (n2 == 0) {
                    StringBuilder stringBuilder;
                    if (internetHeader.line != null && (n2 = internetHeader.line.indexOf(58)) >= 0) {
                        stringBuilder = new StringBuilder(String.valueOf(internetHeader.line.substring(0, n2 + 1)));
                        stringBuilder.append(" ");
                        stringBuilder.append(string3);
                        internetHeader.line = stringBuilder.toString();
                    } else {
                        stringBuilder = new StringBuilder(String.valueOf(string2));
                        stringBuilder.append(": ");
                        stringBuilder.append(string3);
                        internetHeader.line = stringBuilder.toString();
                    }
                    n4 = 1;
                    n3 = n;
                } else {
                    this.headers.remove(n);
                    n3 = n - 1;
                    n4 = n2;
                }
            }
            n = n3 + 1;
            n2 = n4;
        } while (true);
    }

    protected static final class InternetHeader
    extends Header {
        String line;

        public InternetHeader(String string2) {
            super("", "");
            int n = string2.indexOf(58);
            this.name = n < 0 ? string2.trim() : string2.substring(0, n).trim();
            this.line = string2;
        }

        public InternetHeader(String charSequence, String string2) {
            super((String)charSequence, "");
            if (string2 != null) {
                charSequence = new StringBuilder(String.valueOf(charSequence));
                ((StringBuilder)charSequence).append(": ");
                ((StringBuilder)charSequence).append(string2);
                this.line = ((StringBuilder)charSequence).toString();
                return;
            }
            this.line = null;
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public String getValue() {
            var2_2 = var1_1 = this.line.indexOf(58);
            if (var1_1 < 0) {
                return this.line;
            }
            do lbl-1000: // 5 sources:
            {
                if ((var1_1 = var2_2 + 1) >= this.line.length()) {
                    return this.line.substring(var1_1);
                }
                var3_3 = this.line.charAt(var1_1);
                var2_2 = var1_1;
                if (var3_3 == ' ') ** GOTO lbl-1000
                var2_2 = var1_1;
                if (var3_3 == '\t') ** GOTO lbl-1000
                var2_2 = var1_1;
                if (var3_3 == '\r') ** GOTO lbl-1000
                var2_2 = var1_1;
            } while (var3_3 == '\n');
            return this.line.substring(var1_1);
        }
    }

    static class matchEnum
    implements Enumeration {
        private Iterator e;
        private boolean match;
        private String[] names;
        private InternetHeader next_header;
        private boolean want_line;

        matchEnum(List list, String[] arrstring, boolean bl, boolean bl2) {
            this.e = list.iterator();
            this.names = arrstring;
            this.match = bl;
            this.want_line = bl2;
            this.next_header = null;
        }

        /*
         * Unable to fully structure code
         */
        private InternetHeader nextMatch() {
            block0 : do lbl-1000: // 4 sources:
            {
                var1_1 = this.e.hasNext();
                var2_2 = null;
                if (!var1_1) {
                    return null;
                }
                var3_3 = (InternetHeader)this.e.next();
                if (var3_3.line == null) ** GOTO lbl-1000
                if (this.names == null) {
                    if (this.match == false) return var3_3;
                    return var2_2;
                }
                var4_4 = 0;
                do {
                    block5 : {
                        if (var4_4 < (var2_2 = this.names).length) break block5;
                        if (this.match) ** GOTO lbl-1000
                        return var3_3;
                    }
                    if (var2_2[var4_4].equalsIgnoreCase(var3_3.getName())) {
                        if (!this.match) continue block0;
                        return var3_3;
                    }
                    ++var4_4;
                } while (true);
                break;
            } while (true);
        }

        @Override
        public boolean hasMoreElements() {
            if (this.next_header == null) {
                this.next_header = this.nextMatch();
            }
            if (this.next_header == null) return false;
            return true;
        }

        public Object nextElement() {
            InternetHeader internetHeader;
            if (this.next_header == null) {
                this.next_header = this.nextMatch();
            }
            if ((internetHeader = this.next_header) == null) throw new NoSuchElementException("No more headers");
            this.next_header = null;
            if (!this.want_line) return new Header(internetHeader.getName(), internetHeader.getValue());
            return internetHeader.line;
        }
    }

}

