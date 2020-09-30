package okio;

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

// $FF: synthetic class
@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000D\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0007\u001a\u0016\u0010\b\u001a\u00020\u0006*\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u0001H\u0007\u001a\n\u0010\b\u001a\u00020\u0006*\u00020\n\u001a\n\u0010\b\u001a\u00020\u0006*\u00020\u000b\u001a%\u0010\b\u001a\u00020\u0006*\u00020\f2\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000fH\u0007¢\u0006\u0002\u0010\u0010\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0007\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u000b\u001a%\u0010\u0011\u001a\u00020\u0012*\u00020\f2\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000fH\u0007¢\u0006\u0002\u0010\u0014\"\u001c\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00038@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0004¨\u0006\u0015"},
   d2 = {"isAndroidGetsocknameError", "", "Ljava/lang/AssertionError;", "Lkotlin/AssertionError;", "(Ljava/lang/AssertionError;)Z", "appendingSink", "Lokio/Sink;", "Ljava/io/File;", "sink", "append", "Ljava/io/OutputStream;", "Ljava/net/Socket;", "Ljava/nio/file/Path;", "options", "", "Ljava/nio/file/OpenOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Sink;", "source", "Lokio/Source;", "Ljava/io/InputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Lokio/Source;", "okio"},
   k = 5,
   mv = {1, 1, 16},
   xs = "okio/Okio"
)
final class Okio__JvmOkioKt {
   public static final Sink appendingSink(File var0) throws FileNotFoundException {
      Intrinsics.checkParameterIsNotNull(var0, "$this$appendingSink");
      return Okio.sink((OutputStream)(new FileOutputStream(var0, true)));
   }

   public static final boolean isAndroidGetsocknameError(AssertionError var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$isAndroidGetsocknameError");
      Throwable var1 = var0.getCause();
      boolean var2 = false;
      boolean var3 = var2;
      if (var1 != null) {
         String var5 = var0.getMessage();
         boolean var4;
         if (var5 != null) {
            var4 = StringsKt.contains$default((CharSequence)var5, (CharSequence)"getsockname failed", false, 2, (Object)null);
         } else {
            var4 = false;
         }

         var3 = var2;
         if (var4) {
            var3 = true;
         }
      }

      return var3;
   }

   public static final Sink sink(File var0) throws FileNotFoundException {
      return Okio.sink$default(var0, false, 1, (Object)null);
   }

   public static final Sink sink(File var0, boolean var1) throws FileNotFoundException {
      Intrinsics.checkParameterIsNotNull(var0, "$this$sink");
      return Okio.sink((OutputStream)(new FileOutputStream(var0, var1)));
   }

   public static final Sink sink(OutputStream var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$sink");
      return (Sink)(new OutputStreamSink(var0, new Timeout()));
   }

   public static final Sink sink(Socket var0) throws IOException {
      Intrinsics.checkParameterIsNotNull(var0, "$this$sink");
      SocketAsyncTimeout var1 = new SocketAsyncTimeout(var0);
      OutputStream var2 = var0.getOutputStream();
      Intrinsics.checkExpressionValueIsNotNull(var2, "getOutputStream()");
      return var1.sink((Sink)(new OutputStreamSink(var2, (Timeout)var1)));
   }

   public static final Sink sink(Path var0, OpenOption... var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var0, "$this$sink");
      Intrinsics.checkParameterIsNotNull(var1, "options");
      OutputStream var2 = Files.newOutputStream(var0, (OpenOption[])Arrays.copyOf(var1, var1.length));
      Intrinsics.checkExpressionValueIsNotNull(var2, "Files.newOutputStream(this, *options)");
      return Okio.sink(var2);
   }

   // $FF: synthetic method
   public static Sink sink$default(File var0, boolean var1, int var2, Object var3) throws FileNotFoundException {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      return Okio.sink(var0, var1);
   }

   public static final Source source(File var0) throws FileNotFoundException {
      Intrinsics.checkParameterIsNotNull(var0, "$this$source");
      return Okio.source((InputStream)(new FileInputStream(var0)));
   }

   public static final Source source(InputStream var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$source");
      return (Source)(new InputStreamSource(var0, new Timeout()));
   }

   public static final Source source(Socket var0) throws IOException {
      Intrinsics.checkParameterIsNotNull(var0, "$this$source");
      SocketAsyncTimeout var1 = new SocketAsyncTimeout(var0);
      InputStream var2 = var0.getInputStream();
      Intrinsics.checkExpressionValueIsNotNull(var2, "getInputStream()");
      return var1.source((Source)(new InputStreamSource(var2, (Timeout)var1)));
   }

   public static final Source source(Path var0, OpenOption... var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var0, "$this$source");
      Intrinsics.checkParameterIsNotNull(var1, "options");
      InputStream var2 = Files.newInputStream(var0, (OpenOption[])Arrays.copyOf(var1, var1.length));
      Intrinsics.checkExpressionValueIsNotNull(var2, "Files.newInputStream(this, *options)");
      return Okio.source(var2);
   }
}
