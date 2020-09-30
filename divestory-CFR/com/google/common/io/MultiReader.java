/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.CharSource;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class MultiReader
extends Reader {
    @NullableDecl
    private Reader current;
    private final Iterator<? extends CharSource> it;

    MultiReader(Iterator<? extends CharSource> iterator2) throws IOException {
        this.it = iterator2;
        this.advance();
    }

    private void advance() throws IOException {
        this.close();
        if (!this.it.hasNext()) return;
        this.current = this.it.next().openStream();
    }

    @Override
    public void close() throws IOException {
        Reader reader = this.current;
        if (reader == null) return;
        try {
            reader.close();
            return;
        }
        finally {
            this.current = null;
        }
    }

    @Override
    public int read(@NullableDecl char[] arrc, int n, int n2) throws IOException {
        Reader reader = this.current;
        if (reader == null) {
            return -1;
        }
        int n3 = reader.read(arrc, n, n2);
        if (n3 != -1) return n3;
        this.advance();
        return this.read(arrc, n, n2);
    }

    @Override
    public boolean ready() throws IOException {
        Reader reader = this.current;
        if (reader == null) return false;
        if (!reader.ready()) return false;
        return true;
    }

    @Override
    public long skip(long l) throws IOException {
        Reader reader;
        long l2 = l LCMP 0L;
        boolean bl = l2 >= 0;
        Preconditions.checkArgument(bl, "n is negative");
        if (l2 <= 0) return 0L;
        while ((reader = this.current) != null) {
            long l3 = reader.skip(l);
            if (l3 > 0L) {
                return l3;
            }
            this.advance();
        }
        return 0L;
    }
}

