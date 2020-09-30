package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.mail.internet.MailDateFormat;

public class INTERNALDATE implements Item {
   private static SimpleDateFormat df;
   private static MailDateFormat mailDateFormat = new MailDateFormat();
   static final char[] name = new char[]{'I', 'N', 'T', 'E', 'R', 'N', 'A', 'L', 'D', 'A', 'T', 'E'};
   protected Date date;
   public int msgno;

   static {
      df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss ", Locale.US);
   }

   public INTERNALDATE(FetchResponse var1) throws ParsingException {
      this.msgno = var1.getNumber();
      var1.skipSpaces();
      String var3 = var1.readString();
      if (var3 != null) {
         try {
            this.date = mailDateFormat.parse(var3);
         } catch (ParseException var2) {
            throw new ParsingException("INTERNALDATE parse error");
         }
      } else {
         throw new ParsingException("INTERNALDATE is NIL");
      }
   }

   public static String format(Date param0) {
      // $FF: Couldn't be decompiled
   }

   public Date getDate() {
      return this.date;
   }
}
