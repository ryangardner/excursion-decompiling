package com.google.zxing.client.result;

public final class SMSParsedResult extends ParsedResult {
   private final String body;
   private final String[] numbers;
   private final String subject;
   private final String[] vias;

   public SMSParsedResult(String var1, String var2, String var3, String var4) {
      super(ParsedResultType.SMS);
      this.numbers = new String[]{var1};
      this.vias = new String[]{var2};
      this.subject = var3;
      this.body = var4;
   }

   public SMSParsedResult(String[] var1, String[] var2, String var3, String var4) {
      super(ParsedResultType.SMS);
      this.numbers = var1;
      this.vias = var2;
      this.subject = var3;
      this.body = var4;
   }

   public String getBody() {
      return this.body;
   }

   public String getDisplayResult() {
      StringBuilder var1 = new StringBuilder(100);
      maybeAppend(this.numbers, var1);
      maybeAppend(this.subject, var1);
      maybeAppend(this.body, var1);
      return var1.toString();
   }

   public String[] getNumbers() {
      return this.numbers;
   }

   public String getSMSURI() {
      StringBuilder var1 = new StringBuilder();
      var1.append("sms:");
      boolean var2 = false;
      int var3 = 0;

      boolean var4;
      for(var4 = true; var3 < this.numbers.length; ++var3) {
         if (var4) {
            var4 = false;
         } else {
            var1.append(',');
         }

         var1.append(this.numbers[var3]);
         String[] var5 = this.vias;
         if (var5 != null && var5[var3] != null) {
            var1.append(";via=");
            var1.append(this.vias[var3]);
         }
      }

      boolean var6;
      if (this.body != null) {
         var6 = true;
      } else {
         var6 = false;
      }

      var4 = var2;
      if (this.subject != null) {
         var4 = true;
      }

      if (var6 || var4) {
         var1.append('?');
         if (var6) {
            var1.append("body=");
            var1.append(this.body);
         }

         if (var4) {
            if (var6) {
               var1.append('&');
            }

            var1.append("subject=");
            var1.append(this.subject);
         }
      }

      return var1.toString();
   }

   public String getSubject() {
      return this.subject;
   }

   public String[] getVias() {
      return this.vias;
   }
}
