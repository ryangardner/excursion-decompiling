/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.Base64;
import com.google.api.client.util.Preconditions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PemReader {
    private static final Pattern BEGIN_PATTERN = Pattern.compile("-----BEGIN ([A-Z ]+)-----");
    private static final Pattern END_PATTERN = Pattern.compile("-----END ([A-Z ]+)-----");
    private BufferedReader reader;

    public PemReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    public static Section readFirstSectionAndClose(Reader reader) throws IOException {
        return PemReader.readFirstSectionAndClose(reader, null);
    }

    public static Section readFirstSectionAndClose(Reader object, String object2) throws IOException {
        object = new PemReader((Reader)object);
        try {
            object2 = ((PemReader)object).readNextSection((String)object2);
            return object2;
        }
        finally {
            ((PemReader)object).close();
        }
    }

    public void close() throws IOException {
        this.reader.close();
    }

    public Section readNextSection() throws IOException {
        return this.readNextSection(null);
    }

    public Section readNextSection(String string2) throws IOException {
        boolean bl;
        StringBuilder stringBuilder = null;
        Object object = stringBuilder;
        do {
            String string3;
            Object object2;
            if ((string3 = this.reader.readLine()) == null) {
                if (object == null) {
                    bl = true;
                    break;
                }
                bl = false;
                break;
            }
            if (stringBuilder == null) {
                object2 = BEGIN_PATTERN.matcher(string3);
                if (!((Matcher)object2).matches()) continue;
                object2 = ((Matcher)object2).group(1);
                if (string2 != null && !((String)object2).equals(string2)) continue;
                stringBuilder = new StringBuilder();
                object = object2;
                continue;
            }
            object2 = END_PATTERN.matcher(string3);
            if (((Matcher)object2).matches()) {
                string2 = ((Matcher)object2).group(1);
                Preconditions.checkArgument(string2.equals(object), "end tag (%s) doesn't match begin tag (%s)", string2, object);
                return new Section((String)object, Base64.decodeBase64(stringBuilder.toString()));
            }
            stringBuilder.append(string3);
        } while (true);
        Preconditions.checkArgument(bl, "missing end tag (%s)", object);
        return null;
    }

    public static final class Section {
        private final byte[] base64decodedBytes;
        private final String title;

        Section(String string2, byte[] arrby) {
            this.title = Preconditions.checkNotNull(string2);
            this.base64decodedBytes = Preconditions.checkNotNull(arrby);
        }

        public byte[] getBase64DecodedBytes() {
            return this.base64decodedBytes;
        }

        public String getTitle() {
            return this.title;
        }
    }

}

