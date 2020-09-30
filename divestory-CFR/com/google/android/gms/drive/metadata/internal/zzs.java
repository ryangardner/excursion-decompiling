/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONArray
 *  org.json.JSONException
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.zzb;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONException;

public final class zzs
extends zzb<String> {
    public zzs(String string2, int n) {
        super(string2, Collections.singleton(string2), Collections.<String>emptySet(), 4300000);
    }

    @Override
    protected final /* synthetic */ void zza(Bundle bundle, Object object) {
        object = (Collection)object;
        bundle.putStringArrayList(this.getName(), new ArrayList(object));
    }

    @Override
    protected final /* synthetic */ Object zzb(Bundle bundle) {
        return bundle.getStringArrayList(this.getName());
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return ((zzb)this).zzd(dataHolder, n, n2);
    }

    @Override
    protected final Collection<String> zzd(DataHolder arrayList, int n, int n2) {
        try {
            String string2 = ((DataHolder)((Object)arrayList)).getString(this.getName(), n, n2);
            if (string2 == null) {
                return null;
            }
            arrayList = new ArrayList<String>();
            JSONArray jSONArray = new JSONArray(string2);
            n = 0;
            while (n < jSONArray.length()) {
                arrayList.add(jSONArray.getString(n));
                ++n;
            }
            return Collections.unmodifiableCollection(arrayList);
        }
        catch (JSONException jSONException) {
            throw new IllegalStateException("DataHolder supplied invalid JSON", jSONException);
        }
    }
}

