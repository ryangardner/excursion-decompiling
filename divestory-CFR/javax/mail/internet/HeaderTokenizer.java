/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import javax.mail.internet.ParseException;

public class HeaderTokenizer {
    private static final Token EOFToken = new Token(-4, null);
    public static final String MIME = "()<>@,;:\\\"\t []/?=";
    public static final String RFC822 = "()<>@,;:\\\"\t .[]";
    private int currentPos;
    private String delimiters;
    private int maxPos;
    private int nextPos;
    private int peekPos;
    private boolean skipComments;
    private String string;

    public HeaderTokenizer(String string2) {
        this(string2, RFC822);
    }

    public HeaderTokenizer(String string2, String string3) {
        this(string2, string3, true);
    }

    public HeaderTokenizer(String string2, String string3, boolean bl) {
        String string4 = string2;
        if (string2 == null) {
            string4 = "";
        }
        this.string = string4;
        this.skipComments = bl;
        this.delimiters = string3;
        this.peekPos = 0;
        this.nextPos = 0;
        this.currentPos = 0;
        this.maxPos = string4.length();
    }

    /*
     * Unable to fully structure code
     */
    private static String filterToken(String var0, int var1_1, int var2_2) {
        var3_3 = new StringBuffer();
        var4_4 = 0;
        var5_5 = false;
        var6_6 = var1_1;
        var1_1 = var4_4;
        do {
            if (var6_6 >= var2_2) {
                return var3_3.toString();
            }
            var7_7 = var0.charAt(var6_6);
            if (var7_7 == '\n' && var1_1 != 0) ** GOTO lbl21
            if (!var5_5) {
                if (var7_7 == '\\') {
                    var1_1 = 0;
                    var5_5 = true;
                } else if (var7_7 == '\r') {
                    var1_1 = 1;
                } else {
                    var3_3.append(var7_7);
lbl21: // 2 sources:
                    var1_1 = 0;
                }
            } else {
                var3_3.append(var7_7);
                var1_1 = 0;
                var5_5 = false;
            }
            ++var6_6;
        } while (true);
    }

    private Token getNext() throws ParseException {
        if (this.currentPos >= this.maxPos) {
            return EOFToken;
        }
        if (this.skipWhiteSpace() == -4) {
            return EOFToken;
        }
        int n = this.string.charAt(this.currentPos);
        char c = '\u0000';
        char c2 = n;
        do {
            char c3;
            int n2;
            int n3;
            block24 : {
                block23 : {
                    block22 : {
                        block21 : {
                            if (c2 == '(') break block21;
                            if (c2 == '\"') break block22;
                            if (c2 < ' ' || c2 >= '' || this.delimiters.indexOf(c2) >= 0) {
                                ++this.currentPos;
                                return new Token(c2, new String(new char[]{c2}));
                            }
                            break block23;
                        }
                        this.currentPos = n2 = this.currentPos + 1;
                        c3 = '\u0001';
                        n = c;
                        break block24;
                    }
                    this.currentPos = c3 = this.currentPos + 1;
                    while ((n = this.currentPos++) < this.maxPos) {
                        n3 = this.string.charAt(n);
                        if (n3 == 92 || n3 == 13) {
                            n = 1;
                        } else {
                            n = c;
                            if (n3 == 34) {
                                String string2;
                                this.currentPos = n = this.currentPos + 1;
                                if (c != '\u0000') {
                                    string2 = HeaderTokenizer.filterToken(this.string, c3, n - 1);
                                    return new Token(-2, string2);
                                }
                                string2 = this.string.substring(c3, n - 1);
                                return new Token(-2, string2);
                            }
                        }
                        ++this.currentPos;
                        c = n;
                    }
                    throw new ParseException("Unbalanced quoted string");
                }
                c = this.currentPos;
                do {
                    if ((n = this.currentPos) >= this.maxPos) {
                        return new Token(-1, this.string.substring(c, this.currentPos));
                    }
                    if ((n = (int)this.string.charAt(n)) < 32) return new Token(-1, this.string.substring(c, this.currentPos));
                    if (n >= 127) return new Token(-1, this.string.substring(c, this.currentPos));
                    if (n == 40) return new Token(-1, this.string.substring(c, this.currentPos));
                    if (n == 32) return new Token(-1, this.string.substring(c, this.currentPos));
                    if (n == 34) return new Token(-1, this.string.substring(c, this.currentPos));
                    if (this.delimiters.indexOf(n) >= 0) {
                        return new Token(-1, this.string.substring(c, this.currentPos));
                    }
                    ++this.currentPos;
                } while (true);
            }
            while (c3 > '\u0000' && (c = this.currentPos++) < this.maxPos) {
                char c4 = this.string.charAt(c);
                if (c4 == '\\' || c4 == '\r') {
                    n3 = 1;
                    c = c3;
                } else if (c4 == '(') {
                    c = c3 + 1;
                    n3 = n;
                } else {
                    n3 = n;
                    c = c3;
                    if (c4 == ')') {
                        c = c3 - 1;
                        n3 = n;
                    }
                }
                ++this.currentPos;
                n = n3;
                c3 = c;
            }
            if (c3 != '\u0000') throw new ParseException("Unbalanced comments");
            if (!this.skipComments) {
                String string3;
                if (n != 0) {
                    string3 = HeaderTokenizer.filterToken(this.string, n2, this.currentPos - 1);
                    return new Token(-3, string3);
                }
                string3 = this.string.substring(n2, this.currentPos - 1);
                return new Token(-3, string3);
            }
            if (this.skipWhiteSpace() == -4) {
                return EOFToken;
            }
            c2 = c = (char)this.string.charAt(this.currentPos);
            c = n;
        } while (true);
    }

    private int skipWhiteSpace() {
        int n;
        while ((n = this.currentPos) < this.maxPos) {
            if ((n = (int)this.string.charAt(n)) != 32 && n != 9 && n != 13 && n != 10) {
                return this.currentPos;
            }
            ++this.currentPos;
        }
        return -4;
    }

    public String getRemainder() {
        return this.string.substring(this.nextPos);
    }

    public Token next() throws ParseException {
        int n;
        this.currentPos = this.nextPos;
        Token token = this.getNext();
        this.peekPos = n = this.currentPos;
        this.nextPos = n;
        return token;
    }

    public Token peek() throws ParseException {
        this.currentPos = this.peekPos;
        Token token = this.getNext();
        this.peekPos = this.currentPos;
        return token;
    }

    public static class Token {
        public static final int ATOM = -1;
        public static final int COMMENT = -3;
        public static final int EOF = -4;
        public static final int QUOTEDSTRING = -2;
        private int type;
        private String value;

        public Token(int n, String string2) {
            this.type = n;
            this.value = string2;
        }

        public int getType() {
            return this.type;
        }

        public String getValue() {
            return this.value;
        }
    }

}

