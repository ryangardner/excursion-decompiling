package com.sun.mail.imap;

import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import java.util.Vector;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.MultipartDataSource;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;

public class IMAPMultipartDataSource extends MimePartDataSource implements MultipartDataSource {
   private Vector parts;

   protected IMAPMultipartDataSource(MimePart var1, BODYSTRUCTURE[] var2, String var3, IMAPMessage var4) {
      super(var1);
      this.parts = new Vector(var2.length);

      for(int var5 = 0; var5 < var2.length; ++var5) {
         Vector var6 = this.parts;
         BODYSTRUCTURE var7 = var2[var5];
         String var8;
         if (var3 == null) {
            var8 = Integer.toString(var5 + 1);
         } else {
            StringBuilder var9 = new StringBuilder(String.valueOf(var3));
            var9.append(".");
            var9.append(Integer.toString(var5 + 1));
            var8 = var9.toString();
         }

         var6.addElement(new IMAPBodyPart(var7, var8, var4));
      }

   }

   public BodyPart getBodyPart(int var1) throws MessagingException {
      return (BodyPart)this.parts.elementAt(var1);
   }

   public int getCount() {
      return this.parts.size();
   }
}
