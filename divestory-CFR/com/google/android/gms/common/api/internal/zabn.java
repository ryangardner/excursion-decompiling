/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public interface zabn {
    public ConnectionResult zaa(long var1, TimeUnit var3);

    public ConnectionResult zaa(Api<?> var1);

    public <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T var1);

    public void zaa();

    public void zaa(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    public boolean zaa(SignInConnectionListener var1);

    public ConnectionResult zab();

    public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T var1);

    public void zac();

    public boolean zad();

    public boolean zae();

    public void zaf();

    public void zag();
}

