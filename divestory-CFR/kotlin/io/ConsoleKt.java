/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.io.ConsoleKt$decoder
 */
package kotlin.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.io.ConsoleKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0005\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0011H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0012H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0013H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014H\u0087\b\u001a\t\u0010\u0015\u001a\u00020\nH\u0087\b\u001a\u0013\u0010\u0015\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0011H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0012H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0013H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014H\u0087\b\u001a\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u001a\u001a\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\f\u0010\u001a\u001a\u00020\r*\u00020\u001bH\u0002\u001a\f\u0010\u001c\u001a\u00020\n*\u00020\u001dH\u0002\u001a\u0018\u0010\u001e\u001a\u00020\n*\u00020\u001b2\n\u0010\u001f\u001a\u00060 j\u0002`!H\u0002\u001a$\u0010\"\u001a\u00020\r*\u00020\u00042\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\rH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006'"}, d2={"BUFFER_SIZE", "", "LINE_SEPARATOR_MAX_LENGTH", "decoder", "Ljava/nio/charset/CharsetDecoder;", "getDecoder", "()Ljava/nio/charset/CharsetDecoder;", "decoder$delegate", "Lkotlin/Lazy;", "print", "", "message", "", "", "", "", "", "", "", "", "", "println", "readLine", "", "inputStream", "Ljava/io/InputStream;", "endsWithLineSeparator", "Ljava/nio/CharBuffer;", "flipBack", "Ljava/nio/Buffer;", "offloadPrefixTo", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "tryDecode", "byteBuffer", "Ljava/nio/ByteBuffer;", "charBuffer", "isEndOfStream", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class ConsoleKt {
    private static final int BUFFER_SIZE = 32;
    private static final int LINE_SEPARATOR_MAX_LENGTH = 2;
    private static final Lazy decoder$delegate = LazyKt.lazy(decoder.2.INSTANCE);

    private static final boolean endsWithLineSeparator(CharBuffer charBuffer) {
        int n = charBuffer.position();
        boolean bl = true;
        if (n <= 0) return false;
        if (charBuffer.get(n - 1) != '\n') return false;
        return bl;
    }

    private static final void flipBack(Buffer buffer) {
        buffer.position(buffer.limit());
        buffer.limit(buffer.capacity());
    }

    private static final CharsetDecoder getDecoder() {
        return (CharsetDecoder)decoder$delegate.getValue();
    }

    private static final void offloadPrefixTo(CharBuffer charBuffer, StringBuilder stringBuilder) {
        charBuffer.flip();
        int n = charBuffer.limit();
        int n2 = 0;
        do {
            if (n2 >= n - 1) {
                charBuffer.compact();
                return;
            }
            stringBuilder.append(charBuffer.get());
            ++n2;
        } while (true);
    }

    private static final void print(byte by) {
        System.out.print((Object)by);
    }

    private static final void print(char c) {
        System.out.print(c);
    }

    private static final void print(double d) {
        System.out.print(d);
    }

    private static final void print(float f) {
        System.out.print(f);
    }

    private static final void print(int n) {
        System.out.print(n);
    }

    private static final void print(long l) {
        System.out.print(l);
    }

    private static final void print(Object object) {
        System.out.print(object);
    }

    private static final void print(short s) {
        System.out.print((Object)s);
    }

    private static final void print(boolean bl) {
        System.out.print(bl);
    }

    private static final void print(char[] arrc) {
        System.out.print(arrc);
    }

    private static final void println() {
        System.out.println();
    }

    private static final void println(byte by) {
        System.out.println((Object)by);
    }

    private static final void println(char c) {
        System.out.println(c);
    }

    private static final void println(double d) {
        System.out.println(d);
    }

    private static final void println(float f) {
        System.out.println(f);
    }

    private static final void println(int n) {
        System.out.println(n);
    }

    private static final void println(long l) {
        System.out.println(l);
    }

    private static final void println(Object object) {
        System.out.println(object);
    }

    private static final void println(short s) {
        System.out.println((Object)s);
    }

    private static final void println(boolean bl) {
        System.out.println(bl);
    }

    private static final void println(char[] arrc) {
        System.out.println(arrc);
    }

    public static final String readLine() {
        InputStream inputStream2 = System.in;
        Intrinsics.checkExpressionValueIsNotNull(inputStream2, "System.`in`");
        return ConsoleKt.readLine(inputStream2, ConsoleKt.getDecoder());
    }

    public static final String readLine(InputStream inputStream2, CharsetDecoder charsetDecoder) {
        int n;
        Intrinsics.checkParameterIsNotNull(inputStream2, "inputStream");
        Intrinsics.checkParameterIsNotNull(charsetDecoder, "decoder");
        float f = charsetDecoder.maxCharsPerByte();
        float f2 = (float)true;
        int n2 = 0;
        int n3 = f <= f2 ? 1 : 0;
        if (n3 == 0) throw (Throwable)new IllegalArgumentException("Encodings with multiple chars per byte are not supported".toString());
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        CharBuffer charBuffer = CharBuffer.allocate(4);
        StringBuilder stringBuilder = new StringBuilder();
        n3 = n = inputStream2.read();
        if (n == -1) {
            return null;
        }
        do {
            byteBuffer.put((byte)n3);
            Intrinsics.checkExpressionValueIsNotNull(byteBuffer, "byteBuffer");
            Intrinsics.checkExpressionValueIsNotNull(charBuffer, "charBuffer");
            if (ConsoleKt.tryDecode(charsetDecoder, byteBuffer, charBuffer, false)) {
                if (ConsoleKt.endsWithLineSeparator(charBuffer)) break;
                if (charBuffer.remaining() < 2) {
                    ConsoleKt.offloadPrefixTo(charBuffer, stringBuilder);
                }
            }
            n3 = n = inputStream2.read();
        } while (n != -1);
        ConsoleKt.tryDecode(charsetDecoder, byteBuffer, charBuffer, true);
        charsetDecoder.reset();
        n3 = n = charBuffer.position();
        if (n > 0) {
            n3 = n;
            if (charBuffer.get(n - 1) == '\n') {
                n3 = --n;
                if (n > 0) {
                    n3 = n;
                    if (charBuffer.get(n - 1) == '\r') {
                        n3 = n - 1;
                    }
                }
            }
        }
        charBuffer.flip();
        while (n2 < n3) {
            stringBuilder.append(charBuffer.get());
            ++n2;
        }
        return stringBuilder.toString();
    }

    private static final boolean tryDecode(CharsetDecoder object, ByteBuffer byteBuffer, CharBuffer charBuffer, boolean bl) {
        int n = charBuffer.position();
        byteBuffer.flip();
        object = ((CharsetDecoder)object).decode(byteBuffer, charBuffer, bl);
        if (((CoderResult)object).isError()) {
            ((CoderResult)object).throwException();
        }
        if (bl = charBuffer.position() > n) {
            byteBuffer.clear();
            return bl;
        }
        ConsoleKt.flipBack(byteBuffer);
        return bl;
    }
}

