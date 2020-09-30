package androidx.core.os;

import android.os.Trace;
import android.os.Build.VERSION;
import android.util.Log;
import java.lang.reflect.Method;

public final class TraceCompat {
   private static final String TAG = "TraceCompat";
   private static Method sAsyncTraceBeginMethod;
   private static Method sAsyncTraceEndMethod;
   private static Method sIsTagEnabledMethod;
   private static Method sTraceCounterMethod;
   private static long sTraceTagApp;

   static {
      if (VERSION.SDK_INT >= 18 && VERSION.SDK_INT < 29) {
         try {
            sTraceTagApp = Trace.class.getField("TRACE_TAG_APP").getLong((Object)null);
            sIsTagEnabledMethod = Trace.class.getMethod("isTagEnabled", Long.TYPE);
            sAsyncTraceBeginMethod = Trace.class.getMethod("asyncTraceBegin", Long.TYPE, String.class, Integer.TYPE);
            sAsyncTraceEndMethod = Trace.class.getMethod("asyncTraceEnd", Long.TYPE, String.class, Integer.TYPE);
            sTraceCounterMethod = Trace.class.getMethod("traceCounter", Long.TYPE, String.class, Integer.TYPE);
         } catch (Exception var1) {
            Log.i("TraceCompat", "Unable to initialize via reflection.", var1);
         }
      }

   }

   private TraceCompat() {
   }

   public static void beginAsyncSection(String var0, int var1) {
      if (VERSION.SDK_INT >= 29) {
         Trace.beginAsyncSection(var0, var1);
      } else if (VERSION.SDK_INT >= 18) {
         try {
            sAsyncTraceBeginMethod.invoke((Object)null, sTraceTagApp, var0, var1);
         } catch (Exception var2) {
            Log.v("TraceCompat", "Unable to invoke asyncTraceBegin() via reflection.");
         }
      }

   }

   public static void beginSection(String var0) {
      if (VERSION.SDK_INT >= 18) {
         Trace.beginSection(var0);
      }

   }

   public static void endAsyncSection(String var0, int var1) {
      if (VERSION.SDK_INT >= 29) {
         Trace.endAsyncSection(var0, var1);
      } else if (VERSION.SDK_INT >= 18) {
         try {
            sAsyncTraceEndMethod.invoke((Object)null, sTraceTagApp, var0, var1);
         } catch (Exception var2) {
            Log.v("TraceCompat", "Unable to invoke endAsyncSection() via reflection.");
         }
      }

   }

   public static void endSection() {
      if (VERSION.SDK_INT >= 18) {
         Trace.endSection();
      }

   }

   public static boolean isEnabled() {
      if (VERSION.SDK_INT >= 29) {
         return Trace.isEnabled();
      } else {
         if (VERSION.SDK_INT >= 18) {
            try {
               boolean var0 = (Boolean)sIsTagEnabledMethod.invoke((Object)null, sTraceTagApp);
               return var0;
            } catch (Exception var2) {
               Log.v("TraceCompat", "Unable to invoke isTagEnabled() via reflection.");
            }
         }

         return false;
      }
   }

   public static void setCounter(String var0, int var1) {
      if (VERSION.SDK_INT >= 29) {
         Trace.setCounter(var0, (long)var1);
      } else if (VERSION.SDK_INT >= 18) {
         try {
            sTraceCounterMethod.invoke((Object)null, sTraceTagApp, var0, var1);
         } catch (Exception var2) {
            Log.v("TraceCompat", "Unable to invoke traceCounter() via reflection.");
         }
      }

   }
}
