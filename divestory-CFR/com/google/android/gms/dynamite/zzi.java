/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  dalvik.system.PathClassLoader
 */
package com.google.android.gms.dynamite;

import dalvik.system.PathClassLoader;

final class zzi
extends PathClassLoader {
    zzi(String string2, ClassLoader classLoader) {
        super(string2, classLoader);
    }

    protected final Class<?> loadClass(String string2, boolean bl) throws ClassNotFoundException {
        if (string2.startsWith("java.")) return super.loadClass(string2, bl);
        if (string2.startsWith("android.")) return super.loadClass(string2, bl);
        try {
            return this.findClass(string2);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return super.loadClass(string2, bl);
        }
    }
}

