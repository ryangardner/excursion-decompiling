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

@Metadata(
   bv = {1, 0, 3},
   d1 = {"okio/Okio__JvmOkioKt", "okio/Okio__OkioKt"},
   k = 4,
   mv = {1, 1, 16}
)
public final class Okio {
   public static final Sink appendingSink(File var0) throws FileNotFoundException {
      return Okio__JvmOkioKt.appendingSink(var0);
   }

   public static final Sink blackhole() {
      return Okio__OkioKt.blackhole();
   }

   public static final BufferedSink buffer(Sink var0) {
      return Okio__OkioKt.buffer(var0);
   }

   public static final BufferedSource buffer(Source var0) {
      return Okio__OkioKt.buffer(var0);
   }

   public static final boolean isAndroidGetsocknameError(AssertionError var0) {
      return Okio__JvmOkioKt.isAndroidGetsocknameError(var0);
   }

   public static final Sink sink(File var0) throws FileNotFoundException {
      return sink$default(var0, false, 1, (Object)null);
   }

   public static final Sink sink(File var0, boolean var1) throws FileNotFoundException {
      return Okio__JvmOkioKt.sink(var0, var1);
   }

   public static final Sink sink(OutputStream var0) {
      return Okio__JvmOkioKt.sink(var0);
   }

   public static final Sink sink(Socket var0) throws IOException {
      return Okio__JvmOkioKt.sink(var0);
   }

   public static final Sink sink(Path var0, OpenOption... var1) throws IOException {
      return Okio__JvmOkioKt.sink(var0, var1);
   }

   // $FF: synthetic method
   public static Sink sink$default(File var0, boolean var1, int var2, Object var3) throws FileNotFoundException {
      return Okio__JvmOkioKt.sink$default(var0, var1, var2, var3);
   }

   public static final Source source(File var0) throws FileNotFoundException {
      return Okio__JvmOkioKt.source(var0);
   }

   public static final Source source(InputStream var0) {
      return Okio__JvmOkioKt.source(var0);
   }

   public static final Source source(Socket var0) throws IOException {
      return Okio__JvmOkioKt.source(var0);
   }

   public static final Source source(Path var0, OpenOption... var1) throws IOException {
      return Okio__JvmOkioKt.source(var0, var1);
   }
}
