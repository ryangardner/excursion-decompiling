/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedirectLocations {
    private final List<URI> all = new ArrayList<URI>();
    private final Set<URI> unique = new HashSet<URI>();

    public void add(URI uRI) {
        this.unique.add(uRI);
        this.all.add(uRI);
    }

    public boolean contains(URI uRI) {
        return this.unique.contains(uRI);
    }

    public List<URI> getAll() {
        return new ArrayList<URI>(this.all);
    }

    public boolean remove(URI uRI) {
        boolean bl = this.unique.remove(uRI);
        if (!bl) return bl;
        Iterator<URI> iterator2 = this.all.iterator();
        while (iterator2.hasNext()) {
            if (!iterator2.next().equals(uRI)) continue;
            iterator2.remove();
        }
        return bl;
    }
}

