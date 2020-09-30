package okhttp3.internal.http;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u000b"},
   d2 = {"Lokhttp3/internal/http/HttpMethod;", "", "()V", "invalidatesCache", "", "method", "", "permitsRequestBody", "redirectsToGet", "redirectsWithBody", "requiresRequestBody", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class HttpMethod {
   public static final HttpMethod INSTANCE = new HttpMethod();

   private HttpMethod() {
   }

   @JvmStatic
   public static final boolean permitsRequestBody(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "method");
      boolean var1;
      if (!Intrinsics.areEqual((Object)var0, (Object)"GET") && !Intrinsics.areEqual((Object)var0, (Object)"HEAD")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public static final boolean requiresRequestBody(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "method");
      boolean var1;
      if (!Intrinsics.areEqual((Object)var0, (Object)"POST") && !Intrinsics.areEqual((Object)var0, (Object)"PUT") && !Intrinsics.areEqual((Object)var0, (Object)"PATCH") && !Intrinsics.areEqual((Object)var0, (Object)"PROPPATCH") && !Intrinsics.areEqual((Object)var0, (Object)"REPORT")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public final boolean invalidatesCache(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "method");
      boolean var2;
      if (!Intrinsics.areEqual((Object)var1, (Object)"POST") && !Intrinsics.areEqual((Object)var1, (Object)"PATCH") && !Intrinsics.areEqual((Object)var1, (Object)"PUT") && !Intrinsics.areEqual((Object)var1, (Object)"DELETE") && !Intrinsics.areEqual((Object)var1, (Object)"MOVE")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public final boolean redirectsToGet(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "method");
      return Intrinsics.areEqual((Object)var1, (Object)"PROPFIND") ^ true;
   }

   public final boolean redirectsWithBody(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "method");
      return Intrinsics.areEqual((Object)var1, (Object)"PROPFIND");
   }
}
