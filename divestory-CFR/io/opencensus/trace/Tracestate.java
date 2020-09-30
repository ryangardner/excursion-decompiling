/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.internal.Utils;
import io.opencensus.trace.AutoValue_Tracestate;
import io.opencensus.trace.AutoValue_Tracestate_Entry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public abstract class Tracestate {
    private static final int KEY_MAX_SIZE = 256;
    private static final int MAX_KEY_VALUE_PAIRS = 32;
    private static final int VALUE_MAX_SIZE = 256;

    Tracestate() {
    }

    public static Builder builder() {
        return new Builder(Builder.EMPTY);
    }

    private static Tracestate create(List<Entry> list) {
        boolean bl = list.size() <= 32;
        Utils.checkState(bl, "Invalid size");
        return new AutoValue_Tracestate(Collections.unmodifiableList(list));
    }

    private static boolean validateKey(String string2) {
        if (string2.length() > 256) return false;
        if (string2.isEmpty()) return false;
        if (string2.charAt(0) < 'a') return false;
        if (string2.charAt(0) > 'z') {
            return false;
        }
        int n = 1;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (!(c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '_' || c == '-' || c == '*' || c == '/')) {
                return false;
            }
            ++n;
        }
        return true;
    }

    private static boolean validateValue(String string2) {
        if (string2.length() > 256) return false;
        if (string2.charAt(string2.length() - 1) == ' ') {
            return false;
        }
        int n = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (c == ',') return false;
            if (c == '=') return false;
            if (c < ' ') return false;
            if (c > '~') {
                return false;
            }
            ++n;
        }
        return true;
    }

    @Nullable
    public String get(String string2) {
        Entry entry;
        Iterator<Entry> iterator2 = this.getEntries().iterator();
        do {
            if (!iterator2.hasNext()) return null;
        } while (!(entry = iterator2.next()).getKey().equals(string2));
        return entry.getValue();
    }

    public abstract List<Entry> getEntries();

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        private static final Tracestate EMPTY = Tracestate.access$200(Collections.emptyList());
        @Nullable
        private ArrayList<Entry> entries;
        private final Tracestate parent;

        private Builder(Tracestate tracestate) {
            Utils.checkNotNull(tracestate, "parent");
            this.parent = tracestate;
            this.entries = null;
        }

        public Tracestate build() {
            ArrayList<Entry> arrayList = this.entries;
            if (arrayList != null) return Tracestate.create(arrayList);
            return this.parent;
        }

        public Builder remove(String string2) {
            Utils.checkNotNull(string2, "key");
            if (this.entries == null) {
                this.entries = new ArrayList<Entry>(this.parent.getEntries());
            }
            int n = 0;
            while (n < this.entries.size()) {
                if (this.entries.get(n).getKey().equals(string2)) {
                    this.entries.remove(n);
                    return this;
                }
                ++n;
            }
            return this;
        }

        public Builder set(String object, String string2) {
            object = Entry.create((String)object, string2);
            if (this.entries == null) {
                this.entries = new ArrayList<Entry>(this.parent.getEntries());
            }
            for (int i = 0; i < this.entries.size(); ++i) {
                if (!this.entries.get(i).getKey().equals(((Entry)object).getKey())) continue;
                this.entries.remove(i);
                break;
            }
            this.entries.add(0, (Entry)object);
            return this;
        }
    }

    public static abstract class Entry {
        Entry() {
        }

        public static Entry create(String string2, String string3) {
            Utils.checkNotNull(string2, "key");
            Utils.checkNotNull(string3, "value");
            Utils.checkArgument(Tracestate.validateKey(string2), "Invalid key %s", string2);
            Utils.checkArgument(Tracestate.validateValue(string3), "Invalid value %s", string3);
            return new AutoValue_Tracestate_Entry(string2, string3);
        }

        public abstract String getKey();

        public abstract String getValue();
    }

}

