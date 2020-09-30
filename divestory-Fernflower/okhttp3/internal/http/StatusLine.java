package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Protocol;
import okhttp3.Response;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0007H\u0016R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lokhttp3/internal/http/StatusLine;", "", "protocol", "Lokhttp3/Protocol;", "code", "", "message", "", "(Lokhttp3/Protocol;ILjava/lang/String;)V", "toString", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class StatusLine {
   public static final StatusLine.Companion Companion = new StatusLine.Companion((DefaultConstructorMarker)null);
   public static final int HTTP_CONTINUE = 100;
   public static final int HTTP_MISDIRECTED_REQUEST = 421;
   public static final int HTTP_PERM_REDIRECT = 308;
   public static final int HTTP_TEMP_REDIRECT = 307;
   public final int code;
   public final String message;
   public final Protocol protocol;

   public StatusLine(Protocol var1, int var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var1, "protocol");
      Intrinsics.checkParameterIsNotNull(var3, "message");
      super();
      this.protocol = var1;
      this.code = var2;
      this.message = var3;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      if (this.protocol == Protocol.HTTP_1_0) {
         var1.append("HTTP/1.0");
      } else {
         var1.append("HTTP/1.1");
      }

      var1.append(' ');
      var1.append(this.code);
      var1.append(' ');
      var1.append(this.message);
      String var2 = var1.toString();
      Intrinsics.checkExpressionValueIsNotNull(var2, "StringBuilder().apply(builderAction).toString()");
      return var2;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"},
      d2 = {"Lokhttp3/internal/http/StatusLine$Companion;", "", "()V", "HTTP_CONTINUE", "", "HTTP_MISDIRECTED_REQUEST", "HTTP_PERM_REDIRECT", "HTTP_TEMP_REDIRECT", "get", "Lokhttp3/internal/http/StatusLine;", "response", "Lokhttp3/Response;", "parse", "statusLine", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final StatusLine get(Response var1) {
         Intrinsics.checkParameterIsNotNull(var1, "response");
         return new StatusLine(var1.protocol(), var1.code(), var1.message());
      }

      public final StatusLine parse(String var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "statusLine");
         boolean var2 = StringsKt.startsWith$default(var1, "HTTP/1.", false, 2, (Object)null);
         byte var3 = 9;
         int var4;
         Protocol var5;
         StringBuilder var9;
         if (var2) {
            if (var1.length() < 9 || var1.charAt(8) != ' ') {
               var9 = new StringBuilder();
               var9.append("Unexpected status line: ");
               var9.append(var1);
               throw (Throwable)(new ProtocolException(var9.toString()));
            }

            var4 = var1.charAt(7) - 48;
            if (var4 == 0) {
               var5 = Protocol.HTTP_1_0;
            } else {
               if (var4 != 1) {
                  var9 = new StringBuilder();
                  var9.append("Unexpected status line: ");
                  var9.append(var1);
                  throw (Throwable)(new ProtocolException(var9.toString()));
               }

               var5 = Protocol.HTTP_1_1;
            }
         } else {
            if (!StringsKt.startsWith$default(var1, "ICY ", false, 2, (Object)null)) {
               var9 = new StringBuilder();
               var9.append("Unexpected status line: ");
               var9.append(var1);
               throw (Throwable)(new ProtocolException(var9.toString()));
            }

            var5 = Protocol.HTTP_1_0;
            var3 = 4;
         }

         int var6 = var1.length();
         var4 = var3 + 3;
         if (var6 >= var4) {
            try {
               String var7 = var1.substring(var3, var4);
               Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               var6 = Integer.parseInt(var7);
            } catch (NumberFormatException var8) {
               var9 = new StringBuilder();
               var9.append("Unexpected status line: ");
               var9.append(var1);
               throw (Throwable)(new ProtocolException(var9.toString()));
            }

            if (var1.length() > var4) {
               if (var1.charAt(var4) != ' ') {
                  var9 = new StringBuilder();
                  var9.append("Unexpected status line: ");
                  var9.append(var1);
                  throw (Throwable)(new ProtocolException(var9.toString()));
               }

               var1 = var1.substring(var3 + 4);
               Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
            } else {
               var1 = "";
            }

            return new StatusLine(var5, var6, var1);
         } else {
            var9 = new StringBuilder();
            var9.append("Unexpected status line: ");
            var9.append(var1);
            throw (Throwable)(new ProtocolException(var9.toString()));
         }
      }
   }
}
