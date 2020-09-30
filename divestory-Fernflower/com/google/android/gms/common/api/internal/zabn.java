package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public interface zabn {
   ConnectionResult zaa(long var1, TimeUnit var3);

   ConnectionResult zaa(Api<?> var1);

   <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T var1);

   void zaa();

   void zaa(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

   boolean zaa(SignInConnectionListener var1);

   ConnectionResult zab();

   <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T var1);

   void zac();

   boolean zad();

   boolean zae();

   void zaf();

   void zag();
}
