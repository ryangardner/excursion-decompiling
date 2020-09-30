package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;

public class UID implements Item {
   static final char[] name = new char[]{'U', 'I', 'D'};
   public int seqnum;
   public long uid;

   public UID(FetchResponse var1) throws ParsingException {
      this.seqnum = var1.getNumber();
      var1.skipSpaces();
      this.uid = var1.readLong();
   }
}
