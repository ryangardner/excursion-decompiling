package okhttp3.internal.platform.android;

import android.util.Log;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.http2.Http2;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0007\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J/\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0000¢\u0006\u0002\b\u0012J\u0006\u0010\u0013\u001a\u00020\fJ\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nH\u0002J\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lokhttp3/internal/platform/android/AndroidLog;", "", "()V", "MAX_LOG_LENGTH", "", "configuredLoggers", "Ljava/util/concurrent/CopyOnWriteArraySet;", "Ljava/util/logging/Logger;", "knownLoggers", "", "", "androidLog", "", "loggerName", "logLevel", "message", "t", "", "androidLog$okhttp", "enable", "enableLogging", "logger", "tag", "loggerTag", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class AndroidLog {
   public static final AndroidLog INSTANCE = new AndroidLog();
   private static final int MAX_LOG_LENGTH = 4000;
   private static final CopyOnWriteArraySet<Logger> configuredLoggers = new CopyOnWriteArraySet();
   private static final Map<String, String> knownLoggers;

   static {
      LinkedHashMap var0 = new LinkedHashMap();
      Package var1 = OkHttpClient.class.getPackage();
      String var3;
      if (var1 != null) {
         var3 = var1.getName();
      } else {
         var3 = null;
      }

      if (var3 != null) {
         ((Map)var0).put(var3, "OkHttp");
      }

      Map var4 = (Map)var0;
      String var2 = OkHttpClient.class.getName();
      Intrinsics.checkExpressionValueIsNotNull(var2, "OkHttpClient::class.java.name");
      var4.put(var2, "okhttp.OkHttpClient");
      var2 = Http2.class.getName();
      Intrinsics.checkExpressionValueIsNotNull(var2, "Http2::class.java.name");
      var4.put(var2, "okhttp.Http2");
      var2 = TaskRunner.class.getName();
      Intrinsics.checkExpressionValueIsNotNull(var2, "TaskRunner::class.java.name");
      var4.put(var2, "okhttp.TaskRunner");
      knownLoggers = MapsKt.toMap(var4);
   }

   private AndroidLog() {
   }

   private final void enableLogging(String var1, String var2) {
      Logger var3 = Logger.getLogger(var1);
      if (configuredLoggers.add(var3)) {
         Intrinsics.checkExpressionValueIsNotNull(var3, "logger");
         var3.setUseParentHandlers(false);
         Level var4;
         if (Log.isLoggable(var2, 3)) {
            var4 = Level.FINE;
         } else if (Log.isLoggable(var2, 4)) {
            var4 = Level.INFO;
         } else {
            var4 = Level.WARNING;
         }

         var3.setLevel(var4);
         var3.addHandler((Handler)AndroidLogHandler.INSTANCE);
      }

   }

   private final String loggerTag(String var1) {
      String var2 = (String)knownLoggers.get(var1);
      if (var2 != null) {
         var1 = var2;
      } else {
         var1 = StringsKt.take(var1, 23);
      }

      return var1;
   }

   public final void androidLog$okhttp(String var1, int var2, String var3, Throwable var4) {
      Intrinsics.checkParameterIsNotNull(var1, "loggerName");
      Intrinsics.checkParameterIsNotNull(var3, "message");
      String var5 = this.loggerTag(var1);
      if (Log.isLoggable(var5, var2)) {
         var1 = var3;
         if (var4 != null) {
            StringBuilder var10 = new StringBuilder();
            var10.append(var3);
            var10.append("\n");
            var10.append(Log.getStackTraceString(var4));
            var1 = var10.toString();
         }

         int var6 = 0;

         int var9;
         for(int var7 = var1.length(); var6 < var7; var6 = var9 + 1) {
            int var8 = StringsKt.indexOf$default((CharSequence)var1, '\n', var6, false, 4, (Object)null);
            if (var8 == -1) {
               var8 = var7;
            }

            while(true) {
               var9 = Math.min(var8, var6 + 4000);
               if (var1 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var3 = var1.substring(var6, var9);
               Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               Log.println(var2, var5, var3);
               if (var9 >= var8) {
                  break;
               }

               var6 = var9;
            }
         }
      }

   }

   public final void enable() {
      Iterator var1 = knownLoggers.entrySet().iterator();

      while(var1.hasNext()) {
         Entry var2 = (Entry)var1.next();
         this.enableLogging((String)var2.getKey(), (String)var2.getValue());
      }

   }
}
