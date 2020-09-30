/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.InputStreamSource;
import okio.Okio;
import okio.OutputStreamSink;
import okio.Sink;
import okio.SocketAsyncTimeout;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000D\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0007\u001a\u0016\u0010\b\u001a\u00020\u0006*\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u0001H\u0007\u001a\n\u0010\b\u001a\u00020\u0006*\u00020\n\u001a\n\u0010\b\u001a\u00020\u0006*\u00020\u000b\u001a%\u0010\b\u001a\u00020\u0006*\u00020\f2\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000fH\u0007\u00a2\u0006\u0002\u0010\u0010\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0007\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u000b\u001a%\u0010\u0011\u001a\u00020\u0012*\u00020\f2\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000fH\u0007\u00a2\u0006\u0002\u0010\u0014\"\u001c\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00038@X\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0000\u0010\u0004\u00a8\u0006\u0015"}, d2={"isAndroidGetsocknameError", "", "Ljava/lang/AssertionError;", "Lkotlin/AssertionError;", "(Ljava/lang/AssertionError;)Z", "appendingSink", "Lokio/Sink;", "Ljava/io/File;", "sink", "append", "Ljava/io/OutputStream;", "Ljava/net/Socket;", "Ljava/nio/file/Path;", "options", "", "Ljava/nio/file/OpenOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Sink;", "source", "Lokio/Source;", "Ljava/io/InputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Source;", "okio"}, k=5, mv={1, 1, 16}, xs="okio/Okio")
final class Okio__JvmOkioKt {
    public static final Sink appendingSink(File file) throws FileNotFoundException {
        Intrinsics.checkParameterIsNotNull(file, "$this$appendingSink");
        return Okio.sink(new FileOutputStream(file, true));
    }

    public static final boolean isAndroidGetsocknameError(AssertionError object) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull(object, "$this$isAndroidGetsocknameError");
        Throwable throwable = ((Throwable)object).getCause();
        boolean bl2 = bl = false;
        if (throwable == null) return bl2;
        boolean bl3 = (object = ((Throwable)object).getMessage()) != null ? StringsKt.contains$default((CharSequence)object, "getsockname failed", false, 2, null) : false;
        bl2 = bl;
        if (!bl3) return bl2;
        return true;
    }

    public static final Sink sink(File file) throws FileNotFoundException {
        return Okio.sink$default(file, false, 1, null);
    }

    public static final Sink sink(File file, boolean bl) throws FileNotFoundException {
        Intrinsics.checkParameterIsNotNull(file, "$this$sink");
        return Okio.sink(new FileOutputStream(file, bl));
    }

    public static final Sink sink(OutputStream outputStream2) {
        Intrinsics.checkParameterIsNotNull(outputStream2, "$this$sink");
        return new OutputStreamSink(outputStream2, new Timeout());
    }

    public static final Sink sink(Socket closeable) throws IOException {
        Intrinsics.checkParameterIsNotNull(closeable, "$this$sink");
        SocketAsyncTimeout socketAsyncTimeout = new SocketAsyncTimeout((Socket)closeable);
        closeable = closeable.getOutputStream();
        Intrinsics.checkExpressionValueIsNotNull(closeable, "getOutputStream()");
        return socketAsyncTimeout.sink(new OutputStreamSink((OutputStream)closeable, socketAsyncTimeout));
    }

    public static final Sink sink(Path object, OpenOption ... arropenOption) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "$this$sink");
        Intrinsics.checkParameterIsNotNull(arropenOption, "options");
        object = Files.newOutputStream((Path)object, Arrays.copyOf(arropenOption, arropenOption.length));
        Intrinsics.checkExpressionValueIsNotNull(object, "Files.newOutputStream(this, *options)");
        return Okio.sink((OutputStream)object);
    }

    public static /* synthetic */ Sink sink$default(File file, boolean bl, int n, Object object) throws FileNotFoundException {
        if ((n & 1) == 0) return Okio.sink(file, bl);
        bl = false;
        return Okio.sink(file, bl);
    }

    public static final Source source(File file) throws FileNotFoundException {
        Intrinsics.checkParameterIsNotNull(file, "$this$source");
        return Okio.source(new FileInputStream(file));
    }

    public static final Source source(InputStream inputStream2) {
        Intrinsics.checkParameterIsNotNull(inputStream2, "$this$source");
        return new InputStreamSource(inputStream2, new Timeout());
    }

    public static final Source source(Socket closeable) throws IOException {
        Intrinsics.checkParameterIsNotNull(closeable, "$this$source");
        SocketAsyncTimeout socketAsyncTimeout = new SocketAsyncTimeout((Socket)closeable);
        closeable = closeable.getInputStream();
        Intrinsics.checkExpressionValueIsNotNull(closeable, "getInputStream()");
        return socketAsyncTimeout.source(new InputStreamSource((InputStream)closeable, socketAsyncTimeout));
    }

    public static final Source source(Path object, OpenOption ... arropenOption) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "$this$source");
        Intrinsics.checkParameterIsNotNull(arropenOption, "options");
        object = Files.newInputStream((Path)object, Arrays.copyOf(arropenOption, arropenOption.length));
        Intrinsics.checkExpressionValueIsNotNull(object, "Files.newInputStream(this, *options)");
        return Okio.source((InputStream)object);
    }
}

