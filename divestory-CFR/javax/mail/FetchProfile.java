/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.util.Vector;

public class FetchProfile {
    private Vector headers = null;
    private Vector specials = null;

    public void add(String string2) {
        if (this.headers == null) {
            this.headers = new Vector();
        }
        this.headers.addElement(string2);
    }

    public void add(Item item) {
        if (this.specials == null) {
            this.specials = new Vector();
        }
        this.specials.addElement(item);
    }

    public boolean contains(String string2) {
        Vector vector = this.headers;
        if (vector == null) return false;
        if (!vector.contains(string2)) return false;
        return true;
    }

    public boolean contains(Item item) {
        Vector vector = this.specials;
        if (vector == null) return false;
        if (!vector.contains(item)) return false;
        return true;
    }

    public String[] getHeaderNames() {
        Object[] arrobject = this.headers;
        if (arrobject == null) {
            return new String[0];
        }
        arrobject = new String[arrobject.size()];
        this.headers.copyInto(arrobject);
        return arrobject;
    }

    public Item[] getItems() {
        Object[] arrobject = this.specials;
        if (arrobject == null) {
            return new Item[0];
        }
        arrobject = new Item[arrobject.size()];
        this.specials.copyInto(arrobject);
        return arrobject;
    }

    public static class Item {
        public static final Item CONTENT_INFO;
        public static final Item ENVELOPE;
        public static final Item FLAGS;
        private String name;

        static {
            ENVELOPE = new Item("ENVELOPE");
            CONTENT_INFO = new Item("CONTENT_INFO");
            FLAGS = new Item("FLAGS");
        }

        protected Item(String string2) {
            this.name = string2;
        }
    }

}

