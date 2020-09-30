/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

import java.util.Calendar;

public final class NewGroupsOrNewsQuery {
    private final String __date;
    private StringBuffer __distributions = null;
    private final boolean __isGMT;
    private StringBuffer __newsgroups = null;
    private final String __time;

    public NewGroupsOrNewsQuery(Calendar object, boolean bl) {
        this.__isGMT = bl;
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = Integer.toString(((Calendar)object).get(1));
        int n = string2.length();
        if (n >= 2) {
            stringBuilder.append(string2.substring(n - 2));
        } else {
            stringBuilder.append("00");
        }
        string2 = Integer.toString(((Calendar)object).get(2) + 1);
        n = string2.length();
        if (n == 1) {
            stringBuilder.append('0');
            stringBuilder.append(string2);
        } else if (n == 2) {
            stringBuilder.append(string2);
        } else {
            stringBuilder.append("01");
        }
        string2 = Integer.toString(((Calendar)object).get(5));
        n = string2.length();
        if (n == 1) {
            stringBuilder.append('0');
            stringBuilder.append(string2);
        } else if (n == 2) {
            stringBuilder.append(string2);
        } else {
            stringBuilder.append("01");
        }
        this.__date = stringBuilder.toString();
        stringBuilder.setLength(0);
        string2 = Integer.toString(((Calendar)object).get(11));
        n = string2.length();
        if (n == 1) {
            stringBuilder.append('0');
            stringBuilder.append(string2);
        } else if (n == 2) {
            stringBuilder.append(string2);
        } else {
            stringBuilder.append("00");
        }
        string2 = Integer.toString(((Calendar)object).get(12));
        n = string2.length();
        if (n == 1) {
            stringBuilder.append('0');
            stringBuilder.append(string2);
        } else if (n == 2) {
            stringBuilder.append(string2);
        } else {
            stringBuilder.append("00");
        }
        object = Integer.toString(((Calendar)object).get(13));
        n = ((String)object).length();
        if (n == 1) {
            stringBuilder.append('0');
            stringBuilder.append((String)object);
        } else if (n == 2) {
            stringBuilder.append((String)object);
        } else {
            stringBuilder.append("00");
        }
        this.__time = stringBuilder.toString();
    }

    public void addDistribution(String string2) {
        StringBuffer stringBuffer = this.__distributions;
        if (stringBuffer != null) {
            stringBuffer.append(',');
        } else {
            this.__distributions = new StringBuffer();
        }
        this.__distributions.append(string2);
    }

    public void addNewsgroup(String string2) {
        StringBuffer stringBuffer = this.__newsgroups;
        if (stringBuffer != null) {
            stringBuffer.append(',');
        } else {
            this.__newsgroups = new StringBuffer();
        }
        this.__newsgroups.append(string2);
    }

    public String getDate() {
        return this.__date;
    }

    public String getDistributions() {
        CharSequence charSequence = this.__distributions;
        if (charSequence != null) return charSequence.toString();
        return null;
    }

    public String getNewsgroups() {
        CharSequence charSequence = this.__newsgroups;
        if (charSequence != null) return charSequence.toString();
        return null;
    }

    public String getTime() {
        return this.__time;
    }

    public boolean isGMT() {
        return this.__isGMT;
    }

    public void omitNewsgroup(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("!");
        stringBuilder.append(string2);
        this.addNewsgroup(stringBuilder.toString());
    }
}

