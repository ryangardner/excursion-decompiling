package org.apache.commons.net.ntp;

public final class NtpUtils {
   public static String getHostAddress(int var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append(var0 >>> 24 & 255);
      var1.append(".");
      var1.append(var0 >>> 16 & 255);
      var1.append(".");
      var1.append(var0 >>> 8 & 255);
      var1.append(".");
      var1.append(var0 >>> 0 & 255);
      return var1.toString();
   }

   public static String getModeName(int var0) {
      switch(var0) {
      case 0:
         return "Reserved";
      case 1:
         return "Symmetric Active";
      case 2:
         return "Symmetric Passive";
      case 3:
         return "Client";
      case 4:
         return "Server";
      case 5:
         return "Broadcast";
      case 6:
         return "Control";
      case 7:
         return "Private";
      default:
         return "Unknown";
      }
   }

   public static String getRefAddress(NtpV3Packet var0) {
      int var1;
      if (var0 == null) {
         var1 = 0;
      } else {
         var1 = var0.getReferenceId();
      }

      return getHostAddress(var1);
   }

   public static String getReferenceClock(NtpV3Packet var0) {
      if (var0 == null) {
         return "";
      } else {
         int var1 = var0.getReferenceId();
         if (var1 == 0) {
            return "";
         } else {
            StringBuilder var4 = new StringBuilder(4);

            for(int var2 = 24; var2 >= 0; var2 -= 8) {
               char var3 = (char)(var1 >>> var2 & 255);
               if (var3 == 0) {
                  break;
               }

               if (!Character.isLetterOrDigit(var3)) {
                  return "";
               }

               var4.append(var3);
            }

            return var4.toString();
         }
      }
   }
}
