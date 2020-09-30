package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ParsingException;
import java.io.ByteArrayInputStream;

public class RFC822DATA implements Item {
   static final char[] name = new char[]{'R', 'F', 'C', '8', '2', '2'};
   public ByteArray data;
   public int msgno;

   public RFC822DATA(FetchResponse var1) throws ParsingException {
      this.msgno = var1.getNumber();
      var1.skipSpaces();
      this.data = var1.readByteArray();
   }

   public ByteArray getByteArray() {
      return this.data;
   }

   public ByteArrayInputStream getByteArrayInputStream() {
      ByteArray var1 = this.data;
      return var1 != null ? var1.toByteArrayInputStream() : null;
   }
}
