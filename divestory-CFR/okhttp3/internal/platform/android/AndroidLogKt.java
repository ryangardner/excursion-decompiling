/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.platform.android;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0005"}, d2={"androidLevel", "", "Ljava/util/logging/LogRecord;", "getAndroidLevel", "(Ljava/util/logging/LogRecord;)I", "okhttp"}, k=2, mv={1, 1, 16})
public final class AndroidLogKt {
    public static final /* synthetic */ int access$getAndroidLevel$p(LogRecord logRecord) {
        return AndroidLogKt.getAndroidLevel(logRecord);
    }

    private static final int getAndroidLevel(LogRecord logRecord) {
        if (logRecord.getLevel().intValue() > Level.INFO.intValue()) {
            return 5;
        }
        if (logRecord.getLevel().intValue() != Level.INFO.intValue()) return 3;
        return 4;
    }
}

