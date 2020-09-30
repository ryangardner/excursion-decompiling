package com.google.android.gms.common.api;

import com.google.android.gms.common.api.internal.ApiKey;

public interface HasApiKey<O extends Api.ApiOptions> {
   ApiKey<O> getApiKey();
}
