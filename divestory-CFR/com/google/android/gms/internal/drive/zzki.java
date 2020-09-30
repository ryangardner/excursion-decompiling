/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjr;
import com.google.android.gms.internal.drive.zzjx;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class zzki<T extends zzjx> {
    private static final Logger logger = Logger.getLogger(zzjr.class.getName());
    private static String zzro = "com.google.protobuf.BlazeGeneratedExtensionRegistryLiteLoader";

    zzki() {
    }

    static <T extends zzjx> T zza(Class<T> object) {
        Object object2;
        Object object3 = zzki.class.getClassLoader();
        if (object.equals(zzjx.class)) {
            object2 = zzro;
        } else {
            if (!((Class)object).getPackage().equals(zzki.class.getPackage())) throw new IllegalArgumentException(((Class)object).getName());
            object2 = String.format("%s.BlazeGenerated%sLoader", ((Class)object).getPackage().getName(), ((Class)object).getSimpleName());
        }
        try {
            object2 = Class.forName((String)object2, true, (ClassLoader)object3);
            try {
                object2 = (zzki)((Class)object2).getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (InvocationTargetException invocationTargetException) {
                IllegalStateException illegalStateException = new IllegalStateException(invocationTargetException);
                throw illegalStateException;
            }
            catch (IllegalAccessException illegalAccessException) {
                IllegalStateException illegalStateException = new IllegalStateException(illegalAccessException);
                throw illegalStateException;
            }
            catch (InstantiationException instantiationException) {
                object2 = new IllegalStateException(instantiationException);
                throw object2;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                IllegalStateException illegalStateException = new IllegalStateException(noSuchMethodException);
                throw illegalStateException;
            }
            return (T)((zzjx)((Class)object).cast(((zzki)object2).zzcu()));
        }
        catch (ClassNotFoundException classNotFoundException) {
            Iterator<zzki> iterator2 = ServiceLoader.load(zzki.class, (ClassLoader)object3).iterator();
            ArrayList<zzjx> arrayList = new ArrayList<zzjx>();
            while (iterator2.hasNext()) {
                try {
                    arrayList.add((zzjx)((Class)object).cast(iterator2.next().zzcu()));
                }
                catch (ServiceConfigurationError serviceConfigurationError) {
                    Logger logger = zzki.logger;
                    object3 = Level.SEVERE;
                    String string2 = String.valueOf(((Class)object).getSimpleName());
                    string2 = string2.length() != 0 ? "Unable to load ".concat(string2) : new String("Unable to load ");
                    logger.logp((Level)object3, "com.google.protobuf.GeneratedExtensionRegistryLoader", "load", string2, serviceConfigurationError);
                }
            }
            if (arrayList.size() == 1) {
                return (T)((zzjx)arrayList.get(0));
            }
            if (arrayList.size() == 0) {
                return null;
            }
            try {
                object = (zzjx)((Class)object).getMethod("combine", Collection.class).invoke(null, arrayList);
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new IllegalStateException(invocationTargetException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalStateException(illegalAccessException);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                throw new IllegalStateException(noSuchMethodException);
            }
            return (T)object;
        }
    }

    protected abstract T zzcu();
}

