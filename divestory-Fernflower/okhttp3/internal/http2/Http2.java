package okhttp3.internal.http2;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u000bJ\u0015\u0010\"\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u000bH\u0000¢\u0006\u0002\b#J.\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u000b2\u0006\u0010(\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u000bR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\n\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\u0014\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000¨\u0006)"},
   d2 = {"Lokhttp3/internal/http2/Http2;", "", "()V", "BINARY", "", "", "[Ljava/lang/String;", "CONNECTION_PREFACE", "Lokio/ByteString;", "FLAGS", "FLAG_ACK", "", "FLAG_COMPRESSED", "FLAG_END_HEADERS", "FLAG_END_PUSH_PROMISE", "FLAG_END_STREAM", "FLAG_NONE", "FLAG_PADDED", "FLAG_PRIORITY", "FRAME_NAMES", "INITIAL_MAX_FRAME_SIZE", "TYPE_CONTINUATION", "TYPE_DATA", "TYPE_GOAWAY", "TYPE_HEADERS", "TYPE_PING", "TYPE_PRIORITY", "TYPE_PUSH_PROMISE", "TYPE_RST_STREAM", "TYPE_SETTINGS", "TYPE_WINDOW_UPDATE", "formatFlags", "type", "flags", "formattedType", "formattedType$okhttp", "frameLog", "inbound", "", "streamId", "length", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Http2 {
   private static final String[] BINARY;
   public static final ByteString CONNECTION_PREFACE;
   private static final String[] FLAGS;
   public static final int FLAG_ACK = 1;
   public static final int FLAG_COMPRESSED = 32;
   public static final int FLAG_END_HEADERS = 4;
   public static final int FLAG_END_PUSH_PROMISE = 4;
   public static final int FLAG_END_STREAM = 1;
   public static final int FLAG_NONE = 0;
   public static final int FLAG_PADDED = 8;
   public static final int FLAG_PRIORITY = 32;
   private static final String[] FRAME_NAMES;
   public static final int INITIAL_MAX_FRAME_SIZE = 16384;
   public static final Http2 INSTANCE = new Http2();
   public static final int TYPE_CONTINUATION = 9;
   public static final int TYPE_DATA = 0;
   public static final int TYPE_GOAWAY = 7;
   public static final int TYPE_HEADERS = 1;
   public static final int TYPE_PING = 6;
   public static final int TYPE_PRIORITY = 2;
   public static final int TYPE_PUSH_PROMISE = 5;
   public static final int TYPE_RST_STREAM = 3;
   public static final int TYPE_SETTINGS = 4;
   public static final int TYPE_WINDOW_UPDATE = 8;

   static {
      CONNECTION_PREFACE = ByteString.Companion.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
      FRAME_NAMES = new String[]{"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
      FLAGS = new String[64];
      String[] var0 = new String[256];
      byte var1 = 0;

      int var2;
      for(var2 = 0; var2 < 256; ++var2) {
         String var3 = Integer.toBinaryString(var2);
         Intrinsics.checkExpressionValueIsNotNull(var3, "Integer.toBinaryString(it)");
         var0[var2] = StringsKt.replace$default(Util.format("%8s", var3), ' ', '0', false, 4, (Object)null);
      }

      BINARY = var0;
      var0 = FLAGS;
      var0[0] = "";
      var0[1] = "END_STREAM";
      int[] var10 = new int[]{1};
      var0[8] = "PADDED";

      int var4;
      for(var2 = 0; var2 < 1; ++var2) {
         var4 = var10[var2];
         var0 = FLAGS;
         var0[var4 | 8] = Intrinsics.stringPlus(var0[var4], "|PADDED");
      }

      var0 = FLAGS;
      var0[4] = "END_HEADERS";
      var0[32] = "PRIORITY";
      var0[36] = "END_HEADERS|PRIORITY";

      for(var2 = 0; var2 < 3; ++var2) {
         int var5 = (new int[]{4, 32, 36})[var2];

         for(var4 = 0; var4 < 1; ++var4) {
            int var6 = var10[var4];
            String[] var7 = FLAGS;
            int var8 = var6 | var5;
            StringBuilder var9 = new StringBuilder();
            var9.append(FLAGS[var6]);
            var9.append("|");
            var9.append(FLAGS[var5]);
            var7[var8] = var9.toString();
            var7 = FLAGS;
            var9 = new StringBuilder();
            var9.append(FLAGS[var6]);
            var9.append("|");
            var9.append(FLAGS[var5]);
            var9.append("|PADDED");
            var7[var8 | 8] = var9.toString();
         }
      }

      var4 = FLAGS.length;

      for(var2 = var1; var2 < var4; ++var2) {
         String[] var11 = FLAGS;
         if (var11[var2] == null) {
            var11[var2] = BINARY[var2];
         }
      }

   }

   private Http2() {
   }

   public final String formatFlags(int var1, int var2) {
      if (var2 == 0) {
         return "";
      } else {
         if (var1 != 2 && var1 != 3) {
            String var5;
            if (var1 == 4 || var1 == 6) {
               if (var2 == 1) {
                  var5 = "ACK";
               } else {
                  var5 = BINARY[var2];
               }

               return var5;
            }

            if (var1 != 7 && var1 != 8) {
               String[] var3 = FLAGS;
               String var4;
               if (var2 < var3.length) {
                  var4 = var3[var2];
                  var5 = var4;
                  if (var4 == null) {
                     Intrinsics.throwNpe();
                     var5 = var4;
                  }
               } else {
                  var5 = BINARY[var2];
               }

               var4 = var5;
               if (var1 == 5 && (var2 & 4) != 0) {
                  var5 = StringsKt.replace$default(var5, "HEADERS", "PUSH_PROMISE", false, 4, (Object)null);
               } else {
                  var5 = var5;
                  if (var1 == 0) {
                     var5 = var4;
                     if ((var2 & 32) != 0) {
                        var5 = StringsKt.replace$default(var4, "PRIORITY", "COMPRESSED", false, 4, (Object)null);
                     }
                  }
               }

               return var5;
            }
         }

         return BINARY[var2];
      }
   }

   public final String formattedType$okhttp(int var1) {
      String[] var2 = FRAME_NAMES;
      String var3;
      if (var1 < var2.length) {
         var3 = var2[var1];
      } else {
         var3 = Util.format("0x%02x", var1);
      }

      return var3;
   }

   public final String frameLog(boolean var1, int var2, int var3, int var4, int var5) {
      String var6 = this.formattedType$okhttp(var4);
      String var7 = this.formatFlags(var4, var5);
      String var8;
      if (var1) {
         var8 = "<<";
      } else {
         var8 = ">>";
      }

      return Util.format("%s 0x%08x %5d %-13s %s", var8, var2, var3, var6, var7);
   }
}
