/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteProcessor;
import com.google.common.math.IntMath;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public final class ByteStreams {
    private static final int BUFFER_SIZE = 8192;
    private static final int MAX_ARRAY_LEN = 2147483639;
    private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream(){

        public String toString() {
            return "ByteStreams.nullOutputStream()";
        }

        @Override
        public void write(int n) {
        }

        @Override
        public void write(byte[] arrby) {
            Preconditions.checkNotNull(arrby);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) {
            Preconditions.checkNotNull(arrby);
        }
    };
    private static final int TO_BYTE_ARRAY_DEQUE_SIZE = 20;
    private static final int ZERO_COPY_CHUNK_SIZE = 524288;

    private ByteStreams() {
    }

    private static byte[] combineBuffers(Deque<byte[]> deque, int n) {
        byte[] arrby = new byte[n];
        int n2 = n;
        while (n2 > 0) {
            byte[] arrby2 = deque.removeFirst();
            int n3 = Math.min(n2, arrby2.length);
            System.arraycopy(arrby2, 0, arrby, n - n2, n3);
            n2 -= n3;
        }
        return arrby;
    }

    public static long copy(InputStream inputStream2, OutputStream outputStream2) throws IOException {
        Preconditions.checkNotNull(inputStream2);
        Preconditions.checkNotNull(outputStream2);
        byte[] arrby = ByteStreams.createBuffer();
        long l = 0L;
        int n;
        while ((n = inputStream2.read(arrby)) != -1) {
            outputStream2.write(arrby, 0, n);
            l += (long)n;
        }
        return l;
    }

    /*
     * Unable to fully structure code
     */
    public static long copy(ReadableByteChannel var0, WritableByteChannel var1_1) throws IOException {
        block3 : {
            Preconditions.checkNotNull(var0);
            Preconditions.checkNotNull(var1_1);
            var2_2 = var0 instanceof FileChannel;
            var3_3 = 0L;
            if (!var2_2) break block3;
            var0 = (FileChannel)var0;
            var3_3 = var5_4 = var0.position();
            do lbl-1000: // 3 sources:
            {
                var7_5 = var0.transferTo(var3_3, 524288L, var1_1);
                var9_6 = var3_3 + var7_5;
                var0.position(var9_6);
                var3_3 = var9_6;
                if (var7_5 > 0L) ** GOTO lbl-1000
                var3_3 = var9_6;
            } while (var9_6 < var0.size());
            return var9_6 - var5_4;
        }
        var11_7 = ByteBuffer.wrap(ByteStreams.createBuffer());
        while (var0.read(var11_7) != -1) {
            var11_7.flip();
            while (var11_7.hasRemaining()) {
                var3_3 += (long)var1_1.write(var11_7);
            }
            var11_7.clear();
        }
        return var3_3;
    }

    static byte[] createBuffer() {
        return new byte[8192];
    }

    public static long exhaust(InputStream inputStream2) throws IOException {
        long l;
        byte[] arrby = ByteStreams.createBuffer();
        long l2 = 0L;
        while ((l = (long)inputStream2.read(arrby)) != -1L) {
            l2 += l;
        }
        return l2;
    }

    public static InputStream limit(InputStream inputStream2, long l) {
        return new LimitedInputStream(inputStream2, l);
    }

    public static ByteArrayDataInput newDataInput(ByteArrayInputStream byteArrayInputStream) {
        return new ByteArrayDataInputStream(Preconditions.checkNotNull(byteArrayInputStream));
    }

    public static ByteArrayDataInput newDataInput(byte[] arrby) {
        return ByteStreams.newDataInput(new ByteArrayInputStream(arrby));
    }

    public static ByteArrayDataInput newDataInput(byte[] arrby, int n) {
        Preconditions.checkPositionIndex(n, arrby.length);
        return ByteStreams.newDataInput(new ByteArrayInputStream(arrby, n, arrby.length - n));
    }

    public static ByteArrayDataOutput newDataOutput() {
        return ByteStreams.newDataOutput(new ByteArrayOutputStream());
    }

    public static ByteArrayDataOutput newDataOutput(int n) {
        if (n < 0) throw new IllegalArgumentException(String.format("Invalid size: %s", n));
        return ByteStreams.newDataOutput(new ByteArrayOutputStream(n));
    }

    public static ByteArrayDataOutput newDataOutput(ByteArrayOutputStream byteArrayOutputStream) {
        return new ByteArrayDataOutputStream(Preconditions.checkNotNull(byteArrayOutputStream));
    }

    public static OutputStream nullOutputStream() {
        return NULL_OUTPUT_STREAM;
    }

    public static int read(InputStream inputStream2, byte[] arrby, int n, int n2) throws IOException {
        Preconditions.checkNotNull(inputStream2);
        Preconditions.checkNotNull(arrby);
        int n3 = 0;
        if (n2 < 0) {
            throw new IndexOutOfBoundsException(String.format("len (%s) cannot be negative", n2));
        }
        Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
        while (n3 < n2) {
            int n4 = inputStream2.read(arrby, n + n3, n2 - n3);
            if (n4 == -1) {
                return n3;
            }
            n3 += n4;
        }
        return n3;
    }

    public static <T> T readBytes(InputStream inputStream2, ByteProcessor<T> byteProcessor) throws IOException {
        int n;
        Preconditions.checkNotNull(inputStream2);
        Preconditions.checkNotNull(byteProcessor);
        byte[] arrby = ByteStreams.createBuffer();
        do {
            if ((n = inputStream2.read(arrby)) == -1) return byteProcessor.getResult();
        } while (byteProcessor.processBytes(arrby, 0, n));
        return byteProcessor.getResult();
    }

    public static void readFully(InputStream inputStream2, byte[] arrby) throws IOException {
        ByteStreams.readFully(inputStream2, arrby, 0, arrby.length);
    }

    public static void readFully(InputStream object, byte[] arrby, int n, int n2) throws IOException {
        if ((n = ByteStreams.read((InputStream)object, arrby, n, n2)) == n2) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("reached end of stream after reading ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" bytes; ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" bytes expected");
        throw new EOFException(((StringBuilder)object).toString());
    }

    public static void skipFully(InputStream object, long l) throws IOException {
        long l2 = ByteStreams.skipUpTo((InputStream)object, l);
        if (l2 >= l) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("reached end of stream after skipping ");
        ((StringBuilder)object).append(l2);
        ((StringBuilder)object).append(" bytes; ");
        ((StringBuilder)object).append(l);
        ((StringBuilder)object).append(" bytes expected");
        throw new EOFException(((StringBuilder)object).toString());
    }

    private static long skipSafely(InputStream inputStream2, long l) throws IOException {
        int n = inputStream2.available();
        if (n != 0) return inputStream2.skip(Math.min((long)n, l));
        return 0L;
    }

    static long skipUpTo(InputStream inputStream2, long l) throws IOException {
        byte[] arrby = null;
        long l2 = 0L;
        while (l2 < l) {
            long l3 = l - l2;
            long l4 = ByteStreams.skipSafely(inputStream2, l3);
            byte[] arrby2 = arrby;
            long l5 = l4;
            if (l4 == 0L) {
                int n = (int)Math.min(l3, 8192L);
                arrby2 = arrby;
                if (arrby == null) {
                    arrby2 = new byte[n];
                }
                l5 = l4 = (long)inputStream2.read(arrby2, 0, n);
                if (l4 == -1L) {
                    return l2;
                }
            }
            l2 += l5;
            arrby = arrby2;
        }
        return l2;
    }

    public static byte[] toByteArray(InputStream inputStream2) throws IOException {
        Preconditions.checkNotNull(inputStream2);
        return ByteStreams.toByteArrayInternal(inputStream2, new ArrayDeque<byte[]>(20), 0);
    }

    static byte[] toByteArray(InputStream object, long l) throws IOException {
        int n;
        int n2;
        boolean bl = l >= 0L;
        Preconditions.checkArgument(bl, "expectedSize (%s) must be non-negative", l);
        if (l > 0x7FFFFFF7L) {
            object = new StringBuilder();
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append(" bytes is too large to fit in a byte array");
            throw new OutOfMemoryError(((StringBuilder)object).toString());
        }
        int n3 = (int)l;
        byte[] arrby = new byte[n3];
        for (n2 = n3; n2 > 0; n2 -= n) {
            int n4 = n3 - n2;
            n = ((InputStream)object).read(arrby, n4, n2);
            if (n != -1) continue;
            return Arrays.copyOf(arrby, n4);
        }
        n2 = ((InputStream)object).read();
        if (n2 == -1) {
            return arrby;
        }
        ArrayDeque<byte[]> arrayDeque = new ArrayDeque<byte[]>(22);
        arrayDeque.add(arrby);
        arrayDeque.add(new byte[]{(byte)n2});
        return ByteStreams.toByteArrayInternal((InputStream)object, arrayDeque, n3 + 1);
    }

    private static byte[] toByteArrayInternal(InputStream inputStream2, Deque<byte[]> deque, int n) throws IOException {
        int n2 = 8192;
        int n3 = n;
        n = n2;
        do {
            int n4;
            if (n3 >= 2147483639) {
                if (inputStream2.read() != -1) throw new OutOfMemoryError("input is too large to fit in a byte array");
                return ByteStreams.combineBuffers(deque, 2147483639);
            }
            int n5 = Math.min(n, 2147483639 - n3);
            byte[] arrby = new byte[n5];
            deque.add(arrby);
            for (n2 = 0; n2 < n5; n2 += n4, n3 += n4) {
                n4 = inputStream2.read(arrby, n2, n5 - n2);
                if (n4 != -1) continue;
                return ByteStreams.combineBuffers(deque, n3);
            }
            n = IntMath.saturatedMultiply(n, 2);
        } while (true);
    }

    private static class ByteArrayDataInputStream
    implements ByteArrayDataInput {
        final DataInput input;

        ByteArrayDataInputStream(ByteArrayInputStream byteArrayInputStream) {
            this.input = new DataInputStream(byteArrayInputStream);
        }

        @Override
        public boolean readBoolean() {
            try {
                return this.input.readBoolean();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public byte readByte() {
            try {
                return this.input.readByte();
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
            catch (EOFException eOFException) {
                throw new IllegalStateException(eOFException);
            }
        }

        @Override
        public char readChar() {
            try {
                return this.input.readChar();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public double readDouble() {
            try {
                return this.input.readDouble();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public float readFloat() {
            try {
                return this.input.readFloat();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public void readFully(byte[] arrby) {
            try {
                this.input.readFully(arrby);
                return;
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public void readFully(byte[] arrby, int n, int n2) {
            try {
                this.input.readFully(arrby, n, n2);
                return;
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public int readInt() {
            try {
                return this.input.readInt();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public String readLine() {
            try {
                return this.input.readLine();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public long readLong() {
            try {
                return this.input.readLong();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public short readShort() {
            try {
                return this.input.readShort();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public String readUTF() {
            try {
                return this.input.readUTF();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public int readUnsignedByte() {
            try {
                return this.input.readUnsignedByte();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public int readUnsignedShort() {
            try {
                return this.input.readUnsignedShort();
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public int skipBytes(int n) {
            try {
                return this.input.skipBytes(n);
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }
    }

    private static class ByteArrayDataOutputStream
    implements ByteArrayDataOutput {
        final ByteArrayOutputStream byteArrayOutputStream;
        final DataOutput output;

        ByteArrayDataOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
            this.byteArrayOutputStream = byteArrayOutputStream;
            this.output = new DataOutputStream(byteArrayOutputStream);
        }

        @Override
        public byte[] toByteArray() {
            return this.byteArrayOutputStream.toByteArray();
        }

        @Override
        public void write(int n) {
            try {
                this.output.write(n);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void write(byte[] arrby) {
            try {
                this.output.write(arrby);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void write(byte[] arrby, int n, int n2) {
            try {
                this.output.write(arrby, n, n2);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeBoolean(boolean bl) {
            try {
                this.output.writeBoolean(bl);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeByte(int n) {
            try {
                this.output.writeByte(n);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeBytes(String string2) {
            try {
                this.output.writeBytes(string2);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeChar(int n) {
            try {
                this.output.writeChar(n);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeChars(String string2) {
            try {
                this.output.writeChars(string2);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeDouble(double d) {
            try {
                this.output.writeDouble(d);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeFloat(float f) {
            try {
                this.output.writeFloat(f);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeInt(int n) {
            try {
                this.output.writeInt(n);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeLong(long l) {
            try {
                this.output.writeLong(l);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeShort(int n) {
            try {
                this.output.writeShort(n);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }

        @Override
        public void writeUTF(String string2) {
            try {
                this.output.writeUTF(string2);
                return;
            }
            catch (IOException iOException) {
                throw new AssertionError(iOException);
            }
        }
    }

    private static final class LimitedInputStream
    extends FilterInputStream {
        private long left;
        private long mark = -1L;

        LimitedInputStream(InputStream inputStream2, long l) {
            super(inputStream2);
            Preconditions.checkNotNull(inputStream2);
            boolean bl = l >= 0L;
            Preconditions.checkArgument(bl, "limit must be non-negative");
            this.left = l;
        }

        @Override
        public int available() throws IOException {
            return (int)Math.min((long)this.in.available(), this.left);
        }

        @Override
        public void mark(int n) {
            synchronized (this) {
                this.in.mark(n);
                this.mark = this.left;
                return;
            }
        }

        @Override
        public int read() throws IOException {
            if (this.left == 0L) {
                return -1;
            }
            int n = this.in.read();
            if (n == -1) return n;
            --this.left;
            return n;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            long l = this.left;
            if (l == 0L) {
                return -1;
            }
            if ((n = this.in.read(arrby, n, n2 = (int)Math.min((long)n2, l))) == -1) return n;
            this.left -= (long)n;
            return n;
        }

        @Override
        public void reset() throws IOException {
            synchronized (this) {
                if (!this.in.markSupported()) {
                    IOException iOException = new IOException("Mark not supported");
                    throw iOException;
                }
                if (this.mark != -1L) {
                    this.in.reset();
                    this.left = this.mark;
                    return;
                }
                IOException iOException = new IOException("Mark not set");
                throw iOException;
            }
        }

        @Override
        public long skip(long l) throws IOException {
            l = Math.min(l, this.left);
            l = this.in.skip(l);
            this.left -= l;
            return l;
        }
    }

}

