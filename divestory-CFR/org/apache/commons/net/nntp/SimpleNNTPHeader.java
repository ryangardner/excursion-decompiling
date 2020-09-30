/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

public class SimpleNNTPHeader {
    private final String __from;
    private final StringBuilder __headerFields;
    private int __newsgroupCount;
    private final StringBuilder __newsgroups;
    private final String __subject;

    public SimpleNNTPHeader(String string2, String string3) {
        this.__from = string2;
        this.__subject = string3;
        this.__newsgroups = new StringBuilder();
        this.__headerFields = new StringBuilder();
        this.__newsgroupCount = 0;
    }

    public void addHeaderField(String string2, String string3) {
        this.__headerFields.append(string2);
        this.__headerFields.append(": ");
        this.__headerFields.append(string3);
        this.__headerFields.append('\n');
    }

    public void addNewsgroup(String string2) {
        int n = this.__newsgroupCount;
        this.__newsgroupCount = n + 1;
        if (n > 0) {
            this.__newsgroups.append(',');
        }
        this.__newsgroups.append(string2);
    }

    public String getFromAddress() {
        return this.__from;
    }

    public String getNewsgroups() {
        return this.__newsgroups.toString();
    }

    public String getSubject() {
        return this.__subject;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("From: ");
        stringBuilder.append(this.__from);
        stringBuilder.append("\nNewsgroups: ");
        stringBuilder.append(this.__newsgroups.toString());
        stringBuilder.append("\nSubject: ");
        stringBuilder.append(this.__subject);
        stringBuilder.append('\n');
        if (this.__headerFields.length() > 0) {
            stringBuilder.append(this.__headerFields.toString());
        }
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }
}

