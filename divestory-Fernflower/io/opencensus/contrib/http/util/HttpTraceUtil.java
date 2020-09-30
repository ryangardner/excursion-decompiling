package io.opencensus.contrib.http.util;

import io.opencensus.trace.Status;
import javax.annotation.Nullable;

public final class HttpTraceUtil {
   private static final Status STATUS_100;
   private static final Status STATUS_101;
   private static final Status STATUS_402;
   private static final Status STATUS_405;
   private static final Status STATUS_406;
   private static final Status STATUS_407;
   private static final Status STATUS_408;
   private static final Status STATUS_409;
   private static final Status STATUS_410;
   private static final Status STATUS_411;
   private static final Status STATUS_412;
   private static final Status STATUS_413;
   private static final Status STATUS_414;
   private static final Status STATUS_415;
   private static final Status STATUS_416;
   private static final Status STATUS_417;
   private static final Status STATUS_500;
   private static final Status STATUS_502;
   private static final Status STATUS_505;

   static {
      STATUS_100 = Status.UNKNOWN.withDescription("Continue");
      STATUS_101 = Status.UNKNOWN.withDescription("Switching Protocols");
      STATUS_402 = Status.UNKNOWN.withDescription("Payment Required");
      STATUS_405 = Status.UNKNOWN.withDescription("Method Not Allowed");
      STATUS_406 = Status.UNKNOWN.withDescription("Not Acceptable");
      STATUS_407 = Status.UNKNOWN.withDescription("Proxy Authentication Required");
      STATUS_408 = Status.UNKNOWN.withDescription("Request Time-out");
      STATUS_409 = Status.UNKNOWN.withDescription("Conflict");
      STATUS_410 = Status.UNKNOWN.withDescription("Gone");
      STATUS_411 = Status.UNKNOWN.withDescription("Length Required");
      STATUS_412 = Status.UNKNOWN.withDescription("Precondition Failed");
      STATUS_413 = Status.UNKNOWN.withDescription("Request Entity Too Large");
      STATUS_414 = Status.UNKNOWN.withDescription("Request-URI Too Large");
      STATUS_415 = Status.UNKNOWN.withDescription("Unsupported Media Type");
      STATUS_416 = Status.UNKNOWN.withDescription("Requested range not satisfiable");
      STATUS_417 = Status.UNKNOWN.withDescription("Expectation Failed");
      STATUS_500 = Status.UNKNOWN.withDescription("Internal Server Error");
      STATUS_502 = Status.UNKNOWN.withDescription("Bad Gateway");
      STATUS_505 = Status.UNKNOWN.withDescription("HTTP Version not supported");
   }

   private HttpTraceUtil() {
   }

   public static final Status parseResponseStatus(int var0, @Nullable Throwable var1) {
      String var3;
      if (var1 != null) {
         String var2 = var1.getMessage();
         var3 = var2;
         if (var2 == null) {
            var3 = var1.getClass().getSimpleName();
         }
      } else {
         var3 = null;
      }

      if (var0 == 0) {
         return Status.UNKNOWN.withDescription(var3);
      } else if (var0 >= 200 && var0 < 400) {
         return Status.OK;
      } else if (var0 != 100) {
         if (var0 != 101) {
            if (var0 != 429) {
               switch(var0) {
               case 400:
                  return Status.INVALID_ARGUMENT.withDescription(var3);
               case 401:
                  return Status.UNAUTHENTICATED.withDescription(var3);
               case 402:
                  return STATUS_402;
               case 403:
                  return Status.PERMISSION_DENIED.withDescription(var3);
               case 404:
                  return Status.NOT_FOUND.withDescription(var3);
               case 405:
                  return STATUS_405;
               case 406:
                  return STATUS_406;
               case 407:
                  return STATUS_407;
               case 408:
                  return STATUS_408;
               case 409:
                  return STATUS_409;
               case 410:
                  return STATUS_410;
               case 411:
                  return STATUS_411;
               case 412:
                  return STATUS_412;
               case 413:
                  return STATUS_413;
               case 414:
                  return STATUS_414;
               case 415:
                  return STATUS_415;
               case 416:
                  return STATUS_416;
               case 417:
                  return STATUS_417;
               default:
                  switch(var0) {
                  case 500:
                     return STATUS_500;
                  case 501:
                     return Status.UNIMPLEMENTED.withDescription(var3);
                  case 502:
                     return STATUS_502;
                  case 503:
                     return Status.UNAVAILABLE.withDescription(var3);
                  case 504:
                     return Status.DEADLINE_EXCEEDED.withDescription(var3);
                  case 505:
                     return STATUS_505;
                  default:
                     return Status.UNKNOWN.withDescription(var3);
                  }
               }
            } else {
               return Status.RESOURCE_EXHAUSTED.withDescription(var3);
            }
         } else {
            return STATUS_101;
         }
      } else {
         return STATUS_100;
      }
   }
}
