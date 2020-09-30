package com.google.api.client.http;

public class HttpStatusCodes {
   public static final int STATUS_CODE_ACCEPTED = 202;
   public static final int STATUS_CODE_BAD_GATEWAY = 502;
   public static final int STATUS_CODE_BAD_REQUEST = 400;
   public static final int STATUS_CODE_CONFLICT = 409;
   public static final int STATUS_CODE_CREATED = 201;
   public static final int STATUS_CODE_FORBIDDEN = 403;
   public static final int STATUS_CODE_FOUND = 302;
   public static final int STATUS_CODE_METHOD_NOT_ALLOWED = 405;
   public static final int STATUS_CODE_MOVED_PERMANENTLY = 301;
   public static final int STATUS_CODE_MULTIPLE_CHOICES = 300;
   public static final int STATUS_CODE_NOT_FOUND = 404;
   public static final int STATUS_CODE_NOT_MODIFIED = 304;
   public static final int STATUS_CODE_NO_CONTENT = 204;
   public static final int STATUS_CODE_OK = 200;
   private static final int STATUS_CODE_PERMANENT_REDIRECT = 308;
   public static final int STATUS_CODE_PRECONDITION_FAILED = 412;
   public static final int STATUS_CODE_SEE_OTHER = 303;
   public static final int STATUS_CODE_SERVER_ERROR = 500;
   public static final int STATUS_CODE_SERVICE_UNAVAILABLE = 503;
   public static final int STATUS_CODE_TEMPORARY_REDIRECT = 307;
   public static final int STATUS_CODE_UNAUTHORIZED = 401;
   public static final int STATUS_CODE_UNPROCESSABLE_ENTITY = 422;

   public static boolean isRedirect(int var0) {
      if (var0 != 307 && var0 != 308) {
         switch(var0) {
         case 301:
         case 302:
         case 303:
            break;
         default:
            return false;
         }
      }

      return true;
   }

   public static boolean isSuccess(int var0) {
      boolean var1;
      if (var0 >= 200 && var0 < 300) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
