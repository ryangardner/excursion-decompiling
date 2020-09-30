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
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000z\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a!\u0010\n\u001a\u00020\u000b*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001a!\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001aB\u0010\u0010\u001a\u00020\u0001*\u00020\u000226\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001aJ\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\r26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001a7\u0010\u0018\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0019\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0002H\u0087\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0002H\u0087\b\u001a\u0017\u0010\u001f\u001a\u00020 *\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a\n\u0010!\u001a\u00020\u0004*\u00020\u0002\u001a\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0014\u0010$\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010%\u001a\u00020&*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a?\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010(*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010)\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070*\u0012\u0004\u0012\u0002H(0\u0019H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010,\u001a\u0012\u0010-\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010.\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010/\u001a\u000200*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u0082\u0002\b\n\u0006\b\u0011(+0\u0001¨\u00061"},
   d2 = {"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/io/FilesKt"
)
class FilesKt__FileReadWriteKt extends FilesKt__FilePathComponentsKt {
   public FilesKt__FileReadWriteKt() {
   }

   public static final void appendBytes(File var0, byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$appendBytes");
      Intrinsics.checkParameterIsNotNull(var1, "array");
      Closeable var9 = (Closeable)(new FileOutputStream(var0, true));
      Throwable var2 = (Throwable)null;

      try {
         ((FileOutputStream)var9).write(var1);
         Unit var11 = Unit.INSTANCE;
      } catch (Throwable var8) {
         Throwable var10 = var8;

         try {
            throw var10;
         } finally {
            CloseableKt.closeFinally(var9, var8);
         }
      }

      CloseableKt.closeFinally(var9, var2);
   }

   public static final void appendText(File var0, String var1, Charset var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$appendText");
      Intrinsics.checkParameterIsNotNull(var1, "text");
      Intrinsics.checkParameterIsNotNull(var2, "charset");
      byte[] var3 = var1.getBytes(var2);
      Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.String).getBytes(charset)");
      FilesKt.appendBytes(var0, var3);
   }

   // $FF: synthetic method
   public static void appendText$default(File var0, String var1, Charset var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = Charsets.UTF_8;
      }

      FilesKt.appendText(var0, var1, var2);
   }

   private static final BufferedReader bufferedReader(File var0, Charset var1, int var2) {
      Reader var3 = (Reader)(new InputStreamReader((InputStream)(new FileInputStream(var0)), var1));
      BufferedReader var4;
      if (var3 instanceof BufferedReader) {
         var4 = (BufferedReader)var3;
      } else {
         var4 = new BufferedReader(var3, var2);
      }

      return var4;
   }

   // $FF: synthetic method
   static BufferedReader bufferedReader$default(File var0, Charset var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      Reader var5 = (Reader)(new InputStreamReader((InputStream)(new FileInputStream(var0)), var1));
      BufferedReader var6;
      if (var5 instanceof BufferedReader) {
         var6 = (BufferedReader)var5;
      } else {
         var6 = new BufferedReader(var5, var2);
      }

      return var6;
   }

   private static final BufferedWriter bufferedWriter(File var0, Charset var1, int var2) {
      Writer var3 = (Writer)(new OutputStreamWriter((OutputStream)(new FileOutputStream(var0)), var1));
      BufferedWriter var4;
      if (var3 instanceof BufferedWriter) {
         var4 = (BufferedWriter)var3;
      } else {
         var4 = new BufferedWriter(var3, var2);
      }

      return var4;
   }

   // $FF: synthetic method
   static BufferedWriter bufferedWriter$default(File var0, Charset var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      Writer var5 = (Writer)(new OutputStreamWriter((OutputStream)(new FileOutputStream(var0)), var1));
      BufferedWriter var6;
      if (var5 instanceof BufferedWriter) {
         var6 = (BufferedWriter)var5;
      } else {
         var6 = new BufferedWriter(var5, var2);
      }

      return var6;
   }

   public static final void forEachBlock(File var0, int var1, Function2<? super byte[], ? super Integer, Unit> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$forEachBlock");
      Intrinsics.checkParameterIsNotNull(var2, "action");
      byte[] var3 = new byte[RangesKt.coerceAtLeast(var1, 512)];
      Closeable var36 = (Closeable)(new FileInputStream(var0));
      Throwable var4 = (Throwable)null;

      label229: {
         Throwable var10000;
         label224: {
            FileInputStream var5;
            boolean var10001;
            try {
               var5 = (FileInputStream)var36;
            } catch (Throwable var35) {
               var10000 = var35;
               var10001 = false;
               break label224;
            }

            while(true) {
               try {
                  var1 = var5.read(var3);
               } catch (Throwable var33) {
                  var10000 = var33;
                  var10001 = false;
                  break;
               }

               if (var1 <= 0) {
                  try {
                     Unit var37 = Unit.INSTANCE;
                     break label229;
                  } catch (Throwable var32) {
                     var10000 = var32;
                     var10001 = false;
                     break;
                  }
               }

               try {
                  var2.invoke(var3, var1);
               } catch (Throwable var34) {
                  var10000 = var34;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var38 = var10000;

         try {
            throw var38;
         } finally {
            CloseableKt.closeFinally(var36, var38);
         }
      }

      CloseableKt.closeFinally(var36, var4);
   }

   public static final void forEachBlock(File var0, Function2<? super byte[], ? super Integer, Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$forEachBlock");
      Intrinsics.checkParameterIsNotNull(var1, "action");
      FilesKt.forEachBlock(var0, 4096, var1);
   }

   public static final void forEachLine(File var0, Charset var1, Function1<? super String, Unit> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$forEachLine");
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      Intrinsics.checkParameterIsNotNull(var2, "action");
      TextStreamsKt.forEachLine((Reader)(new BufferedReader((Reader)(new InputStreamReader((InputStream)(new FileInputStream(var0)), var1)))), var2);
   }

   // $FF: synthetic method
   public static void forEachLine$default(File var0, Charset var1, Function1 var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      FilesKt.forEachLine(var0, var1, var2);
   }

   private static final FileInputStream inputStream(File var0) {
      return new FileInputStream(var0);
   }

   private static final FileOutputStream outputStream(File var0) {
      return new FileOutputStream(var0);
   }

   private static final PrintWriter printWriter(File var0, Charset var1) {
      Writer var2 = (Writer)(new OutputStreamWriter((OutputStream)(new FileOutputStream(var0)), var1));
      BufferedWriter var3;
      if (var2 instanceof BufferedWriter) {
         var3 = (BufferedWriter)var2;
      } else {
         var3 = new BufferedWriter(var2, 8192);
      }

      return new PrintWriter((Writer)var3);
   }

   // $FF: synthetic method
   static PrintWriter printWriter$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      Writer var4 = (Writer)(new OutputStreamWriter((OutputStream)(new FileOutputStream(var0)), var1));
      BufferedWriter var5;
      if (var4 instanceof BufferedWriter) {
         var5 = (BufferedWriter)var4;
      } else {
         var5 = new BufferedWriter(var4, 8192);
      }

      return new PrintWriter((Writer)var5);
   }

   public static final byte[] readBytes(File var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readBytes");
      Closeable var1 = (Closeable)(new FileInputStream(var0));
      Throwable var2 = (Throwable)null;

      Throwable var10000;
      label890: {
         FileInputStream var3;
         long var4;
         boolean var10001;
         try {
            var3 = (FileInputStream)var1;
            var4 = var0.length();
         } catch (Throwable var121) {
            var10000 = var121;
            var10001 = false;
            break label890;
         }

         if (var4 > (long)Integer.MAX_VALUE) {
            label857:
            try {
               StringBuilder var127 = new StringBuilder();
               var127.append("File ");
               var127.append(var0);
               var127.append(" is too big (");
               var127.append(var4);
               var127.append(" bytes) to fit in memory.");
               OutOfMemoryError var125 = new OutOfMemoryError(var127.toString());
               throw (Throwable)var125;
            } catch (Throwable var114) {
               var10000 = var114;
               var10001 = false;
               break label857;
            }
         } else {
            label893: {
               int var6 = (int)var4;

               byte[] var7;
               try {
                  var7 = new byte[var6];
               } catch (Throwable var120) {
                  var10000 = var120;
                  var10001 = false;
                  break label893;
               }

               int var8 = var6;

               int var9;
               int var10;
               for(var9 = 0; var8 > 0; var9 += var10) {
                  try {
                     var10 = var3.read(var7, var9, var8);
                  } catch (Throwable var119) {
                     var10000 = var119;
                     var10001 = false;
                     break label893;
                  }

                  if (var10 < 0) {
                     break;
                  }

                  var8 -= var10;
               }

               byte[] var122;
               if (var8 > 0) {
                  try {
                     var122 = Arrays.copyOf(var7, var9);
                     Intrinsics.checkExpressionValueIsNotNull(var122, "java.util.Arrays.copyOf(this, newSize)");
                  } catch (Throwable var116) {
                     var10000 = var116;
                     var10001 = false;
                     break label893;
                  }
               } else {
                  try {
                     var9 = var3.read();
                  } catch (Throwable var118) {
                     var10000 = var118;
                     var10001 = false;
                     break label893;
                  }

                  if (var9 == -1) {
                     var122 = var7;
                  } else {
                     ExposingBufferByteArrayOutputStream var11;
                     try {
                        var11 = new ExposingBufferByteArrayOutputStream(8193);
                        var11.write(var9);
                        ByteStreamsKt.copyTo$default((InputStream)var3, (OutputStream)var11, 0, 2, (Object)null);
                        var9 = var11.size() + var6;
                     } catch (Throwable var117) {
                        var10000 = var117;
                        var10001 = false;
                        break label893;
                     }

                     if (var9 < 0) {
                        try {
                           StringBuilder var124 = new StringBuilder();
                           var124.append("File ");
                           var124.append(var0);
                           var124.append(" is too big to fit in memory.");
                           OutOfMemoryError var126 = new OutOfMemoryError(var124.toString());
                           throw (Throwable)var126;
                        } catch (Throwable var113) {
                           var10000 = var113;
                           var10001 = false;
                           break label893;
                        }
                     }

                     try {
                        var122 = var11.getBuffer();
                        var7 = Arrays.copyOf(var7, var9);
                        Intrinsics.checkExpressionValueIsNotNull(var7, "java.util.Arrays.copyOf(this, newSize)");
                        var122 = ArraysKt.copyInto(var122, var7, var6, 0, var11.size());
                     } catch (Throwable var115) {
                        var10000 = var115;
                        var10001 = false;
                        break label893;
                     }
                  }
               }

               CloseableKt.closeFinally(var1, var2);
               return var122;
            }
         }
      }

      Throwable var123 = var10000;

      try {
         throw var123;
      } finally {
         CloseableKt.closeFinally(var1, var123);
      }
   }

   public static final List<String> readLines(File var0, Charset var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readLines");
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      final ArrayList var2 = new ArrayList();
      FilesKt.forEachLine(var0, var1, (Function1)(new Function1<String, Unit>() {
         public final void invoke(String var1) {
            Intrinsics.checkParameterIsNotNull(var1, "it");
            var2.add(var1);
         }
      }));
      return (List)var2;
   }

   // $FF: synthetic method
   public static List readLines$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return FilesKt.readLines(var0, var1);
   }

   public static final String readText(File var0, Charset var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readText");
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      Closeable var9 = (Closeable)(new InputStreamReader((InputStream)(new FileInputStream(var0)), var1));
      Throwable var10 = (Throwable)null;

      String var2;
      try {
         var2 = TextStreamsKt.readText((Reader)((InputStreamReader)var9));
      } catch (Throwable var8) {
         var10 = var8;

         try {
            throw var10;
         } finally {
            CloseableKt.closeFinally(var9, var8);
         }
      }

      CloseableKt.closeFinally(var9, var10);
      return var2;
   }

   // $FF: synthetic method
   public static String readText$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return FilesKt.readText(var0, var1);
   }

   private static final InputStreamReader reader(File var0, Charset var1) {
      return new InputStreamReader((InputStream)(new FileInputStream(var0)), var1);
   }

   // $FF: synthetic method
   static InputStreamReader reader$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return new InputStreamReader((InputStream)(new FileInputStream(var0)), var1);
   }

   public static final <T> T useLines(File var0, Charset var1, Function1<? super Sequence<String>, ? extends T> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$useLines");
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      Intrinsics.checkParameterIsNotNull(var2, "block");
      Reader var15 = (Reader)(new InputStreamReader((InputStream)(new FileInputStream(var0)), var1));
      BufferedReader var16;
      if (var15 instanceof BufferedReader) {
         var16 = (BufferedReader)var15;
      } else {
         var16 = new BufferedReader(var15, 8192);
      }

      Closeable var18 = (Closeable)var16;
      Throwable var17 = (Throwable)null;

      Object var20;
      try {
         var20 = var2.invoke(TextStreamsKt.lineSequence((BufferedReader)var18));
      } catch (Throwable var14) {
         Throwable var19 = var14;

         try {
            throw var19;
         } finally {
            InlineMarker.finallyStart(1);
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               label135:
               try {
                  var18.close();
               } finally {
                  break label135;
               }
            } else {
               CloseableKt.closeFinally(var18, var14);
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         CloseableKt.closeFinally(var18, var17);
      } else {
         var18.close();
      }

      InlineMarker.finallyEnd(1);
      return var20;
   }

   // $FF: synthetic method
   public static Object useLines$default(File var0, Charset var1, Function1 var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      Intrinsics.checkParameterIsNotNull(var0, "$this$useLines");
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      Intrinsics.checkParameterIsNotNull(var2, "block");
      Reader var17 = (Reader)(new InputStreamReader((InputStream)(new FileInputStream(var0)), var1));
      BufferedReader var18;
      if (var17 instanceof BufferedReader) {
         var18 = (BufferedReader)var17;
      } else {
         var18 = new BufferedReader(var17, 8192);
      }

      Closeable var19 = (Closeable)var18;
      Throwable var20 = (Throwable)null;

      Object var22;
      try {
         var22 = var2.invoke(TextStreamsKt.lineSequence((BufferedReader)var19));
      } catch (Throwable var16) {
         Throwable var21 = var16;

         try {
            throw var21;
         } finally {
            InlineMarker.finallyStart(1);
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               label149:
               try {
                  var19.close();
               } finally {
                  break label149;
               }
            } else {
               CloseableKt.closeFinally(var19, var16);
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         CloseableKt.closeFinally(var19, var20);
      } else {
         var19.close();
      }

      InlineMarker.finallyEnd(1);
      return var22;
   }

   public static final void writeBytes(File var0, byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$writeBytes");
      Intrinsics.checkParameterIsNotNull(var1, "array");
      Closeable var9 = (Closeable)(new FileOutputStream(var0));
      Throwable var2 = (Throwable)null;

      try {
         ((FileOutputStream)var9).write(var1);
         Unit var10 = Unit.INSTANCE;
      } catch (Throwable var8) {
         var2 = var8;

         try {
            throw var2;
         } finally {
            CloseableKt.closeFinally(var9, var8);
         }
      }

      CloseableKt.closeFinally(var9, var2);
   }

   public static final void writeText(File var0, String var1, Charset var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$writeText");
      Intrinsics.checkParameterIsNotNull(var1, "text");
      Intrinsics.checkParameterIsNotNull(var2, "charset");
      byte[] var3 = var1.getBytes(var2);
      Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.String).getBytes(charset)");
      FilesKt.writeBytes(var0, var3);
   }

   // $FF: synthetic method
   public static void writeText$default(File var0, String var1, Charset var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = Charsets.UTF_8;
      }

      FilesKt.writeText(var0, var1, var2);
   }

   private static final OutputStreamWriter writer(File var0, Charset var1) {
      return new OutputStreamWriter((OutputStream)(new FileOutputStream(var0)), var1);
   }

   // $FF: synthetic method
   static OutputStreamWriter writer$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return new OutputStreamWriter((OutputStream)(new FileOutputStream(var0)), var1);
   }
}
