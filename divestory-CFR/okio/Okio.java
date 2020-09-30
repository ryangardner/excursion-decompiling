/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import kotlin.Metadata;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio__JvmOkioKt;
import okio.Okio__OkioKt;
import okio.Sink;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"okio/Okio__JvmOkioKt", "okio/Okio__OkioKt"}, k=4, mv={1, 1, 16})
public final class Okio {
    public static final Sink appendingSink(File file) throws FileNotFoundException {
        return Okio__JvmOkioKt.appendingSink(file);
    }

    public static final Sink blackhole() {
        return Okio__OkioKt.blackhole();
    }

    public static final BufferedSink buffer(Sink sink2) {
        return Okio__OkioKt.buffer(sink2);
    }

    public static final BufferedSource buffer(Source source2) {
        return Okio__OkioKt.buffer(source2);
    }

    public static final boolean isAndroidGetsocknameError(AssertionError assertionError) {
        return Okio__JvmOkioKt.isAndroidGetsocknameError(assertionError);
    }

    public static final Sink sink(File file) throws FileNotFoundException {
        return Okio.sink$default(file, false, 1, null);
    }

    public static final Sink sink(File file, boolean bl) throws FileNotFoundException {
        return Okio__JvmOkioKt.sink(file, bl);
    }

    public static final Sink sink(OutputStream outputStream2) {
        return Okio__JvmOkioKt.sink(outputStream2);
    }

    public static final Sink sink(Socket socket) throws IOException {
        return Okio__JvmOkioKt.sink(socket);
    }

    public static final Sink sink(Path path, OpenOption ... arropenOption) throws IOException {
        return Okio__JvmOkioKt.sink(path, arropenOption);
    }

    public static /* synthetic */ Sink sink$default(File file, boolean bl, int n, Object object) throws FileNotFoundException {
        return Okio__JvmOkioKt.sink$default(file, bl, n, object);
    }

    public static final Source source(File file) throws FileNotFoundException {
        return Okio__JvmOkioKt.source(file);
    }

    public static final Source source(InputStream inputStream2) {
        return Okio__JvmOkioKt.source(inputStream2);
    }

    public static final Source source(Socket socket) throws IOException {
        return Okio__JvmOkioKt.source(socket);
    }

    public static final Source source(Path path, OpenOption ... arropenOption) throws IOException {
        return Okio__JvmOkioKt.source(path, arropenOption);
    }
}

