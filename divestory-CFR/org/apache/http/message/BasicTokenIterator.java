/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.ParseException;
import org.apache.http.TokenIterator;

public class BasicTokenIterator
implements TokenIterator {
    public static final String HTTP_SEPARATORS = " ,;=()<>@:\\\"/[]?{}\t";
    protected String currentHeader;
    protected String currentToken;
    protected final HeaderIterator headerIt;
    protected int searchPos;

    public BasicTokenIterator(HeaderIterator headerIterator) {
        if (headerIterator == null) throw new IllegalArgumentException("Header iterator must not be null.");
        this.headerIt = headerIterator;
        this.searchPos = this.findNext(-1);
    }

    protected String createToken(String string2, int n, int n2) {
        return string2.substring(n, n2);
    }

    protected int findNext(int n) throws ParseException {
        if (n < 0) {
            if (!this.headerIt.hasNext()) {
                return -1;
            }
            this.currentHeader = this.headerIt.nextHeader().getValue();
            n = 0;
        } else {
            n = this.findTokenSeparator(n);
        }
        n = this.findTokenStart(n);
        if (n < 0) {
            this.currentToken = null;
            return -1;
        }
        int n2 = this.findTokenEnd(n);
        this.currentToken = this.createToken(this.currentHeader, n, n2);
        return n2;
    }

    protected int findTokenEnd(int n) {
        if (n < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Token start position must not be negative: ");
            stringBuffer.append(n);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        int n2 = this.currentHeader.length();
        while (++n < n2) {
            if (!this.isTokenChar(this.currentHeader.charAt(n))) return n;
        }
        return n;
    }

    protected int findTokenSeparator(int n) {
        char c;
        block5 : {
            if (n < 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Search position must not be negative: ");
                stringBuffer.append(n);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            boolean bl = false;
            int n2 = this.currentHeader.length();
            while (!bl) {
                if (n >= n2) return n;
                c = this.currentHeader.charAt(n);
                if (this.isTokenSeparator(c)) {
                    bl = true;
                    continue;
                }
                if (this.isWhitespace(c)) {
                    ++n;
                    continue;
                }
                break block5;
            }
            return n;
        }
        if (this.isTokenChar(c)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Tokens without separator (pos ");
            stringBuffer.append(n);
            stringBuffer.append("): ");
            stringBuffer.append(this.currentHeader);
            throw new ParseException(stringBuffer.toString());
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid character after token (pos ");
        stringBuffer.append(n);
        stringBuffer.append("): ");
        stringBuffer.append(this.currentHeader);
        throw new ParseException(stringBuffer.toString());
    }

    protected int findTokenStart(int n) {
        if (n < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Search position must not be negative: ");
            stringBuffer.append(n);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        boolean bl = false;
        do {
            int n2;
            boolean bl2;
            CharSequence charSequence;
            int n3;
            if (!bl && (charSequence = this.currentHeader) != null) {
                n2 = ((String)charSequence).length();
                n3 = n;
                bl2 = bl;
            } else {
                if (!bl) return -1;
                return n;
            }
            while (!bl2 && n3 < n2) {
                char c = this.currentHeader.charAt(n3);
                if (!this.isTokenSeparator(c) && !this.isWhitespace(c)) {
                    if (!this.isTokenChar(this.currentHeader.charAt(n3))) {
                        charSequence = new StringBuffer();
                        ((StringBuffer)charSequence).append("Invalid character before token (pos ");
                        ((StringBuffer)charSequence).append(n3);
                        ((StringBuffer)charSequence).append("): ");
                        ((StringBuffer)charSequence).append(this.currentHeader);
                        throw new ParseException(((StringBuffer)charSequence).toString());
                    }
                    bl2 = true;
                    continue;
                }
                ++n3;
            }
            bl = bl2;
            n = n3;
            if (bl2) continue;
            if (this.headerIt.hasNext()) {
                this.currentHeader = this.headerIt.nextHeader().getValue();
                n = 0;
                bl = bl2;
                continue;
            }
            this.currentHeader = null;
            bl = bl2;
            n = n3;
        } while (true);
    }

    @Override
    public boolean hasNext() {
        if (this.currentToken == null) return false;
        return true;
    }

    protected boolean isHttpSeparator(char c) {
        if (HTTP_SEPARATORS.indexOf(c) < 0) return false;
        return true;
    }

    protected boolean isTokenChar(char c) {
        if (Character.isLetterOrDigit(c)) {
            return true;
        }
        if (Character.isISOControl(c)) {
            return false;
        }
        if (!this.isHttpSeparator(c)) return true;
        return false;
    }

    protected boolean isTokenSeparator(char c) {
        if (c != ',') return false;
        return true;
    }

    protected boolean isWhitespace(char c) {
        if (c == '\t') return true;
        if (Character.isSpaceChar(c)) return true;
        return false;
    }

    public final Object next() throws NoSuchElementException, ParseException {
        return this.nextToken();
    }

    @Override
    public String nextToken() throws NoSuchElementException, ParseException {
        String string2 = this.currentToken;
        if (string2 == null) throw new NoSuchElementException("Iteration already finished.");
        this.searchPos = this.findNext(this.searchPos);
        return string2;
    }

    @Override
    public final void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removing tokens is not supported.");
    }
}

