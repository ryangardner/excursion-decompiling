/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import java.io.IOException;

public class DefaultIndenter
extends DefaultPrettyPrinter.NopIndenter {
    private static final int INDENT_LEVELS = 16;
    public static final DefaultIndenter SYSTEM_LINEFEED_INSTANCE;
    public static final String SYS_LF;
    private static final long serialVersionUID = 1L;
    private final int charsPerLevel;
    private final String eol;
    private final char[] indents;

    static {
        String string2;
        try {
            string2 = System.getProperty("line.separator");
        }
        catch (Throwable throwable) {
            string2 = "\n";
        }
        SYS_LF = string2;
        SYSTEM_LINEFEED_INSTANCE = new DefaultIndenter("  ", SYS_LF);
    }

    public DefaultIndenter() {
        this("  ", SYS_LF);
    }

    public DefaultIndenter(String string2, String string3) {
        this.charsPerLevel = string2.length();
        this.indents = new char[string2.length() * 16];
        int n = 0;
        int n2 = 0;
        do {
            if (n >= 16) {
                this.eol = string3;
                return;
            }
            string2.getChars(0, string2.length(), this.indents, n2);
            n2 += string2.length();
            ++n;
        } while (true);
    }

    public String getEol() {
        return this.eol;
    }

    public String getIndent() {
        return new String(this.indents, 0, this.charsPerLevel);
    }

    @Override
    public boolean isInline() {
        return false;
    }

    public DefaultIndenter withIndent(String string2) {
        if (!string2.equals(this.getIndent())) return new DefaultIndenter(string2, this.eol);
        return this;
    }

    public DefaultIndenter withLinefeed(String string2) {
        if (!string2.equals(this.eol)) return new DefaultIndenter(this.getIndent(), string2);
        return this;
    }

    @Override
    public void writeIndentation(JsonGenerator jsonGenerator, int n) throws IOException {
        jsonGenerator.writeRaw(this.eol);
        if (n <= 0) return;
        n *= this.charsPerLevel;
        do {
            char[] arrc;
            if (n <= (arrc = this.indents).length) {
                jsonGenerator.writeRaw(arrc, 0, n);
                return;
            }
            jsonGenerator.writeRaw(arrc, 0, arrc.length);
            n -= this.indents.length;
        } while (true);
    }
}

