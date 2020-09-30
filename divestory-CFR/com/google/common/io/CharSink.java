/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.google.common.io.Closer;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public abstract class CharSink {
    protected CharSink() {
    }

    public Writer openBufferedStream() throws IOException {
        Writer writer = this.openStream();
        if (!(writer instanceof BufferedWriter)) return new BufferedWriter(writer);
        return (BufferedWriter)writer;
    }

    public abstract Writer openStream() throws IOException;

    public void write(CharSequence charSequence) throws IOException {
        Preconditions.checkNotNull(charSequence);
        Closer closer = Closer.create();
        try {
            Writer writer = closer.register(this.openStream());
            writer.append(charSequence);
            writer.flush();
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
    }

    public long writeFrom(Readable readable) throws IOException {
        long l;
        Preconditions.checkNotNull(readable);
        Closer closer = Closer.create();
        try {
            Writer writer = closer.register(this.openStream());
            l = CharStreams.copy(readable, writer);
            writer.flush();
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
        return l;
    }

    public void writeLines(Iterable<? extends CharSequence> iterable) throws IOException {
        this.writeLines(iterable, System.getProperty("line.separator"));
    }

    public void writeLines(Iterable<? extends CharSequence> object, String string2) throws IOException {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(string2);
        Closer closer = Closer.create();
        try {
            Writer writer = closer.register(this.openBufferedStream());
            object = object.iterator();
            while (object.hasNext()) {
                writer.append((CharSequence)object.next()).append(string2);
            }
            writer.flush();
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
    }
}

