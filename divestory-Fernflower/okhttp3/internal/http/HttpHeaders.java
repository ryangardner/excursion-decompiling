package okhttp3.internal.http;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Challenge;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0005\n\u0000\u001a\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\u001a\n\u0010\r\u001a\u00020\u0004*\u00020\u0006\u001a\u001a\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u0012H\u0002\u001a\u000e\u0010\u0013\u001a\u0004\u0018\u00010\f*\u00020\u0010H\u0002\u001a\u000e\u0010\u0014\u001a\u0004\u0018\u00010\f*\u00020\u0010H\u0002\u001a\u001a\u0010\u0015\u001a\u00020\u000f*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\n\u001a\f\u0010\u001a\u001a\u00020\u0004*\u00020\u0010H\u0002\u001a\u0014\u0010\u001b\u001a\u00020\u0004*\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"},
   d2 = {"QUOTED_STRING_DELIMITERS", "Lokio/ByteString;", "TOKEN_DELIMITERS", "hasBody", "", "response", "Lokhttp3/Response;", "parseChallenges", "", "Lokhttp3/Challenge;", "Lokhttp3/Headers;", "headerName", "", "promisesBody", "readChallengeHeader", "", "Lokio/Buffer;", "result", "", "readQuotedString", "readToken", "receiveHeaders", "Lokhttp3/CookieJar;", "url", "Lokhttp3/HttpUrl;", "headers", "skipCommasAndWhitespace", "startsWith", "prefix", "", "okhttp"},
   k = 2,
   mv = {1, 1, 16}
)
public final class HttpHeaders {
   private static final ByteString QUOTED_STRING_DELIMITERS;
   private static final ByteString TOKEN_DELIMITERS;

   static {
      QUOTED_STRING_DELIMITERS = ByteString.Companion.encodeUtf8("\"\\");
      TOKEN_DELIMITERS = ByteString.Companion.encodeUtf8("\t ,=");
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "No longer supported",
      replaceWith = @ReplaceWith(
   expression = "response.promisesBody()",
   imports = {}
)
   )
   public static final boolean hasBody(Response var0) {
      Intrinsics.checkParameterIsNotNull(var0, "response");
      return promisesBody(var0);
   }

   public static final List<Challenge> parseChallenges(Headers var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$parseChallenges");
      Intrinsics.checkParameterIsNotNull(var1, "headerName");
      List var2 = (List)(new ArrayList());
      int var3 = var0.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         if (StringsKt.equals(var1, var0.name(var4), true)) {
            Buffer var5 = (new Buffer()).writeUtf8(var0.value(var4));

            try {
               readChallengeHeader(var5, var2);
            } catch (EOFException var6) {
               Platform.Companion.get().log("Unable to parse challenge", 5, (Throwable)var6);
            }
         }
      }

      return var2;
   }

   public static final boolean promisesBody(Response var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$promisesBody");
      if (Intrinsics.areEqual((Object)var0.request().method(), (Object)"HEAD")) {
         return false;
      } else {
         int var1 = var0.code();
         if ((var1 < 100 || var1 >= 200) && var1 != 204 && var1 != 304) {
            return true;
         } else {
            return Util.headersContentLength(var0) != -1L || StringsKt.equals("chunked", Response.header$default(var0, "Transfer-Encoding", (String)null, 2, (Object)null), true);
         }
      }
   }

   private static final void readChallengeHeader(Buffer var0, List<Challenge> var1) throws EOFException {
      String var2 = (String)null;

      while(true) {
         String var3 = var2;

         while(true) {
            String var4 = var3;
            if (var3 == null) {
               skipCommasAndWhitespace(var0);
               var3 = readToken(var0);
               var4 = var3;
               if (var3 == null) {
                  return;
               }
            }

            boolean var5 = skipCommasAndWhitespace(var0);
            String var6 = readToken(var0);
            if (var6 == null) {
               if (!var0.exhausted()) {
                  return;
               }

               var1.add(new Challenge(var4, MapsKt.emptyMap()));
               return;
            }

            byte var7 = (byte)61;
            int var8 = Util.skipAll(var0, var7);
            boolean var9 = skipCommasAndWhitespace(var0);
            if (!var5 && (var9 || var0.exhausted())) {
               StringBuilder var11 = new StringBuilder();
               var11.append(var6);
               var11.append(StringsKt.repeat((CharSequence)"=", var8));
               Map var12 = Collections.singletonMap((Object)null, var11.toString());
               Intrinsics.checkExpressionValueIsNotNull(var12, "Collections.singletonMap…ek + \"=\".repeat(eqCount))");
               var1.add(new Challenge(var4, var12));
               break;
            }

            Map var10 = (Map)(new LinkedHashMap());
            var8 += Util.skipAll(var0, var7);

            while(true) {
               var3 = var6;
               if (var6 == null) {
                  var3 = readToken(var0);
                  if (skipCommasAndWhitespace(var0)) {
                     break;
                  }

                  var8 = Util.skipAll(var0, var7);
               }

               if (var8 == 0) {
                  break;
               }

               if (var8 > 1) {
                  return;
               }

               if (skipCommasAndWhitespace(var0)) {
                  return;
               }

               if (startsWith(var0, (byte)34)) {
                  var6 = readQuotedString(var0);
               } else {
                  var6 = readToken(var0);
               }

               if (var6 == null) {
                  return;
               }

               if ((String)var10.put(var3, var6) != null) {
                  return;
               }

               if (!skipCommasAndWhitespace(var0) && !var0.exhausted()) {
                  return;
               }

               var6 = var2;
            }

            var1.add(new Challenge(var4, var10));
         }
      }
   }

   private static final String readQuotedString(Buffer var0) throws EOFException {
      byte var1 = var0.readByte();
      byte var2 = (byte)34;
      boolean var6;
      if (var1 == var2) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var6) {
         Buffer var3 = new Buffer();

         while(true) {
            long var4 = var0.indexOfElement(QUOTED_STRING_DELIMITERS);
            if (var4 == -1L) {
               return null;
            }

            if (var0.getByte(var4) == var2) {
               var3.write(var0, var4);
               var0.readByte();
               return var3.readUtf8();
            }

            if (var0.size() == var4 + 1L) {
               return null;
            }

            var3.write(var0, var4);
            var0.readByte();
            var3.write(var0, 1L);
         }
      } else {
         throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
      }
   }

   private static final String readToken(Buffer var0) {
      long var1 = var0.indexOfElement(TOKEN_DELIMITERS);
      long var3 = var1;
      if (var1 == -1L) {
         var3 = var0.size();
      }

      String var5;
      if (var3 != 0L) {
         var5 = var0.readUtf8(var3);
      } else {
         var5 = null;
      }

      return var5;
   }

   public static final void receiveHeaders(CookieJar var0, HttpUrl var1, Headers var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$receiveHeaders");
      Intrinsics.checkParameterIsNotNull(var1, "url");
      Intrinsics.checkParameterIsNotNull(var2, "headers");
      if (var0 != CookieJar.NO_COOKIES) {
         List var3 = Cookie.Companion.parseAll(var1, var2);
         if (!var3.isEmpty()) {
            var0.saveFromResponse(var1, var3);
         }
      }
   }

   private static final boolean skipCommasAndWhitespace(Buffer var0) {
      boolean var1 = false;

      while(!var0.exhausted()) {
         byte var2 = var0.getByte(0L);
         if (var2 != 9 && var2 != 32) {
            if (var2 != 44) {
               break;
            }

            var0.readByte();
            var1 = true;
         } else {
            var0.readByte();
         }
      }

      return var1;
   }

   private static final boolean startsWith(Buffer var0, byte var1) {
      boolean var2;
      if (!var0.exhausted() && var0.getByte(0L) == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
