package org.apache.commons.net.smtp;

public final class SMTPReply {
   public static final int ACTION_ABORTED = 451;
   public static final int ACTION_NOT_TAKEN = 450;
   public static final int ACTION_OK = 250;
   public static final int BAD_COMMAND_SEQUENCE = 503;
   public static final int COMMAND_NOT_IMPLEMENTED = 502;
   public static final int COMMAND_NOT_IMPLEMENTED_FOR_PARAMETER = 504;
   public static final int HELP_MESSAGE = 214;
   public static final int INSUFFICIENT_STORAGE = 452;
   public static final int MAILBOX_NAME_NOT_ALLOWED = 553;
   public static final int MAILBOX_UNAVAILABLE = 550;
   public static final int SERVICE_CLOSING_TRANSMISSION_CHANNEL = 221;
   public static final int SERVICE_NOT_AVAILABLE = 421;
   public static final int SERVICE_READY = 220;
   public static final int START_MAIL_INPUT = 354;
   public static final int STORAGE_ALLOCATION_EXCEEDED = 552;
   public static final int SYNTAX_ERROR_IN_ARGUMENTS = 501;
   public static final int SYSTEM_STATUS = 211;
   public static final int TRANSACTION_FAILED = 554;
   public static final int UNRECOGNIZED_COMMAND = 500;
   public static final int USER_NOT_LOCAL = 551;
   public static final int USER_NOT_LOCAL_WILL_FORWARD = 251;

   private SMTPReply() {
   }

   public static boolean isNegativePermanent(int var0) {
      boolean var1;
      if (var0 >= 500 && var0 < 600) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isNegativeTransient(int var0) {
      boolean var1;
      if (var0 >= 400 && var0 < 500) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isPositiveCompletion(int var0) {
      boolean var1;
      if (var0 >= 200 && var0 < 300) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isPositiveIntermediate(int var0) {
      boolean var1;
      if (var0 >= 300 && var0 < 400) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isPositivePreliminary(int var0) {
      boolean var1;
      if (var0 >= 100 && var0 < 200) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
