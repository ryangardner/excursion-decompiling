/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.io.ByteSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class FileBackedOutputStream
extends OutputStream {
    @NullableDecl
    private File file;
    private final int fileThreshold;
    private MemoryOutput memory;
    private OutputStream out;
    @NullableDecl
    private final File parentDirectory;
    private final boolean resetOnFinalize;
    private final ByteSource source;

    public FileBackedOutputStream(int n) {
        this(n, false);
    }

    public FileBackedOutputStream(int n, boolean bl) {
        this(n, bl, null);
    }

    private FileBackedOutputStream(int n, boolean bl, @NullableDecl File object) {
        this.fileThreshold = n;
        this.resetOnFinalize = bl;
        this.parentDirectory = object;
        this.memory = object = new MemoryOutput();
        this.out = object;
        if (bl) {
            this.source = new ByteSource(){

                protected void finalize() {
                    try {
                        FileBackedOutputStream.this.reset();
                        return;
                    }
                    catch (Throwable throwable) {
                        throwable.printStackTrace(System.err);
                    }
                }

                @Override
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.openInputStream();
                }
            };
            return;
        }
        this.source = new ByteSource(){

            @Override
            public InputStream openStream() throws IOException {
                return FileBackedOutputStream.this.openInputStream();
            }
        };
    }

    private InputStream openInputStream() throws IOException {
        synchronized (this) {
            if (this.file == null) return new ByteArrayInputStream(this.memory.getBuffer(), 0, this.memory.getCount());
            return new FileInputStream(this.file);
        }
    }

    private void update(int n) throws IOException {
        if (this.file != null) return;
        if (this.memory.getCount() + n <= this.fileThreshold) return;
        File file = File.createTempFile("FileBackedOutputStream", null, this.parentDirectory);
        if (this.resetOnFinalize) {
            file.deleteOnExit();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(this.memory.getBuffer(), 0, this.memory.getCount());
        fileOutputStream.flush();
        this.out = fileOutputStream;
        this.file = file;
        this.memory = null;
    }

    public ByteSource asByteSource() {
        return this.source;
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            this.out.close();
            return;
        }
    }

    @Override
    public void flush() throws IOException {
        synchronized (this) {
            this.out.flush();
            return;
        }
    }

    File getFile() {
        synchronized (this) {
            return this.file;
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void reset() throws IOException {
        Object object;
        // MONITORENTER : this
        this.close();
        {
            catch (Throwable throwable) {
                Object object2;
                if (this.memory == null) {
                    object2 = new MemoryOutput();
                    this.memory = object2;
                } else {
                    this.memory.reset();
                }
                this.out = this.memory;
                if (this.file == null) throw throwable;
                object2 = this.file;
                this.file = null;
                if (((File)object2).delete()) throw throwable;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not delete: ");
                stringBuilder.append(object2);
                IOException iOException = new IOException(stringBuilder.toString());
                throw iOException;
            }
        }
        if (this.memory == null) {
            object = new MemoryOutput();
            this.memory = object;
        } else {
            this.memory.reset();
        }
        this.out = this.memory;
        if (this.file == null) {
            // MONITOREXIT : this
            return;
        }
        File file = this.file;
        this.file = null;
        if (file.delete()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not delete: ");
        stringBuilder.append(file);
        object = new IOException(stringBuilder.toString());
        throw object;
    }

    @Override
    public void write(int n) throws IOException {
        synchronized (this) {
            this.update(1);
            this.out.write(n);
            return;
        }
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        synchronized (this) {
            this.write(arrby, 0, arrby.length);
            return;
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            this.update(n2);
            this.out.write(arrby, n, n2);
            return;
        }
    }

    private static class MemoryOutput
    extends ByteArrayOutputStream {
        private MemoryOutput() {
        }

        byte[] getBuffer() {
            return this.buf;
        }

        int getCount() {
            return this.count;
        }
    }

}

