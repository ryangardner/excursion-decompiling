/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import java.util.Vector;

public class Rights
implements Cloneable {
    private boolean[] rights;

    public Rights() {
        this.rights = new boolean[128];
    }

    public Rights(Right right) {
        boolean[] arrbl = new boolean[128];
        this.rights = arrbl;
        arrbl[right.right] = true;
    }

    public Rights(Rights rights) {
        boolean[] arrbl = new boolean[128];
        this.rights = arrbl;
        System.arraycopy(rights.rights, 0, arrbl, 0, arrbl.length);
    }

    public Rights(String string2) {
        this.rights = new boolean[128];
        int n = 0;
        while (n < string2.length()) {
            this.add(Right.getInstance(string2.charAt(n)));
            ++n;
        }
        return;
    }

    public void add(Right right) {
        this.rights[right.right] = true;
    }

    public void add(Rights rights) {
        int n = 0;
        boolean[] arrbl;
        while (n < (arrbl = rights.rights).length) {
            if (arrbl[n]) {
                this.rights[n] = true;
            }
            ++n;
        }
        return;
    }

    public Object clone() {
        Rights rights;
        boolean[] arrbl = null;
        try {
            rights = (Rights)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return arrbl;
        }
        try {
            arrbl = new boolean[128];
            rights.rights = arrbl;
            System.arraycopy(this.rights, 0, arrbl, 0, this.rights.length);
            return rights;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return rights;
    }

    public boolean contains(Right right) {
        return this.rights[right.right];
    }

    public boolean contains(Rights rights) {
        int n = 0;
        boolean[] arrbl;
        while (n < (arrbl = rights.rights).length) {
            if (arrbl[n] && !this.rights[n]) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public boolean equals(Object arrbl) {
        if (!(arrbl instanceof Rights)) {
            return false;
        }
        Rights rights = (Rights)arrbl;
        int n = 0;
        while (n < (arrbl = rights.rights).length) {
            if (arrbl[n] != this.rights[n]) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public Right[] getRights() {
        Vector<Right> vector = new Vector<Right>();
        int n = 0;
        do {
            Object[] arrobject;
            if (n >= (arrobject = this.rights).length) {
                arrobject = new Right[vector.size()];
                vector.copyInto(arrobject);
                return arrobject;
            }
            if (arrobject[n]) {
                vector.addElement(Right.getInstance((char)n));
            }
            ++n;
        } while (true);
    }

    public int hashCode() {
        int n = 0;
        int n2 = 0;
        boolean[] arrbl;
        while (n < (arrbl = this.rights).length) {
            int n3 = n2;
            if (arrbl[n]) {
                n3 = n2 + 1;
            }
            ++n;
            n2 = n3;
        }
        return n2;
    }

    public void remove(Right right) {
        this.rights[right.right] = false;
    }

    public void remove(Rights rights) {
        int n = 0;
        boolean[] arrbl;
        while (n < (arrbl = rights.rights).length) {
            if (arrbl[n]) {
                this.rights[n] = false;
            }
            ++n;
        }
        return;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        boolean[] arrbl;
        while (n < (arrbl = this.rights).length) {
            if (arrbl[n]) {
                stringBuffer.append((char)n);
            }
            ++n;
        }
        return stringBuffer.toString();
    }

    public static final class Right {
        public static final Right ADMINISTER;
        public static final Right CREATE;
        public static final Right DELETE;
        public static final Right INSERT;
        public static final Right KEEP_SEEN;
        public static final Right LOOKUP;
        public static final Right POST;
        public static final Right READ;
        public static final Right WRITE;
        private static Right[] cache;
        char right;

        static {
            cache = new Right[128];
            LOOKUP = Right.getInstance('l');
            READ = Right.getInstance('r');
            KEEP_SEEN = Right.getInstance('s');
            WRITE = Right.getInstance('w');
            INSERT = Right.getInstance('i');
            POST = Right.getInstance('p');
            CREATE = Right.getInstance('c');
            DELETE = Right.getInstance('d');
            ADMINISTER = Right.getInstance('a');
        }

        private Right(char c) {
            if (c >= '') throw new IllegalArgumentException("Right must be ASCII");
            this.right = c;
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled unnecessary exception pruning
         */
        public static Right getInstance(char c) {
            synchronized (Right.class) {
                if (c < '') {
                    Right right;
                    if (cache[c] != null) return cache[c];
                    Right[] arrright = cache;
                    arrright[c] = right = new Right(c);
                    return cache[c];
                }
                try {
                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Right must be ASCII");
                    throw illegalArgumentException;
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }

        public String toString() {
            return String.valueOf(this.right);
        }
    }

}

