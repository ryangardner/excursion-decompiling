package org.apache.commons.net.imap;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.net.MalformedServerReplyException;

public final class IMAPReply {
   public static final int BAD = 2;
   public static final int CONT = 3;
   private static final String IMAP_BAD = "BAD";
   private static final String IMAP_CONTINUATION_PREFIX = "+";
   private static final String IMAP_NO = "NO";
   private static final String IMAP_OK = "OK";
   private static final String IMAP_UNTAGGED_PREFIX = "* ";
   private static final Pattern LITERAL_PATTERN = Pattern.compile("\\{(\\d+)\\}$");
   public static final int NO = 1;
   public static final int OK = 0;
   public static final int PARTIAL = 3;
   private static final Pattern TAGGED_PATTERN = Pattern.compile("^\\w+ (\\S+).*");
   private static final String TAGGED_RESPONSE = "^\\w+ (\\S+).*";
   private static final Pattern UNTAGGED_PATTERN = Pattern.compile("^\\* (\\S+).*");
   private static final String UNTAGGED_RESPONSE = "^\\* (\\S+).*";

   private IMAPReply() {
   }

   public static int getReplyCode(String var0) throws IOException {
      return getReplyCode(var0, TAGGED_PATTERN);
   }

   private static int getReplyCode(String var0, Pattern var1) throws IOException {
      if (isContinuation(var0)) {
         return 3;
      } else {
         Matcher var2 = var1.matcher(var0);
         if (var2.matches()) {
            String var3 = var2.group(1);
            if (var3.equals("OK")) {
               return 0;
            }

            if (var3.equals("BAD")) {
               return 2;
            }

            if (var3.equals("NO")) {
               return 1;
            }
         }

         StringBuilder var4 = new StringBuilder();
         var4.append("Received unexpected IMAP protocol response from server: '");
         var4.append(var0);
         var4.append("'.");
         throw new MalformedServerReplyException(var4.toString());
      }
   }

   public static int getUntaggedReplyCode(String var0) throws IOException {
      return getReplyCode(var0, UNTAGGED_PATTERN);
   }

   public static boolean isContinuation(int var0) {
      boolean var1;
      if (var0 == 3) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isContinuation(String var0) {
      return var0.startsWith("+");
   }

   public static boolean isSuccess(int var0) {
      boolean var1;
      if (var0 == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isUntagged(String var0) {
      return var0.startsWith("* ");
   }

   public static int literalCount(String var0) {
      Matcher var1 = LITERAL_PATTERN.matcher(var0);
      return var1.find() ? Integer.parseInt(var1.group(1)) : -1;
   }
}
