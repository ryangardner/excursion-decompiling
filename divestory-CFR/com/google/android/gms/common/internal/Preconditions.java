/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.text.TextUtils
 *  org.checkerframework.checker.nullness.qual.EnsuresNonNull
 */
package com.google.android.gms.common.internal;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.common.util.zzb;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public final class Preconditions {
    private Preconditions() {
        throw new AssertionError((Object)"Uninstantiable");
    }

    public static void checkArgument(boolean bl) {
        if (!bl) throw new IllegalArgumentException();
    }

    public static void checkArgument(boolean bl, Object object) {
        if (!bl) throw new IllegalArgumentException(String.valueOf(object));
    }

    public static void checkArgument(boolean bl, String string2, Object ... arrobject) {
        if (!bl) throw new IllegalArgumentException(String.format(string2, arrobject));
    }

    public static void checkHandlerThread(Handler object) {
        Object object2 = Looper.myLooper();
        if (object2 == object.getLooper()) return;
        object2 = object2 != null ? object2.getThread().getName() : "null current looper";
        object = object.getLooper().getThread().getName();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(object).length() + 36 + String.valueOf(object2).length());
        stringBuilder.append("Must be called on ");
        stringBuilder.append((String)object);
        stringBuilder.append(" thread, but got ");
        stringBuilder.append((String)object2);
        stringBuilder.append(".");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public static void checkHandlerThread(Handler handler, String string2) {
        if (Looper.myLooper() != handler.getLooper()) throw new IllegalStateException(string2);
    }

    public static void checkMainThread(String string2) {
        if (!zzb.zza()) throw new IllegalStateException(string2);
    }

    public static String checkNotEmpty(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) throw new IllegalArgumentException("Given String is empty or null");
        return string2;
    }

    public static String checkNotEmpty(String string2, Object object) {
        if (TextUtils.isEmpty((CharSequence)string2)) throw new IllegalArgumentException(String.valueOf(object));
        return string2;
    }

    public static void checkNotMainThread() {
        Preconditions.checkNotMainThread("Must not be called on the main application thread");
    }

    public static void checkNotMainThread(String string2) {
        if (zzb.zza()) throw new IllegalStateException(string2);
    }

    @EnsuresNonNull(value={"#1"})
    public static <T> T checkNotNull(T t) {
        if (t == null) throw new NullPointerException("null reference");
        return t;
    }

    @EnsuresNonNull(value={"#1"})
    public static <T> T checkNotNull(T t, Object object) {
        if (t == null) throw new NullPointerException(String.valueOf(object));
        return t;
    }

    public static int checkNotZero(int n) {
        if (n == 0) throw new IllegalArgumentException("Given Integer is zero");
        return n;
    }

    public static int checkNotZero(int n, Object object) {
        if (n == 0) throw new IllegalArgumentException(String.valueOf(object));
        return n;
    }

    public static long checkNotZero(long l) {
        if (l == 0L) throw new IllegalArgumentException("Given Long is zero");
        return l;
    }

    public static long checkNotZero(long l, Object object) {
        if (l == 0L) throw new IllegalArgumentException(String.valueOf(object));
        return l;
    }

    public static void checkState(boolean bl) {
        if (!bl) throw new IllegalStateException();
    }

    public static void checkState(boolean bl, Object object) {
        if (!bl) throw new IllegalStateException(String.valueOf(object));
    }

    public static void checkState(boolean bl, String string2, Object ... arrobject) {
        if (!bl) throw new IllegalStateException(String.format(string2, arrobject));
    }
}

