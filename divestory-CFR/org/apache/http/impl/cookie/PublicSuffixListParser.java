/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.http.impl.cookie.PublicSuffixFilter;

public class PublicSuffixListParser {
    private static final int MAX_LINE_LEN = 256;
    private final PublicSuffixFilter filter;

    PublicSuffixListParser(PublicSuffixFilter publicSuffixFilter) {
        this.filter = publicSuffixFilter;
    }

    private boolean readLine(Reader reader, StringBuilder stringBuilder) throws IOException {
        char c;
        int n;
        boolean bl = false;
        stringBuilder.setLength(0);
        boolean bl2 = false;
        while ((n = reader.read()) != -1 && (c = (char)n) != '\n') {
            if (Character.isWhitespace(c)) {
                bl2 = true;
            }
            if (!bl2) {
                stringBuilder.append(c);
            }
            if (stringBuilder.length() > 256) throw new IOException("Line too long");
        }
        if (n == -1) return bl;
        return true;
    }

    public void parse(Reader object) throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        BufferedReader bufferedReader = new BufferedReader((Reader)object);
        StringBuilder stringBuilder = new StringBuilder(256);
        boolean bl = true;
        do {
            if (!bl) {
                this.filter.setPublicSuffixes(arrayList);
                this.filter.setExceptions(arrayList2);
                return;
            }
            bl = this.readLine(bufferedReader, stringBuilder);
            Object object2 = stringBuilder.toString();
            if (((String)object2).length() == 0 || ((String)object2).startsWith("//")) continue;
            object = object2;
            if (((String)object2).startsWith(".")) {
                object = ((String)object2).substring(1);
            }
            boolean bl2 = ((String)object).startsWith("!");
            object2 = object;
            if (bl2) {
                object2 = ((String)object).substring(1);
            }
            if (bl2) {
                arrayList2.add((String)object2);
                continue;
            }
            arrayList.add((String)object2);
        } while (true);
    }
}

