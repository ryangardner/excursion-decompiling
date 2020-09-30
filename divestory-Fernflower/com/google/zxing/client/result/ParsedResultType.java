package com.google.zxing.client.result;

public enum ParsedResultType {
   ADDRESSBOOK,
   CALENDAR,
   EMAIL_ADDRESS,
   GEO,
   ISBN,
   PRODUCT,
   SMS,
   TEL,
   TEXT,
   URI,
   VIN,
   WIFI;

   static {
      ParsedResultType var0 = new ParsedResultType("VIN", 11);
      VIN = var0;
   }
}
