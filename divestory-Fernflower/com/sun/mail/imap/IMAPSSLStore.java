package com.sun.mail.imap;

import javax.mail.Session;
import javax.mail.URLName;

public class IMAPSSLStore extends IMAPStore {
   public IMAPSSLStore(Session var1, URLName var2) {
      super(var1, var2, "imaps", 993, true);
   }
}
