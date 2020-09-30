/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.search.SearchTerm;

public abstract class ComparisonTerm
extends SearchTerm {
    public static final int EQ = 3;
    public static final int GE = 6;
    public static final int GT = 5;
    public static final int LE = 1;
    public static final int LT = 2;
    public static final int NE = 4;
    private static final long serialVersionUID = 1456646953666474308L;
    protected int comparison;

    public boolean equals(Object object) {
        if (!(object instanceof ComparisonTerm)) {
            return false;
        }
        if (((ComparisonTerm)object).comparison != this.comparison) return false;
        return true;
    }

    public int hashCode() {
        return this.comparison;
    }
}

