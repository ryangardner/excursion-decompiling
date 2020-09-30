package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;

public class RFC822SIZE implements Item {
   static final char[] name = new char[]{'R', 'F', 'C', '8', '2', '2', '.', 'S', 'I', 'Z', 'E'};
   public int msgno;
   public int size;

   public RFC822SIZE(FetchResponse var1) throws ParsingException {
      this.msgno = var1.getNumber();
      var1.skipSpaces();
      this.size = var1.readNumber();
   }
}
