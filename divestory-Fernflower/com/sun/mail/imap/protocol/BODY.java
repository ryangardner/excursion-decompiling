package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ParsingException;
import java.io.ByteArrayInputStream;

public class BODY implements Item {
   static final char[] name = new char[]{'B', 'O', 'D', 'Y'};
   public ByteArray data;
   public int msgno;
   public int origin = 0;
   public String section;

   public BODY(FetchResponse var1) throws ParsingException {
      this.msgno = var1.getNumber();
      var1.skipSpaces();

      byte var2;
      do {
         var2 = var1.readByte();
         if (var2 == 93) {
            if (var1.readByte() == 60) {
               this.origin = var1.readNumber();
               var1.skip(1);
            }

            this.data = var1.readByteArray();
            return;
         }
      } while(var2 != 0);

      throw new ParsingException("BODY parse error: missing ``]'' at section end");
   }

   public ByteArray getByteArray() {
      return this.data;
   }

   public ByteArrayInputStream getByteArrayInputStream() {
      ByteArray var1 = this.data;
      return var1 != null ? var1.toByteArrayInputStream() : null;
   }
}
