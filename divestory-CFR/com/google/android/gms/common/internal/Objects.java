/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class Objects {
    private Objects() {
        throw new AssertionError((Object)"Uninstantiable");
    }

    public static boolean checkBundlesEquality(Bundle bundle, Bundle bundle2) {
        if (bundle != null && bundle2 != null) {
            if (bundle.size() != bundle2.size()) {
                return false;
            }
            Object object = bundle.keySet();
            if (!object.containsAll(bundle2.keySet())) {
                return false;
            }
            Iterator iterator2 = object.iterator();
            do {
                if (!iterator2.hasNext()) return true;
            } while (Objects.equal(bundle.get((String)(object = (String)iterator2.next())), bundle2.get((String)object)));
            return false;
        }
        if (bundle != bundle2) return false;
        return true;
    }

    public static boolean equal(Object object, Object object2) {
        if (object == object2) return true;
        if (object == null) return false;
        if (!object.equals(object2)) return false;
        return true;
    }

    public static int hashCode(Object ... arrobject) {
        return Arrays.hashCode(arrobject);
    }

    public static ToStringHelper toStringHelper(Object object) {
        return new ToStringHelper(object, null);
    }

    public static final class ToStringHelper {
        private final List<String> zza;
        private final Object zzb;

        private ToStringHelper(Object object) {
            this.zzb = Preconditions.checkNotNull(object);
            this.zza = new ArrayList<String>();
        }

        /* synthetic */ ToStringHelper(Object object, zzu zzu2) {
            this(object);
        }

        public final ToStringHelper add(String string2, Object object) {
            List<String> list = this.zza;
            string2 = Preconditions.checkNotNull(string2);
            object = String.valueOf(object);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 1 + String.valueOf(object).length());
            stringBuilder.append(string2);
            stringBuilder.append("=");
            stringBuilder.append((String)object);
            list.add(stringBuilder.toString());
            return this;
        }

        public final String toString() {
            StringBuilder stringBuilder = new StringBuilder(100);
            stringBuilder.append(this.zzb.getClass().getSimpleName());
            stringBuilder.append('{');
            int n = this.zza.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    stringBuilder.append('}');
                    return stringBuilder.toString();
                }
                stringBuilder.append(this.zza.get(n2));
                if (n2 < n - 1) {
                    stringBuilder.append(", ");
                }
                ++n2;
            } while (true);
        }
    }

}

