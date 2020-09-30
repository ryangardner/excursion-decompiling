/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.io.FilesKt__FileReadWriteKt$readLines
 */
package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.ExposingBufferByteArrayOutputStream;
import kotlin.io.FilesKt;
import kotlin.io.FilesKt__FilePathComponentsKt;
import kotlin.io.FilesKt__FileReadWriteKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;

@Metadata(bv={1, 0, 3}, d1={"\u0000z\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a!\u0010\n\u001a\u00020\u000b*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001a!\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001aB\u0010\u0010\u001a\u00020\u0001*\u00020\u000226\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001aJ\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\r26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001a7\u0010\u0018\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0019\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0002H\u0087\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0002H\u0087\b\u001a\u0017\u0010\u001f\u001a\u00020 *\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a\n\u0010!\u001a\u00020\u0004*\u00020\u0002\u001a\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0014\u0010$\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010%\u001a\u00020&*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a?\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010(*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010)\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070*\u0012\u0004\u0012\u0002H(0\u0019H\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010,\u001a\u0012\u0010-\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010.\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010/\u001a\u000200*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u0082\u0002\b\n\u0006\b\u0011(+0\u0001\u00a8\u00061"}, d2={"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/io/FilesKt")
class FilesKt__FileReadWriteKt
extends FilesKt__FilePathComponentsKt {
    public static final void appendBytes(File object, byte[] object2) {
        Intrinsics.checkParameterIsNotNull(object, "$this$appendBytes");
        Intrinsics.checkParameterIsNotNull(object2, "array");
        object = new FileOutputStream((File)object, true);
        Throwable throwable = null;
        try {
            ((FileOutputStream)object).write((byte[])object2);
            object2 = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            try {
                throw throwable2;
            }
            catch (Throwable throwable3) {
                CloseableKt.closeFinally((Closeable)object, throwable2);
                throw throwable3;
            }
        }
        CloseableKt.closeFinally((Closeable)object, throwable);
    }

    public static final void appendText(File file, String arrby, Charset charset) {
        Intrinsics.checkParameterIsNotNull(file, "$this$appendText");
        Intrinsics.checkParameterIsNotNull(arrby, "text");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        arrby = arrby.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
        FilesKt.appendBytes(file, arrby);
    }

    public static /* synthetic */ void appendText$default(File file, String string2, Charset charset, int n, Object object) {
        if ((n & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.appendText(file, string2, charset);
    }

    private static final BufferedReader bufferedReader(File object, Charset charset, int n) {
        if (!((object = (Reader)new InputStreamReader((InputStream)new FileInputStream((File)object), charset)) instanceof BufferedReader)) return new BufferedReader((Reader)object, n);
        return (BufferedReader)object;
    }

    static /* synthetic */ BufferedReader bufferedReader$default(File object, Charset charset, int n, int n2, Object object2) {
        if ((n2 & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((n2 & 2) != 0) {
            n = 8192;
        }
        if (!((object = (Reader)new InputStreamReader((InputStream)new FileInputStream((File)object), charset)) instanceof BufferedReader)) return new BufferedReader((Reader)object, n);
        return (BufferedReader)object;
    }

    private static final BufferedWriter bufferedWriter(File object, Charset charset, int n) {
        if (!((object = (Writer)new OutputStreamWriter((OutputStream)new FileOutputStream((File)object), charset)) instanceof BufferedWriter)) return new BufferedWriter((Writer)object, n);
        return (BufferedWriter)object;
    }

    static /* synthetic */ BufferedWriter bufferedWriter$default(File object, Charset charset, int n, int n2, Object object2) {
        if ((n2 & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((n2 & 2) != 0) {
            n = 8192;
        }
        if (!((object = (Writer)new OutputStreamWriter((OutputStream)new FileOutputStream((File)object), charset)) instanceof BufferedWriter)) return new BufferedWriter((Writer)object, n);
        return (BufferedWriter)object;
    }

    public static final void forEachBlock(File object, int n, Function2<? super byte[], ? super Integer, Unit> object2) {
        Intrinsics.checkParameterIsNotNull(object, "$this$forEachBlock");
        Intrinsics.checkParameterIsNotNull(object2, "action");
        byte[] arrby = new byte[RangesKt.coerceAtLeast(n, 512)];
        object = new FileInputStream((File)object);
        Throwable throwable = null;
        try {
            FileInputStream fileInputStream = (FileInputStream)object;
            do {
                if ((n = fileInputStream.read(arrby)) <= 0) {
                    object2 = Unit.INSTANCE;
                    break;
                }
                object2.invoke(arrby, n);
            } while (true);
        }
        catch (Throwable throwable2) {
            try {
                throw throwable2;
            }
            catch (Throwable throwable3) {
                CloseableKt.closeFinally((Closeable)object, throwable2);
                throw throwable3;
            }
        }
        CloseableKt.closeFinally((Closeable)object, throwable);
    }

    public static final void forEachBlock(File file, Function2<? super byte[], ? super Integer, Unit> function2) {
        Intrinsics.checkParameterIsNotNull(file, "$this$forEachBlock");
        Intrinsics.checkParameterIsNotNull(function2, "action");
        FilesKt.forEachBlock(file, 4096, function2);
    }

    public static final void forEachLine(File file, Charset charset, Function1<? super String, Unit> function1) {
        Intrinsics.checkParameterIsNotNull(file, "$this$forEachLine");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        Intrinsics.checkParameterIsNotNull(function1, "action");
        TextStreamsKt.forEachLine(new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(file), charset)), function1);
    }

    public static /* synthetic */ void forEachLine$default(File file, Charset charset, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.forEachLine(file, charset, function1);
    }

    private static final FileInputStream inputStream(File file) {
        return new FileInputStream(file);
    }

    private static final FileOutputStream outputStream(File file) {
        return new FileOutputStream(file);
    }

    private static final PrintWriter printWriter(File object, Charset charset) {
        if ((object = (Writer)new OutputStreamWriter((OutputStream)new FileOutputStream((File)object), charset)) instanceof BufferedWriter) {
            object = (BufferedWriter)object;
            return new PrintWriter((Writer)object);
        }
        object = new BufferedWriter((Writer)object, 8192);
        return new PrintWriter((Writer)object);
    }

    static /* synthetic */ PrintWriter printWriter$default(File object, Charset charset, int n, Object object2) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((object = (Writer)new OutputStreamWriter((OutputStream)new FileOutputStream((File)object), charset)) instanceof BufferedWriter) {
            object = (BufferedWriter)object;
            return new PrintWriter((Writer)object);
        }
        object = new BufferedWriter((Writer)object, 8192);
        return new PrintWriter((Writer)object);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static final byte[] readBytes(File var0) {
        Intrinsics.checkParameterIsNotNull(var0, "$this$readBytes");
        var1_2 = new FileInputStream((File)var0);
        var2_3 = null;
        try {
            block11 : {
                block10 : {
                    var3_4 = (FileInputStream)var1_2;
                    var4_5 = var0.length();
                    if (var4_5 <= (long)Integer.MAX_VALUE) {
                        var6_6 = (int)var4_5;
                        var7_7 = new byte[var6_6];
                        var9_13 = 0;
                    } else {
                        var7_10 = new StringBuilder();
                        var7_10.append("File ");
                        var7_10.append(var0);
                        var7_10.append(" is too big (");
                        var7_10.append(var4_5);
                        var7_10.append(" bytes) to fit in memory.");
                        var2_3 = new OutOfMemoryError(var7_10.toString());
                        throw var2_3;
                    }
                    for (var8_12 = var6_6; var8_12 > 0 && (var10_14 = var3_4.read(var7_7, var9_13, var8_12)) >= 0; var8_12 -= var10_14, var9_13 += var10_14) {
                    }
                    if (var8_12 <= 0) ** GOTO lbl32
                    var0 = Arrays.copyOf(var7_7, var9_13);
                    Intrinsics.checkExpressionValueIsNotNull(var0, "java.util.Arrays.copyOf(this, newSize)");
                    break block10;
lbl32: // 1 sources:
                    var9_13 = var3_4.read();
                    if (var9_13 == -1) {
                        var0 = var7_7;
                        break block10;
                    }
                    var11_15 = new ExposingBufferByteArrayOutputStream(8193);
                    var11_15.write(var9_13);
                    ByteStreamsKt.copyTo$default(var3_4, var11_15, 0, 2, null);
                    var9_13 = var11_15.size() + var6_6;
                    if (var9_13 < 0) break block11;
                    var0 = var11_15.getBuffer();
                    var7_8 = Arrays.copyOf(var7_7, var9_13);
                    Intrinsics.checkExpressionValueIsNotNull(var7_8, "java.util.Arrays.copyOf(this, newSize)");
                    var0 = ArraysKt.copyInto(var0, var7_8, var6_6, 0, var11_15.size());
                }
                CloseableKt.closeFinally(var1_2, (Throwable)var2_3);
                return var0;
            }
            var2_3 = new StringBuilder();
            var2_3.append("File ");
            var2_3.append(var0);
            var2_3.append(" is too big to fit in memory.");
            var7_9 = new OutOfMemoryError(var2_3.toString());
            throw (Throwable)var7_9;
        }
        catch (Throwable var0_1) {
            try {
                throw var0_1;
            }
            catch (Throwable var7_11) {
                CloseableKt.closeFinally(var1_2, var0_1);
                throw var7_11;
            }
        }
    }

    public static final List<String> readLines(File file, Charset charset) {
        Intrinsics.checkParameterIsNotNull(file, "$this$readLines");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        ArrayList arrayList = new ArrayList();
        FilesKt.forEachLine(file, charset, (Function1<? super String, Unit>)new Function1<String, Unit>(arrayList){
            final /* synthetic */ ArrayList $result;
            {
                this.$result = arrayList;
                super(1);
            }

            public final void invoke(String string2) {
                Intrinsics.checkParameterIsNotNull(string2, "it");
                this.$result.add(string2);
            }
        });
        return arrayList;
    }

    public static /* synthetic */ List readLines$default(File file, Charset charset, int n, Object object) {
        if ((n & 1) == 0) return FilesKt.readLines(file, charset);
        charset = Charsets.UTF_8;
        return FilesKt.readLines(file, charset);
    }

    public static final String readText(File object, Charset object2) {
        String string2;
        Intrinsics.checkParameterIsNotNull(object, "$this$readText");
        Intrinsics.checkParameterIsNotNull(object2, "charset");
        object = new InputStreamReader((InputStream)new FileInputStream((File)object), (Charset)object2);
        object2 = null;
        try {
            string2 = TextStreamsKt.readText((InputStreamReader)object);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                CloseableKt.closeFinally((Closeable)object, throwable);
                throw throwable2;
            }
        }
        CloseableKt.closeFinally((Closeable)object, (Throwable)object2);
        return string2;
    }

    public static /* synthetic */ String readText$default(File file, Charset charset, int n, Object object) {
        if ((n & 1) == 0) return FilesKt.readText(file, charset);
        charset = Charsets.UTF_8;
        return FilesKt.readText(file, charset);
    }

    private static final InputStreamReader reader(File file, Charset charset) {
        return new InputStreamReader((InputStream)new FileInputStream(file), charset);
    }

    static /* synthetic */ InputStreamReader reader$default(File file, Charset charset, int n, Object object) {
        if ((n & 1) == 0) return new InputStreamReader((InputStream)new FileInputStream(file), charset);
        charset = Charsets.UTF_8;
        return new InputStreamReader((InputStream)new FileInputStream(file), charset);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static final <T> T useLines(File object, Charset object2, Function1<? super Sequence<String>, ? extends T> function1) {
        Intrinsics.checkParameterIsNotNull(object, "$this$useLines");
        Intrinsics.checkParameterIsNotNull(object2, "charset");
        Intrinsics.checkParameterIsNotNull(function1, "block");
        object = new InputStreamReader((InputStream)new FileInputStream((File)object), (Charset)object2);
        object = object instanceof BufferedReader ? (BufferedReader)object : new BufferedReader((Reader)object, 8192);
        object = (Closeable)object;
        object2 = null;
        try {
            function1 = function1.invoke(TextStreamsKt.lineSequence((BufferedReader)object));
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                block9 : {
                    InlineMarker.finallyStart(1);
                    if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                        object.close();
                    }
                    CloseableKt.closeFinally((Closeable)object, throwable);
                    break block9;
                    catch (Throwable throwable3) {}
                }
                InlineMarker.finallyEnd(1);
                throw throwable2;
            }
        }
        InlineMarker.finallyStart(1);
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
            CloseableKt.closeFinally((Closeable)object, (Throwable)object2);
        } else {
            object.close();
        }
        InlineMarker.finallyEnd(1);
        return (T)function1;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static /* synthetic */ Object useLines$default(File object, Charset object2, Function1 function1, int n, Object object3) {
        if ((n & 1) != 0) {
            object2 = Charsets.UTF_8;
        }
        Intrinsics.checkParameterIsNotNull(object, "$this$useLines");
        Intrinsics.checkParameterIsNotNull(object2, "charset");
        Intrinsics.checkParameterIsNotNull(function1, "block");
        object = new InputStreamReader((InputStream)new FileInputStream((File)object), (Charset)object2);
        object = object instanceof BufferedReader ? (BufferedReader)object : new BufferedReader((Reader)object, 8192);
        object = (Closeable)object;
        object2 = null;
        try {
            function1 = function1.invoke(TextStreamsKt.lineSequence((BufferedReader)object));
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                block10 : {
                    InlineMarker.finallyStart(1);
                    if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                        object.close();
                    }
                    CloseableKt.closeFinally((Closeable)object, throwable);
                    break block10;
                    catch (Throwable throwable3) {}
                }
                InlineMarker.finallyEnd(1);
                throw throwable2;
            }
        }
        InlineMarker.finallyStart(1);
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
            CloseableKt.closeFinally((Closeable)object, (Throwable)object2);
        } else {
            object.close();
        }
        InlineMarker.finallyEnd(1);
        return function1;
    }

    public static final void writeBytes(File object, byte[] object2) {
        Intrinsics.checkParameterIsNotNull(object, "$this$writeBytes");
        Intrinsics.checkParameterIsNotNull(object2, "array");
        object = new FileOutputStream((File)object);
        Throwable throwable = null;
        try {
            ((FileOutputStream)object).write((byte[])object2);
            object2 = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            try {
                throw throwable2;
            }
            catch (Throwable throwable3) {
                CloseableKt.closeFinally((Closeable)object, throwable2);
                throw throwable3;
            }
        }
        CloseableKt.closeFinally((Closeable)object, throwable);
    }

    public static final void writeText(File file, String arrby, Charset charset) {
        Intrinsics.checkParameterIsNotNull(file, "$this$writeText");
        Intrinsics.checkParameterIsNotNull(arrby, "text");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        arrby = arrby.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
        FilesKt.writeBytes(file, arrby);
    }

    public static /* synthetic */ void writeText$default(File file, String string2, Charset charset, int n, Object object) {
        if ((n & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.writeText(file, string2, charset);
    }

    private static final OutputStreamWriter writer(File file, Charset charset) {
        return new OutputStreamWriter((OutputStream)new FileOutputStream(file), charset);
    }

    static /* synthetic */ OutputStreamWriter writer$default(File file, Charset charset, int n, Object object) {
        if ((n & 1) == 0) return new OutputStreamWriter((OutputStream)new FileOutputStream(file), charset);
        charset = Charsets.UTF_8;
        return new OutputStreamWriter((OutputStream)new FileOutputStream(file), charset);
    }
}

