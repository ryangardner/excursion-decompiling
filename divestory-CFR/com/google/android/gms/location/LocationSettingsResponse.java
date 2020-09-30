/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.location;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;

public class LocationSettingsResponse
extends Response<LocationSettingsResult> {
    public LocationSettingsStates getLocationSettingsStates() {
        return ((LocationSettingsResult)this.getResult()).getLocationSettingsStates();
    }
}

