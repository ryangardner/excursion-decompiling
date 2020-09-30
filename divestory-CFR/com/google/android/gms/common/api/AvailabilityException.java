/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.common.api;

import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class AvailabilityException
extends Exception {
    private final ArrayMap<ApiKey<?>, ConnectionResult> zaa;

    public AvailabilityException(ArrayMap<ApiKey<?>, ConnectionResult> arrayMap) {
        this.zaa = arrayMap;
    }

    public ConnectionResult getConnectionResult(GoogleApi<? extends Api.ApiOptions> object) {
        ApiKey<? extends Api.ApiOptions> apiKey = ((GoogleApi)object).getApiKey();
        boolean bl = this.zaa.get(apiKey) != null;
        String string2 = apiKey.getApiName();
        object = new StringBuilder(String.valueOf(string2).length() + 58);
        ((StringBuilder)object).append("The given API (");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(") was not part of the availability request.");
        Preconditions.checkArgument(bl, ((StringBuilder)object).toString());
        return Preconditions.checkNotNull((ConnectionResult)this.zaa.get(apiKey));
    }

    public ConnectionResult getConnectionResult(HasApiKey<? extends Api.ApiOptions> object) {
        ApiKey<? extends Api.ApiOptions> apiKey = object.getApiKey();
        boolean bl = this.zaa.get(apiKey) != null;
        String string2 = apiKey.getApiName();
        object = new StringBuilder(String.valueOf(string2).length() + 58);
        ((StringBuilder)object).append("The given API (");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(") was not part of the availability request.");
        Preconditions.checkArgument(bl, ((StringBuilder)object).toString());
        return Preconditions.checkNotNull((ConnectionResult)this.zaa.get(apiKey));
    }

    @Override
    public String getMessage() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Object object = this.zaa.keySet().iterator();
        boolean bl = true;
        while (object.hasNext()) {
            ApiKey<?> apiKey = object.next();
            Object object2 = Preconditions.checkNotNull((ConnectionResult)this.zaa.get(apiKey));
            if (((ConnectionResult)object2).isSuccess()) {
                bl = false;
            }
            apiKey = apiKey.getApiName();
            String string2 = String.valueOf(object2);
            object2 = new StringBuilder(String.valueOf(apiKey).length() + 2 + String.valueOf(string2).length());
            ((StringBuilder)object2).append((String)((Object)apiKey));
            ((StringBuilder)object2).append(": ");
            ((StringBuilder)object2).append(string2);
            arrayList.add(((StringBuilder)object2).toString());
        }
        object = new StringBuilder();
        if (bl) {
            ((StringBuilder)object).append("None of the queried APIs are available. ");
        } else {
            ((StringBuilder)object).append("Some of the queried APIs are unavailable. ");
        }
        ((StringBuilder)object).append(TextUtils.join((CharSequence)"; ", arrayList));
        return ((StringBuilder)object).toString();
    }
}

