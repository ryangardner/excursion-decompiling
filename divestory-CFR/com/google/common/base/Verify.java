/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Strings;
import com.google.common.base.VerifyException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Verify {
    private Verify() {
    }

    public static void verify(boolean bl) {
        if (!bl) throw new VerifyException();
    }

    public static void verify(boolean bl, @NullableDecl String string2, char c) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, Character.valueOf(c)));
    }

    public static void verify(boolean bl, @NullableDecl String string2, char c, char c2) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, Character.valueOf(c), Character.valueOf(c2)));
    }

    public static void verify(boolean bl, @NullableDecl String string2, char c, int n) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, Character.valueOf(c), n));
    }

    public static void verify(boolean bl, @NullableDecl String string2, char c, long l) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, Character.valueOf(c), l));
    }

    public static void verify(boolean bl, @NullableDecl String string2, char c, @NullableDecl Object object) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, Character.valueOf(c), object));
    }

    public static void verify(boolean bl, @NullableDecl String string2, int n) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, n));
    }

    public static void verify(boolean bl, @NullableDecl String string2, int n, char c) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, n, Character.valueOf(c)));
    }

    public static void verify(boolean bl, @NullableDecl String string2, int n, int n2) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, n, n2));
    }

    public static void verify(boolean bl, @NullableDecl String string2, int n, long l) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, n, l));
    }

    public static void verify(boolean bl, @NullableDecl String string2, int n, @NullableDecl Object object) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, n, object));
    }

    public static void verify(boolean bl, @NullableDecl String string2, long l) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, l));
    }

    public static void verify(boolean bl, @NullableDecl String string2, long l, char c) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, l, Character.valueOf(c)));
    }

    public static void verify(boolean bl, @NullableDecl String string2, long l, int n) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, l, n));
    }

    public static void verify(boolean bl, @NullableDecl String string2, long l, long l2) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, l, l2));
    }

    public static void verify(boolean bl, @NullableDecl String string2, long l, @NullableDecl Object object) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, l, object));
    }

    public static void verify(boolean bl, @NullableDecl String string2, @NullableDecl Object object) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, object));
    }

    public static void verify(boolean bl, @NullableDecl String string2, @NullableDecl Object object, char c) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, object, Character.valueOf(c)));
    }

    public static void verify(boolean bl, @NullableDecl String string2, @NullableDecl Object object, int n) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, object, n));
    }

    public static void verify(boolean bl, @NullableDecl String string2, @NullableDecl Object object, long l) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, object, l));
    }

    public static void verify(boolean bl, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, object, object2));
    }

    public static void verify(boolean bl, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2, @NullableDecl Object object3) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, object, object2, object3));
    }

    public static void verify(boolean bl, @NullableDecl String string2, @NullableDecl Object object, @NullableDecl Object object2, @NullableDecl Object object3, @NullableDecl Object object4) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, object, object2, object3, object4));
    }

    public static void verify(boolean bl, @NullableDecl String string2, @NullableDecl Object ... arrobject) {
        if (!bl) throw new VerifyException(Strings.lenientFormat(string2, arrobject));
    }

    public static <T> T verifyNotNull(@NullableDecl T t) {
        return Verify.verifyNotNull(t, "expected a non-null reference", new Object[0]);
    }

    public static <T> T verifyNotNull(@NullableDecl T t, @NullableDecl String string2, @NullableDecl Object ... arrobject) {
        boolean bl = t != null;
        Verify.verify(bl, string2, arrobject);
        return t;
    }
}

