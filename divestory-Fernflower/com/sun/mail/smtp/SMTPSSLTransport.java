package com.sun.mail.smtp;

import javax.mail.Session;
import javax.mail.URLName;

public class SMTPSSLTransport extends SMTPTransport {
   public SMTPSSLTransport(Session var1, URLName var2) {
      super(var1, var2, "smtps", 465, true);
   }
}
