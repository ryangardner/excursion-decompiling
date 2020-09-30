/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import java.util.Date;
import javax.mail.search.ComparisonTerm;

public abstract class DateTerm
extends ComparisonTerm {
    private static final long serialVersionUID = 4818873430063720043L;
    protected Date date;

    protected DateTerm(int n, Date date) {
        this.comparison = n;
        this.date = date;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DateTerm)) {
            return false;
        }
        if (!((DateTerm)object).date.equals(this.date)) return false;
        if (!super.equals(object)) return false;
        return true;
    }

    public int getComparison() {
        return this.comparison;
    }

    public Date getDate() {
        return new Date(this.date.getTime());
    }

    @Override
    public int hashCode() {
        return this.date.hashCode() + super.hashCode();
    }

    protected boolean match(Date date) {
        switch (this.comparison) {
            default: {
                return false;
            }
            case 6: {
                if (date.after(this.date)) return true;
                if (date.equals(this.date)) return true;
                return false;
            }
            case 5: {
                return date.after(this.date);
            }
            case 4: {
                return date.equals(this.date) ^ true;
            }
            case 3: {
                return date.equals(this.date);
            }
            case 2: {
                return date.before(this.date);
            }
            case 1: 
        }
        if (date.before(this.date)) return true;
        if (date.equals(this.date)) return true;
        return false;
    }
}

