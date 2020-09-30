/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.activation.registries;

public class MailcapTokenizer {
    public static final int EOI_TOKEN = 5;
    public static final int EQUALS_TOKEN = 61;
    public static final int SEMICOLON_TOKEN = 59;
    public static final int SLASH_TOKEN = 47;
    public static final int START_TOKEN = 1;
    public static final int STRING_TOKEN = 2;
    public static final int UNKNOWN_TOKEN = 0;
    private char autoquoteChar;
    private int currentToken;
    private String currentTokenValue;
    private String data;
    private int dataIndex;
    private int dataLength;
    private boolean isAutoquoting;

    public MailcapTokenizer(String string2) {
        this.data = string2;
        this.dataIndex = 0;
        this.dataLength = string2.length();
        this.currentToken = 1;
        this.currentTokenValue = "";
        this.isAutoquoting = false;
        this.autoquoteChar = (char)59;
    }

    private static String fixEscapeSequences(String string2) {
        int n = string2.length();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.ensureCapacity(n);
        int n2 = 0;
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (c != '\\') {
                stringBuffer.append(c);
            } else if (n2 < n - 1) {
                stringBuffer.append(string2.charAt(++n2));
            } else {
                stringBuffer.append(c);
            }
            ++n2;
        }
        return stringBuffer.toString();
    }

    private static boolean isControlChar(char c) {
        return Character.isISOControl(c);
    }

    private static boolean isSpecialChar(char c) {
        if (c == '\"') return true;
        if (c == ',') return true;
        if (c == '/') return true;
        if (c == '(') return true;
        if (c == ')') return true;
        switch (c) {
            default: {
                switch (c) {
                    default: {
                        return false;
                    }
                    case '[': 
                    case '\\': 
                    case ']': 
                }
            }
            case ':': 
            case ';': 
            case '<': 
            case '=': 
            case '>': 
            case '?': 
            case '@': 
        }
        return true;
    }

    private static boolean isStringTokenChar(char c) {
        if (MailcapTokenizer.isSpecialChar(c)) return false;
        if (MailcapTokenizer.isControlChar(c)) return false;
        if (MailcapTokenizer.isWhiteSpaceChar(c)) return false;
        return true;
    }

    private static boolean isWhiteSpaceChar(char c) {
        return Character.isWhitespace(c);
    }

    public static String nameForToken(int n) {
        if (n == 0) {
            return "unknown";
        }
        if (n == 1) {
            return "start";
        }
        if (n == 2) {
            return "string";
        }
        if (n == 5) {
            return "EOI";
        }
        if (n == 47) {
            return "'/'";
        }
        if (n == 59) {
            return "';'";
        }
        if (n == 61) return "'='";
        return "really unknown";
    }

    private void processAutoquoteToken() {
        int n;
        int n2 = this.dataIndex;
        boolean bl = false;
        while ((n = this.dataIndex++) < this.dataLength && !bl) {
            if (this.data.charAt(n) != this.autoquoteChar) continue;
            bl = true;
        }
        this.currentToken = 2;
        this.currentTokenValue = MailcapTokenizer.fixEscapeSequences(this.data.substring(n2, this.dataIndex));
    }

    private void processStringToken() {
        int n;
        int n2 = this.dataIndex;
        while ((n = this.dataIndex++) < this.dataLength && MailcapTokenizer.isStringTokenChar(this.data.charAt(n))) {
        }
        this.currentToken = 2;
        this.currentTokenValue = this.data.substring(n2, this.dataIndex);
    }

    public int getCurrentToken() {
        return this.currentToken;
    }

    public String getCurrentTokenValue() {
        return this.currentTokenValue;
    }

    public int nextToken() {
        int n;
        if (this.dataIndex >= this.dataLength) {
            this.currentToken = 5;
            this.currentTokenValue = null;
            return this.currentToken;
        }
        while ((n = this.dataIndex++) < this.dataLength && MailcapTokenizer.isWhiteSpaceChar(this.data.charAt(n))) {
        }
        if ((n = this.dataIndex++) >= this.dataLength) {
            this.currentToken = 5;
            this.currentTokenValue = null;
            return this.currentToken;
        }
        char c = this.data.charAt(n);
        if (this.isAutoquoting) {
            if (c != ';' && c != '=') {
                this.processAutoquoteToken();
                return this.currentToken;
            }
            this.currentToken = c;
            this.currentTokenValue = new Character(c).toString();
            ++this.dataIndex;
            return this.currentToken;
        }
        if (MailcapTokenizer.isStringTokenChar(c)) {
            this.processStringToken();
            return this.currentToken;
        }
        if (c != '/' && c != ';' && c != '=') {
            this.currentToken = 0;
            this.currentTokenValue = new Character(c).toString();
            return this.currentToken;
        }
        this.currentToken = c;
        this.currentTokenValue = new Character(c).toString();
        ++this.dataIndex;
        return this.currentToken;
    }

    public void setIsAutoquoting(boolean bl) {
        this.isAutoquoting = bl;
    }
}

