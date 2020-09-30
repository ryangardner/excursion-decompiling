/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.io.ByteStreamsKt$iterator
 */
package kotlin.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ByteIterator;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(bv={1, 0, 3}, d1={"\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0007\u001a\u00020\b*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u000b\u001a\u00020\f*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\r\u001a\u00020\u000e*\u00020\u000f2\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u001c\u0010\u0010\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\r\u0010\u0013\u001a\u00020\u000e*\u00020\u0014H\u0087\b\u001a\u001d\u0010\u0013\u001a\u00020\u000e*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0018*\u00020\u0001H\u0086\u0002\u001a\f\u0010\u0019\u001a\u00020\u0014*\u00020\u0002H\u0007\u001a\u0016\u0010\u0019\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u0004H\u0007\u001a\u0017\u0010\u001b\u001a\u00020\u001c*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u001d\u001a\u00020\u001e*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u00a8\u0006\u001f"}, d2={"buffered", "Ljava/io/BufferedInputStream;", "Ljava/io/InputStream;", "bufferSize", "", "Ljava/io/BufferedOutputStream;", "Ljava/io/OutputStream;", "bufferedReader", "Ljava/io/BufferedReader;", "charset", "Ljava/nio/charset/Charset;", "bufferedWriter", "Ljava/io/BufferedWriter;", "byteInputStream", "Ljava/io/ByteArrayInputStream;", "", "copyTo", "", "out", "inputStream", "", "offset", "length", "iterator", "Lkotlin/collections/ByteIterator;", "readBytes", "estimatedSize", "reader", "Ljava/io/InputStreamReader;", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class ByteStreamsKt {
    private static final BufferedInputStream buffered(InputStream inputStream2, int n) {
        if (!(inputStream2 instanceof BufferedInputStream)) return new BufferedInputStream(inputStream2, n);
        return (BufferedInputStream)inputStream2;
    }

    private static final BufferedOutputStream buffered(OutputStream outputStream2, int n) {
        if (!(outputStream2 instanceof BufferedOutputStream)) return new BufferedOutputStream(outputStream2, n);
        return (BufferedOutputStream)outputStream2;
    }

    static /* synthetic */ BufferedInputStream buffered$default(InputStream inputStream2, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 8192;
        }
        if (!(inputStream2 instanceof BufferedInputStream)) return new BufferedInputStream(inputStream2, n);
        return (BufferedInputStream)inputStream2;
    }

    static /* synthetic */ BufferedOutputStream buffered$default(OutputStream outputStream2, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 8192;
        }
        if (!(outputStream2 instanceof BufferedOutputStream)) return new BufferedOutputStream(outputStream2, n);
        return (BufferedOutputStream)outputStream2;
    }

    private static final BufferedReader bufferedReader(InputStream closeable, Charset charset) {
        if (!((closeable = (Reader)new InputStreamReader((InputStream)closeable, charset)) instanceof BufferedReader)) return new BufferedReader((Reader)closeable, 8192);
        return (BufferedReader)closeable;
    }

    static /* synthetic */ BufferedReader bufferedReader$default(InputStream closeable, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if (!((closeable = (Reader)new InputStreamReader((InputStream)closeable, charset)) instanceof BufferedReader)) return new BufferedReader((Reader)closeable, 8192);
        return (BufferedReader)closeable;
    }

    private static final BufferedWriter bufferedWriter(OutputStream closeable, Charset charset) {
        if (!((closeable = (Writer)new OutputStreamWriter((OutputStream)closeable, charset)) instanceof BufferedWriter)) return new BufferedWriter((Writer)closeable, 8192);
        return (BufferedWriter)closeable;
    }

    static /* synthetic */ BufferedWriter bufferedWriter$default(OutputStream closeable, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if (!((closeable = (Writer)new OutputStreamWriter((OutputStream)closeable, charset)) instanceof BufferedWriter)) return new BufferedWriter((Writer)closeable, 8192);
        return (BufferedWriter)closeable;
    }

    private static final ByteArrayInputStream byteInputStream(String arrby, Charset charset) {
        if (arrby == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        arrby = arrby.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
        return new ByteArrayInputStream(arrby);
    }

    static /* synthetic */ ByteArrayInputStream byteInputStream$default(String arrby, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if (arrby == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        arrby = arrby.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
        return new ByteArrayInputStream(arrby);
    }

    public static final long copyTo(InputStream inputStream2, OutputStream outputStream2, int n) {
        Intrinsics.checkParameterIsNotNull(inputStream2, "$this$copyTo");
        Intrinsics.checkParameterIsNotNull(outputStream2, "out");
        byte[] arrby = new byte[n];
        n = inputStream2.read(arrby);
        long l = 0L;
        while (n >= 0) {
            outputStream2.write(arrby, 0, n);
            l += (long)n;
            n = inputStream2.read(arrby);
        }
        return l;
    }

    public static /* synthetic */ long copyTo$default(InputStream inputStream2, OutputStream outputStream2, int n, int n2, Object object) {
        if ((n2 & 2) == 0) return ByteStreamsKt.copyTo(inputStream2, outputStream2, n);
        n = 8192;
        return ByteStreamsKt.copyTo(inputStream2, outputStream2, n);
    }

    private static final ByteArrayInputStream inputStream(byte[] arrby) {
        return new ByteArrayInputStream(arrby);
    }

    private static final ByteArrayInputStream inputStream(byte[] arrby, int n, int n2) {
        return new ByteArrayInputStream(arrby, n, n2);
    }

    public static final ByteIterator iterator(BufferedInputStream bufferedInputStream) {
        Intrinsics.checkParameterIsNotNull(bufferedInputStream, "$this$iterator");
        return new ByteIterator(bufferedInputStream){
            final /* synthetic */ BufferedInputStream $this_iterator;
            private boolean finished;
            private int nextByte;
            private boolean nextPrepared;
            {
                this.$this_iterator = bufferedInputStream;
                this.nextByte = -1;
            }

            private final void prepareNext() {
                int n;
                if (this.nextPrepared) return;
                if (this.finished) return;
                this.nextByte = n = this.$this_iterator.read();
                boolean bl = true;
                this.nextPrepared = true;
                if (n != -1) {
                    bl = false;
                }
                this.finished = bl;
            }

            public final boolean getFinished() {
                return this.finished;
            }

            public final int getNextByte() {
                return this.nextByte;
            }

            public final boolean getNextPrepared() {
                return this.nextPrepared;
            }

            public boolean hasNext() {
                this.prepareNext();
                return this.finished ^ true;
            }

            public byte nextByte() {
                this.prepareNext();
                if (this.finished) throw (java.lang.Throwable)new java.util.NoSuchElementException("Input stream is over.");
                byte by = (byte)this.nextByte;
                this.nextPrepared = false;
                return by;
            }

            public final void setFinished(boolean bl) {
                this.finished = bl;
            }

            public final void setNextByte(int n) {
                this.nextByte = n;
            }

            public final void setNextPrepared(boolean bl) {
                this.nextPrepared = bl;
            }
        };
    }

    public static final byte[] readBytes(InputStream arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$readBytes");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(8192, arrby.available()));
        ByteStreamsKt.copyTo$default((InputStream)arrby, byteArrayOutputStream, 0, 2, null);
        arrby = byteArrayOutputStream.toByteArray();
        Intrinsics.checkExpressionValueIsNotNull(arrby, "buffer.toByteArray()");
        return arrby;
    }

    @Deprecated(message="Use readBytes() overload without estimatedSize parameter", replaceWith=@ReplaceWith(expression="readBytes()", imports={}))
    public static final byte[] readBytes(InputStream arrby, int n) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$readBytes");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(n, arrby.available()));
        ByteStreamsKt.copyTo$default((InputStream)arrby, byteArrayOutputStream, 0, 2, null);
        arrby = byteArrayOutputStream.toByteArray();
        Intrinsics.checkExpressionValueIsNotNull(arrby, "buffer.toByteArray()");
        return arrby;
    }

    public static /* synthetic */ byte[] readBytes$default(InputStream inputStream2, int n, int n2, Object object) {
        if ((n2 & 1) == 0) return ByteStreamsKt.readBytes(inputStream2, n);
        n = 8192;
        return ByteStreamsKt.readBytes(inputStream2, n);
    }

    private static final InputStreamReader reader(InputStream inputStream2, Charset charset) {
        return new InputStreamReader(inputStream2, charset);
    }

    static /* synthetic */ InputStreamReader reader$default(InputStream inputStream2, Charset charset, int n, Object object) {
        if ((n & 1) == 0) return new InputStreamReader(inputStream2, charset);
        charset = Charsets.UTF_8;
        return new InputStreamReader(inputStream2, charset);
    }

    private static final OutputStreamWriter writer(OutputStream outputStream2, Charset charset) {
        return new OutputStreamWriter(outputStream2, charset);
    }

    static /* synthetic */ OutputStreamWriter writer$default(OutputStream outputStream2, Charset charset, int n, Object object) {
        if ((n & 1) == 0) return new OutputStreamWriter(outputStream2, charset);
        charset = Charsets.UTF_8;
        return new OutputStreamWriter(outputStream2, charset);
    }
}

