/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.database.sqlite.SQLiteCursor
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.database.sqlite;

import android.database.sqlite.SQLiteCursor;
import android.os.Build;

public final class SQLiteCursorCompat {
    private SQLiteCursorCompat() {
    }

    public static void setFillWindowForwardOnly(SQLiteCursor sQLiteCursor, boolean bl) {
        if (Build.VERSION.SDK_INT < 28) return;
        sQLiteCursor.setFillWindowForwardOnly(bl);
    }
}

