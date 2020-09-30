/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.search;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.search.SearchTerm;

public final class FlagTerm
extends SearchTerm {
    private static final long serialVersionUID = -142991500302030647L;
    protected Flags flags;
    protected boolean set;

    public FlagTerm(Flags flags, boolean bl) {
        this.flags = flags;
        this.set = bl;
    }

    public boolean equals(Object object) {
        if (!(object instanceof FlagTerm)) {
            return false;
        }
        object = (FlagTerm)object;
        if (((FlagTerm)object).set != this.set) return false;
        if (!((FlagTerm)object).flags.equals(this.flags)) return false;
        return true;
    }

    public Flags getFlags() {
        return (Flags)this.flags.clone();
    }

    public boolean getTestSet() {
        return this.set;
    }

    public int hashCode() {
        if (!this.set) return this.flags.hashCode();
        return this.flags.hashCode();
    }

    @Override
    public boolean match(Message object) {
        try {
            object = ((Message)object).getFlags();
            if (this.set) {
                if (!((Flags)object).contains(this.flags)) return false;
                return true;
            }
            Object[] arrobject = this.flags.getSystemFlags();
            int n = 0;
            do {
                if (n >= arrobject.length) break;
                boolean bl = ((Flags)object).contains(arrobject[n]);
                if (bl) {
                    return false;
                }
                ++n;
            } while (true);
            arrobject = this.flags.getUserFlags();
            n = 0;
            do {
                if (n >= arrobject.length) {
                    return true;
                }
                if (((Flags)object).contains((String)arrobject[n])) {
                    return false;
                }
                ++n;
            } while (true);
        }
        catch (Exception exception) {
            return false;
        }
    }
}

