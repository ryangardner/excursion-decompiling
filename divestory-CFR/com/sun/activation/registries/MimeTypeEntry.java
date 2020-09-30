/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.activation.registries;

public class MimeTypeEntry {
    private String extension;
    private String type;

    public MimeTypeEntry(String string2, String string3) {
        this.type = string2;
        this.extension = string3;
    }

    public String getFileExtension() {
        return this.extension;
    }

    public String getMIMEType() {
        return this.type;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("MIMETypeEntry: ");
        stringBuilder.append(this.type);
        stringBuilder.append(", ");
        stringBuilder.append(this.extension);
        return stringBuilder.toString();
    }
}

