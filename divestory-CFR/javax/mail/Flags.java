/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

public class Flags
implements Cloneable,
Serializable {
    private static final int ANSWERED_BIT = 1;
    private static final int DELETED_BIT = 2;
    private static final int DRAFT_BIT = 4;
    private static final int FLAGGED_BIT = 8;
    private static final int RECENT_BIT = 16;
    private static final int SEEN_BIT = 32;
    private static final int USER_BIT = Integer.MIN_VALUE;
    private static final long serialVersionUID = 6243590407214169028L;
    private int system_flags = 0;
    private Hashtable user_flags = null;

    public Flags() {
    }

    public Flags(String string2) {
        Hashtable<String, String> hashtable;
        this.user_flags = hashtable = new Hashtable<String, String>(1);
        hashtable.put(string2.toLowerCase(Locale.ENGLISH), string2);
    }

    public Flags(Flag flag) {
        this.system_flags = flag.bit | 0;
    }

    public Flags(Flags cloneable) {
        this.system_flags = ((Flags)cloneable).system_flags;
        cloneable = ((Flags)cloneable).user_flags;
        if (cloneable == null) return;
        this.user_flags = (Hashtable)((Hashtable)cloneable).clone();
    }

    public void add(String string2) {
        if (this.user_flags == null) {
            this.user_flags = new Hashtable(1);
        }
        this.user_flags.put(string2.toLowerCase(Locale.ENGLISH), string2);
    }

    public void add(Flag flag) {
        int n = this.system_flags;
        this.system_flags = flag.bit | n;
    }

    public void add(Flags flags) {
        this.system_flags |= flags.system_flags;
        if (flags.user_flags == null) return;
        if (this.user_flags == null) {
            this.user_flags = new Hashtable(1);
        }
        Enumeration enumeration = flags.user_flags.keys();
        while (enumeration.hasMoreElements()) {
            String string2 = (String)enumeration.nextElement();
            this.user_flags.put(string2, flags.user_flags.get(string2));
        }
        return;
    }

    public Object clone() {
        Flags flags;
        try {
            flags = (Flags)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            flags = null;
        }
        Hashtable hashtable = this.user_flags;
        if (hashtable == null) return flags;
        if (flags == null) return flags;
        flags.user_flags = (Hashtable)hashtable.clone();
        return flags;
    }

    public boolean contains(String string2) {
        Hashtable hashtable = this.user_flags;
        if (hashtable != null) return hashtable.containsKey(string2.toLowerCase(Locale.ENGLISH));
        return false;
    }

    public boolean contains(Flag flag) {
        int n = this.system_flags;
        if ((flag.bit & n) == 0) return false;
        return true;
    }

    public boolean contains(Flags object) {
        int n = ((Flags)object).system_flags;
        if ((this.system_flags & n) != n) {
            return false;
        }
        object = ((Flags)object).user_flags;
        if (object == null) return true;
        if (this.user_flags == null) {
            return false;
        }
        object = ((Hashtable)object).keys();
        do {
            if (object.hasMoreElements()) continue;
            return true;
        } while (this.user_flags.containsKey(object.nextElement()));
        return false;
    }

    public boolean equals(Object enumeration) {
        if (!(enumeration instanceof Flags)) {
            return false;
        }
        enumeration = (Flags)((Object)enumeration);
        if (((Flags)enumeration).system_flags != this.system_flags) {
            return false;
        }
        if (((Flags)enumeration).user_flags == null && this.user_flags == null) {
            return true;
        }
        Hashtable hashtable = ((Flags)enumeration).user_flags;
        if (hashtable == null) return false;
        if (this.user_flags == null) return false;
        if (hashtable.size() != this.user_flags.size()) return false;
        enumeration = ((Flags)enumeration).user_flags.keys();
        do {
            if (enumeration.hasMoreElements()) continue;
            return true;
        } while (this.user_flags.containsKey(enumeration.nextElement()));
        return false;
    }

    public Flag[] getSystemFlags() {
        Vector<Flag> vector = new Vector<Flag>();
        if ((this.system_flags & 1) != 0) {
            vector.addElement(Flag.ANSWERED);
        }
        if ((this.system_flags & 2) != 0) {
            vector.addElement(Flag.DELETED);
        }
        if ((this.system_flags & 4) != 0) {
            vector.addElement(Flag.DRAFT);
        }
        if ((this.system_flags & 8) != 0) {
            vector.addElement(Flag.FLAGGED);
        }
        if ((this.system_flags & 16) != 0) {
            vector.addElement(Flag.RECENT);
        }
        if ((this.system_flags & 32) != 0) {
            vector.addElement(Flag.SEEN);
        }
        if ((this.system_flags & Integer.MIN_VALUE) != 0) {
            vector.addElement(Flag.USER);
        }
        Object[] arrobject = new Flag[vector.size()];
        vector.copyInto(arrobject);
        return arrobject;
    }

    public String[] getUserFlags() {
        Vector vector = new Vector();
        Object object = this.user_flags;
        if (object != null) {
            object = object.elements();
            while (object.hasMoreElements()) {
                vector.addElement(object.nextElement());
            }
        }
        object = new String[vector.size()];
        vector.copyInto((Object[])object);
        return object;
    }

    public int hashCode() {
        int n = this.system_flags;
        Object object = this.user_flags;
        int n2 = n;
        if (object == null) return n2;
        object = ((Hashtable)object).keys();
        n2 = n;
        while (object.hasMoreElements()) {
            n2 += ((String)object.nextElement()).hashCode();
        }
        return n2;
    }

    public void remove(String string2) {
        Hashtable hashtable = this.user_flags;
        if (hashtable == null) return;
        hashtable.remove(string2.toLowerCase(Locale.ENGLISH));
    }

    public void remove(Flag flag) {
        int n = this.system_flags;
        this.system_flags = flag.bit & n;
    }

    public void remove(Flags object) {
        this.system_flags &= ((Flags)object).system_flags;
        object = ((Flags)object).user_flags;
        if (object == null) return;
        if (this.user_flags == null) {
            return;
        }
        object = ((Hashtable)object).keys();
        while (object.hasMoreElements()) {
            this.user_flags.remove(object.nextElement());
        }
        return;
    }

    public static final class Flag {
        public static final Flag ANSWERED = new Flag(1);
        public static final Flag DELETED = new Flag(2);
        public static final Flag DRAFT = new Flag(4);
        public static final Flag FLAGGED = new Flag(8);
        public static final Flag RECENT = new Flag(16);
        public static final Flag SEEN = new Flag(32);
        public static final Flag USER = new Flag(Integer.MIN_VALUE);
        private int bit;

        private Flag(int n) {
            this.bit = n;
        }
    }

}

