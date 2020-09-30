package kotlin.text;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000bR\u0010\u0010\u0010\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lkotlin/text/Charsets;", "", "()V", "ISO_8859_1", "Ljava/nio/charset/Charset;", "US_ASCII", "UTF_16", "UTF_16BE", "UTF_16LE", "UTF_32", "UTF32", "()Ljava/nio/charset/Charset;", "UTF_32BE", "UTF32_BE", "UTF_32LE", "UTF32_LE", "UTF_8", "utf_32", "utf_32be", "utf_32le", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Charsets {
   public static final Charsets INSTANCE = new Charsets();
   public static final Charset ISO_8859_1;
   public static final Charset US_ASCII;
   public static final Charset UTF_16;
   public static final Charset UTF_16BE;
   public static final Charset UTF_16LE;
   public static final Charset UTF_8;
   private static Charset utf_32;
   private static Charset utf_32be;
   private static Charset utf_32le;

   static {
      Charset var0 = Charset.forName("UTF-8");
      Intrinsics.checkExpressionValueIsNotNull(var0, "Charset.forName(\"UTF-8\")");
      UTF_8 = var0;
      var0 = Charset.forName("UTF-16");
      Intrinsics.checkExpressionValueIsNotNull(var0, "Charset.forName(\"UTF-16\")");
      UTF_16 = var0;
      var0 = Charset.forName("UTF-16BE");
      Intrinsics.checkExpressionValueIsNotNull(var0, "Charset.forName(\"UTF-16BE\")");
      UTF_16BE = var0;
      var0 = Charset.forName("UTF-16LE");
      Intrinsics.checkExpressionValueIsNotNull(var0, "Charset.forName(\"UTF-16LE\")");
      UTF_16LE = var0;
      var0 = Charset.forName("US-ASCII");
      Intrinsics.checkExpressionValueIsNotNull(var0, "Charset.forName(\"US-ASCII\")");
      US_ASCII = var0;
      var0 = Charset.forName("ISO-8859-1");
      Intrinsics.checkExpressionValueIsNotNull(var0, "Charset.forName(\"ISO-8859-1\")");
      ISO_8859_1 = var0;
   }

   private Charsets() {
   }

   public final Charset UTF32() {
      Charset var1 = utf_32;
      if (var1 == null) {
         Charsets var2 = (Charsets)this;
         var1 = Charset.forName("UTF-32");
         Intrinsics.checkExpressionValueIsNotNull(var1, "Charset.forName(\"UTF-32\")");
         utf_32 = var1;
      }

      return var1;
   }

   public final Charset UTF32_BE() {
      Charset var1 = utf_32be;
      if (var1 == null) {
         Charsets var2 = (Charsets)this;
         var1 = Charset.forName("UTF-32BE");
         Intrinsics.checkExpressionValueIsNotNull(var1, "Charset.forName(\"UTF-32BE\")");
         utf_32be = var1;
      }

      return var1;
   }

   public final Charset UTF32_LE() {
      Charset var1 = utf_32le;
      if (var1 == null) {
         Charsets var2 = (Charsets)this;
         var1 = Charset.forName("UTF-32LE");
         Intrinsics.checkExpressionValueIsNotNull(var1, "Charset.forName(\"UTF-32LE\")");
         utf_32le = var1;
      }

      return var1;
   }
}
