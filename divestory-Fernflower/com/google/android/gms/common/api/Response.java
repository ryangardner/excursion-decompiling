package com.google.android.gms.common.api;

public class Response<T extends Result> {
   private T zza;

   public Response() {
   }

   protected Response(T var1) {
      this.zza = var1;
   }

   protected T getResult() {
      return this.zza;
   }

   public void setResult(T var1) {
      this.zza = var1;
   }
}
