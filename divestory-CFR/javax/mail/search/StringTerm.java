/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.search.SearchTerm;

public abstract class StringTerm
extends SearchTerm {
    private static final long serialVersionUID = 1274042129007696269L;
    protected boolean ignoreCase;
    protected String pattern;

    protected StringTerm(String string2) {
        this.pattern = string2;
        this.ignoreCase = true;
    }

    protected StringTerm(String string2, boolean bl) {
        this.pattern = string2;
        this.ignoreCase = bl;
    }

    public boolean equals(Object object) {
        if (!(object instanceof StringTerm)) {
            return false;
        }
        object = (StringTerm)object;
        if (this.ignoreCase) {
            if (!((StringTerm)object).pattern.equalsIgnoreCase(this.pattern)) return false;
            if (((StringTerm)object).ignoreCase != this.ignoreCase) return false;
            return true;
        }
        if (!((StringTerm)object).pattern.equals(this.pattern)) return false;
        if (((StringTerm)object).ignoreCase != this.ignoreCase) return false;
        return true;
    }

    public boolean getIgnoreCase() {
        return this.ignoreCase;
    }

    public String getPattern() {
        return this.pattern;
    }

    public int hashCode() {
        if (!this.ignoreCase) return this.pattern.hashCode();
        return this.pattern.hashCode();
    }

    protected boolean match(String string2) {
        int n = string2.length();
        int n2 = this.pattern.length();
        int n3 = 0;
        while (n3 <= n - n2) {
            boolean bl = this.ignoreCase;
            String string3 = this.pattern;
            if (string2.regionMatches(bl, n3, string3, 0, string3.length())) {
                return true;
            }
            ++n3;
        }
        return false;
    }
}

