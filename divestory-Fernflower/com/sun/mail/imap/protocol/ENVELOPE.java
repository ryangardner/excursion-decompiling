package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import java.util.Date;
import java.util.Vector;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MailDateFormat;

public class ENVELOPE implements Item {
   private static MailDateFormat mailDateFormat = new MailDateFormat();
   static final char[] name = new char[]{'E', 'N', 'V', 'E', 'L', 'O', 'P', 'E'};
   public InternetAddress[] bcc;
   public InternetAddress[] cc;
   public Date date = null;
   public InternetAddress[] from;
   public String inReplyTo;
   public String messageId;
   public int msgno;
   public InternetAddress[] replyTo;
   public InternetAddress[] sender;
   public String subject;
   public InternetAddress[] to;

   public ENVELOPE(FetchResponse var1) throws ParsingException {
      this.msgno = var1.getNumber();
      var1.skipSpaces();
      if (var1.readByte() == 40) {
         String var2 = var1.readString();
         if (var2 != null) {
            try {
               this.date = mailDateFormat.parse(var2);
            } catch (Exception var3) {
            }
         }

         this.subject = var1.readString();
         this.from = this.parseAddressList(var1);
         this.sender = this.parseAddressList(var1);
         this.replyTo = this.parseAddressList(var1);
         this.to = this.parseAddressList(var1);
         this.cc = this.parseAddressList(var1);
         this.bcc = this.parseAddressList(var1);
         this.inReplyTo = var1.readString();
         this.messageId = var1.readString();
         if (var1.readByte() != 41) {
            throw new ParsingException("ENVELOPE parse error");
         }
      } else {
         throw new ParsingException("ENVELOPE parse error");
      }
   }

   private InternetAddress[] parseAddressList(Response var1) throws ParsingException {
      var1.skipSpaces();
      byte var2 = var1.readByte();
      if (var2 != 40) {
         if (var2 != 78 && var2 != 110) {
            throw new ParsingException("ADDRESS parse error");
         } else {
            var1.skip(2);
            return null;
         }
      } else {
         Vector var3 = new Vector();

         do {
            IMAPAddress var4 = new IMAPAddress(var1);
            if (!var4.isEndOfGroup()) {
               var3.addElement(var4);
            }
         } while(var1.peekByte() != 41);

         var1.skip(1);
         InternetAddress[] var5 = new InternetAddress[var3.size()];
         var3.copyInto(var5);
         return var5;
      }
   }
}
