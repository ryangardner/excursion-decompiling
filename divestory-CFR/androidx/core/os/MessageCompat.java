/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Message
 */
package androidx.core.os;

import android.os.Build;
import android.os.Message;

public final class MessageCompat {
    private static boolean sTryIsAsynchronous = true;
    private static boolean sTrySetAsynchronous = true;

    private MessageCompat() {
    }

    public static boolean isAsynchronous(Message message) {
        if (Build.VERSION.SDK_INT >= 22) {
            return message.isAsynchronous();
        }
        if (!sTryIsAsynchronous) return false;
        if (Build.VERSION.SDK_INT < 16) return false;
        try {
            return message.isAsynchronous();
        }
        catch (NoSuchMethodError noSuchMethodError) {
            sTryIsAsynchronous = false;
        }
        return false;
    }

    public static void setAsynchronous(Message message, boolean bl) {
        if (Build.VERSION.SDK_INT >= 22) {
            message.setAsynchronous(bl);
            return;
        }
        if (!sTrySetAsynchronous) return;
        if (Build.VERSION.SDK_INT < 16) return;
        try {
            message.setAsynchronous(bl);
            return;
        }
        catch (NoSuchMethodError noSuchMethodError) {
            sTrySetAsynchronous = false;
        }
    }
}

