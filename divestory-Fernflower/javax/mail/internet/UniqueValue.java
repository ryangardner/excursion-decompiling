package javax.mail.internet;

import javax.mail.Session;

class UniqueValue {
   private static int id;

   public static String getUniqueBoundaryValue() {
      StringBuffer var0 = new StringBuffer();
      var0.append("----=_Part_");
      var0.append(getUniqueId());
      var0.append("_");
      var0.append(var0.hashCode());
      var0.append('.');
      var0.append(System.currentTimeMillis());
      return var0.toString();
   }

   private static int getUniqueId() {
      synchronized(UniqueValue.class){}

      int var0;
      try {
         var0 = id++;
      } finally {
         ;
      }

      return var0;
   }

   public static String getUniqueMessageIDValue(Session var0) {
      InternetAddress var2 = InternetAddress.getLocalAddress(var0);
      String var3;
      if (var2 != null) {
         var3 = var2.getAddress();
      } else {
         var3 = "javamailuser@localhost";
      }

      StringBuffer var1 = new StringBuffer();
      var1.append(var1.hashCode());
      var1.append('.');
      var1.append(getUniqueId());
      var1.append('.');
      var1.append(System.currentTimeMillis());
      var1.append('.');
      var1.append("JavaMail.");
      var1.append(var3);
      return var1.toString();
   }
}
