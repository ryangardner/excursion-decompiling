/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.message.HeaderValueFormatter;
import org.apache.http.util.CharArrayBuffer;

public class BasicHeaderValueFormatter
implements HeaderValueFormatter {
    public static final BasicHeaderValueFormatter DEFAULT = new BasicHeaderValueFormatter();
    public static final String SEPARATORS = " ;,:@()<>\\\"/[]?={}\t";
    public static final String UNSAFE_CHARS = "\"\\";

    public static final String formatElements(HeaderElement[] arrheaderElement, boolean bl, HeaderValueFormatter headerValueFormatter) {
        HeaderValueFormatter headerValueFormatter2 = headerValueFormatter;
        if (headerValueFormatter != null) return headerValueFormatter2.formatElements(null, arrheaderElement, bl).toString();
        headerValueFormatter2 = DEFAULT;
        return headerValueFormatter2.formatElements(null, arrheaderElement, bl).toString();
    }

    public static final String formatHeaderElement(HeaderElement headerElement, boolean bl, HeaderValueFormatter headerValueFormatter) {
        HeaderValueFormatter headerValueFormatter2 = headerValueFormatter;
        if (headerValueFormatter != null) return headerValueFormatter2.formatHeaderElement(null, headerElement, bl).toString();
        headerValueFormatter2 = DEFAULT;
        return headerValueFormatter2.formatHeaderElement(null, headerElement, bl).toString();
    }

    public static final String formatNameValuePair(NameValuePair nameValuePair, boolean bl, HeaderValueFormatter headerValueFormatter) {
        HeaderValueFormatter headerValueFormatter2 = headerValueFormatter;
        if (headerValueFormatter != null) return headerValueFormatter2.formatNameValuePair(null, nameValuePair, bl).toString();
        headerValueFormatter2 = DEFAULT;
        return headerValueFormatter2.formatNameValuePair(null, nameValuePair, bl).toString();
    }

    public static final String formatParameters(NameValuePair[] arrnameValuePair, boolean bl, HeaderValueFormatter headerValueFormatter) {
        HeaderValueFormatter headerValueFormatter2 = headerValueFormatter;
        if (headerValueFormatter != null) return headerValueFormatter2.formatParameters(null, arrnameValuePair, bl).toString();
        headerValueFormatter2 = DEFAULT;
        return headerValueFormatter2.formatParameters(null, arrnameValuePair, bl).toString();
    }

    protected void doFormatValue(CharArrayBuffer charArrayBuffer, String string2, boolean bl) {
        int n;
        int n2 = 0;
        boolean bl2 = bl;
        if (!bl) {
            n = 0;
            do {
                bl2 = bl;
                if (n >= string2.length()) break;
                bl2 = bl;
                if (bl) break;
                bl = this.isSeparator(string2.charAt(n));
                ++n;
            } while (true);
        }
        n = n2;
        if (bl2) {
            charArrayBuffer.append('\"');
            n = n2;
        }
        do {
            if (n >= string2.length()) {
                if (!bl2) return;
                charArrayBuffer.append('\"');
                return;
            }
            char c = string2.charAt(n);
            if (this.isUnsafe(c)) {
                charArrayBuffer.append('\\');
            }
            charArrayBuffer.append(c);
            ++n;
        } while (true);
    }

    protected int estimateElementsLen(HeaderElement[] arrheaderElement) {
        int n = 0;
        if (arrheaderElement == null) return 0;
        if (arrheaderElement.length < 1) {
            return 0;
        }
        int n2 = (arrheaderElement.length - 1) * 2;
        while (n < arrheaderElement.length) {
            n2 += this.estimateHeaderElementLen(arrheaderElement[n]);
            ++n;
        }
        return n2;
    }

    protected int estimateHeaderElementLen(HeaderElement headerElement) {
        int n = 0;
        if (headerElement == null) {
            return 0;
        }
        int n2 = headerElement.getName().length();
        String string2 = headerElement.getValue();
        int n3 = n2;
        if (string2 != null) {
            n3 = n2 + (string2.length() + 3);
        }
        int n4 = headerElement.getParameterCount();
        n2 = n3;
        if (n4 <= 0) return n2;
        do {
            n2 = n3;
            if (n >= n4) return n2;
            n3 += this.estimateNameValuePairLen(headerElement.getParameter(n)) + 2;
            ++n;
        } while (true);
    }

    protected int estimateNameValuePairLen(NameValuePair object) {
        if (object == null) {
            return 0;
        }
        int n = object.getName().length();
        object = object.getValue();
        int n2 = n;
        if (object == null) return n2;
        return n + (((String)object).length() + 3);
    }

    protected int estimateParametersLen(NameValuePair[] arrnameValuePair) {
        int n = 0;
        if (arrnameValuePair == null) return 0;
        if (arrnameValuePair.length < 1) {
            return 0;
        }
        int n2 = (arrnameValuePair.length - 1) * 2;
        while (n < arrnameValuePair.length) {
            n2 += this.estimateNameValuePairLen(arrnameValuePair[n]);
            ++n;
        }
        return n2;
    }

    @Override
    public CharArrayBuffer formatElements(CharArrayBuffer charArrayBuffer, HeaderElement[] arrheaderElement, boolean bl) {
        if (arrheaderElement == null) throw new IllegalArgumentException("Header element array must not be null.");
        int n = this.estimateElementsLen(arrheaderElement);
        if (charArrayBuffer == null) {
            charArrayBuffer = new CharArrayBuffer(n);
        } else {
            charArrayBuffer.ensureCapacity(n);
        }
        n = 0;
        while (n < arrheaderElement.length) {
            if (n > 0) {
                charArrayBuffer.append(", ");
            }
            this.formatHeaderElement(charArrayBuffer, arrheaderElement[n], bl);
            ++n;
        }
        return charArrayBuffer;
    }

    @Override
    public CharArrayBuffer formatHeaderElement(CharArrayBuffer charArrayBuffer, HeaderElement headerElement, boolean bl) {
        int n;
        if (headerElement == null) throw new IllegalArgumentException("Header element must not be null.");
        int n2 = this.estimateHeaderElementLen(headerElement);
        if (charArrayBuffer == null) {
            charArrayBuffer = new CharArrayBuffer(n2);
        } else {
            charArrayBuffer.ensureCapacity(n2);
        }
        charArrayBuffer.append(headerElement.getName());
        String string2 = headerElement.getValue();
        if (string2 != null) {
            charArrayBuffer.append('=');
            this.doFormatValue(charArrayBuffer, string2, bl);
        }
        if ((n = headerElement.getParameterCount()) <= 0) return charArrayBuffer;
        n2 = 0;
        while (n2 < n) {
            charArrayBuffer.append("; ");
            this.formatNameValuePair(charArrayBuffer, headerElement.getParameter(n2), bl);
            ++n2;
        }
        return charArrayBuffer;
    }

    @Override
    public CharArrayBuffer formatNameValuePair(CharArrayBuffer charArrayBuffer, NameValuePair object, boolean bl) {
        if (object == null) throw new IllegalArgumentException("NameValuePair must not be null.");
        int n = this.estimateNameValuePairLen((NameValuePair)object);
        if (charArrayBuffer == null) {
            charArrayBuffer = new CharArrayBuffer(n);
        } else {
            charArrayBuffer.ensureCapacity(n);
        }
        charArrayBuffer.append(object.getName());
        object = object.getValue();
        if (object == null) return charArrayBuffer;
        charArrayBuffer.append('=');
        this.doFormatValue(charArrayBuffer, (String)object, bl);
        return charArrayBuffer;
    }

    @Override
    public CharArrayBuffer formatParameters(CharArrayBuffer charArrayBuffer, NameValuePair[] arrnameValuePair, boolean bl) {
        if (arrnameValuePair == null) throw new IllegalArgumentException("Parameters must not be null.");
        int n = this.estimateParametersLen(arrnameValuePair);
        if (charArrayBuffer == null) {
            charArrayBuffer = new CharArrayBuffer(n);
        } else {
            charArrayBuffer.ensureCapacity(n);
        }
        n = 0;
        while (n < arrnameValuePair.length) {
            if (n > 0) {
                charArrayBuffer.append("; ");
            }
            this.formatNameValuePair(charArrayBuffer, arrnameValuePair[n], bl);
            ++n;
        }
        return charArrayBuffer;
    }

    protected boolean isSeparator(char c) {
        if (SEPARATORS.indexOf(c) < 0) return false;
        return true;
    }

    protected boolean isUnsafe(char c) {
        if (UNSAFE_CHARS.indexOf(c) < 0) return false;
        return true;
    }
}

