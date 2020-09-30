/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.xml;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;

public class XmlEscapers {
    private static final char MAX_ASCII_CONTROL_CHAR = '\u001f';
    private static final char MIN_ASCII_CONTROL_CHAR = '\u0000';
    private static final Escaper XML_ATTRIBUTE_ESCAPER;
    private static final Escaper XML_CONTENT_ESCAPER;
    private static final Escaper XML_ESCAPER;

    static {
        Escapers.Builder builder = Escapers.builder();
        char c = '\u0000';
        builder.setSafeRange('\u0000', '\ufffd');
        builder.setUnsafeReplacement("\ufffd");
        char c2 = c;
        do {
            if (c2 > '\u001f') {
                builder.addEscape('&', "&amp;");
                builder.addEscape('<', "&lt;");
                builder.addEscape('>', "&gt;");
                XML_CONTENT_ESCAPER = builder.build();
                builder.addEscape('\'', "&apos;");
                builder.addEscape('\"', "&quot;");
                XML_ESCAPER = builder.build();
                builder.addEscape('\t', "&#x9;");
                builder.addEscape('\n', "&#xA;");
                builder.addEscape('\r', "&#xD;");
                XML_ATTRIBUTE_ESCAPER = builder.build();
                return;
            }
            if (c2 != '\t' && c2 != '\n' && c2 != '\r') {
                builder.addEscape(c2, "\ufffd");
            }
            c2 = c = (char)((char)(c2 + 1));
        } while (true);
    }

    private XmlEscapers() {
    }

    public static Escaper xmlAttributeEscaper() {
        return XML_ATTRIBUTE_ESCAPER;
    }

    public static Escaper xmlContentEscaper() {
        return XML_CONTENT_ESCAPER;
    }
}

