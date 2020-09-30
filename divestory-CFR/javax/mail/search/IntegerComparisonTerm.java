/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.search.ComparisonTerm;

public abstract class IntegerComparisonTerm
extends ComparisonTerm {
    private static final long serialVersionUID = -6963571240154302484L;
    protected int number;

    protected IntegerComparisonTerm(int n, int n2) {
        this.comparison = n;
        this.number = n2;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IntegerComparisonTerm)) {
            return false;
        }
        if (((IntegerComparisonTerm)object).number != this.number) return false;
        if (!super.equals(object)) return false;
        return true;
    }

    public int getComparison() {
        return this.comparison;
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public int hashCode() {
        return this.number + super.hashCode();
    }

    protected boolean match(int n) {
        switch (this.comparison) {
            default: {
                return false;
            }
            case 6: {
                if (n < this.number) return false;
                return true;
            }
            case 5: {
                if (n <= this.number) return false;
                return true;
            }
            case 4: {
                if (n == this.number) return false;
                return true;
            }
            case 3: {
                if (n != this.number) return false;
                return true;
            }
            case 2: {
                if (n >= this.number) return false;
                return true;
            }
            case 1: 
        }
        if (n > this.number) return false;
        return true;
    }
}

