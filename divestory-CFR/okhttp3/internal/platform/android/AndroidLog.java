/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package okhttp3.internal.platform.android;

import android.util.Log;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
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
import okhttp3.internal.platform.android.AndroidLogHandler;

@Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0007\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J/\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0000\u00a2\u0006\u0002\b\u0012J\u0006\u0010\u0013\u001a\u00020\fJ\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nH\u0002J\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lokhttp3/internal/platform/android/AndroidLog;", "", "()V", "MAX_LOG_LENGTH", "", "configuredLoggers", "Ljava/util/concurrent/CopyOnWriteArraySet;", "Ljava/util/logging/Logger;", "knownLoggers", "", "", "androidLog", "", "loggerName", "logLevel", "message", "t", "", "androidLog$okhttp", "enable", "enableLogging", "logger", "tag", "loggerTag", "okhttp"}, k=1, mv={1, 1, 16})
public final class AndroidLog {
    public static final AndroidLog INSTANCE = new AndroidLog();
    private static final int MAX_LOG_LENGTH = 4000;
    private static final CopyOnWriteArraySet<Logger> configuredLoggers = new CopyOnWriteArraySet();
    private static final Map<String, String> knownLoggers;

    static {
        Object object = new LinkedHashMap();
        Object object2 = OkHttpClient.class.getPackage();
        object2 = object2 != null ? ((Package)object2).getName() : null;
        if (object2 != null) {
            ((Map)object).put(object2, "OkHttp");
        }
        object2 = (Map)object;
        object = OkHttpClient.class.getName();
        Intrinsics.checkExpressionValueIsNotNull(object, "OkHttpClient::class.java.name");
        object2.put(object, "okhttp.OkHttpClient");
        object = Http2.class.getName();
        Intrinsics.checkExpressionValueIsNotNull(object, "Http2::class.java.name");
        object2.put(object, "okhttp.Http2");
        object = TaskRunner.class.getName();
        Intrinsics.checkExpressionValueIsNotNull(object, "TaskRunner::class.java.name");
        object2.put(object, "okhttp.TaskRunner");
        knownLoggers = MapsKt.toMap(object2);
    }

    private AndroidLog() {
    }

    private final void enableLogging(String object, String string2) {
        Logger logger = Logger.getLogger((String)object);
        if (!configuredLoggers.add(logger)) return;
        Intrinsics.checkExpressionValueIsNotNull(logger, "logger");
        logger.setUseParentHandlers(false);
        object = Log.isLoggable((String)string2, (int)3) ? Level.FINE : (Log.isLoggable((String)string2, (int)4) ? Level.INFO : Level.WARNING);
        logger.setLevel((Level)object);
        logger.addHandler(AndroidLogHandler.INSTANCE);
    }

    private final String loggerTag(String string2) {
        String string3 = knownLoggers.get(string2);
        if (string3 == null) return StringsKt.take(string2, 23);
        return string3;
    }

    public final void androidLog$okhttp(String charSequence, int n, String string2, Throwable throwable) {
        Intrinsics.checkParameterIsNotNull(charSequence, "loggerName");
        Intrinsics.checkParameterIsNotNull(string2, "message");
        String string3 = this.loggerTag((String)charSequence);
        if (!Log.isLoggable((String)string3, (int)n)) return;
        charSequence = string2;
        if (throwable != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("\n");
            ((StringBuilder)charSequence).append(Log.getStackTraceString((Throwable)throwable));
            charSequence = ((StringBuilder)charSequence).toString();
        }
        int n2 = 0;
        int n3 = ((String)charSequence).length();
        block0 : while (n2 < n3) {
            int n4 = StringsKt.indexOf$default(charSequence, '\n', n2, false, 4, null);
            if (n4 == -1) {
                n4 = n3;
            }
            do {
                int n5 = Math.min(n4, n2 + 4000);
                if (charSequence == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                string2 = ((String)charSequence).substring(n2, n5);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                Log.println((int)n, (String)string3, (String)string2);
                if (n5 >= n4) {
                    n2 = n5 + 1;
                    continue block0;
                }
                n2 = n5;
            } while (true);
            break;
        }
        return;
    }

    public final void enable() {
        Iterator<Map.Entry<String, String>> iterator2 = knownLoggers.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, String> entry = iterator2.next();
            this.enableLogging(entry.getKey(), entry.getValue());
        }
    }
}

