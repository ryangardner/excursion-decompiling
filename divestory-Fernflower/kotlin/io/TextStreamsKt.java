package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000b0\r\u001a\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0002\u001a\u0017\u0010\u0016\u001a\u00020\u000e*\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0087\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u000eH\u0087\b\u001a5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c*\u00020\u00022\u0018\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0004\u0012\u0002H\u001c0\rH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001f\u0082\u0002\b\n\u0006\b\u0011(\u001e0\u0001¨\u0006 "},
   d2 = {"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class TextStreamsKt {
   private static final BufferedReader buffered(Reader var0, int var1) {
      BufferedReader var2;
      if (var0 instanceof BufferedReader) {
         var2 = (BufferedReader)var0;
      } else {
         var2 = new BufferedReader(var0, var1);
      }

      return var2;
   }

   private static final BufferedWriter buffered(Writer var0, int var1) {
      BufferedWriter var2;
      if (var0 instanceof BufferedWriter) {
         var2 = (BufferedWriter)var0;
      } else {
         var2 = new BufferedWriter(var0, var1);
      }

      return var2;
   }

   // $FF: synthetic method
   static BufferedReader buffered$default(Reader var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 8192;
      }

      BufferedReader var4;
      if (var0 instanceof BufferedReader) {
         var4 = (BufferedReader)var0;
      } else {
         var4 = new BufferedReader(var0, var1);
      }

      return var4;
   }

   // $FF: synthetic method
   static BufferedWriter buffered$default(Writer var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 8192;
      }

      BufferedWriter var4;
      if (var0 instanceof BufferedWriter) {
         var4 = (BufferedWriter)var0;
      } else {
         var4 = new BufferedWriter(var0, var1);
      }

      return var4;
   }

   public static final long copyTo(Reader var0, Writer var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$copyTo");
      Intrinsics.checkParameterIsNotNull(var1, "out");
      char[] var3 = new char[var2];
      var2 = var0.read(var3);

      long var4;
      for(var4 = 0L; var2 >= 0; var2 = var0.read(var3)) {
         var1.write(var3, 0, var2);
         var4 += (long)var2;
      }

      return var4;
   }

   // $FF: synthetic method
   public static long copyTo$default(Reader var0, Writer var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      return copyTo(var0, var1, var2);
   }

   public static final void forEachLine(Reader var0, Function1<? super String, Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$forEachLine");
      Intrinsics.checkParameterIsNotNull(var1, "action");
      BufferedReader var24;
      if (var0 instanceof BufferedReader) {
         var24 = (BufferedReader)var0;
      } else {
         var24 = new BufferedReader(var0, 8192);
      }

      Closeable var25 = (Closeable)var24;
      Throwable var2 = (Throwable)null;

      label220: {
         Throwable var10000;
         label215: {
            boolean var10001;
            Iterator var3;
            try {
               var3 = lineSequence((BufferedReader)var25).iterator();
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label215;
            }

            while(true) {
               try {
                  if (var3.hasNext()) {
                     var1.invoke(var3.next());
                     continue;
                  }
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break;
               }

               try {
                  Unit var26 = Unit.INSTANCE;
                  break label220;
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break;
               }
            }
         }

         var2 = var10000;

         try {
            throw var2;
         } finally {
            CloseableKt.closeFinally(var25, var2);
         }
      }

      CloseableKt.closeFinally(var25, var2);
   }

   public static final Sequence<String> lineSequence(BufferedReader var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$lineSequence");
      return SequencesKt.constrainOnce((Sequence)(new LinesSequence(var0)));
   }

   public static final byte[] readBytes(URL var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readBytes");
      Closeable var9 = (Closeable)var0.openStream();
      Throwable var1 = (Throwable)null;

      byte[] var11;
      try {
         InputStream var10 = (InputStream)var9;
         Intrinsics.checkExpressionValueIsNotNull(var10, "it");
         var11 = ByteStreamsKt.readBytes(var10);
      } catch (Throwable var8) {
         Throwable var2 = var8;

         try {
            throw var2;
         } finally {
            CloseableKt.closeFinally(var9, var8);
         }
      }

      CloseableKt.closeFinally(var9, var1);
      return var11;
   }

   public static final List<String> readLines(Reader var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readLines");
      final ArrayList var1 = new ArrayList();
      forEachLine(var0, (Function1)(new Function1<String, Unit>() {
         public final void invoke(String var1x) {
            Intrinsics.checkParameterIsNotNull(var1x, "it");
            var1.add(var1x);
         }
      }));
      return (List)var1;
   }

   public static final String readText(Reader var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readText");
      StringWriter var1 = new StringWriter();
      copyTo$default(var0, (Writer)var1, 0, 2, (Object)null);
      String var2 = var1.toString();
      Intrinsics.checkExpressionValueIsNotNull(var2, "buffer.toString()");
      return var2;
   }

   private static final String readText(URL var0, Charset var1) {
      return new String(readBytes(var0), var1);
   }

   // $FF: synthetic method
   static String readText$default(URL var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return new String(readBytes(var0), var1);
   }

   private static final StringReader reader(String var0) {
      return new StringReader(var0);
   }

   public static final <T> T useLines(Reader var0, Function1<? super Sequence<String>, ? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$useLines");
      Intrinsics.checkParameterIsNotNull(var1, "block");
      BufferedReader var15;
      if (var0 instanceof BufferedReader) {
         var15 = (BufferedReader)var0;
      } else {
         var15 = new BufferedReader(var0, 8192);
      }

      Closeable var16 = (Closeable)var15;
      Throwable var2 = (Throwable)null;

      Object var17;
      try {
         var17 = var1.invoke(lineSequence((BufferedReader)var16));
      } catch (Throwable var14) {
         var2 = var14;

         try {
            throw var2;
         } finally {
            InlineMarker.finallyStart(1);
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               label135:
               try {
                  var16.close();
               } finally {
                  break label135;
               }
            } else {
               CloseableKt.closeFinally(var16, var14);
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         CloseableKt.closeFinally(var16, var2);
      } else {
         var16.close();
      }

      InlineMarker.finallyEnd(1);
      return var17;
   }
}
