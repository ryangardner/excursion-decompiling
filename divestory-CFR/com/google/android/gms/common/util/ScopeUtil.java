/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.common.util;

import android.text.TextUtils;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class ScopeUtil {
    private ScopeUtil() {
    }

    public static Set<Scope> fromScopeString(Collection<String> collection) {
        Preconditions.checkNotNull(collection, "scopeStrings can't be null.");
        return ScopeUtil.fromScopeString(collection.toArray(new String[collection.size()]));
    }

    public static Set<Scope> fromScopeString(String ... arrstring) {
        Preconditions.checkNotNull(arrstring, "scopeStrings can't be null.");
        HashSet<Scope> hashSet = new HashSet<Scope>(arrstring.length);
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String string2 = arrstring[n2];
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                hashSet.add(new Scope(string2));
            }
            ++n2;
        }
        return hashSet;
    }

    public static String[] toScopeString(Set<Scope> set) {
        Preconditions.checkNotNull(set, "scopes can't be null.");
        return ScopeUtil.toScopeString(set.toArray(new Scope[set.size()]));
    }

    public static String[] toScopeString(Scope[] arrscope) {
        Preconditions.checkNotNull(arrscope, "scopes can't be null.");
        String[] arrstring = new String[arrscope.length];
        int n = 0;
        while (n < arrscope.length) {
            arrstring[n] = arrscope[n].getScopeUri();
            ++n;
        }
        return arrstring;
    }
}

