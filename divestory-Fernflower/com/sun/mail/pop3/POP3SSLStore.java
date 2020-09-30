package com.sun.mail.pop3;

import javax.mail.Session;
import javax.mail.URLName;

public class POP3SSLStore extends POP3Store {
   public POP3SSLStore(Session var1, URLName var2) {
      super(var1, var2, "pop3s", 995, true);
   }
}
