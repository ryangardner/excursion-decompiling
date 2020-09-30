/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

final class ReaderInputStream
extends InputStream {
    private ByteBuffer byteBuffer;
    private CharBuffer charBuffer;
    private boolean doneFlushing;
    private boolean draining;
    private final CharsetEncoder encoder;
    private boolean endOfInput;
    private final Reader reader;
    private final byte[] singleByte;

    ReaderInputStream(Reader reader, Charset charset, int n) {
        this(reader, charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), n);
    }

    ReaderInputStream(Reader readable, CharsetEncoder charsetEncoder, int n) {
        boolean bl = true;
        this.singleByte = new byte[1];
        this.reader = Preconditions.checkNotNull(readable);
        this.encoder = Preconditions.checkNotNull(charsetEncoder);
        if (n <= 0) {
            bl = false;
        }
        Preconditions.checkArgument(bl, "bufferSize must be positive: %s", n);
        charsetEncoder.reset();
        readable = CharBuffer.allocate(n);
        this.charBuffer = readable;
        ((CharBuffer)readable).flip();
        this.byteBuffer = ByteBuffer.allocate(n);
    }

    private static int availableCapacity(Buffer buffer) {
        return buffer.capacity() - buffer.limit();
    }

    private int drain(byte[] arrby, int n, int n2) {
        n2 = Math.min(n2, this.byteBuffer.remaining());
        this.byteBuffer.get(arrby, n, n2);
        return n2;
    }

    private static CharBuffer grow(CharBuffer charBuffer) {
        CharBuffer charBuffer2 = CharBuffer.wrap(Arrays.copyOf(charBuffer.array(), charBuffer.capacity() * 2));
        charBuffer2.position(charBuffer.position());
        charBuffer2.limit(charBuffer.limit());
        return charBuffer2;
    }

    private void readMoreChars() throws IOException {
        if (ReaderInputStream.availableCapacity(this.charBuffer) == 0) {
            if (this.charBuffer.position() > 0) {
                this.charBuffer.compact().flip();
            } else {
                this.charBuffer = ReaderInputStream.grow(this.charBuffer);
            }
        }
        int n = this.charBuffer.limit();
        int n2 = this.reader.read(this.charBuffer.array(), n, ReaderInputStream.availableCapacity(this.charBuffer));
        if (n2 == -1) {
            this.endOfInput = true;
            return;
        }
        this.charBuffer.limit(n + n2);
    }

    private void startDraining(boolean bl) {
        this.byteBuffer.flip();
        if (bl && this.byteBuffer.remaining() == 0) {
            this.byteBuffer = ByteBuffer.allocate(this.byteBuffer.capacity() * 2);
            return;
        }
        this.draining = true;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    @Override
    public int read() throws IOException {
        if (this.read(this.singleByte) != 1) return -1;
        return UnsignedBytes.toInt(this.singleByte[0]);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        CoderResult coderResult;
        Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
        if (n2 == 0) {
            return 0;
        }
        boolean bl = this.endOfInput;
        int n3 = 0;
        block0 : do {
            boolean bl2 = bl;
            int n4 = n3;
            if (this.draining) {
                n4 = n3 + this.drain(arrby, n + n3, n2 - n3);
                if (n4 != n2 && !this.doneFlushing) {
                    this.draining = false;
                    this.byteBuffer.clear();
                    bl2 = bl;
                } else {
                    if (n4 <= 0) return -1;
                    return n4;
                }
            }
            do {
                if ((coderResult = this.doneFlushing ? CoderResult.UNDERFLOW : (bl2 ? this.encoder.flush(this.byteBuffer) : this.encoder.encode(this.charBuffer, this.byteBuffer, this.endOfInput))).isOverflow()) {
                    this.startDraining(true);
                    bl = bl2;
                    n3 = n4;
                    continue block0;
                }
                if (coderResult.isUnderflow()) {
                    if (bl2) {
                        this.doneFlushing = true;
                        this.startDraining(false);
                        bl = bl2;
                        n3 = n4;
                        continue block0;
                    }
                    if (this.endOfInput) {
                        bl2 = true;
                        continue;
                    }
                    this.readMoreChars();
                    continue;
                }
                if (coderResult.isError()) break block0;
            } while (true);
            break;
        } while (true);
        coderResult.throwException();
        return 0;
    }
}

